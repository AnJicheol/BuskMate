package org.example.buskmate.messenger.room.service;

import lombok.RequiredArgsConstructor;
import org.example.buskmate.messenger.room.dto.MyChatRoomResponse;
import org.example.buskmate.messenger.room.repository.ChatMemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 채팅방 조회 전용 Reader.
 *
 * <p>“내 채팅방 목록”과 같이 읽기 전용 조회 유스케이스를 캡슐화한다.</p>
 *
 * <p>현재 구현은 {@link ChatMemberRepository#findMyRoomsOrderByLastMessage(String)}를 위임하여
 * 사용자가 참여 중인 채팅방 목록을 조회한다.</p>
 *
 * <h2>트랜잭션</h2>
 * <p>읽기 전용 트랜잭션({@code readOnly = true})으로 수행한다.</p>
 */
@Component
@RequiredArgsConstructor
public class ChatRoomReader {

    private final ChatMemberRepository chatMemberRepository;


    /**
     * 사용자가 참여 중인 채팅방 목록을 조회한다.
     *
     * <p>정렬 기준(마지막 메시지 우선 등)은 repository 쿼리 정책을 따른다.</p>
     *
     * @param userId 사용자 식별자
     * @return 내 채팅방 목록 DTO
     */
    @Transactional(readOnly = true)
    public List<MyChatRoomResponse> findMyRooms(String userId) {
        return chatMemberRepository.findMyRoomsOrderByLastMessage(userId);
    }
}
