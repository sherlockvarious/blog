package com.sherlockvarious.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sherlockvarious.blog.dao.TypeMapper;
import com.sherlockvarious.blog.entity.Type;
import com.sherlockvarious.blog.entity.TypeExample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sunchao
 * @created at 2020-11-14-16:32
 */
@Service
public class TypeServiceImp implements TypeService {

    @Resource
    TypeMapper typeMapper;

    @Override
    public PageInfo<Type> listType(int pageNo, int pageSize) {

        PageHelper.startPage(pageNo, pageSize);
        TypeExample example = new TypeExample();
        example.createCriteria();
        List<Type> typeList = typeMapper.selectByExample(example);
        return new PageInfo<>(typeList);
    }

    @Override
    public boolean deleteById(int id) {
        if (typeMapper.deleteByPrimaryKey(id) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean add(Type type) {
        if (typeMapper.insertSelective(type) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean ifHas(Type type) {
        TypeExample example = new TypeExample();
        example.createCriteria().andNameEqualTo(type.getName());

        return (typeMapper.selectByExample(example).size() == 0);
    }

    @Override
    public Type selectById(int id) {
        return typeMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean edit(Type type) {
        if (typeMapper.updateByPrimaryKey(type) == 1) {
            return true;
        }

        return false;
    }
}
