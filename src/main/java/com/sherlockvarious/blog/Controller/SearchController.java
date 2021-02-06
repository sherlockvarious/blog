package com.sherlockvarious.blog.Controller;

import com.sherlockvarious.blog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author sunchao
 * @created at 2021-02-06-14:33
 */
@Controller
public class SearchController {

    @Resource
    BlogService blogService;

    @RequestMapping("/search")
    public String search(@RequestParam(defaultValue = "1") int pageNum,
                         @RequestParam(defaultValue = "10") int pageSize,
                         Model model,String query){
        model.addAttribute("pageInfo", blogService.listBlogByKeyword(pageNum,pageSize,query));


        return "search";
    }
}
