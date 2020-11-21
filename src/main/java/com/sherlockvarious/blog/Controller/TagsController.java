package com.sherlockvarious.blog.Controller;

import com.github.pagehelper.PageInfo;
import com.sherlockvarious.blog.entity.Tag;
import com.sherlockvarious.blog.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author sunchao
 * @created at 2020-11-21-8:42
 */

@Controller
public class TagsController {

    @Resource
    TagService tagService;


    @RequestMapping("/admin/tags/input/{id}")
    public String toEdittag(@PathVariable int id, Model model){
        model.addAttribute("tag", tagService.selectById(id));
        return "admin/tag-publish";

    }
    @GetMapping("/page/admin/tags")
    public String tags(@RequestParam(defaultValue = "1") int pageNum,
                        @RequestParam(defaultValue = "10") int pageSize,
                        Model model) {

        PageInfo<Tag> pageInfo = tagService.listTag(pageNum, pageSize);


        model.addAttribute("pageInfo", pageInfo);

        System.out.println(pageInfo.getList().size());
        return "admin/tags";
    }



    @PostMapping("/admin/tags")
    public String publishTag(Tag tag, RedirectAttributes attributes, Model model){
        if (!tagService.ifHas(tag)){
            model.addAttribute("message", "该标签已存在");
            return "admin/tag-publish";
        }

        if (tagService.add(tag)) {
            attributes.addFlashAttribute("message", "添加成功");
        } else {
            attributes.addFlashAttribute("message", "添加失败");
        }

        return "redirect:/page/admin/tags";
    }


    @PostMapping("/admin/tags/{id}")
    public String editTag(@Valid Tag tag, @PathVariable int id, RedirectAttributes attributes, Model model)  {

        if (!tagService.ifHas(tag)){
            model.addAttribute("message", "不能重复添加标签");
            return "admin/tag-publish";
        }

        if (tagService.edit(tag)) {
            attributes.addFlashAttribute("message", "修改成功");
        } else {
            attributes.addFlashAttribute("message", "修改失败");
        }

        return "redirect:/page/admin/tags";
    }
    @RequestMapping("/admin/tags/delete/{id}")
    public String delete(@PathVariable int id,  Model model) {

        if (tagService.deleteById(id) == true) {
            model.addAttribute("message", "删除成功");
        } else {
            model.addAttribute("message", "删除失败");
        }

        return "redirect:/page/admin/tags";
    }

}
