package com.sherlockvarious.blog.service;

import java.util.ArrayList;

/**
 * @author sunchao
 * @created at 2020-11-30-14:18
 */
public interface Blog_TagsService {
    boolean addNewRelationship(Integer id, String tagIds);

    boolean deleteByBlogId(int id);

    ArrayList<Integer> selectByBlogId(int id);
}
