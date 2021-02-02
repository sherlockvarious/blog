package com.sherlockvarious.blog.Controller;

import com.sherlockvarious.blog.entity.Tag;
import com.sherlockvarious.blog.service.BlogService;
import com.sherlockvarious.blog.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sunchao
 * @created at 2021-02-02-14:24
 */
@Controller
public class TagController {


    @Resource
    TagService tagService;

    @Resource
    BlogService blogService;

    @RequestMapping("/tags/{id}")
    public String tag(@PathVariable int id, Model model,
                      @RequestParam(defaultValue = "1") int pageNum,
                      @RequestParam(defaultValue = "8") int pageSize){

        List<Tag> tags = tagService.listTagByPopularity(100);

        if (id == -1) {
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("pageInfo", blogService.listBlogByTags(pageNum,pageSize,id));
        model.addAttribute("activeTagId", id);
        return "tags";



    }
}
