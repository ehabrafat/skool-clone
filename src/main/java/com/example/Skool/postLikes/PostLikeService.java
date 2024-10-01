package com.example.Skool.postLikes;

import com.example.Skool.auth.entities.UserCreator;
import com.example.Skool.posts.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;

    public Page<PostLike> getPostsLikes(int postId, Pageable pageable) {
        return postLikeRepository.findAllByPostId(postId, pageable);
    }

    public PostLike addPostLike(int postId, int userId) {
        Post post = new Post();
        post.setId(postId);
        UserCreator creator = new UserCreator();
        creator.setId(userId);
        PostLike postLike = new PostLike();
        postLike.setPost(post);
        postLike.setCreator(creator);
        return postLikeRepository.save(postLike);
    }
}
