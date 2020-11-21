package com.sherlockvarious.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sherlockvarious.blog.dao.TagMapper;
import com.sherlockvarious.blog.entity.Tag;
import com.sherlockvarious.blog.entity.TagExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sunchao
 * @created at 2020-11-21-8:43
 */

@Service
public class TagServiceImp implements TagService{

    @Resource
    TagMapper TagMapper;

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
        if (TagMapper.deleteByPrimaryKey(id) == 1) {
            return true;
        }
        return false;
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
}
