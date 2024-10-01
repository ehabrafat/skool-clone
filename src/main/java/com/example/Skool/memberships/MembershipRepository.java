package com.example.Skool.memberships;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Integer> {

    Optional<Membership> findByMemberIdAndCommunityId(int memberId, int communityId);

    List<Membership> findAllByCommunityId(int communityId);

    Page<Membership> findAllByCommunityIdAndStatus(Pageable pageable, int communityId, MembershipStatus status);
}
