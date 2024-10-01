package com.example.Skool.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
     private final PostRepository postRepository;

    public boolean isExists(int postId){
        return postRepository.existsById(postId);
    }

    public Optional<Post> getById(int postId){
        return postRepository.findById(postId);
    }

    public Page<Post> getPosts(int communityId, Pageable pageable){
        return postRepository.findAllByCommunityId(communityId, pageable);
    }

    public Post save(Post post){
        return postRepository.save(post);
    }
}
