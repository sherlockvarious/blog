package com.sherlockvarious.blog.service;

import com.github.pagehelper.PageInfo;
import com.sherlockvarious.blog.entity.Tag;

import java.util.List;

/**
 * @author sunchao
 * @created at 2020-11-21-8:43
 */
public interface TagService {

    PageInfo<Tag> listTag(int pageNo, int pageSize);

    boolean deleteById(int id);

    boolean add(Tag tag);

    boolean ifHas(Tag tag);

    Tag selectById(int id);

    boolean edit(Tag tag);

    List<Tag> listAllTags();
}


