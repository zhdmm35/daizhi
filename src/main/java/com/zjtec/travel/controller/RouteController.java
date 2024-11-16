package com.zjtec.travel.controller;

import com.zjtec.travel.domain.PageBean;
import com.zjtec.travel.domain.Route;
import com.zjtec.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/route")
public class RouteController {

  @Autowired
  private RouteService routeService;

  @RequestMapping("/pageQuery")
  @ResponseBody
  public PageBean<Route> pageQuery(@RequestParam("cid") Integer cid,@RequestParam(value="pageSize",required = false, defaultValue = "10") Integer pageSize,@RequestParam(value="currentPage",required = false, defaultValue = "1") Integer currentPage){
    //TODO:完成pageQuery 功能
    System.out.println("cid: " + cid + ", pageSize: " + pageSize + ", currentPage: " + currentPage);
    // 这里可以加上日志输出，检查传入的参数是否为 null 或非法
    if (cid == null || pageSize == null || currentPage == null) {
      // 处理参数异常
      return null;
    }


    PageBean<Route> pageBean = routeService.pageQuery(cid, currentPage, pageSize);
    return pageBean;
  }
}
