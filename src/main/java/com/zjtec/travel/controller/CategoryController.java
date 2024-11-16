package com.zjtec.travel.controller;

import com.zjtec.travel.domain.Category;
import com.zjtec.travel.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

  private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

  @Autowired
  private CategoryService categoryService;

  @RequestMapping(value="/findAll")
  @ResponseBody
  public List<Category> findAll(){
    logger.info("开始查找旅游目录12");
    //TODO:完成查询所有产品目录的功能
    logger.info("Starting to fetch all categories");
    return categoryService.findAll();
  }
}
