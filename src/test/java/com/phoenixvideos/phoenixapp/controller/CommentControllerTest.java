package com.phoenixvideos.phoenixapp.controller;


import antlr.build.Tool;
import com.phoenixvideos.phoenixapp.model.Comment;
import com.phoenixvideos.phoenixapp.model.User;
import com.phoenixvideos.phoenixapp.repository.CommentRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CommentControllerTest {

    @MockBean
    CommentRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createCommentTest() throws Exception {
        Comment outComment = new Comment("test Comment");
        Comment inComment = new Comment("test Comment");
        Long givenId = 1L;
        outComment.setId(givenId);

        BDDMockito
                .given(repository.save(inComment))
                .willReturn(outComment);

        String inputContent="{" +
                "\"comment\":\"Thats great!!!\"" +
                "}";
        String expectedContent="{" +
                "\"id\"1," +
                "\"firstName\"\"Marlys\"," +
                "\"lastName\"\"Alva\"," +
                "\"email\"\"sylram7@gmail.com\"," +
                "\"userName\"\"sylram\"," +
                "\"password\"\"secret\"" +
                "}";
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/users/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputContent)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));

    }

    @Test
    public void showTest(){

    }

    @Test
    public void updateCommentTest(){

    }
}
