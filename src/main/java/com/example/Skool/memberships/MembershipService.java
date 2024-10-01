package com.example.Skool.memberships;

import com.example.Skool.auth.entities.UserCreator;
import com.example.Skool.communities.Community;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MembershipRepository membershipRepository;

    public Optional<Membership> getMembership(int memberId, int communityId) {
        return membershipRepository.findByMemberIdAndCommunityId(memberId, communityId);
    }

    public Membership createMembership(int memberId, int communityId, MembershipStatus status) {
        Community community = new Community();
        community.setId(communityId);
        UserCreator member = new UserCreator();
        member.setId(memberId);
        Membership membership = new Membership();
        membership.setCommunity(community);
        membership.setMember(member);
        membership.setStatus(status);
        return membershipRepository.save(membership);
    }

    public Membership saveMembership(Membership membership) {
        return membershipRepository.save(membership);
    }

    public List<Membership> getMembershipsByCommunityId(int communityId) {
        return membershipRepository.findAllByCommunityId(communityId);
    }

    public Page<Membership> getMembershipsByCommunityIdAndStatus(Pageable pageable, int communityId, MembershipStatus status) {
        return membershipRepository.findAllByCommunityIdAndStatus(pageable, communityId, status);
    }

}
