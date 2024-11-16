package com.zjtec.travel.controller;

import com.zjtec.travel.Application;
import com.zjtec.travel.constant.Const;
import com.zjtec.travel.domain.User;
import com.zjtec.travel.service.UserService;
import com.zjtec.travel.vo.ResMsg;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class RegisterController {

  private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private HttpSession session;

  // 用户注册
  @RequestMapping(value = "/signup")
  @ResponseBody
  public ResMsg signup(@RequestBody User user) {
    ResMsg resMsg = new ResMsg();

    // 校验验证码
    if (!validateCaptcha(user.getCode())) {
      resMsg.setErrcode("4");
      resMsg.setErrmsg("验证码不正确");
      return resMsg;
    }

    // 校验必填字段
    if (!validateUserInput(user)) {
      resMsg.setErrcode("3");
      resMsg.setErrmsg("注册表格输入框均不能为空");
      return resMsg;
    }

    // 检查用户名和邮箱是否已存在
    if (userService.existUserNameOrEmail(user.getUsername(), user.getEmail())) {
      resMsg.setErrcode("2");
      resMsg.setErrmsg("用户名或Email已存在");
      return resMsg;
    }

    // 设置用户默认属性
    user.setStatus(Const.USER_STATUS_INACTIVE);
    user.setCode(RandomStringUtils.random(20, Const.CHARSET_ALPHA)); // 设置激活码
    user.setRole(Const.USER_ROLE_MEMBER);

    // 保存用户
    if (userService.save(user)) {
      resMsg.setErrcode("0");
      resMsg.setErrmsg("注册成功");
      logger.info("注册成功，激活链接: http://localhost:8082/activation?username={}&code={}", user.getUsername(), user.getCode());
    } else {
      resMsg.setErrcode("1");
      resMsg.setErrmsg("注册失败，请稍后重试");
    }

    return resMsg;
  }

  // 激活用户
  @RequestMapping(value = "/activation")
  public String activation(ModelMap map, String username, String code) {
    if (StringUtils.isNoneBlank(username, code)) {
      boolean activationResult = userService.activeUser(username, code);
      if (activationResult) {
        map.put("msg", "激活成功，请点击跳转链接登录");
        map.put("redirect", "/login.html");
      } else {
        map.put("msg", "激活失败，点击跳转链接返回首页");
        map.put("redirect", "/");
      }
    } else {
      map.put("msg", "激活失败，点击跳转链接返回首页");
      map.put("redirect", "/");
    }

    map.put("title", "用户激活");
    return "msg";
  }

  // 校验验证码
  private boolean validateCaptcha(String captcha) {
    String sessionCaptcha = (String) session.getAttribute(Const.SESSION_KEY_CAPTCHA);
    if (sessionCaptcha == null) {
      // 提示验证码过期或不存在
      System.out.println("yanzhengma过期");
      return false;
    }
    return  sessionCaptcha.equalsIgnoreCase(captcha);

  }

  // 校验用户输入
  private boolean validateUserInput(User user) {
    return StringUtils.isNoneBlank(user.getUsername(), user.getPassword(), user.getEmail(),
            user.getName(), user.getTelephone(), user.getBirthday());
  }

}
