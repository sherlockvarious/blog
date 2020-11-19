package com.sherlockvarious.blog.Controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sherlockvarious.blog.dao.TypeMapper;
import com.sherlockvarious.blog.entity.Type;
import com.sherlockvarious.blog.entity.TypeExample;
import com.sherlockvarious.blog.service.TypeService;

import com.sherlockvarious.blog.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sunchao
 * @created at 2020-11-14-16:28
 */
@Controller
public class TypesController {

    @Resource
    TypeService typeService;

    @Resource
    TypeMapper typeMapper;

    @GetMapping("/page/admin/types")
    public String types(@RequestParam(defaultValue = "1") int pageNum,
                        @RequestParam(defaultValue = "10") int pageSize,
                        Model model) {

        PageInfo<Type> pageInfo = typeService.listType(pageNum, pageSize);


        model.addAttribute("pageInfo", pageInfo);

        System.out.println(pageInfo.getList().size());
        return "admin/types";
    }

    @PostMapping("/admin/types")
    public String publishType(Type type, RedirectAttributes attributes)  {
        if (!typeService.ifHas(type)){
            attributes.addFlashAttribute("message", "该标签已存在");
            return "redirect:/page/admin/type-publish";
        }

        if (typeService.add(type)) {
            attributes.addFlashAttribute("message", "添加成功");
        } else {
            attributes.addFlashAttribute("message", "添加失败");
        }

     return "redirect:/page/admin/types";
    }

    @RequestMapping("/admin/types/delete")
    public String delete(@RequestParam int id,  RedirectAttributes attributes) {

        if (typeService.deleteById(id) == true) {
           attributes.addFlashAttribute("message", "删除成功");
        } else {
            attributes.addFlashAttribute("message", "删除失败");
        }

        return "redirect:/page/admin/types";
    }
}
