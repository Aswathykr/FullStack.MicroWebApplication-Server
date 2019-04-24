package com.phoenixvideos.phoenixapp.service;

import com.phoenixvideos.phoenixapp.PhoenixappApplicationTests;
import com.phoenixvideos.phoenixapp.model.Comment;
import com.phoenixvideos.phoenixapp.model.User;
import com.phoenixvideos.phoenixapp.model.Video;
import com.phoenixvideos.phoenixapp.repository.CommentRepository;
import com.phoenixvideos.phoenixapp.repository.UserRepository;
import com.phoenixvideos.phoenixapp.repository.VideoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PhoenixappApplicationTests.class)
public class CommentServiceTest {
    @MockBean
    CommentRepository repository;
    @MockBean
    VideoRepository videoRepository;
    @MockBean
    UserRepository userRepository;

    CommentService service;
    @Before
    public void setup(){
        this.service = new CommentService(repository, videoRepository, userRepository);
    }

    @Test
    public void createCommentTest() throws Exception {

        User user = new User();
        user.setId(1L);

        Video video = new Video("video", "description", "mp4");
        video.setId(2L);

        Comment outComment = new Comment("test Comment");
        Long givenId = 3L;
        outComment.setId(givenId);
        outComment.setUser(user);
        outComment.setVideo(video);

        Comment inComment = new Comment("test Comment");
        inComment.setUser(user);
        inComment.setVideo(video);

        BDDMockito
                .given(userRepository.findById(1L))
                .willReturn(Optional.of(user));

        BDDMockito
                .given(videoRepository.findById(2L))
                .willReturn(Optional.of(video));
        BDDMockito
                .given(repository.save(inComment))
                .willReturn(outComment);

        Comment returnedComment = service.create(1L, 2L, inComment);
        Assert.assertEquals(returnedComment.getComment(), outComment.getComment());

    }
    @Test
    public void createCommentNegativeTestWithInvalidUser() throws Exception {


        Video video = new Video("video", "description", "mp4");
        video.setId(2L);

        Comment outComment = new Comment("test Comment");
        Long givenId = 3L;
        outComment.setId(givenId);
        outComment.setVideo(video);

        Comment inComment = new Comment("test Comment");
        inComment.setVideo(video);

        BDDMockito
                .given(userRepository.findById(1L))
                .willReturn(Optional.empty());

        BDDMockito
                .given(videoRepository.findById(2L))
                .willReturn(Optional.of(video));
        BDDMockito
                .given(repository.save(inComment))
                .willReturn(outComment);

        Comment returnedComment = service.create(1L, 2L, inComment);
        Assert.assertEquals(returnedComment, null);

    }

    @Test
    public void createCommentNegativeTestWithInvalidVideo() throws Exception {

        User user = new User();
        user.setId(1L);

        Comment outComment = new Comment("test Comment");
        Long givenId = 3L;
        outComment.setId(givenId);
        outComment.setUser(user);

        Comment inComment = new Comment("test Comment");
        inComment.setUser(user);

        BDDMockito
                .given(userRepository.findById(1L))
                .willReturn(Optional.of(user));

        BDDMockito
                .given(videoRepository.findById(2L))
                .willReturn(Optional.empty());
        BDDMockito
                .given(repository.save(inComment))
                .willReturn(outComment);

        Comment returnedComment = service.create(1L, 2L, inComment);
        Assert.assertEquals(returnedComment, null);

    }

    @Test
    public void showTest() throws Exception {
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment("comment 1", 2L));
        comments.add(new Comment("comment 2", 3L));
        Video video = new Video("video", "description", "mp4");
        video.setId(2L);
        video.setComments(comments);
        BDDMockito
                .given(videoRepository.findById(1L))
                .willReturn(Optional.of(video));
        List<Comment> returnedComments = service.findCommentsByVideo(1L);

        Assert.assertEquals(comments, returnedComments);

    }
    @Test
    public void showNegativeTest() throws Exception {
        ArrayList<Comment> comments = new ArrayList<>();
        Video video = new Video("video", "description", "mp4");
        video.setId(2L);
        video.setComments(comments);
        BDDMockito
                .given(videoRepository.findById(1L))
                .willReturn(Optional.empty());
        List<Comment> returnedComments = service.findCommentsByVideo(1L);

        Assert.assertEquals(comments, returnedComments);
    }

    @Test
    public void updateCommentTest() throws Exception {
        Comment comment = new Comment("comment 1", 1L);
        Comment outComment = new Comment("comment 2", 1L);
        BDDMockito
                .given(repository.findById(1L))
                .willReturn(Optional.of(comment));

        comment.setComment(outComment.getComment());

        BDDMockito
                .given(repository.save(comment))
                .willReturn(outComment);
        Comment returnedComment = service.update(1L, comment);

        Assert.assertEquals(returnedComment, outComment);

    }

    @Test
    public void updateCommentNegativeTest() throws Exception {
        Comment comment = new Comment("comment 1", 1L);
        BDDMockito
                .given(repository.findById(1L))
                .willReturn(Optional.empty());


        BDDMockito
                .given(repository.save(comment))
                .willReturn(comment);
        Comment returnedComment = service.update(1L, comment);

        Assert.assertEquals(returnedComment, null);

    }
}
