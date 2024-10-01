package com.example.Skool.communities;

import com.example.Skool.communities.dtos.CommunityResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer> {

    @Query("SELECT c FROM Community c where (:categoryId = 0 OR c.category.id = :categoryId) AND (:type is NULL or c.visibility = :type) AND (:price is NULL OR (c.costPerMonth = 0 AND :price = 'FREE' OR c.costPerMonth > 0 AND :price = 'PAID') )")
    Page<Community> findAllByFilter(CommunityVisibility type, String price, int categoryId, Pageable pageable);


    @Query("SELECT COUNT(c.id) FROM Community c INNER JOIN Membership m ON c.id = m.community.id WHERE c.id = :id AND m.status = 'ACCEPTED'")
    int getCommunityMembersCount(int id);
}
