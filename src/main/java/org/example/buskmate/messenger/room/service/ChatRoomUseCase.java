package org.example.buskmate.messenger.room.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.messenger.room.domain.ChatRoom;
import org.example.buskmate.messenger.room.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 채팅방 유스케이스(애플리케이션 서비스).
 *
 * <p>채팅방 생성/멤버 초대/강퇴/삭제 및 “내 채팅방 목록 조회” 시나리오를 오케스트레이션한다.</p>
 *
 * <h2>역할 분리</h2>
 * <ul>
 *   <li>{@link ChatRoomService}: 채팅방 생성/조회/삭제(soft delete) 등 “방” 자체의 상태 관리</li>
 *   <li>{@link ChatMemberService}: 멤버 추가/초대/강퇴/검증 등 “멤버십” 관리</li>
 *   <li>{@link ChatRoomReader}: 읽기 전용 조회(내 방 목록) 담당</li>
 * </ul>
 *
 * <h2>트랜잭션</h2>
 * <ul>
 *   <li>생성/초대/강퇴/삭제: {@code @Transactional}</li>
 *   <li>조회: {@code @Transactional(readOnly = true)}</li>
 * </ul>
 *
 * <h2>삭제 정책</h2>
 * <p>{@link #deleteRoom(String, CustomUser)}는 다음 순서로 처리한다.</p>
 * <ol>
 *   <li>채팅방 조회 및 유효성 확인</li>
 *   <li>OWNER 권한 확인 후 활성 멤버 전체 leave 처리</li>
 *   <li>채팅방 상태를 DELETED로 전환(soft delete)</li>
 * </ol>
 *
 * <p><b>주의:</b> 권한/멤버십 검증 실패 및 존재하지 않는 방 등의 예외는 하위 서비스에서 발생할 수 있다.</p>
 */
@Service
@RequiredArgsConstructor
public class ChatRoomUseCase {
    private final ChatRoomService chatRoomService;
    private final ChatMemberService chatMemberService;
    private final ChatRoomReader chatRoomReader;

    /**
     * 그룹 채팅방을 생성하고, 생성자를 OWNER로 멤버 등록한다.
     *
     * @param request 채팅방 생성 요청
     * @param owner   생성자(인증 사용자)
     */
    @Transactional
    public void createGroupChatRoom(RoomCreateRequest request, CustomUser owner) {
        ChatRoom chatRoom = chatRoomService.createChatRoom(request.getRoomTitle());
        chatMemberService.addMemberToChatRoom(chatRoom, owner);
    }

    /**
     * 특정 채팅방에 멤버를 초대(추가)한다.
     *
     * <p>일반적으로 OWNER만 수행 가능하며, 권한 검증은 {@link ChatMemberService}에서 처리한다.</p>
     *
     * @param roomId  채팅방 식별자(외부 노출용)
     * @param request 멤버 초대 요청
     * @param owner   요청자(인증 사용자)
     */
    @Transactional
    public void inviteMember(String roomId, MemberInviteRequest request, CustomUser owner) {
        ChatRoom chatRoom = chatRoomService.getByRoomId(roomId);
        chatMemberService.inviteMember(chatRoom, request.memberId(), owner);
    }

    /**
     * 특정 채팅방에서 멤버를 강퇴한다.
     *
     * <p>일반적으로 OWNER만 수행 가능하며, 권한 검증은 {@link ChatMemberService}에서 처리한다.</p>
     *
     * @param roomId  채팅방 식별자(외부 노출용)
     * @param request 멤버 강퇴 요청
     * @param owner   요청자(인증 사용자)
     */
    @Transactional
    public void kickMember(String roomId, MemberKickRequest request, CustomUser owner) {
        ChatRoom room = chatRoomService.getByRoomId(roomId);
        chatMemberService.kickMember(room, request.memberId(), owner);
    }

    /**
     * 채팅방을 삭제(soft delete)한다.
     *
     * <p>처리 순서:</p>
     * <ol>
     *   <li>채팅방 조회</li>
     *   <li>OWNER 권한 확인 후 활성 멤버 전체 leave 처리</li>
     *   <li>채팅방 상태를 DELETED로 전환(soft delete)</li>
     * </ol>
     *
     * @param roomId 채팅방 식별자(외부 노출용)
     * @param owner  요청자(인증 사용자)
     */
    @Transactional
    public void deleteRoom(String roomId, CustomUser owner) {
        ChatRoom room = chatRoomService.getByRoomId(roomId);
        chatMemberService.kickAllMembersByOwner(room, owner);
        chatRoomService.softDelete(roomId);
    }

    /**
     * 로그인한 사용자가 참여 중인 채팅방 목록을 조회한다.
     *
     * @param user 로그인 사용자
     * @return 내 채팅방 목록(요약 DTO)
     */
    @Transactional(readOnly = true)
    public List<MyChatRoomResponse> getMyRooms(CustomUser user) {
        return chatRoomReader.findMyRooms(user.getUserId());
    }

}
