package com.afeng.admin.module.common.utils;

import com.afeng.admin.common.cache.RedisUtils;
import com.afeng.admin.common.util.SessionUtil;
import com.afeng.admin.framework.core.constant.Constants;
import com.afeng.admin.framework.exception.AdminException;
import com.afeng.admin.module.admin.model.Menu;
import com.afeng.admin.module.admin.model.User;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * 数据库角色主键为1是管理员
 *
 * @author afeng
 * @date: 2020/5/9 10:06
 */
public class AdminUtils {

    private static final String SysUserPermissionCacheKey = "sys-perm:admin:";//后台用户权限缓存KEY前缀

    public static User getSysUser() {
        User user = (User) SessionUtil.getSessionObject(Constants.CURRENT_USER);
        if (user == null) {
            throw AdminException.toAccessDenied("系统用户不存在");
        }
        return user;
    }

    //判断用户是否拥有某个权限
    public static boolean isPermitted(RedisUtils redisUtils, String permission) {
        if (getSysUser().isAdmin()) return true;
        return redisUtils.sHasKey(SysUserPermissionCacheKey + getSysUser().getUserId(), permission);
    }

    //判断用户是否拥有某个角色
    public static boolean hasRole(String role) {
        //TODO 未实现
        return true;
    }

    /**
     * 保存用户权限缓存
     *
     * @param redisUtils 缓存
     * @param menus      菜单列表
     */
    public static void setSysUserPermissionCache(RedisUtils redisUtils, List<Menu> menus) {
        if (menus != null && menus.size() > 0) {
            String cacheKey = SysUserPermissionCacheKey + getSysUser().getUserId();
            if (!redisUtils.exits(cacheKey)) {
                for (Menu item : menus) {
                    if (StringUtils.isNotEmpty(item.getPerms())) {
                        redisUtils.sSet(cacheKey, item.getPerms());
                    }
                }
            }
        }
    }

    /**
     * 获取用户权限缓存
     *
     * @param redisUtils 缓存
     */
    public static Set<Object> getSysUserPermissionCache(RedisUtils redisUtils) {
        String cacheKey = SysUserPermissionCacheKey + getSysUser().getUserId();
        return redisUtils.sGet(cacheKey);
    }

    //清除权限缓存
    public static void cleanSysUserPermissionCache(RedisUtils redisUtils) {
        String cacheKey = SysUserPermissionCacheKey + getSysUser().getUserId();
        redisUtils.del(cacheKey);
    }

}
