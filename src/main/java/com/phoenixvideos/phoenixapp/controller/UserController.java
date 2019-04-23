package com.phoenixvideos.phoenixapp.controller;

import com.phoenixvideos.phoenixapp.model.Comment;
import com.phoenixvideos.phoenixapp.model.User;
import com.phoenixvideos.phoenixapp.model.Video;
import com.phoenixvideos.phoenixapp.service.CommentService;
import com.phoenixvideos.phoenixapp.service.UserService;
import com.phoenixvideos.phoenixapp.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@CrossOrigin
public class UserController {

    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private VideoService videoService;


    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/users/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.create(user),HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.show(id),HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return new ResponseEntity<>(userService.update(id,user), HttpStatus.OK);
    }


    @DeleteMapping("/users/{id}")
    @ResponseStatus(value =  HttpStatus.NO_CONTENT)
    public void DeleteEmployee(@PathVariable Long id){
        userService.delete(id);
    }
}