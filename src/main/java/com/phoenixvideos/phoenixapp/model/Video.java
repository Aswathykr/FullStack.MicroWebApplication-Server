package com.phoenixvideos.phoenixapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String format;
    private String path;
    private String uniqueName;
    private String thumbnailPath;

    @ManyToOne
    @JoinColumn
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "video")
    private List<Comment> comments;

    public Video(){

    }

    public Video(String title, String path, long id){
        this.title = title;
        this.path = path;
        this.id = id;
        this.format = "mp4";
    }
    public Video(String title, String description, String format){
        this.title = title;
        this.description = description;
        this.format = format;
    }
    public Video(String title){
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Video)) return false;
        Video video = (Video) o;
        return Objects.equals(id, video.id) &&
                Objects.equals(title, video.title) &&
                Objects.equals(description, video.description) &&
                Objects.equals(format, video.format) &&
                Objects.equals(path, video.path) &&
                Objects.equals(uniqueName, video.uniqueName) &&
                Objects.equals(user, video.user) &&
                Objects.equals(comments, video.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, format, path, uniqueName, user, comments);
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
}
