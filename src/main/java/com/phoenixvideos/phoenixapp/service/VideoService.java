package com.phoenixvideos.phoenixapp.service;

import com.phoenixvideos.phoenixapp.model.User;
import com.phoenixvideos.phoenixapp.model.Video;
import com.phoenixvideos.phoenixapp.repository.UserRepository;
import com.phoenixvideos.phoenixapp.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class VideoService {
    private VideoRepository videoRepository;
    private UserRepository userRepository;
    private AmazonS3ClientService amazonS3ClientService;

    @Autowired
    public VideoService(VideoRepository videoRepository, UserRepository userRepository, AmazonS3ClientService amazonS3ClientService) {
        this.videoRepository = videoRepository;
        this.userRepository = userRepository;
        this.amazonS3ClientService = amazonS3ClientService;
    }

    public Video create(MultipartFile videoFile, Long user_id, String videoTitle, String videoDescription, String format) {
        Video newVideo = new Video();

        if(userRepository.findById(user_id).isPresent()) {
            User user = userRepository.findById(user_id).get();
            newVideo.setUser(user);
            newVideo.setTitle(videoTitle);
            newVideo.setDescription(videoDescription);
            newVideo.setFormat(format);

            videoRepository.save(newVideo);

            String uniqueVideoName = generateUniqueName(videoFile.getOriginalFilename(), format, newVideo.getId());
            newVideo.setUniqueName(uniqueVideoName);

            amazonS3ClientService.uploadFileToS3Bucket(videoFile, uniqueVideoName);
            newVideo.setPath(amazonS3ClientService.gertUrl());



            videoRepository.save(newVideo);
        }


        return newVideo;
    }

    public Iterable<Video> index() {
        return videoRepository.findAll();
    }

    public String generateUniqueName(String name, String format, Long id) {
        name = name.replace(".mp4", "");
        return name + "_" + id + "." + format;
    }

    public Video getVideo(Long id) {
        Optional<Video> optionalVideo = videoRepository.findById(id);
        return optionalVideo.isPresent() ? optionalVideo.get() : null;
    }

    public Video updateVideoDetails(Long id, Video video) {
        Video originalVideo = videoRepository.findById(id).get();

        originalVideo.setTitle(video.getTitle());
        originalVideo.setUniqueName(video.getUniqueName());
        originalVideo.setDescription(video.getDescription());
        originalVideo.setPath(video.getPath());
        originalVideo.setTitle(video.getTitle());
        originalVideo.setFormat(video.getFormat());
        originalVideo.setComments(video.getComments());
        originalVideo.setThumbnailPath(video.getThumbnailPath());

        videoRepository.save(originalVideo);
        return originalVideo;
    }

    public boolean delete(Long id) {
        videoRepository.deleteById(id);
        return true;
    }

    @Override
    public String toString() {
        return "********************************************\nVideoService{" +
                "videoRepository=" + videoRepository +
                ", userRepository=" + userRepository +
                ", amazonS3ClientService=" + amazonS3ClientService +
                '}' + "********************************************\n";
    }
}

