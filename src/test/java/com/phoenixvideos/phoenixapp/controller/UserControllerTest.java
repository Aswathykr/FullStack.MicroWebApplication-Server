package com.phoenixvideos.phoenixapp.controller;

import com.phoenixvideos.phoenixapp.model.User;
import com.phoenixvideos.phoenixapp.repository.UserRepository;
import com.phoenixvideos.phoenixapp.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.net.ssl.SSLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
//@WebMvcTest(UserController.class)
public class UserControllerTest {

//    @InjectMocks
//    UserController controller;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository repository;


    @Test
    public void testCreate() throws Exception {
        User user = new User();
        Long givenId = 1L;
        user.setId(givenId);

        BDDMockito
                .given(repository.save(user))
                .willReturn(user);

        String expectedContent="{\"id\":1,\"firstName\":null,\"lastName\":null,\"email\":null,\"userName\":null,\"password\":null}";
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/users/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedContent)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void getUserTest() throws Exception {

        Long givenId = 3L;
        User user = new User();
        user.setId(givenId);

        BDDMockito
                    .given(repository.findById(givenId))
                    .willReturn(Optional.of(user));

        String expectedContent="{\"id\":3,\"firstName\":null,\"lastName\":null,\"email\":null,\"userName\":null,\"password\":null}";


            this.mockMvc.perform(MockMvcRequestBuilders
                    .get("/users/"+givenId))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void testDelete() throws Exception {
        User user = new User();
        Long givenId = 1L;
        user.setId(givenId);
        BDDMockito
                .given(repository.save(user))
                .willReturn(user);

//        String expectedContent="{\"id\":1,\"firstName\":null,\"lastName\":null,\"email\":null,\"userName\":null,\"password\":null}";
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/users/"+givenId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
//        this.mockMvc.perform(MockMvcRequestBuilders
//                .get("/users/"+givenId)).
    }

    @Test
    public void getAllUsersTest() throws Exception {

        Long givenId = 1L;
        Long givenId2 = 2L;
        Long givenId3 = 3L;
        User user = new User();
        User user2 = new User();
        User user3 = new User();
        user.setId(givenId);
        user2.setId(givenId2);
        user3.setId(givenId3);
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        users.add(user3);
        BDDMockito
                .given(repository.findAll())
                .willReturn(users);

        String expectedContent="[{\"id\":1,\"firstName\":null,\"lastName\":null," +
                "\"email\":null,\"userName\":null,\"password\":null},{\"id\":2,\"firstName\":null,\"lastName\":null," +
                "\"email\":null,\"userName\":null,\"password\":null},{\"id\":3,\"firstName\":null,\"lastName\":null,\"email\":null,\"userName\":null,\"password\":null}]";


        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/users/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void updateUserTest() throws Exception {

        Long givenId = 3L;

        User newUser = new User();
        newUser.setId(givenId);
        newUser.setFirstName("Mar");


        BDDMockito
                .given(repository.findById(givenId))
                .willReturn(Optional.of(newUser));

        String expectedContent="{\"id\":3,\"firstName\":\"Marlys\",\"lastName\":null,\"email\":null,\"userName\":null,\"password\":null}";
        System.out.println("this is " +mockMvc.toString());

        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/users/3")
                .content(expectedContent)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));


    }

}

