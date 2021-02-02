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
import java.util.*;

/**
 * @author sunchao
 * @created at 2020-11-14-16:32
 */
@Service
public class TypeServiceImp implements TypeService {

    @Resource
    TypeMapper typeMapper;

    @Resource
    BlogService blogService;

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
        try {
            if (typeMapper.deleteByPrimaryKey(id) == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public List<Type> listAllType() {

        TypeExample example = new TypeExample();
        example.createCriteria();
        return typeMapper.selectByExample(example);
    }

    //按照type被使用的频率 返回最高的 i 个
    @Override
    public List<Type> listTypeByPopularity(int i) {

        TypeExample example = new TypeExample();
        example.createCriteria();
        Map<Integer, Integer> map = new HashMap<>();


        List<Type> types1 = typeMapper.selectByExample(example);
        //将typeId作为key放入map中
        for (Type type : types1) {
            map.put(type.getId(), 0);
        }

        List<Integer> list1 = blogService.listAllBlogTypeId();
        //每一个blog使用了type，该typeId的value就++
        for (Integer integer : list1) {
            int value = map.get(integer);
            map.put(integer, ++value);
        }

        Set<Map.Entry<Integer, Integer>> entrySet = map.entrySet();

        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }

        });

        ArrayList<Type> types = new ArrayList<>();

        for (Map.Entry s : list) {
            Type type = typeMapper.selectByPrimaryKey((int) s.getKey());
            type.setNumsOfBlog((int) s.getValue());
            types.add(type);
            if (types.size() == i) {
                break;
            }
        }

        return types;


    }
}
