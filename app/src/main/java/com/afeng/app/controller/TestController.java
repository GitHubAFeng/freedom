package com.afeng.app.controller;


import com.afeng.common.component.cache.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author afeng
 * @date: 2021/1/2 13:07
 */

@Controller
public class TestController {


    @RequestMapping("/test")
    @ResponseBody
    public Object test() {
//        return personMapper.findPerson("2");

        RedisUtils.instance.setString("test", "222222", 60);
        return RedisUtils.instance.getString("test");

//        stringRedisTemplate.opsForValue().set("test", "222222", 60, TimeUnit.SECONDS);
//        return stringRedisTemplate.opsForValue().get("test");

    }

}
