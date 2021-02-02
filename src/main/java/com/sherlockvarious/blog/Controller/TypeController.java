package com.sherlockvarious.blog.Controller;

import com.sherlockvarious.blog.entity.Type;
import com.sherlockvarious.blog.service.BlogService;
import com.sherlockvarious.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sunchao
 * @created at 2021-01-12-20:16
 */
@Controller
public class TypeController {

    @Resource
    TypeService typeService;

    @Resource
    BlogService blogService;

    @RequestMapping("/types/{id}")
    public String types(@PathVariable int id, Model model,
                        @RequestParam(defaultValue = "1") int pageNum,
                        @RequestParam(defaultValue = "8") int pageSize) {
        List<Type> types = typeService.listTypeByPopularity(100);

        if (id == -1) {
            id = types.get(0).getId();
        }


        model.addAttribute("pageInfo", blogService.listBlogByTypes(pageNum, pageSize,id));
        model.addAttribute("types", types);
        model.addAttribute("activeTypeId", id);

        return "types";
    }
}
