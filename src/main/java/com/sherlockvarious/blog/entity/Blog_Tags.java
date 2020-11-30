package com.sherlockvarious.blog.entity;

public class Blog_Tags {
    private Integer tagId;

    private Integer blogId;

    public Blog_Tags(Integer tagId, Integer blogId) {
        this.tagId = tagId;
        this.blogId = blogId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }
}