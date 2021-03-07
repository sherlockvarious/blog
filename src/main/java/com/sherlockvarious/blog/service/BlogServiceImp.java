package com.sherlockvarious.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sherlockvarious.blog.dao.*;
import com.sherlockvarious.blog.entity.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * @author sunchao
 * @created at 2020-11-16-20:24
 */
@Service
public class BlogServiceImp implements BlogService {

    @Resource
    BlogMapper blogMapper;

    @Resource
    Blog_TagsMapper blog_tagsMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    TypeMapper typeMapper;

    @Resource
    TagMapper tagMapper;

    @Resource
    TypeService typeService;

    @Resource
    UserService userService;

    @Resource
    Blog_TagsService blog_tagsService;


    @Override
    public boolean deleteById(int id) {
        return blogMapper.deleteByPrimaryKey(id) == 1;
    }

    @Override
    public Blog getBlog(int id) {
        return blogMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateBlog(Blog blog) {
        viewCheckedOption(blog);
        blogMapper.updateByPrimaryKeySelective(blog);
    }

    @Override
    public String findTags(int id) {
        ArrayList<Integer> tags = blog_tagsService.selectByBlogId(id);

        StringBuffer tagIds = new StringBuffer();
        for (Integer tag : tags) {
            tagIds.append(tag);
            tagIds.append(",");
        }

        if (tagIds.length() > 1) {
            tagIds.delete(tagIds.length() - 1, tagIds.length());
        }
        return tagIds.toString();
    }

    public static void viewCheckedOption(Blog blog) {
        if (blog.getCommentabled() == null) {
            blog.setCommentabled(false);
        }
        if (blog.getAppreciation() == null) {
            blog.setAppreciation(false);
        }
        if (blog.getPublished() == null) {
            blog.setPublished(false);
        }
        if (blog.getRecommend() == null) {
            blog.setRecommend(false);
        }
        if (blog.getShareStatement() == null) {
            blog.setShareStatement(false);
        }
    }

    @Override
    public boolean saveNewBlog(Blog blog) {

        try {
            //设置文章创建时间
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            //设置初始访问量为 0
            blog.setViews(0);

            viewCheckedOption(blog);

            blogMapper.insertSelective(blog);

            System.out.println(blog.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public PageInfo<Blog> listConditionalBlog(int pageNum, int pageSize, Blog blog) {
        PageHelper.startPage(pageNum, pageSize);
        BlogExample example = new BlogExample();

        if (blog.getTypeId() != null && blog.getTypeId() != 0) {
            example.createCriteria().andTitleLike("%" + blog.getTitle() + "%").andTypeIdEqualTo(blog.getTypeId());
        } else {
            example.createCriteria().andTitleLike("%" + blog.getTitle() + "%");

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

            Blog_TagsExample example1 = new Blog_TagsExample();
            example1.createCriteria().andBlogIdEqualTo(blog.getId());
            List<Blog_Tags> list = blog_tagsMapper.selectByExample(example1);

            List<Tag> tags = new ArrayList<>();
            for (Blog_Tags blog_tags : list) {
                tags.add(tagMapper.selectByPrimaryKey(blog_tags.getTagId()));
            }

            blog.setTags(tags);

            blog.setType(typeService.selectById(blog.getTypeId()));
            blog.setUser(userService.selectById(blog.getUserId()));
        }


        return new PageInfo<>(blogs);
    }

    @Override
    public List<Integer> listAllBlogTypeId() {
        ArrayList<Integer> list = new ArrayList<>();

        BlogExample example = new BlogExample();
        example.createCriteria();
        for (Blog blog : blogMapper.selectByExample(example)) {
            list.add(blog.getTypeId());
        }

        return list;
    }

    @Override
    public List<Blog> listRecommendBlogs(int i) {

        BlogExample example = new BlogExample();
        example.setOrderByClause("views desc");

        List<Blog> blogs = blogMapper.selectByExample(example);
        if (blogs.size() <= 3) {
            return blogs;
        } else {
            List<Blog> returnBlogs = new ArrayList<>();
            returnBlogs.add(blogs.get(0));
            returnBlogs.add(blogs.get(1));
            returnBlogs.add(blogs.get(2));

            return returnBlogs;
        }

    }

    @Override
    public PageInfo<Blog> listBlogByTypes(int pageNum, int pageSize, int id) {
        PageHelper.startPage(pageNum, pageSize);
        BlogExample example = new BlogExample();
        example.createCriteria().andTypeIdEqualTo(id);

        List<Blog> blogs = blogMapper.selectByExample(example);

        for (Blog blog : blogs) {

            Blog_TagsExample example1 = new Blog_TagsExample();
            example1.createCriteria().andBlogIdEqualTo(blog.getId());
            List<Blog_Tags> list = blog_tagsMapper.selectByExample(example1);

            List<Tag> tags = new ArrayList<>();
            for (Blog_Tags blog_tags : list) {
                tags.add(tagMapper.selectByPrimaryKey(blog_tags.getTagId()));
            }

            blog.setTags(tags);
            blog.setType(typeService.selectById(blog.getTypeId()));
            blog.setUser(userService.selectById(blog.getUserId()));
        }

        return new PageInfo<>(blogs);
    }


    //根据关键词查找blog （即搜索框查找相关blog）
    @Override
    public PageInfo<Blog> listBlogByKeyword(int pageNum, int pageSize, String query) {

        PageHelper.startPage(pageNum, pageSize);
        List<Blog> blogs= blogMapper.selectByKeyword(query);

        //初始化blog中的user
        for (Blog blog : blogs) {
            blog.setUser(userMapper.selectByPrimaryKey(blog.getUserId()));
        }

        //初始化blog中的type
        for (Blog blog : blogs) {
            blog.setType(typeMapper.selectByPrimaryKey(blog.getTypeId()));
        }

        //初始化blog中的tag
        for (Blog blog : blogs) {
            Blog_TagsExample example = new Blog_TagsExample();
            example.createCriteria().andBlogIdEqualTo(blog.getId());
            List<Blog_Tags> blog_tagsList = blog_tagsMapper.selectByExample(example);


            List<Tag> tags = new ArrayList<>();
            for (Blog_Tags blog_tags : blog_tagsList) {
                tags.add(tagMapper.selectByPrimaryKey(blog_tags.getTagId()));
            }

            blog.setTags(tags);
        }

        return new PageInfo<>(blogs);
    }

    @Override
    public PageInfo<Blog> listBlogByTags(int pageNum, int pageSize, int id) {
        PageHelper.startPage(pageNum, pageSize);

        Blog_TagsExample example = new Blog_TagsExample();
        example.createCriteria().andTagIdEqualTo(id);

        List<Blog_Tags> blogList = blog_tagsMapper.selectByExample(example);

        List<Blog> blogs = new ArrayList<>();

        for (Blog_Tags blog_tags : blogList) {
            Blog it = blogMapper.selectByPrimaryKey(blog_tags.getBlogId());

            //找到该blog所对应的所有tag
            Blog_TagsExample example1 = new Blog_TagsExample();
            example1.createCriteria().andBlogIdEqualTo(it.getId());
            List<Blog_Tags> blog_tagsList = blog_tagsMapper.selectByExample(example1);

            List<Tag> tags = new ArrayList<>();
            for (Blog_Tags blogTags : blog_tagsList) {
                tags.add(tagMapper.selectByPrimaryKey(blogTags.getTagId()));
            }

            it.setTags(tags);
            it.setUser(userMapper.selectByPrimaryKey(it.getUserId()));
            it.setType(typeMapper.selectByPrimaryKey(it.getTypeId()));
            blogs.add(it);

        }

        return new PageInfo<>(blogs);


    }

    @Override
    public int countBlogs() {

        BlogExample example = new BlogExample();
        example.createCriteria();

        return (int) blogMapper.countByExample(example);

    }

    @Override
    public Map<Integer, List<Blog>> archiveBlog() {

        //网站在2021年搭建 所以只需要从今年的一直往前查到2021年的。即可查完所有日期
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        Map<Integer, List<Blog>> map = new HashMap<>();

        for (int i =  2021 ; i <= year; i++) {
            map.put(i, blogMapper.selectByYear(i));
        }

        return map;
    }

    @Override
    public Blog getAndConvert(int id) {
        Blog it = blogMapper.selectByPrimaryKey(id);

        it.setUser(userMapper.selectByPrimaryKey(it.getUserId()));
        it.setType(typeMapper.selectByPrimaryKey(it.getTypeId()));

        Blog_TagsExample example = new Blog_TagsExample();
        example.createCriteria().andBlogIdEqualTo(id);
        List<Blog_Tags> blog_tagsList = blog_tagsMapper.selectByExample(example);

        List<Tag> tags = new ArrayList<>();
        for (Blog_Tags blog_tags : blog_tagsList) {
            tags.add(tagMapper.selectByPrimaryKey(blog_tags.getTagId()));
        }
        it.setTags(tags);

        int views = it.getViews();
        it.setViews(++views);

        return it;
    }
}
