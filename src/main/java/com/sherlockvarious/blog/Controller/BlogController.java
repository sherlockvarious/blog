package com.sherlockvarious.blog.Controller;

import com.sherlockvarious.blog.entity.Blog;
import com.sherlockvarious.blog.entity.User;
import com.sherlockvarious.blog.service.BlogService;
import com.sherlockvarious.blog.service.Blog_TagsService;
import com.sherlockvarious.blog.service.TagService;
import com.sherlockvarious.blog.service.TypeService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

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

    @Resource
    TagService tagService;

    @Resource
    Blog_TagsService blog_tagsService;

    @RequestMapping("/page/admin/blogs")
    public String blogs(@RequestParam(defaultValue = "1") int pageNum,
                        @RequestParam(defaultValue = "10") int pageSize,
                        Model model) {

        model.addAttribute("types", typeService.listAllType());
        model.addAttribute("pageInfo", blogService.listBlog(pageNum, pageSize));


        return "admin/blogs";
    }

    @RequestMapping("/admin/blog/publish")
    public String publishBlog(Blog blog, String tagIds, RedirectAttributes attributes, HttpSession session) {
        User user = (User) session.getAttribute("user");
        blog.setUserId(user.getId());

        if (blogService.saveNewBlog(blog) == true) {
            blog_tagsService.addNewRelationship(blog.getId(),tagIds);
        }

        return "redirect:/page/admin/blogs";
    }


    @RequestMapping("/admin/blogs/search")
    public String search(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "10") int pageSize,
                         Model model, Blog blog) {

        System.out.println(page);

        model.addAttribute("pageInfo", blogService.listConditionalBlog(page, pageSize, blog));


        return "admin/blogs :: blogList";
    }

    @RequestMapping("/admin/blogs/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes attributes) {
        if (blogService.deleteById(id)) {
            attributes.addFlashAttribute("message", "删除成功");
        } else {
            attributes.addFlashAttribute("message", "删除失败");
        }

        return "redirect:/page/admin/blogs";
    }

    @GetMapping("/page/admin/blogs-publish")
    public String inputBlog(Model model) {
        model.addAttribute("types", typeService.listAllType());
        model.addAttribute("tags", tagService.listAllTags());

        model.addAttribute("blog", new Blog());

        return "admin/blogs-publish";
    }

}
