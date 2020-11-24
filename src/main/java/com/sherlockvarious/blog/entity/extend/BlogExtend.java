package com.sherlockvarious.blog.entity.extend;

import com.sherlockvarious.blog.entity.Blog;
import com.sherlockvarious.blog.entity.Type;
import com.sherlockvarious.blog.entity.User;

/**
 * @author sunchao
 * @created at 2020-11-21-21:14
 */
public class BlogExtend extends Blog {
    private Type type;
    private User user;

    public BlogExtend() {
    }

    public BlogExtend(Type type) {
        super();
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
