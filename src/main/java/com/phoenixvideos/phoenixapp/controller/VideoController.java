package com.phoenixvideos.phoenixapp.controller;

import com.phoenixvideos.phoenixapp.model.Video;
import com.phoenixvideos.phoenixapp.service.AmazonS3ClientService;
import com.phoenixvideos.phoenixapp.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin
public class VideoController {
    private final VideoService videoService;
    private final AmazonS3ClientService amazonS3ClientService;

    @Autowired
    public VideoController(VideoService videoService, AmazonS3ClientService amazonS3ClientService) {
        this.videoService = videoService;
        this.amazonS3ClientService = amazonS3ClientService;
    }

    @PostMapping(value="/videos/{user_id}", consumes = "multipart/form-data")//pass the id of the user uploading
    public ResponseEntity<Video> createVideo(@RequestPart(value = "file") MultipartFile videoFile,
                                             @PathVariable Long user_id,
                                             @RequestPart(value = "title") String videoTitle,
                                             @RequestPart(value = "desc") String videoDescription,
                                             @RequestPart(value = "format") String videoFormat) {

        Video createdVideo = videoService.create(videoFile, user_id, videoTitle, videoDescription, videoFormat);

        return new ResponseEntity<>(createdVideo, createdVideo== null ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED);
    }


    @GetMapping("/videos/all")
    public ResponseEntity<Iterable<Video>> getAllVideos() {
        return new ResponseEntity<>(videoService.index(), HttpStatus.OK);
    }

    @GetMapping("/videos/{id}")
    public ResponseEntity<Video> getVideo(@PathVariable Long id) {
        return new ResponseEntity<>(videoService.getVideo(id), HttpStatus.OK);
    }
    
    @PutMapping("/videos/{id}")
    public ResponseEntity<Video> updateVideo(@PathVariable Long id, @RequestBody Video video) {
        return new ResponseEntity<>(videoService.updateVideoDetails(id, video), HttpStatus.OK);
    }

    @DeleteMapping("/videos/{id}")
    //@ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Boolean> deleteVideo(@PathVariable Long id) {
        Video video = videoService.getVideo(id);
        amazonS3ClientService.deleteFileFromS3Bucket(video.getUniqueName());

        return new ResponseEntity<>(videoService.delete(id), HttpStatus.NO_CONTENT);
    }
}
