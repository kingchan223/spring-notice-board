package com.community.config.webSocket;

public class THelloMessage {

    private String content;
    private String username;

    public THelloMessage() {}

    public THelloMessage(String content, String username) {
        this.content = content;
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

