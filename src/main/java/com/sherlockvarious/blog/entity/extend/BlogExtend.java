package com.sherlockvarious.blog.entity.extend;

import com.sherlockvarious.blog.entity.Tag;
import com.sherlockvarious.blog.entity.Type;
import com.sherlockvarious.blog.entity.User;

import java.util.List;

/**
 * @author sunchao
 * @created at 2021-02-02-14:48
 */
public class BlogExtend {
    private User user;

    private Type type;

    private List<Tag> tags;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
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
