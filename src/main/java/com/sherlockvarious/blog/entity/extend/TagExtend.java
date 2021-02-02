package com.sherlockvarious.blog.entity.extend;

/**
 * @author sunchao
 * @created at 2021-01-12-18:46
 */
public class TagExtend {
    //该tag有多少blog使用过
    //仅在进行首页推荐tags排序时会用到此变量
    int numsOfBlog;

    public int getNumsOfBlog() {
        return numsOfBlog;
    }

    public void setNumsOfBlog(int numsOfBlog) {
        this.numsOfBlog = numsOfBlog;
    }
}
