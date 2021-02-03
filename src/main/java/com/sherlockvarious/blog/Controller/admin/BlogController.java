package com.sherlockvarious.blog.Controller.admin;

import com.sherlockvarious.blog.entity.Blog;
import com.sherlockvarious.blog.entity.User;
import com.sherlockvarious.blog.service.BlogService;
import com.sherlockvarious.blog.service.Blog_TagsService;
import com.sherlockvarious.blog.service.TagService;
import com.sherlockvarious.blog.service.TypeService;
import com.sun.mail.imap.protocol.ID;
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
import java.util.ArrayList;
import java.util.Date;
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


    //进入后台的博客首页
    @RequestMapping("/page/admin/blogs")
    public String blogs(@RequestParam(defaultValue = "1") int pageNum,
                        @RequestParam(defaultValue = "10") int pageSize,
                        Model model) {

        model.addAttribute("types", typeService.listAllType());
        model.addAttribute("pageInfo", blogService.listBlog(pageNum, pageSize));


        return "admin/blogs";
    }


    //发布新博客
    @RequestMapping("/admin/blog/publish")
    public String publishBlog(Blog blog, String tagIds, RedirectAttributes attributes, HttpSession session,Model model) {

        if(blog.getTypeId()==null||"".equals(tagIds)){
            model.addAttribute("message", "类型不能为空");
            model.addAttribute("types", typeService.listAllType());
            model.addAttribute("tags", tagService.listAllTags());
            return "admin/blogs-publish";
        }
        if (blog.getId() == null) {
            User user = (User) session.getAttribute("user");
            blog.setUserId(user.getId());

            if (blogService.saveNewBlog(blog) == true) {
                blog_tagsService.addNewRelationship(blog.getId(), tagIds);
                attributes.addFlashAttribute("message", "操作成功");
                return "redirect:/page/admin/blogs";
            }

            attributes.addFlashAttribute("message", "操作失败");

        }else {
            blog.setUpdateTime(new Date());
            blogService.updateBlog(blog);
            System.out.println(blogService.findTags(blog.getId()));
            if(!(blogService.findTags(blog.getId()).equals(tagIds))){
                blog_tagsService.deleteByBlogId(blog.getId());
                blog_tagsService.addNewRelationship(blog.getId(), tagIds);
            }
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
        if (!blog_tagsService.deleteByBlogId(id)) {
            attributes.addFlashAttribute("message", "删除失败");
            return "redirect:/page/admin/blogs";
        }
        if (blogService.deleteById(id)) {
            attributes.addFlashAttribute("message", "删除成功");
        } else {
            attributes.addFlashAttribute("message", "删除失败");
        }

        return "redirect:/page/admin/blogs";
    }

    @RequestMapping("/admin/blogs/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("types", typeService.listAllType());
        model.addAttribute("tags", tagService.listAllTags());

        Blog blog = blogService.getBlog(id);

        blog.setTagIds(blogService.findTags(id));

        model.addAttribute("blog", blog);

        return "admin/blogs-publish";
    }

    @GetMapping("/page/admin/blogs-publish")
    public String inputBlog(Model model) {
        model.addAttribute("types", typeService.listAllType());
        model.addAttribute("tags", tagService.listAllTags());

        model.addAttribute("blog", new Blog());

        return "admin/blogs-publish";
    }

}
