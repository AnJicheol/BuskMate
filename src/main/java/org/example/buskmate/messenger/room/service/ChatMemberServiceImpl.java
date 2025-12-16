package org.example.buskmate.messenger.room.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.messenger.room.domain.ChatRoom;
import org.example.buskmate.messenger.room.domain.ChatRoomMember;
import org.example.buskmate.messenger.room.domain.ChatRoomRole;
import org.example.buskmate.messenger.room.dto.CustomUser;
import org.example.buskmate.messenger.room.repository.ChatMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


/**
 * {@link ChatMemberService} 구현체.
 *
 * <p>채팅방 멤버십의 생성/초대/강퇴/검증을 담당한다.</p>
 *
 * <h2>권한/정책</h2>
 * <ul>
 *   <li>초대/강퇴/전체 강퇴(정리)는 OWNER만 가능</li>
 *   <li>자기 자신에 대한 초대/추방은 금지</li>
 *   <li>중복 초대는 멱등 처리(이미 멤버면 아무 것도 하지 않고 종료)</li>
 *   <li>추방 대상이 이미 나간 상태면 멱등 처리(아무 것도 하지 않고 종료)</li>
 * </ul>
 *
 * <h2>예외</h2>
 * <p>현재 구현은 다음 예외를 사용한다.</p>
 * <ul>
 *   <li>{@link IllegalArgumentException}: 잘못된 요청(자기 자신 초대/추방, 대상 없음 등)</li>
 *   <li>{@link SecurityException}: 권한/멤버십 위반(OWNER 아님, 방 멤버가 아님, 나간 멤버 등)</li>
 * </ul>
 *
 * <p><b>트랜잭션:</b> 모든 변경 작업은 트랜잭션으로 수행하며, 검증 메서드는 readOnly로 수행한다.</p>
 */
@Service
@RequiredArgsConstructor
public class ChatMemberServiceImpl implements ChatMemberService {
    private final ChatMemberRepository chatMemberRepository;

    /**
     * {@inheritDoc}
     *
     * <p>생성자를 OWNER 역할로 채팅방에 가입시킨다.</p>
     */
    @Transactional
    public void addMemberToChatRoom(ChatRoom chatRoom, CustomUser owner) {
        ChatRoomMember chatRoomMember = new ChatRoomMember(chatRoom, owner.getUserId(), ChatRoomRole.OWNER);
        chatMemberRepository.save(chatRoomMember);
    }

    /**
     * {@inheritDoc}
     *
     * <p>요청자(OWNER)가 초대 대상 사용자를 멤버로 추가한다.</p>
     * <ul>
     *   <li>자기 자신은 초대할 수 없다.</li>
     *   <li>요청자가 방 멤버가 아니면 실패한다.</li>
     *   <li>요청자가 OWNER가 아니면 실패한다.</li>
     *   <li>대상이 이미 멤버이면 멱등 처리로 종료한다.</li>
     * </ul>
     *
     * @param room           대상 채팅방
     * @param inviteeUserId  초대할 사용자 식별자
     * @param owner          요청자(인증 사용자)
     */
    @Override
    @Transactional
    public void inviteMember(ChatRoom room, String inviteeUserId, CustomUser owner) {
        String inviterUserId = owner.getUserId();

        if (inviterUserId.equals(inviteeUserId)) {
            throw new IllegalArgumentException("자기 자신은 초대할 수 없습니다.");
        }

        ChatRoomMember inviter = chatMemberRepository.findByRoomAndUserId(room, inviterUserId)
                .orElseThrow(() -> new SecurityException("초대자가 해당 채팅방의 멤버가 아닙니다."));

        if (inviter.getRole() != ChatRoomRole.OWNER) {
            throw new SecurityException("방장만 멤버를 초대할 수 있습니다.");
        }

        if (chatMemberRepository.existsByRoomAndUserId(room, inviteeUserId)) {
            return;
            // throw new IllegalStateException("이미 채팅방에 참여 중인 멤버입니다.");
        }

        chatMemberRepository.save(new ChatRoomMember(room, inviteeUserId, ChatRoomRole.MEMBER));
    }

    /**
     * {@inheritDoc}
     *
     * <p>요청자(OWNER)가 특정 멤버를 추방한다.</p>
     * <ul>
     *   <li>자기 자신은 추방할 수 없다.</li>
     *   <li>요청자가 방 멤버가 아니면 실패한다.</li>
     *   <li>요청자가 OWNER가 아니면 실패한다.</li>
     *   <li>추방 대상이 없으면 실패한다.</li>
     *   <li>추방 대상이 이미 나간 상태면 멱등 처리로 종료한다.</li>
     * </ul>
     *
     * <p>추방은 물리 삭제 대신 {@link ChatRoomMember#leave()}를 호출하여
     * {@code leftAt}을 설정하는 방식으로 수행한다.</p>
     */
    @Override
    @Transactional
    public void kickMember(ChatRoom room, String memberId, CustomUser owner) {
        String kickerId = owner.getUserId();

        if (kickerId.equals(memberId)) {
            throw new IllegalArgumentException("자기 자신은 추방할 수 없습니다.");
        }

        ChatRoomMember kicker = chatMemberRepository.findByRoomAndUserId(room, kickerId)
                .orElseThrow(() -> new SecurityException("요청자가 해당 채팅방의 멤버가 아닙니다."));

        if (kicker.getRole() != ChatRoomRole.OWNER) {
            throw new SecurityException("방장만 멤버를 추방할 수 있습니다.");
        }

        ChatRoomMember target = chatMemberRepository.findByRoomAndUserId(room, memberId)
                .orElseThrow(() -> new IllegalArgumentException("추방 대상 멤버가 존재하지 않습니다."));

        if (!target.isActive()) {
            return;
            // throw new IllegalStateException("이미 채팅방을 나간 멤버입니다.");
        }

        target.leave(); // leftAt = now()
    }

    /**
     * {@inheritDoc}
     *
     * <p>요청자(OWNER) 권한을 확인한 뒤, 방의 활성 멤버 전체를 일괄 leave 처리한다.</p>
     * <p>구현은 {@link ChatMemberRepository#leaveAllActiveByRoom(ChatRoom, LocalDateTime)}를 사용한다.</p>
     */
    @Override
    @Transactional
    public void kickAllMembersByOwner(ChatRoom room, CustomUser owner) {

        ChatRoomMember requester = chatMemberRepository.findByRoomAndUserId(room, owner.getUserId())
                .orElseThrow(() -> new SecurityException("요청자가 해당 채팅방의 멤버가 아닙니다."));

        if (requester.getRole() != ChatRoomRole.OWNER) {
            throw new SecurityException("방장만 채팅방을 삭제할 수 있습니다.");
        }
        chatMemberRepository.leaveAllActiveByRoom(room, LocalDateTime.now());
    }


    /**
     * {@inheritDoc}
     *
     * <p>사용자가 해당 방의 멤버인지 확인하고, 나간 멤버(leftAt != null)는 실패 처리한다.</p>
     */
    @Override
    @Transactional(readOnly = true)
    public void validMember(ChatRoom room, String userId) {
        ChatRoomMember member = chatMemberRepository.findByRoomAndUserId(room, userId)
                .orElseThrow(() -> new SecurityException("해당 채팅방의 멤버가 아닙니다."));

        if (!member.isActive()) {
            throw new SecurityException("채팅방을 나간 멤버는 조회할 수 없습니다.");
        }
    }
}
