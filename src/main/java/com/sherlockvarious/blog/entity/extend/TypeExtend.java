package com.sherlockvarious.blog.entity.extend;

import com.sherlockvarious.blog.entity.Blog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunchao
 * @created at 2021-01-19-13:28
 */
public class TypeExtend {

    private int numsOfBlog;
    private List<Blog> blogs = new ArrayList<>();

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }


    public void setNumsOfBlog(int numsOfBlog) {
        this.numsOfBlog = numsOfBlog;
    }

    public int getNumsOfBlog() {
        return numsOfBlog;
    }

}
