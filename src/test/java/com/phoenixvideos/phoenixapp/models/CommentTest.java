package com.phoenixvideos.phoenixapp.models;

import com.phoenixvideos.phoenixapp.model.Comment;
import com.phoenixvideos.phoenixapp.model.User;
import org.junit.Assert;
import org.junit.Test;

public class CommentTest {


    @Test
    public void getUserTest(){
        User user = new User();
        Comment comment = new Comment();

        comment.setUser(user);
        User actual = comment.getUser();

        Assert.assertEquals(user,actual);
    }

    @Test
    public void getCommentTest(){

        Comment comment = new Comment();
        String expected = "New Comment";
        comment.setComment(expected);

        String actual = comment.getComment();

        Assert.assertEquals(expected,actual);
    }


}
