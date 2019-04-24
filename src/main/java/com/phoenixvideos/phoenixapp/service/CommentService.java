package com.phoenixvideos.phoenixapp.service;

import com.amazonaws.services.apigateway.model.Op;
import com.phoenixvideos.phoenixapp.model.Comment;
import com.phoenixvideos.phoenixapp.model.User;
import com.phoenixvideos.phoenixapp.model.Video;
import com.phoenixvideos.phoenixapp.repository.CommentRepository;
import com.phoenixvideos.phoenixapp.repository.UserRepository;
import com.phoenixvideos.phoenixapp.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          VideoRepository videoRepository,
                          UserRepository userRepository) {
        this.commentRepository =commentRepository;
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
    }

    public VideoRepository videoRepository;

    public UserRepository userRepository;

    public Comment create(Long user_id, Long video_id, Comment comment) {

        Comment commentResult = null;
        User user = null;
        Video video = null;
        Optional<User> optionalUser = userRepository.findById(user_id);
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
            Optional<Video> optionalVideo = videoRepository.findById(video_id);
            if (optionalVideo.isPresent()) {
                video = optionalVideo.get();
                comment.setUser(user);
                comment.setVideo(video);

                commentResult = commentRepository.save(comment);
            }
        }

        return commentResult;
    }

    public Iterable<Comment> index() {
        return commentRepository.findAll();
    }

    public Comment show(Long id) {
        return commentRepository.findById(id).get();
    }

    public List<Comment> findCommentsByVideo(Long id) throws IllegalArgumentException {
        Video video = null;
        Optional<Video> optional = videoRepository.findById(id);
        List<Comment> result = new ArrayList<>();
        if (optional.isPresent()) {
            video = optional.get();
            result = video.getComments();
        }

        if (result != null) {
            return result;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Comment update(Long id, Comment comment) {
        Comment originalComment = null;
        Optional<Comment> optional =  commentRepository.findById(id);
        if(optional.isPresent() ) {
            originalComment = optional.get();
            originalComment.setComment(comment.getComment());
            originalComment = commentRepository.save(originalComment);
        }
        return originalComment;
    }

}