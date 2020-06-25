package com.rajendra.covid19ui;


public class VideoDetails {

    public  String videoId;
    public String Url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String title;

    public VideoDetails() {

    }

    public VideoDetails(String videoId) {
        this.videoId = videoId;

    }


    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
