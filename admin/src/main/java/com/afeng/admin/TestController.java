package com.afeng.admin;

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
    public String test() {

        return "ok";
    }

}
