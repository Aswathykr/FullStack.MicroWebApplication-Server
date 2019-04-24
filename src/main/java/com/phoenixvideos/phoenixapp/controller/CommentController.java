package com.phoenixvideos.phoenixapp.controller;

import com.phoenixvideos.phoenixapp.model.Comment;

import com.phoenixvideos.phoenixapp.model.Video;
import com.phoenixvideos.phoenixapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

//import javax.xml.ws.Response;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
public class CommentController {


    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/videos/comment/{user_id}/{video_id}")//videos/comment/1/2
    public ResponseEntity<Comment> createComment(@PathVariable("user_id") Long user_id,
                                                 @PathVariable("video_id") Long video_id, @RequestBody Comment comment) {

        Comment returnComment = commentService.create(user_id,video_id,comment);
        return (returnComment == null) ? (new ResponseEntity<>(HttpStatus.BAD_REQUEST))
                : new ResponseEntity<>(returnComment,HttpStatus.CREATED);
    }


    @GetMapping("/videos/comments/{video_id}")//videos/comment/1/2
    public ResponseEntity<List<Comment>> show(@PathVariable ("video_id") Long id) {
        return new ResponseEntity<>(commentService.findCommentsByVideo(id),HttpStatus.OK);

    }

    @PutMapping("videos/comment/{comment_id}")
    public ResponseEntity<Comment> updateComment(@PathVariable("comment_id") Long comment_id,
                                                 @RequestBody Comment comment) {

        Comment newComment = commentService.update(comment_id,comment);

        return new ResponseEntity<>(newComment,HttpStatus.OK);
    }

    @DeleteMapping("videos/comment/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void DeleteComment(@PathVariable Long id) {
    }
}