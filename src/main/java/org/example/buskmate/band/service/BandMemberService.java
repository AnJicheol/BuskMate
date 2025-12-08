package org.example.buskmate.band.service;

import org.example.buskmate.band.domain.Band;
import org.example.buskmate.band.dto.bandmember.BandMemberListItemResponse;

import java.util.List;

public interface BandMemberService {

    List<BandMemberListItemResponse> getMembers(String bandId);

    void inviteMember(String bandId, String leaderId, String targetUserId);

    void acceptInvitation(String bandId, String userId);

    void rejectInvitation(String bandId, String userId);

    void addMemberAccepted(Band band, String userId);

    void kickMember(String bandId, String leaderId, String targetUserId);
}
