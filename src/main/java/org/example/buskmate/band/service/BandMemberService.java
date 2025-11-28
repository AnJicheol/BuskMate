package org.example.buskmate.band.service;

import org.example.buskmate.band.dto.BandMemberListItemResponse;

import java.util.List;

public interface BandMemberService {

    List<BandMemberListItemResponse> getMembers(String bandId);

}
