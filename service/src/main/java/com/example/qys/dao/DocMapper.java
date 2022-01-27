package com.example.qys.dao;

import com.example.qys.entity.Doc;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DocMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(Doc record);

    int insertSelective(Doc record);

    Doc selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Doc record);

    int updateByPrimaryKey(Doc record);
}