package com.sherlockvarious.blog.Controller;

import com.sherlockvarious.blog.entity.Blog;
import com.sherlockvarious.blog.service.BlogService;
import com.sherlockvarious.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author sunchao
 * @created at 2020-11-21-10:02
 */
@Controller
public class BlogController {

    @Resource
    BlogService blogService;

    @Resource
    TypeService typeService;

    @RequestMapping("/page/admin/blogs")
    public String blogs(@RequestParam(defaultValue = "1")int pageNum,
                        @RequestParam(defaultValue = "10") int pageSize,
                        Model model) {

        model.addAttribute("types",  typeService.listAllType());
        model.addAttribute("pageInfo", blogService.listBlog(pageNum,pageSize));


        return "admin/blogs";
    }

    @RequestMapping("/admin/blogs/search")
    public String search(@RequestParam(defaultValue = "1")int page,
                        @RequestParam(defaultValue = "10") int pageSize,
                        Model model, Blog blog) {

        System.out.println(page);

        model.addAttribute("pageInfo", blogService.listConditionalBlog(page,pageSize,blog));


        return "admin/blogs :: blogList";
    }
}
