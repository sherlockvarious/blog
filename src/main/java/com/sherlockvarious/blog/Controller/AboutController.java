package com.sherlockvarious.blog.Controller;

import com.sherlockvarious.blog.service.TagService;
import com.sherlockvarious.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author sunchao
 * @created at 2021-02-04-21:42
 */

@Controller
public class AboutController {


    @Resource
    TagService tagService;

    @Resource
    TypeService typeService;

    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("tags", tagService.listTagByPopularity(3));

        model.addAttribute("types", typeService.listTypeByPopularity(3));



        return "about";
    }



}
