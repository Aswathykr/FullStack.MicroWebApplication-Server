package com.phoenixvideos.phoenixapp.controller;

import com.phoenixvideos.phoenixapp.model.Video;
import com.phoenixvideos.phoenixapp.repository.VideoRepository;
import com.phoenixvideos.phoenixapp.service.VideoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.nio.charset.Charset;
import java.util.ArrayList;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class VideoControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private VideoService mockService;

    @Test
    public void getAllVideosTest() throws Exception {

        String testPath = "https://s3.us-east-2.amazonaws.com/phoenix.videos/BigBuckBunny.mp4";
        String testPath2 = "https://s3.us-east-2.amazonaws.com/phoenix.videos/IMG_4786.mp4";
        ArrayList<Video> videos = new ArrayList<>();
        videos.add(new Video("BigBuckBunny", testPath, 2L));
        videos.add(new Video("Zipcode window", testPath2,3L));

        BDDMockito
                .given(mockService.index())
                .willReturn(videos);

        String expectedContent = "[{\"id\":2," +
               "\"title\":\"BigBuckBunny\"," +
                "\"description\":null," +
                "\"format\":\"mp4\"," +
                "\"path\":\"https://s3.us-east-2.amazonaws.com/phoenix.videos/BigBuckBunny.mp4\"," +
                "\"uniqueName\":null," +
                "\"thumbnailPath\":null,"+
                "\"user\":null" +
                "}," +
                "{" +
                "\"id\":3," +
                "\"title\":\"Zipcode window\"," +
                "\"description\":null," +
                "\"format\":\"mp4\"," +
                "\"path\":\"https://s3.us-east-2.amazonaws.com/phoenix.videos/IMG_4786.mp4\"," +
                "\"uniqueName\":null," +
                "\"thumbnailPath\":null,"+
                "\"user\":null" +
                "}" +
                "]";

        this.mvc.perform(MockMvcRequestBuilders
                .get("/videos/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void getAllVideosWhenNoVideosPresentTest() throws Exception {

        ArrayList<Video> videos = new ArrayList<>();

        BDDMockito
                .given(mockService.index())
                .willReturn(videos);

        String expectedContent = "[]";

        this.mvc.perform(MockMvcRequestBuilders
                .get("/videos/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }
    @Test
    public void getVideoTest() throws Exception {

        String testPath = "https://s3.us-east-2.amazonaws.com/phoenix.videos/BigBuckBunny.mp4";
        Video video = new Video("BigBuckBunny", testPath, 2L);

        BDDMockito
                .given(mockService.getVideo(2L))
                .willReturn(video);

        String expectedContent = "{\"id\":2," +
                "\"title\":\"BigBuckBunny\"," +
                "\"description\":null," +
                "\"format\":\"mp4\"," +
                "\"path\":\"https://s3.us-east-2.amazonaws.com/phoenix.videos/BigBuckBunny.mp4\"," +
                "\"uniqueName\":null," +
                "\"thumbnailPath\":null,"+
                "\"user\":null" +
                "}";

        this.mvc.perform(MockMvcRequestBuilders
                .get("/videos/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }
    @Test
    public void getVideoNegetiveTest() throws Exception {

        BDDMockito
                .given(mockService.getVideo(2L))
                .willReturn( null);

        String expectedContent = "";

        this.mvc.perform(MockMvcRequestBuilders
                .get("/videos/2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void createVideoTest() throws Exception {
        String testPath = "https://s3.us-east-2.amazonaws.com/phoenix.videos/BigBuckBunny.mp4";
        Video video = new Video("BigBuckBunny", testPath, 2L);
        MockMultipartFile mockFile = new MockMultipartFile("file", "", "application/undefined", "".getBytes());
        MockMultipartFile part1 = new MockMultipartFile("title", "", "application/text",
                "title".getBytes(Charset.forName("UTF-8")));
        MockMultipartFile part2 = new MockMultipartFile("desc", "", "application/text",
                "description".getBytes(Charset.forName("UTF-8")));
        MockMultipartFile part3 = new MockMultipartFile("format", "", "application/text",
                "mp4".getBytes(Charset.forName("UTF-8")));

        BDDMockito.given(mockService.create(mockFile, 1L, "title", "description", "mp4"))
                .willReturn(video);


        String expectedContent = "{\"id\":2," +
                "\"title\":\"BigBuckBunny\"," +
                "\"description\":null," +
                "\"format\":\"mp4\"," +
                "\"path\":\"https://s3.us-east-2.amazonaws.com/phoenix.videos/BigBuckBunny.mp4\"," +
                "\"uniqueName\":null," +
                "\"thumbnailPath\":null,"+
                "\"user\":null" +
                "}";

        this.mvc.perform(MockMvcRequestBuilders.multipart("/videos/1")
                .file(mockFile)
                .file(part1)
                .file(part2)
                .file(part3))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));

    }

    @Test
    public void createVideoNegativeTest() throws Exception {
        String testPath = "https://s3.us-east-2.amazonaws.com/phoenix.videos/BigBuckBunny.mp4";
        Video video = new Video("BigBuckBunny", testPath, 2L);
        MockMultipartFile mockFile = new MockMultipartFile("file", "", "application/undefined", "".getBytes());
        MockMultipartFile part1 = new MockMultipartFile("title", "", "application/text",
                "title".getBytes(Charset.forName("UTF-8")));
        MockMultipartFile part2 = new MockMultipartFile("desc", "", "application/text",
                "description".getBytes(Charset.forName("UTF-8")));
        MockMultipartFile part3 = new MockMultipartFile("format", "", "application/text",
                "mp4".getBytes(Charset.forName("UTF-8")));

        BDDMockito.given(mockService.create(mockFile, 1L, "title", "description", "mp4"))
                .willReturn(null);


        String expectedContent = "";

        this.mvc.perform(MockMvcRequestBuilders.multipart("/videos/1")
                .file(mockFile)
                .file(part1)
                .file(part2)
                .file(part3))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));

    }

}
