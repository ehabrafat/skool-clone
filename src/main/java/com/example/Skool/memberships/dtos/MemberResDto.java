package com.example.Skool.memberships.dtos;

import com.example.Skool.memberships.MembershipStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResDto {
    private int id;
    private String name;
    private MembershipStatus status;
}
