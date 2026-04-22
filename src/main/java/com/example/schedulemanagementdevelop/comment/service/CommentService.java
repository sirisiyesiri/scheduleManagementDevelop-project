package com.example.schedulemanagementdevelop.comment.service;

import com.example.schedulemanagementdevelop.ExceptionHandler.*;
import com.example.schedulemanagementdevelop.comment.dto.*;
import com.example.schedulemanagementdevelop.comment.entity.Comment;
import com.example.schedulemanagementdevelop.comment.repository.CommentRepository;
import com.example.schedulemanagementdevelop.schedule.dto.GetAllScheduleResponse;
import com.example.schedulemanagementdevelop.schedule.entity.Schedule;
import com.example.schedulemanagementdevelop.schedule.repository.ScheduleRepository;
import com.example.schedulemanagementdevelop.user.dto.SessionUser;
import com.example.schedulemanagementdevelop.user.entity.User;
import com.example.schedulemanagementdevelop.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateCommentResponse save(SessionUser sessionUser, Long scheduleId, @Valid CreateCommentRequest request) {
        if(sessionUser == null) {   // 로그인 상태 확인
            throw new AuthenticationRequiredException();
        }

        User user = userRepository.findById(sessionUser.getId()).orElseThrow(   // 댓글 작성자 유저 객체 찾기
                NotExistUser::new   // Comment entity에 User 객체를 넣기 위해서 찾는 것일 뿐, 이미 로그인된 상태 이므로 해당 예외는 일어나지 않음.
        );

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(    // 댓글 작성된 일정 객체 찾기
                NotExistSchedule::new
        );

        Comment comment = new Comment(
                request.getContent(),   // 댓글 내용
                user,   // 댓글 작성자 유저
                schedule    // 댓글 작성된 일정
        );

        Comment savedComment = commentRepository.save(comment);

        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getUser().getUserName(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public GetOneCommentResponse findOne(SessionUser sessionUser, Long commentId) {
        if(sessionUser == null) {   // 로그인 상태 확인
            throw new AuthenticationRequiredException();
        }

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                NotExistComment::new
        );

        return new GetOneCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getUserName(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetAllCommentResponse> findAll(SessionUser sessionUser, Long scheduleId) {
        if(sessionUser == null) {   // 로그인 상태 확인
            throw new AuthenticationRequiredException();
        }

        boolean scheduleExistence = scheduleRepository.existsById(scheduleId);
        if(!scheduleExistence) {    // 일정 존재 유무 확인
            throw new NotExistSchedule();
        }

        List<Comment> comments = commentRepository.findBySchedule_Id(scheduleId);

        return comments.stream()
                .map(comment -> new GetAllCommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUser().getUserName(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                )).toList();
    }

    @Transactional
    public ModifyCommentResponse modify(SessionUser sessionUser, Long commentId, @Valid ModifyCommentRequest request) {
        if(sessionUser == null) {   // 로그인 상태 확인
            throw new AuthenticationRequiredException();
        }

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                NotExistComment::new
        );

        if(!sessionUser.getId().equals(comment.getUser().getId())) {    // 자신이 작성한 댓글만 수정, 삭제 가능
            throw new ForbiddenException();
        }

        comment.modify(request.getContent());

        return new ModifyCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getModifiedAt()
        );
    }

    @Transactional
    public void delete(SessionUser sessionUser, Long commentId) {
        if(sessionUser == null) {   // 로그인 상태 확인
            throw new AuthenticationRequiredException();
        }

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                NotExistComment::new
        );

        if(!sessionUser.getId().equals(comment.getUser().getId())) {    // 자신이 작성한 댓글만 수정, 삭제 가능
            throw new ForbiddenException();
        }

        commentRepository.deleteById(commentId);
    }
}
