package com.aaa.ms.controller;

import com.aaa.common.entity.Menu;
import com.aaa.ms.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;


    @DeleteMapping("delete")
    public int delete(Integer id){
        return menuService.deleteByPrimaryKey(id);
    }

    @PutMapping("update")
    public int update(@RequestBody Menu menu){
        return menuService.updateByPrimaryKey(menu);
    }

    @PostMapping("insert")
    public int insert(@RequestBody Menu menu){
        return menuService.insertSelective(menu);
    }

    @GetMapping("selectAll")
    public List<Menu> selectAll(){
        return menuService.selectAll();
    }


}
