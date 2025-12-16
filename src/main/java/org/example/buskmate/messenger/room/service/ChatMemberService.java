package org.example.buskmate.messenger.room.service;

import org.example.buskmate.messenger.room.domain.ChatRoom;
import org.example.buskmate.messenger.room.dto.CustomUser;

/**
 * 채팅방 멤버십 도메인 서비스 인터페이스.
 *
 * <p>채팅방 멤버의 추가/초대/강퇴/검증 등 “멤버십 관리” 책임을 담당한다.</p>
 *
 * <h2>주요 책임</h2>
 * <ul>
 *   <li>채팅방 생성자(OWNER) 가입 처리</li>
 *   <li>멤버 초대(추가) 처리</li>
 *   <li>멤버 강퇴 처리</li>
 *   <li>방 종료/삭제 시 전체 멤버 정리</li>
 *   <li>멤버십 검증(메시지 전송/구독 등에서 사용)</li>
 * </ul>
 *
 * <p><b>권한 정책:</b> 실제로 OWNER만 가능한 작업(초대/강퇴/전체 강퇴 등)은
 * 구현체에서 {@link CustomUser} 또는 inviterUserId 기반으로 검증해야 한다.</p>
 */
public interface ChatMemberService {

    /**
     * 채팅방 생성자(OWNER)를 해당 채팅방 멤버로 추가한다.
     *
     * <p>일반적으로 채팅방 생성 직후 호출되며, 생성자를 OWNER 역할로 가입시킨다.</p>
     *
     * @param chatRoom 대상 채팅방
     * @param owner    생성자(인증 사용자)
     */
    void addMemberToChatRoom(ChatRoom chatRoom, CustomUser owner);

    /**
     * 채팅방에 새 멤버를 초대(추가)한다.
     *
     * <p>초대 권한이 있는 사용자(보통 OWNER)만 수행할 수 있다.</p>
     *
     * @param room          대상 채팅방
     * @param inviterUserId 초대 대상 사용자 식별자(※ 메서드명 기준으로는 “초대할 사용자”로 해석됨)
     * @param owner         요청자(인증 사용자, 보통 OWNER)
     */
    void inviteMember(ChatRoom room, String inviterUserId, CustomUser owner);

    /**
     * 채팅방에서 특정 멤버를 강퇴한다.
     *
     * <p>강퇴 권한이 있는 사용자(보통 OWNER)만 수행할 수 있다.</p>
     *
     * @param room     대상 채팅방
     * @param memberId 강퇴할 사용자 식별자
     * @param owner    요청자(인증 사용자, 보통 OWNER)
     */
    void kickMember(ChatRoom room, String memberId, CustomUser owner);

    /**
     * OWNER 권한으로 채팅방의 모든 멤버를 정리(일괄 강퇴/일괄 leave 처리)한다.
     *
     * <p>채팅방 삭제/종료 시점에 사용될 수 있다. 구현 정책에 따라
     * 실제 삭제 대신 {@code leftAt} 갱신과 같은 논리적 방식으로 처리될 수 있다.</p>
     *
     * @param room  대상 채팅방
     * @param owner 요청자(인증 사용자, 보통 OWNER)
     */
    void kickAllMembersByOwner(ChatRoom room, CustomUser owner);

    /**
     * 특정 사용자가 채팅방의 “활성 멤버”인지 검증한다.
     *
     * <p>일반적으로 다음 상황에서 호출된다.</p>
     * <ul>
     *   <li>메시지 전송</li>
     *   <li>채팅방 구독(수신) 허용 여부 판단</li>
     * </ul>
     *
     * <p>검증 실패 시 예외를 던지는 “검증 메서드” 형태로 설계된다.</p>
     *
     * @param room   대상 채팅방
     * @param userId 사용자 식별자
     */
    void validMember(ChatRoom room, String userId);
}
