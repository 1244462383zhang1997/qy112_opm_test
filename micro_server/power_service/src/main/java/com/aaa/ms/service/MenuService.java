package com.aaa.ms.service;

import com.aaa.common.entity.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {

    int deleteByPrimaryKey(Integer menuId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> selectAll();

}
