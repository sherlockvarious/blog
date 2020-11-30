package com.sherlockvarious.blog.service;

import com.github.pagehelper.PageInfo;
import com.sherlockvarious.blog.entity.Blog;
import com.sherlockvarious.blog.entity.extend.BlogExtend;

/**
 * @author sunchao
 * @created at 2020-11-16-20:24
 */
public interface BlogService {
    PageInfo<Blog> listBlog(int pageNum, int pageSize);

    PageInfo<Blog> listConditionalBlog(int pageNum, int pageSize,Blog blog);

    boolean deleteById(int id);

    boolean saveNewBlog(Blog blog);
}
