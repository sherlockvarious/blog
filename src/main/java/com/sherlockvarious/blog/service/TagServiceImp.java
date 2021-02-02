package com.sherlockvarious.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sherlockvarious.blog.dao.Blog_TagsMapper;
import com.sherlockvarious.blog.dao.TagMapper;
import com.sherlockvarious.blog.entity.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author sunchao
 * @created at 2020-11-21-8:43
 */

@Service
public class TagServiceImp implements TagService {

    @Resource
    TagMapper TagMapper;

    @Resource
    Blog_TagsMapper blog_tagsMapper;

    @Override
    public List<Tag> listTagByPopularity(int i) {
        Blog_TagsExample example = new Blog_TagsExample();
        example.createCriteria();

        HashMap<Integer, Integer> hashMap = new HashMap<>();

        List<Blog_Tags> list = blog_tagsMapper.selectByExample(example);
        for (Blog_Tags blog_tags : list) {
            hashMap.put(blog_tags.getTagId(), 0);
        }

        for (Blog_Tags blog_tags : list) {
            int num = hashMap.get(blog_tags.getTagId());
            hashMap.put(blog_tags.getTagId(), ++num);
        }

        //注意 ArrayList<>() 括号里要传入map.entrySet()
        List<Map.Entry<Integer, Integer>> list1 = new ArrayList<>(hashMap.entrySet());
        Collections.sort(list1, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });

        ArrayList<Tag> tags = new ArrayList<>();
        //注意这里遍历的是list，也就是我们将map.Entry放进了list，排序后的集合
        for (Map.Entry s : list1) {
            Tag tag = TagMapper.selectByPrimaryKey((int) s.getKey());
            tag.setNumsOfBlog((int) s.getValue());
            tags.add(tag);
            //i是一共要显示的数量  即前台只要前i名
            if (tags.size()==i){
                break;
            }

        }

        return tags;


    }

    @Override
    public PageInfo<Tag> listTag(int pageNo, int pageSize) {

        PageHelper.startPage(pageNo, pageSize);
        TagExample example = new TagExample();
        example.createCriteria();
        List<Tag> TagList = TagMapper.selectByExample(example);
        return new PageInfo<>(TagList);
    }

    @Override
    public boolean deleteById(int id) {
        try {
            if (TagMapper.deleteByPrimaryKey(id) == 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public boolean add(Tag Tag) {
        if (TagMapper.insertSelective(Tag) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean ifHas(Tag Tag) {
        TagExample example = new TagExample();
        example.createCriteria().andNameEqualTo(Tag.getName());

        return (TagMapper.selectByExample(example).size() == 0);
    }

    @Override
    public Tag selectById(int id) {
        return TagMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean edit(Tag Tag) {
        if (TagMapper.updateByPrimaryKey(Tag) == 1) {
            return true;
        }

        return false;
    }

    @Override
    public List<Tag> listAllTags() {

        TagExample example = new TagExample();
        example.createCriteria();
        return TagMapper.selectByExample(example);
    }
}
