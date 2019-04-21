package com.phoenixvideos.phoenixapp.service;

import com.phoenixvideos.phoenixapp.model.Comment;
import com.phoenixvideos.phoenixapp.model.User;
import com.phoenixvideos.phoenixapp.model.Video;
import com.phoenixvideos.phoenixapp.repository.CommentRepository;
import com.phoenixvideos.phoenixapp.repository.UserRepository;
import com.phoenixvideos.phoenixapp.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository =commentRepository;
    }

    @Autowired
    public VideoRepository videoRepository;

    @Autowired
    public UserRepository userRepository;

    public Comment create(Long user_id, Long video_id, Comment comment) {

        Video videoResult = null;
        User userResult = null;
        Comment commentResult = null;
        User user = userRepository.findById(user_id).orElseGet(null);
        Video video = videoRepository.findById(video_id).orElseGet(null);
        if(user != null && video!= null) {
            comment.setUser(user);
            comment.setVideo(video);

            commentResult = commentRepository.save(comment);
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
        Video video = videoRepository.findById(id).get();
        List<Comment> result = video.getComments();
        if (result!=null) {
            return result;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Comment update(Long id, Comment comment) {
        Comment originalComment = commentRepository.findById(id).get();
        originalComment.setUser(originalComment.getUser());
        originalComment.setComment(comment.getComment());
        return commentRepository.save(originalComment);
    }

}