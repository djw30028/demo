package com.michaelw.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class Greeting implements Serializable {

    private long id;
    private String content;

    public Greeting() {

    }
    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "The name of the user", required = true)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
