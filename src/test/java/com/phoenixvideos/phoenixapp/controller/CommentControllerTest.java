package com.phoenixvideos.phoenixapp.controller;


import antlr.build.Tool;
import com.phoenixvideos.phoenixapp.model.Comment;
import com.phoenixvideos.phoenixapp.model.User;
import com.phoenixvideos.phoenixapp.model.Video;
import com.phoenixvideos.phoenixapp.repository.CommentRepository;
import com.phoenixvideos.phoenixapp.repository.UserRepository;
import com.phoenixvideos.phoenixapp.repository.VideoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CommentControllerTest {

    @MockBean
    CommentRepository repository;
    @MockBean
    VideoRepository videoRepository;
    @MockBean
    UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

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

        String inputContent="{" +
                "\"comment\":\"test Comment\"" +
                "}";
        String expectedContent="{" +
                "\"id\":3," +
                "\"comment\":\"test Comment\"," +
                    "\"user\":{" +
                    "\"id\":1," +
                    "\"firstName\":null," +
                    "\"lastName\":null," +
                    "\"email\":null," +
                    "\"userName\":null," +
                    "\"password\":null" +
                    "}," +
                    "\"video\":{" +
                        "\"id\":2," +
                        "\"title\":\"video\"," +
                        "\"description\":\"description\"," +
                        "\"format\":\"mp4\"," +
                        "\"path\":null," +
                        "\"uniqueName\":null," +
                        "\"thumbnailPath\":null," +
                        "\"user\":null" +
                    "}" +
                "}";
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/videos/comment/1/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputContent)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));

    }
    @Test
    public void createNegetiveCommentTestWithInvalidUser() throws Exception {

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

        String inputContent="{" +
                "\"comment\":\"test Comment\"" +
                "}";
        String expectedContent="";
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/videos/comment/1/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputContent)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));

    }
    @Test
    public void createNegetiveCommentTestWithInvalidVideo() throws Exception {

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

        String inputContent="{" +
                "\"comment\":\"test Comment\"" +
                "}";
        String expectedContent="";
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/videos/comment/1/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputContent)
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));

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

        String expectedContent = "[" +
                "{" +
                "\"id\":2," +
                "\"comment\":\"comment 1\"," +
                "\"user\":null," +
                "\"video\":null" +
                "}," +
                "{" +
                "\"id\":3," +
                "\"comment\":\"comment 2\"," +
                "\"user\":null," +
                "\"video\":null" +
                "}" +
                "]";

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/videos/comments/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));

    }

    @Test
    public void showNegativeTest() throws Exception {

        BDDMockito
                .given(videoRepository.findById(1L))
                .willReturn(Optional.empty());

        String expectedContent = "[]";

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/videos/comments/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));

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
        String inputContent="{" +
                "\"comment\":\"comment 2\"" +
                "}";
        String expectedContent =
                "{" +
                "\"id\":1," +
                "\"comment\":\"comment 2\"," +
                "\"user\":null," +
                "\"video\":null" +
                "}";

        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/videos/comment/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputContent))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));


    }

    @Test
    public void updateCommentNegetiveTest() throws Exception {
        Comment comment = new Comment("comment 1", 1L);
        Comment outComment = new Comment("comment 2", 1L);
        BDDMockito
                .given(repository.findById(1L))
                .willReturn(Optional.empty());


        String inputContent="{" +
                "\"comment\":\"comment 2\"" +
                "}";

        String expectedContent =
                "";
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/videos/comment/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputContent))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));

    }
}
