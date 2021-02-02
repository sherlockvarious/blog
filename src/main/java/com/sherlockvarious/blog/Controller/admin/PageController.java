package com.sherlockvarious.blog.Controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 页面跳转Controller
 */

@Controller
public class PageController {

    /**
     * 页面跳转方法
     */
    @RequestMapping("page/{path}")
    public String showPage(@PathVariable String path){
        return path;
    }

    @RequestMapping("page/{path1}/{path2}")
    public String showPageSecond(@PathVariable String path1,@PathVariable String path2){

        String path = path1+"/"+path2;
        return path;
    }

}
