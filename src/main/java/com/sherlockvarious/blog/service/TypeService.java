package com.sherlockvarious.blog.service;

import com.github.pagehelper.PageInfo;
import com.sherlockvarious.blog.entity.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author sunchao
 * @created at 2020-11-14-16:31
 */

public interface TypeService {

    PageInfo<Type> listType( int pageNo, int pageSize);

    boolean deleteById(int id);

    boolean add(Type type);

    boolean ifHas(Type type);

    Type selectById(int id);

    boolean edit(Type type);

    List<Type> listAllType();

    List<Type> listTypeByPopularity(int i);
}
