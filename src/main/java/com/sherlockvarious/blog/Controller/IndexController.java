package com.sherlockvarious.blog.Controller;

import com.sherlockvarious.blog.service.BlogService;
import com.sherlockvarious.blog.service.TagService;
import com.sherlockvarious.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Types;

/**
 * @author sunchao
 * @created at 2020-12-07-15:20
 */

@Controller
public class IndexController {

    @Resource
    BlogService blogService;

    @Resource
    TypeService typeService;

    @Resource
    TagService tagService;


    @RequestMapping("/page/index")
    public String index(@RequestParam(defaultValue = "1") int pageNum,
                        @RequestParam(defaultValue = "10") int pageSize,
                        Model model){

        model.addAttribute("page", blogService.listBlog(pageNum, pageSize));
        model.addAttribute("types", typeService.listTypeByPopularity(10));
        model.addAttribute("tags",tagService.listTagByPopularity(6));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogs(3));
        return "index";

    }
}
