package com.sherlockvarious.blog.Controller;

import com.sherlockvarious.blog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author sunchao
 * @created at 2021-02-03-21:50
 */
@Controller
public class ArchivesController {

    @Resource
    BlogService blogService;

    @RequestMapping("/archives")
    public String blog(Model model){

        model.addAttribute("blogCount",blogService.countBlogs() );
        model.addAttribute("archiveMap", blogService.archiveBlog());
        return "archives";
    }
}
