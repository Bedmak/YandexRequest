package com.defaultapps.yandexrequest;


public class Result {
    private String title;
    private String link;

    Result(String title, String link) {
        this.title = title;
        this.link = link;
    }

    String getTitle() {
        return title;
    }

    String getLink() {
        return link;
    }
}
