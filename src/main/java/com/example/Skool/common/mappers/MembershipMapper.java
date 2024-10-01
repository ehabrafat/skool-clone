package com.example.Skool.common.mappers;


import com.example.Skool.communities.dtos.MembershipResDto;
import com.example.Skool.memberships.Membership;
import com.example.Skool.memberships.dtos.MemberResDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class MembershipMapper {


    public MembershipResDto toResDto(Membership membership) {
        return MembershipResDto.builder().id(membership.getId()).status(membership.getStatus()).build();
    }

    public MemberResDto toMemberResDto(Membership membership) {
        return MemberResDto.builder()
                .id(membership.getMember().getId())
                .name(membership.getMember().getUsername())
                .status(membership.getStatus())
                .build();
    }
}
