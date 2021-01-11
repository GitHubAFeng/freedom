package com.afeng.admin.framework.shiro.service;

import com.afeng.admin.module.admin.aspectj.AsyncFactory;
import com.afeng.admin.module.admin.aspectj.AsyncManager;
import com.afeng.admin.module.admin.model.User;
import com.afeng.admin.module.admin.service.IUserService;
import com.afeng.admin.module.common.constant.Constants;
import com.afeng.admin.module.common.constant.ShiroConstants;
import com.afeng.admin.module.common.constant.UserConstants;
import com.afeng.admin.module.common.utils.DateUtils;
import com.afeng.admin.module.common.utils.MessageUtils;
import com.afeng.common.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 注册校验方法
 *
 * @author ruoyi
 */
@Component
public class RegisterService {
    @Autowired
    private IUserService userService;

    /**
     * 注册
     */
    public String register(User user) {
        String msg = "", loginName = user.getLoginName(), password = user.getPassword();

        if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
            msg = "验证码错误";
        } else if (StringUtils.isEmpty(loginName)) {
            msg = "用户名不能为空";
        } else if (StringUtils.isEmpty(password)) {
            msg = "用户密码不能为空";
        } else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            msg = "密码长度必须在5到20个字符之间";
        } else if (loginName.length() < UserConstants.USERNAME_MIN_LENGTH
                || loginName.length() > UserConstants.USERNAME_MAX_LENGTH) {
            msg = "账户长度必须在2到20个字符之间";
        } else if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(loginName))) {
            msg = "保存用户'" + loginName + "'失败，注册账号已存在";
        } else {
            user.setPwdUpdateDate(DateUtils.getNowDate());
            user.setUserName(loginName);
            boolean regFlag = userService.registerUser(user);
            if (!regFlag) {
                msg = "注册失败,请联系系统管理人员";
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.REGISTER, MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }
}
