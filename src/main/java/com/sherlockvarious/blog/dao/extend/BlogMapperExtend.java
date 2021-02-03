package com.sherlockvarious.blog.dao.extend;

import com.sherlockvarious.blog.entity.Blog;

import java.util.List;

/**
 * @author sunchao
 * @created at 2021-01-12-19:13
 */
public interface BlogMapperExtend {

    //按访问量排序博客的sql
    //已弃用 因为可以直接通过mybatis实现order by
    List<Blog> sortByTraffic();

    List<Blog> selectByYear(int i);
}
