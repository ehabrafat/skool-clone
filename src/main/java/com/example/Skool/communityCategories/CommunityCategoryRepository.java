package com.example.Skool.communityCategories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityCategoryRepository extends JpaRepository<CommunityCategory, Integer> {

    Optional<CommunityCategory> findByName(String name);

    @Query("SELECT c FROM CommunityCategory c")
    Page<CommunityCategoryProjection> findNames(Pageable pageable);
}
