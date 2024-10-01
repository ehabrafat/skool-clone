package com.example.Skool.communities.dtos;

import com.example.Skool.memberships.MembershipStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MembershipResDto {
    private int id;
    private MembershipStatus status;
}
