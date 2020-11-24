package com.sherlockvarious.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sherlockvarious.blog.dao.BlogMapper;
import com.sherlockvarious.blog.entity.Blog;
import com.sherlockvarious.blog.entity.BlogExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author sunchao
 * @created at 2020-11-16-20:24
 */
@Service
public class BlogServiceImp implements BlogService{

    @Resource
    BlogMapper blogMapper;

    @Resource
    TypeService typeService;

    @Override
    public PageInfo<Blog> listConditionalBlog(int pageNum, int pageSize,Blog blog) {
        PageHelper.startPage(pageNum, pageSize);
        BlogExample example = new BlogExample();

        if(blog.getTypeId()!=null&&blog.getTypeId()!=0){
            example.createCriteria().andTitleLike("%"+blog.getTitle()+"%").andTypeIdEqualTo(blog.getTypeId());
        }else {
            example.createCriteria().andTitleLike("%"+blog.getTitle()+"%");

        }

        List<Blog> blogs = blogMapper.selectByExample(example);

        for (Blog b : blogs) {
            b.setType(typeService.selectById(b.getTypeId()));
        }
        return new PageInfo<>(blogs);

    }

    @Override
    public PageInfo<Blog> listBlog(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        BlogExample example = new BlogExample();
        example.createCriteria();
        List<Blog> blogs = blogMapper.selectByExample(example);

        for (Blog blog : blogs) {
            blog.setType(typeService.selectById(blog.getTypeId()));
        }

        return new PageInfo<>(blogs);
    }
}
