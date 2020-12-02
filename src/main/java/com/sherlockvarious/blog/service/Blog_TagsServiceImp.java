package com.sherlockvarious.blog.service;

import com.sherlockvarious.blog.dao.Blog_TagsMapper;
import com.sherlockvarious.blog.entity.Blog_Tags;
import com.sherlockvarious.blog.entity.Blog_TagsExample;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author sunchao
 * @created at 2020-11-30-14:18
 */
@Service
public class Blog_TagsServiceImp implements Blog_TagsService {

    @Resource
    Blog_TagsMapper blog_tagsMapper;


    @Override
    public ArrayList<Integer> selectByBlogId(int id) {
        Blog_TagsExample example = new Blog_TagsExample();
        example.createCriteria().andBlogIdEqualTo(id);

        ArrayList<Integer> list  = new ArrayList<>();

        for (Blog_Tags blog_tags : blog_tagsMapper.selectByExample(example)) {
            list.add(blog_tags.getTagId());
        }

        return list;
    }

    @Override
    public boolean deleteByBlogId(int id) {
        try {
            Blog_TagsExample example = new Blog_TagsExample();
            example.createCriteria().andBlogIdEqualTo(id);
            blog_tagsMapper.deleteByExample(example);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    @Override
    public boolean addNewRelationship(Integer id, String tagIds) {
        String[] lists = tagIds.split(",");
        ArrayList<Integer> tags = new ArrayList<>();

        for (String list : lists) {
            tags.add(Integer.parseInt(list));
        }

        for (Integer tag : tags) {
            blog_tagsMapper.insert(new Blog_Tags(tag, id));
        }

        return true;
    }
}
