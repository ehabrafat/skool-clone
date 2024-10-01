package com.example.Skool.communities;

import com.example.Skool.auth.entities.UserCreator;
import com.example.Skool.auth.entities.UserPrincipal;
import com.example.Skool.auth.services.UserCreatorService;
import com.example.Skool.commentLikes.CommentLike;
import com.example.Skool.commentLikes.CommentLikeService;
import com.example.Skool.comments.Comment;
import com.example.Skool.comments.CommentService;
import com.example.Skool.comments.dtos.CreateCommentDto;
import com.example.Skool.common.exceptions.SkoolException;
import com.example.Skool.common.mappers.CommunityMapper;
import com.example.Skool.communities.dtos.*;
import com.example.Skool.communities.queries.GetCommunitiesQueryFilter;
import com.example.Skool.comments.types.CommentRepliesPage;
import com.example.Skool.comments.types.CommentReply;
import com.example.Skool.comments.types.CommentWithReplies;
import com.example.Skool.comments.types.CommentWithRepliesPage;
import com.example.Skool.communityCategories.CommunityCategory;
import com.example.Skool.communityCategories.CommunityCategoryRepository;
import com.example.Skool.communityQuestions.CommunityQuestion;
import com.example.Skool.communityQuestions.CommunityQuestionService;
import com.example.Skool.communityQuestions.dtos.CommunityQuestionDto;
import com.example.Skool.membersAnswers.MemberAnswer;
import com.example.Skool.membersAnswers.MemberAnswerService;
import com.example.Skool.memberships.Membership;
import com.example.Skool.memberships.MembershipService;
import com.example.Skool.memberships.MembershipStatus;
import com.example.Skool.postLikes.PostLike;
import com.example.Skool.postLikes.PostLikeService;
import com.example.Skool.posts.Post;
import com.example.Skool.posts.PostService;
import com.example.Skool.posts.dtos.CreatePostDto;
import com.example.Skool.storage.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityCategoryRepository communityCategoryRepository;
    private final StorageService storageService;
    private final UserCreatorService userCreatorService;
    private final MembershipService membershipService;
    private final CommunityQuestionService communityQuestionService;
    private final MemberAnswerService memberAnswerService;
    private final CommunityMapper communityMapper;
    private final PostService postService;
    private final CommentService commentService;
    private final PostLikeService postLikeService;
    private final CommentLikeService commentLikeService;



    public Page<CommentLike> getCommentLikes(int communityId, int postId, int commentId, Pageable pageable, Authentication authentication) {
        if(!isExists(communityId))
            throw new SkoolException("Community not found", this.getClass().toString(), HttpStatus.NOT_FOUND);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int userId = userPrincipal.getId();
        Membership membership = membershipService.getMembership(userId, communityId)
                .orElseThrow(() -> new SkoolException("Not a member of the community", this.getClass().toString(), HttpStatus.FORBIDDEN));
        if(membership.getStatus() != MembershipStatus.ACCEPTED)
            throw new SkoolException("FORBIDDEN", this.getClass().toString(), HttpStatus.FORBIDDEN);
        if(!postService.isExists(postId)){
            throw new SkoolException("Post not exists", this.getClass().toString(), HttpStatus.NOT_FOUND);
        }
        if(!commentService.isExists(commentId)){
            throw new SkoolException("Comment not exists", this.getClass().toString(), HttpStatus.NOT_FOUND);
        }
        return commentLikeService.getAll(commentId, pageable);
    }



    public CommentLike addCommentLike(int communityId, int postId, int commentId, Authentication authentication) {
        if(!isExists(communityId))
            throw new SkoolException("Community not found", this.getClass().toString(), HttpStatus.NOT_FOUND);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int userId = userPrincipal.getId();
        Membership membership = membershipService.getMembership(userId, communityId)
                .orElseThrow(() -> new SkoolException("Not a member of the community", this.getClass().toString(), HttpStatus.FORBIDDEN));
        if(membership.getStatus() != MembershipStatus.ACCEPTED)
            throw new SkoolException("FORBIDDEN", this.getClass().toString(), HttpStatus.FORBIDDEN);
        if(!postService.isExists(postId)){
            throw new SkoolException("Post not exists", this.getClass().toString(), HttpStatus.NOT_FOUND);
        }
        if(!commentService.isExists(commentId)){
            throw new SkoolException("Comment not exists", this.getClass().toString(), HttpStatus.NOT_FOUND);
        }
        return commentLikeService.save(userId, commentId);
    }



    public PostLike addPostLike(int communityId, int postId, Authentication authentication){
        if(!isExists(communityId))
            throw new SkoolException("Community not found", this.getClass().toString(), HttpStatus.NOT_FOUND);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int userId = userPrincipal.getId();
        Membership membership = membershipService.getMembership(userId, communityId)
                .orElseThrow(() -> new SkoolException("Not a member of the community", this.getClass().toString(), HttpStatus.FORBIDDEN));
        if(membership.getStatus() != MembershipStatus.ACCEPTED)
            throw new SkoolException("FORBIDDEN", this.getClass().toString(), HttpStatus.FORBIDDEN);
        if(!postService.isExists(postId)){
            throw new SkoolException("Post not exists", this.getClass().toString(), HttpStatus.NOT_FOUND);
        }
        return postLikeService.addPostLike(postId, userId);
    }

    public Comment addComment(int communityId, int postId, CreateCommentDto commentDto, Authentication authentication){
        if(!isExists(communityId))
            throw new SkoolException("Community not found", this.getClass().toString(), HttpStatus.NOT_FOUND);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int userId = userPrincipal.getId();
        Membership membership = membershipService.getMembership(userId, communityId)
                .orElseThrow(() -> new SkoolException("Not a member of the community", this.getClass().toString(), HttpStatus.FORBIDDEN));
        if(membership.getStatus() != MembershipStatus.ACCEPTED)
            throw new SkoolException("FORBIDDEN", this.getClass().toString(), HttpStatus.FORBIDDEN);

        if(!postService.isExists(postId)){
            throw new SkoolException("Post not exists", this.getClass().toString(), HttpStatus.NOT_FOUND);
        }
        if(commentDto.getReplyToCommentId() != null && !commentService.isExists(commentDto.getReplyToCommentId())){
            throw new SkoolException("Comment to reply to not found", this.getClass().toString(), HttpStatus.NOT_FOUND);
        }

        return commentService.save(userId, postId, commentDto);
    }

    public Page<PostLike> getPostsLikes(int communityId, int postId, Pageable pageable, Authentication authentication) {
        if(!isExists(communityId))
            throw new SkoolException("Community not found", this.getClass().toString(), HttpStatus.NOT_FOUND);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int userId = userPrincipal.getId();
        Membership membership = membershipService.getMembership(userId, communityId)
                .orElseThrow(() -> new SkoolException("Not a member of the community", this.getClass().toString(), HttpStatus.FORBIDDEN));
        if(membership.getStatus() != MembershipStatus.ACCEPTED)
            throw new SkoolException("FORBIDDEN", this.getClass().toString(), HttpStatus.FORBIDDEN);
        return postLikeService.getPostsLikes(postId, pageable);
    }

    public Page<Post> getPosts(int communityId, Pageable pageable, Authentication authentication){
        if(!isExists(communityId))
            throw new SkoolException("Community not found", this.getClass().toString(), HttpStatus.NOT_FOUND);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int userId = userPrincipal.getId();
        Membership membership = membershipService.getMembership(userId, communityId)
                .orElseThrow(() -> new SkoolException("Not a member of the community", this.getClass().toString(), HttpStatus.FORBIDDEN));
        if(membership.getStatus() != MembershipStatus.ACCEPTED)
            throw new SkoolException(String.format("Your membership status is %s so you can't see comments now!", membership.getStatus()), this.getClass().toString(), HttpStatus.FORBIDDEN);

        return postService.getPosts(communityId, pageable);
    }

    public CommentWithRepliesPage getComments(int communityId, int postId, Integer replyTo, Pageable pageable, Authentication authentication){
        if(!isExists(communityId))
            throw new SkoolException("Community not found", this.getClass().toString(), HttpStatus.NOT_FOUND);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int userId = userPrincipal.getId();
        Membership membership = membershipService.getMembership(userId, communityId)
                .orElseThrow(() -> new SkoolException("Not a member of the community", this.getClass().toString(), HttpStatus.FORBIDDEN));
        if(membership.getStatus() != MembershipStatus.ACCEPTED)
            throw new SkoolException("FORBIDDEN", this.getClass().toString(), HttpStatus.FORBIDDEN);
        Page<Comment> comments =  commentService.getComments(postId, replyTo, pageable);
        CommentWithRepliesPage commentsPage = new CommentWithRepliesPage();
        List<CommentWithReplies> content = new ArrayList<>();
        for(Comment comment : comments.getContent()){
            CommentWithReplies commentWithReplies = new CommentWithReplies();
            commentWithReplies.setId(comment.getId());
            commentWithReplies.setContent(comment.getContent());
            commentWithReplies.setEdited(comment.isEdited());
            commentWithReplies.setLikes(commentLikeService.getLikesCount(comment.getId()));
            Page<Comment> replies = commentService.getComments(postId, comment.getId(), PageRequest.of(0, 2));
            CommentRepliesPage repliesPage = new CommentRepliesPage();
            List<CommentReply> repliesList = new ArrayList<>();
            for(Comment reply : replies.getContent()){
                CommentReply replyObj = new CommentReply();
                replyObj.setId(reply.getId());
                replyObj.setContent(reply.getContent());
                replyObj.setEdited(reply.isEdited());
                replyObj.setLikes(commentLikeService.getLikesCount(reply.getId()));
                replyObj.setHasMoreRepliesCount(commentService.getRepliesCount(postId, reply.getId()));
                repliesList.add(replyObj);
            }
            repliesPage.setContent(repliesList);
            repliesPage.setHasPrevious(replies.hasPrevious());
            repliesPage.setHasNext(replies.hasNext());
            repliesPage.setNumberOfElements(replies.getNumberOfElements());
            repliesPage.setHasMoreCommentsCount(Math.max(0, replies.getTotalElements() - 2));
            commentWithReplies.setReplies(repliesPage);
            content.add(commentWithReplies);
        }
        commentsPage.setContent(content);
        commentsPage.setHasPrevious(comments.hasPrevious());
        commentsPage.setHasNext(comments.hasNext());
        commentsPage.setHasMoreCommentsCount(Math.max(0, comments.getTotalElements() - (long) (pageable.getPageNumber() + 1) * pageable.getPageSize()));
        return commentsPage;
    }



    public Post addPost(int communityId, CreatePostDto postDto, Authentication authentication){
        if(!isExists(communityId))
            throw new SkoolException("Community not found", this.getClass().toString(), HttpStatus.NOT_FOUND);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int userId = userPrincipal.getId();
        Membership membership = membershipService.getMembership(userId, communityId)
                .orElseThrow(() -> new SkoolException("Not a member of the community", this.getClass().toString(), HttpStatus.FORBIDDEN));
        if(membership.getStatus() != MembershipStatus.ACCEPTED)
            throw new SkoolException("FORBIDDEN", this.getClass().toString(), HttpStatus.FORBIDDEN);

        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .community(Community.builder().id(communityId).build())
                .creator(UserCreator.builder().id(userId).build())
                .build();
        return postService.save(post);
    }


    public CommunityResDto getCommunityRes(int communityId){
        Community community = getCommunity(communityId);
        CommunityResDto communityResDto = communityMapper.toCommunityResponseDto(community);
        communityResDto.setTotalNumberOfMembers(communityRepository.getCommunityMembersCount(communityId));
        return communityResDto;
    }

    public Community getCommunity(int communityId){
        return communityRepository.findById(communityId).orElseThrow(
                () -> new SkoolException("Community not found", this.getClass().toString(), HttpStatus.NOT_FOUND)
        );
    }

    public Page<Membership> getMembers(int communityId, Pageable pageable, Authentication authentication) {
        Community community = getCommunity(communityId);
        UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
        int authUserId = userPrincipal.getId();
        if(authUserId != community.getCreator().getId()) {
            throw new SkoolException("Forbidden", this.getClass().toString(), HttpStatus.FORBIDDEN);
        }
        return membershipService.getMembershipsByCommunityIdAndStatus(pageable, communityId, MembershipStatus.ACCEPTED);
    }



    public Membership acceptMember(int communityId, int memberId, Authentication authentication){
        Community community = getCommunity(communityId);
        UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
        int authUserId = userPrincipal.getId();
        if(authUserId != community.getCreator().getId()) {
            throw new SkoolException("Forbidden", this.getClass().toString(), HttpStatus.FORBIDDEN);
        }
        Membership membership = membershipService.getMembership(memberId, communityId)
                .orElseThrow(() -> new SkoolException("Membership not found", this.getClass().toString(), HttpStatus.NOT_FOUND));

        if(membership.getStatus() != MembershipStatus.PENDING)
            throw new SkoolException(String.format("Membership already %s", membership.getStatus().toString()), this.getClass().toString(), HttpStatus.FORBIDDEN);
        membership.setStatus(MembershipStatus.ACCEPTED);
        return membershipService.saveMembership(membership);
    }

    public Membership rejectMember(int communityId, int memberId, Authentication authentication){
        Community community = getCommunity(communityId);
        UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
        int authUserId = userPrincipal.getId();
        if(authUserId != community.getCreator().getId()) {
            throw new SkoolException("Forbidden", this.getClass().toString(), HttpStatus.FORBIDDEN);
        }
        Membership membership = membershipService.getMembership(memberId, communityId)
                .orElseThrow(() -> new SkoolException("Membership not found", this.getClass().toString(), HttpStatus.NOT_FOUND));

        if(membership.getStatus() != MembershipStatus.PENDING)
            throw new SkoolException(String.format("Membership already %s", membership.getStatus().toString()), this.getClass().toString(), HttpStatus.FORBIDDEN);
        membership.setStatus(MembershipStatus.REJECTED);
        return membershipService.saveMembership(membership);
    }

    public List<MemberAnswerResDto> getMembersAnswers(int communityId, int memberId, Authentication authentication) {
        Community community = getCommunity(communityId);
        UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
        int authUserId = userPrincipal.getId();
        if(authUserId != community.getCreator().getId()) {
            throw new SkoolException("Forbidden", this.getClass().toString(), HttpStatus.FORBIDDEN);
        }
        Optional<Membership> membership = this.membershipService.getMembership(memberId, communityId);
        if(membership.isEmpty())
            throw new SkoolException("This member didn't join the community yet", this.getClass().toString(), HttpStatus.BAD_REQUEST);
        List<CommunityQuestion> questions = communityQuestionService.getCommunityQuestions(communityId);
        List<MemberAnswerResDto> result = new ArrayList<>();
        for(CommunityQuestion question : questions) {
           MemberAnswer memberAnswer = memberAnswerService.getMemberAnswer(memberId, question.getId())
                     .orElseThrow(() -> new SkoolException("Member didn't provide enough answers to the community questions", this.getClass().toString(), HttpStatus.BAD_REQUEST));
           result.add(new MemberAnswerResDto(question.getId(), question.getQuestionText(), memberAnswer.getAnswerText()));
        }
        return result;
    }

    public void joinCommunity(int communityId, JoinCommunityDto joinCommunityDto, Authentication authentication) {
        if(!isExists(communityId))
               throw new SkoolException("Community not found", this.getClass().toString(), HttpStatus.NOT_FOUND);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        int userId = userPrincipal.getId();
        Optional<Membership> membership = membershipService.getMembership(userId, communityId);
        if(membership.isPresent()) throw new SkoolException(String.format("Your request to join this community is now %s", membership.get().getStatus().toString().toLowerCase()), this.getClass().toString(), HttpStatus.CONFLICT);
        List<CommunityQuestion> questions = communityQuestionService.getCommunityQuestions(communityId);
        if(!questions.isEmpty()) {
            if (joinCommunityDto.getAnswers() == null || joinCommunityDto.getAnswers().isEmpty())
                throw new SkoolException("You didn't provide answers to the community questions", this.getClass().toString(), HttpStatus.NOT_FOUND);
            Map<Integer, String> answers = new HashMap<>();
            joinCommunityDto.getAnswers().forEach(answer -> answers.put(answer.getQuestionId(), answer.getAnswer()));
            List<QuestionAnswer> validAnswers = new ArrayList<>();
            for (CommunityQuestion question : questions) {
                if (!answers.containsKey(question.getId()) || answers.get(question.getId()) == null) {
                    throw new SkoolException(String.format("You didn't provide an answer to this question '%s'", question.getQuestionText()),
                            this.getClass().toString(), HttpStatus.BAD_REQUEST);
                }
                validAnswers.add(new QuestionAnswer(question.getId(), answers.get(question.getId())));
            }
            validAnswers.forEach(answer -> memberAnswerService.createMemberAnswer(userId, answer));
        }
        membershipService.createMembership(userId, communityId, MembershipStatus.PENDING);
    }

    public Optional<Community> getCommunityById(int id) {
        return this.communityRepository.findById(id);
    }

    public boolean isExists(int communityId) {
        return this.communityRepository.existsById(communityId);
    }

    public Page<Community> getAllCommunities(GetCommunitiesQueryFilter queryFilter) {
        Pageable pageable = PageRequest.of(queryFilter.getPageNum(), queryFilter.getPageSize());
        return this.communityRepository.findAllByFilter(
                queryFilter.getType() != null ? CommunityVisibility.valueOf(queryFilter.getType().toUpperCase()) : null,
                queryFilter.getPrice() != null ? queryFilter.getPrice().toUpperCase() : null,
                queryFilter.getCategoryId(),
                pageable);
    }




    public Community createCommunity(CreateCommunityDto communityDto, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
        UserCreator user = userCreatorService.findById(userPrincipal.getId()).get();
        CommunityCategory communityCategory = communityCategoryRepository.findById(communityDto.getCategoryId())
                .orElseThrow(() -> new SkoolException("Category not found", this.getClass().toString(), HttpStatus.NOT_FOUND));
        if(!communityDto.getQuestions().isEmpty()){
            for(CommunityQuestionDto questionDto : communityDto.getQuestions()) {
                if(questionDto.getQuestion() == null || questionDto.getQuestion().isEmpty())
                    throw new SkoolException("Question cannot be empty", this.getClass().toString(), HttpStatus.BAD_REQUEST);
            }
        }
        Community community = Community.builder()
                .title(communityDto.getTitle())
                .description(communityDto.getDescription())
                .imgUrl(communityDto.getImgUrl())
                .visibility(CommunityVisibility.valueOf(communityDto.getVisibility()))
                .costPerMonth(communityDto.getCostPerMonth())
                .category(communityCategory)
                .social(String.join(",", communityDto.getSocial()))
                .creator(user)
                .build();
        community = this.communityRepository.save(community);
        List<CommunityQuestion> questions = addCommunityQuestions(community, communityDto.getQuestions());
        community.setCommunityQuestions(questions);
        return community;
    }

    public Community updateCommunity(UpdateCommunityDto communityDto) {
        if(!this.communityRepository.existsById(communityDto.getId()))
            throw new SkoolException("Community not exist", this.getClass().toString(), HttpStatus.NOT_FOUND);

       CommunityCategory communityCategory = communityCategoryRepository.findById(communityDto.getCategoryId()).orElseThrow(
               () -> new SkoolException("Community category not exist", this.getClass().toString(), HttpStatus.NOT_FOUND)
       );

        Community community = Community.builder()
                .id(communityDto.getId())
                .title(communityDto.getTitle())
                .description(communityDto.getDescription())
                .visibility(CommunityVisibility.valueOf(communityDto.getVisibility()))
                .costPerMonth(communityDto.getCostPerMonth())
                .category(communityCategory)
                .social(String.join(",", communityDto.getSocial()))
                .build();

        return this.communityRepository.save(community);
    }


    private List<CommunityQuestion> addCommunityQuestions(Community community, List<CommunityQuestionDto> questions){
        List<CommunityQuestion> communityQuestions = new ArrayList<>();
        for(CommunityQuestionDto questionDto : questions) {
            communityQuestions.add(communityQuestionService.addCommunityQuestion(community.getId(), questionDto));
        }
        return communityQuestions;
    }
}
