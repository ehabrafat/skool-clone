package com.example.Skool.communities;

import com.example.Skool.auth.dtos.UserCreatorRes;
import com.example.Skool.commentLikes.CommentLike;
import com.example.Skool.commentLikes.dtos.CommentLikeResDto;
import com.example.Skool.comments.Comment;
import com.example.Skool.comments.dtos.CommentResDto;
import com.example.Skool.comments.dtos.CreateCommentDto;
import com.example.Skool.comments.types.CommentWithRepliesPage;
import com.example.Skool.common.dtos.PageResponseDto;
import com.example.Skool.common.mappers.*;
import com.example.Skool.communities.dtos.*;
import com.example.Skool.communities.queries.GetCommunitiesQueryFilter;
import com.example.Skool.communityQuestions.CommunityQuestion;
import com.example.Skool.communityQuestions.CommunityQuestionService;
import com.example.Skool.memberships.Membership;
import com.example.Skool.memberships.dtos.MemberResDto;
import com.example.Skool.postLikes.PostLike;
import com.example.Skool.postLikes.dtos.PostLikeResDto;
import com.example.Skool.posts.Post;
import com.example.Skool.posts.PostService;
import com.example.Skool.posts.dtos.CreatePostDto;
import com.example.Skool.posts.dtos.PostResDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("communities")
@Slf4j
public class CommunityController {
    private final CommunityService communityService;
    private final CommunityMapper communityMapper;
    private final HelperMapper helperMapper;
    private final CommunityQuestionService communityQuestionService;
    private final MembershipMapper membershipMapper;
    private final CommunityRepository communityRepository;
    private final PostService postService;
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;
    private final PostLikeMapper postLikeMapper;
    private final CommentLikeMapper commentLikeMapper;

    @GetMapping("{id}/posts/{postId}/comments/{commentId}/likes")
    public ResponseEntity<PageResponseDto<UserCreatorRes>> getCommentLikes(
            @PathVariable int id,
            @PathVariable int postId,
            @PathVariable int commentId,
            @RequestParam(name = "pageNum", defaultValue = "0", required = false) Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            Authentication authentication
    ){
        Page<CommentLike> postLikes = communityService.getCommentLikes(id, postId, commentId,PageRequest.of(pageNum, pageSize),authentication);
        List<UserCreatorRes> content = postLikes.getContent().stream().map(postLike -> userMapper.toUserResDto(postLike.getCreator())).toList();
        return ResponseEntity.ok(helperMapper.toPageResponseDto(postLikes, content));
    }

    @PostMapping("{id}/posts/{postId}/comments/{commentId}/likes")
    public ResponseEntity<CommentLikeResDto> addCommentLikes(
            @PathVariable int id,
            @PathVariable int postId,
            @PathVariable int commentId,
            Authentication authentication
    ){
        CommentLike commentLike = communityService.addCommentLike(id, postId, commentId, authentication);
        return ResponseEntity.ok(commentLikeMapper.toResDto(commentLike));
    }
    @PostMapping("{id}/posts/{postId}/likes")
    public ResponseEntity<PostLikeResDto> addPostLike(
            @PathVariable int id,
            @PathVariable int postId,
            Authentication authentication
    ){
        PostLike postLike = communityService.addPostLike(id, postId, authentication);
        return ResponseEntity.ok(postLikeMapper.toResDto(postLike));
    }

    @PostMapping("{id}/posts/{postId}/comments")
    public ResponseEntity<CommentResDto> addComment(
            @PathVariable int id,
            @PathVariable int postId,
            @Valid @RequestBody CreateCommentDto commentDto,
            Authentication authentication
            ){
        Comment comment = communityService.addComment(id, postId, commentDto,  authentication);
        return ResponseEntity.ok(commentMapper.toResDto(comment));
    }

    @GetMapping("{id}/posts/{postId}/likes")
    public ResponseEntity<PageResponseDto<UserCreatorRes>> getPostLikes(
            @PathVariable int id,
            @PathVariable int postId,
            @RequestParam(name = "pageNum", defaultValue = "0", required = false) Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            Authentication authentication

    ){
        Page<PostLike> postLikes = communityService.getPostsLikes(id, postId, PageRequest.of(pageNum, pageSize), authentication);
        List<UserCreatorRes> content = postLikes.getContent().stream().map(postLike -> userMapper.toUserResDto(postLike.getCreator())).toList();
        return ResponseEntity.ok(helperMapper.toPageResponseDto(postLikes, content));
    }


    @GetMapping("{id}/posts")
    public ResponseEntity<PageResponseDto<PostResDto>> getPosts(
            @PathVariable int id,
            @RequestParam(name = "pageNum", defaultValue = "0", required = false) Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            Authentication authentication

    ){
        Page<Post> posts =  communityService.getPosts(id, PageRequest.of(pageNum, pageSize), authentication);
        List<PostResDto> content = posts.getContent().stream().map(postMapper::toResDto).toList();
        return ResponseEntity.ok(helperMapper.toPageResponseDto(posts, content));
    }


    @GetMapping("{id}/posts/{postId}/comments")
    public ResponseEntity<CommentWithRepliesPage> getComments(
            @PathVariable int id,
            @PathVariable int postId,
            @RequestParam(name = "pageNum", defaultValue = "0", required = false) Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @RequestParam(name = "replyTo", required = false) Integer replyTo,
            Authentication authentication){
        CommentWithRepliesPage page = communityService.getComments(id, postId, replyTo, PageRequest.of(pageNum, pageSize),  authentication);
        return ResponseEntity.ok(page);
    }



    @PostMapping("{id}/posts")
    public ResponseEntity<PostResDto> addPost(
            @PathVariable int id,
            @Valid @RequestBody CreatePostDto postDto,
            Authentication authentication){
        Post post = communityService.addPost(id, postDto, authentication);
        return ResponseEntity.ok(postMapper.toResDto(post));
    }

    @GetMapping("{id}/members")
    public ResponseEntity<PageResponseDto<MemberResDto>> getMembers(
            @PathVariable int id,
            @RequestParam(name = "pageNum", defaultValue =  "0", required = false) int pageNum,
            @RequestParam(name = "pageSize", defaultValue =  "10", required = false) int pageSize,
            Authentication authentication
    ) {
        Page<Membership> memberships = communityService.getMembers(id, PageRequest.of(pageNum, pageSize), authentication);
        List<MemberResDto> content = memberships.getContent().stream().map(membershipMapper::toMemberResDto).toList();
        return ResponseEntity.ok(helperMapper.toPageResponseDto(memberships, content));
    }


    @PostMapping("{id}/accept-member")
    public ResponseEntity<MembershipResDto> acceptMember(
            @PathVariable int id,
            @RequestParam(name = "memberId") int memberId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(membershipMapper.toResDto(this.communityService.acceptMember(id, memberId, authentication)));
    }

    @PostMapping("{id}/reject-member")
    public ResponseEntity<MembershipResDto> rejectMember(
            @PathVariable int id,
            @RequestParam(name = "memberId") int memberId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(membershipMapper.toResDto(this.communityService.rejectMember(id, memberId, authentication)));
    }

    @GetMapping("{id}/members-answers")
    public ResponseEntity<List<MemberAnswerResDto>> getMembersAnswers(
            @PathVariable int id,
            @RequestParam(name = "memberId") int memberId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(communityService.getMembersAnswers(id, memberId, authentication));
    }
    @PostMapping("{id}/join")
    public ResponseEntity<?> joinCommunity(
            @PathVariable int id,
            @Valid @RequestBody JoinCommunityDto joinCommunityDto,
            Authentication authentication
    ) {
        this.communityService.joinCommunity(id, joinCommunityDto, authentication);
        return ResponseEntity.ok("Your request to join this group has been received");
    }


    @GetMapping("{id}/questions")
    public ResponseEntity<List<CommunityQuestion>> getCommunityQuestions(
            @PathVariable int id
    ) {
       return ResponseEntity.ok(communityQuestionService.getCommunityQuestions(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<CommunityResDto> getCommunity(
            @PathVariable int id
    ) {
        return new ResponseEntity<>(communityService.getCommunityRes(id), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<PageResponseDto<CommunityResDto>> getAllCommunities(
         @Valid @ModelAttribute GetCommunitiesQueryFilter queryFilter
    ) {
        Page<Community> communitiesPage =  this.communityService.getAllCommunities(queryFilter);
        List<CommunityResDto> content =  communitiesPage.getContent().stream().map(communityMapper::toCommunityResponseDto).toList();
        Page<CommunityResDto> CommunitiesResponsePage = new PageImpl<>(content, PageRequest.of(queryFilter.getPageNum(), queryFilter.getPageSize()), communitiesPage.getTotalElements());
        PageResponseDto<CommunityResDto> communitiesResponse = this.helperMapper.toPageResponseDto(CommunitiesResponsePage);
        return ResponseEntity.ok(communitiesResponse);
    }

    @PostMapping
    public ResponseEntity<CommunityResDto> createCommunity(@Valid @RequestBody CreateCommunityDto communityDto, Authentication authentication) {
        Community community = communityService.createCommunity(communityDto, authentication);
        return new ResponseEntity<>(communityMapper.toCommunityResponseDto(community), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CommunityResDto> updateCommunity(@Valid @RequestBody UpdateCommunityDto communityDto) {
        Community community = communityService.updateCommunity(communityDto);
        return new ResponseEntity<>(communityMapper.toCommunityResponseDto(community), HttpStatus.OK);
    }

}

