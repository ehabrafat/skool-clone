package com.example.Skool.communityCategories;

import com.example.Skool.common.exceptions.SkoolException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityCategoryService {
    private final CommunityCategoryRepository communityCategoryRepository;

    // for testing purpose
    public CommunityCategory createCategory(String name) {
        Optional<CommunityCategory> oldCommunityCategory = this.communityCategoryRepository.findByName(name);
        if(oldCommunityCategory.isPresent()) throw new SkoolException("Category already exists", this.getClass().toString(), HttpStatus.BAD_REQUEST);
        CommunityCategory communityCategory = CommunityCategory.builder().name(name).build();
        return this.communityCategoryRepository.save(communityCategory);
    }
    public Page<CommunityCategoryProjection> getAllCategories(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return this.communityCategoryRepository.findNames(pageable);
    }
}
