package com.afeng.admin.module.admin.controller;

import com.afeng.admin.framework.shiro.service.RegisterService;
import com.afeng.admin.module.admin.model.AjaxResult;
import com.afeng.admin.module.admin.model.User;
import com.afeng.admin.module.admin.service.IConfigService;
import com.afeng.admin.module.common.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 注册验证
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/admin")
public class RegisterController extends BaseController {
    @Autowired
    private RegisterService registerService;

    @Autowired
    private IConfigService configService;

    private String prefix = "admin/";


    @GetMapping("/register")
    public String register() {
        return prefix + "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public AjaxResult ajaxRegister(User user, HttpServletRequest request) {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return error("当前系统没有开启注册功能！");
        }
        String ip = IpUtils.getIpAddr(request);
        user.setIp(ip);
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }
}
