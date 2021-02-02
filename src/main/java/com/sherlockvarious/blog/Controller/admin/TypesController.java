package com.sherlockvarious.blog.Controller.admin;

import com.github.pagehelper.PageInfo;
import com.sherlockvarious.blog.entity.Type;
import com.sherlockvarious.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.annotation.Resource;
import javax.validation.Valid;


/**
 * @author sunchao
 * @created at 2020-11-14-16:28
 */
@Controller
public class TypesController {

    @Resource
    TypeService typeService;



    @RequestMapping("/admin/types/input/{id}")
    public String toEditType(@PathVariable int id,Model model){
        model.addAttribute("type", typeService.selectById(id));
        return "admin/type-publish";

    }
    @GetMapping("/page/admin/types")
    public String types(@RequestParam(defaultValue = "1") int pageNum,
                        @RequestParam(defaultValue = "10") int pageSize,
                        Model model) {

        PageInfo<Type> pageInfo = typeService.listType(pageNum, pageSize);


        model.addAttribute("pageInfo", pageInfo);

        return "admin/types";
    }



    @PostMapping("/admin/types")
    public String publishType(Type type, RedirectAttributes attributes,Model model){
        if (!typeService.ifHas(type)){
            model.addAttribute("message", "该标签已存在");
            return "admin/type-publish";
        }

        if (typeService.add(type)) {
            attributes.addFlashAttribute("message", "添加成功");
        } else {
            attributes.addFlashAttribute("message", "添加失败");
        }

     return "redirect:/page/admin/types";
    }


    @PostMapping("/admin/types/{id}")
    public String editType(@Valid Type type,@PathVariable int id, RedirectAttributes attributes,Model model)  {

        if (!typeService.ifHas(type)){
            model.addAttribute("message", "不能重复添加标签");
            return "admin/type-publish";
        }

        if (typeService.edit(type)) {
            attributes.addFlashAttribute("message", "修改成功");
        } else {
            attributes.addFlashAttribute("message", "修改失败");
        }

        return "redirect:/page/admin/types";
    }
    @RequestMapping("/admin/types/delete/{id}")
    public String delete(@PathVariable int id,  RedirectAttributes attributes) {

        if (typeService.deleteById(id) == true) {
            attributes.addFlashAttribute("message", "删除成功");
        } else {
            attributes.addFlashAttribute("message", "删除失败");
        }

        return "redirect:/page/admin/types";
    }
}
