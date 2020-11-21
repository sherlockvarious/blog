package com.sherlockvarious.blog.entity;

public class Type {
    private Integer id;

    private String name;

    public Type(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Type() {
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