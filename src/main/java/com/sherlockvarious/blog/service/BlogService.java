package com.sherlockvarious.blog.service;

import com.github.pagehelper.PageInfo;
import com.sherlockvarious.blog.entity.Blog;

import java.util.List;
import java.util.Map;

/**
 * @author sunchao
 * @created at 2020-11-16-20:24
 */
public interface BlogService {
    PageInfo<Blog> listBlog(int pageNum, int pageSize);

    PageInfo<Blog> listConditionalBlog(int pageNum, int pageSize,Blog blog);

    boolean deleteById(int id);

    boolean saveNewBlog(Blog blog);

    Blog getBlog(int id);

    void updateBlog(Blog blog);

    String findTags(int id);

    List<Integer> listAllBlogTypeId();

    List<Blog> listRecommendBlogs(int i);

    PageInfo<Blog> listBlogByTypes(int pageNum, int pageSize, int id);

    PageInfo<Blog>  listBlogByTags(int pageNum, int pageSize, int id);

    int countBlogs();

    Map<Integer,List<Blog>> archiveBlog();

    PageInfo<Blog> listBlogByKeyword(int pageNum, int pageSize, String query);
}
