package com.example.qys.dao;

import com.example.qys.entity.File;

public interface FileMapper {
    int deleteByPrimaryKey(Integer uuid);

    int insert(File record);

    int insertSelective(File record);

    File selectByPrimaryKey(Integer uuid);

    int updateByPrimaryKeySelective(File record);

    int updateByPrimaryKey(File record);
}