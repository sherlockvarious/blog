package com.sherlockvarious.blog.entity;

import com.sherlockvarious.blog.entity.extend.TypeExtend;

public class Type extends TypeExtend {
    private Integer id;

    private String name;

    private int numsOfBlog;

    public int getNumsOfBlog() {
        return numsOfBlog;
    }

    public void setNumsOfBlog(int numsOfBlog) {
        this.numsOfBlog = numsOfBlog;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}