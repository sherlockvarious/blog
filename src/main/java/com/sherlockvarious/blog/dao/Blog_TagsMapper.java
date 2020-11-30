package com.sherlockvarious.blog.dao;

import com.sherlockvarious.blog.entity.Blog_Tags;
import com.sherlockvarious.blog.entity.Blog_TagsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Blog_TagsMapper {
    long countByExample(Blog_TagsExample example);

    int deleteByExample(Blog_TagsExample example);

    int insert(Blog_Tags record);

    int insertSelective(Blog_Tags record);

    List<Blog_Tags> selectByExample(Blog_TagsExample example);

    int updateByExampleSelective(@Param("record") Blog_Tags record, @Param("example") Blog_TagsExample example);

    int updateByExample(@Param("record") Blog_Tags record, @Param("example") Blog_TagsExample example);
}