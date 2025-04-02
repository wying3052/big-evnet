package com.wuying.service.impl;


import com.wuying.mapper.ArticleMapper;
import com.wuying.mapper.CategoryMapper;
import com.wuying.pojo.Article;
import com.wuying.pojo.Category;
import com.wuying.service.CategoryService;
import com.wuying.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleMapper articleMapper;

    public void add(Category category) {
        //完善category信息
        Integer id = (Integer) ThreadLocalUtil.getUserInfo().get("id");
        category.setCreateUser(id);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.add(category);
    }


    public List<Category> categoryList() {
        Integer id = (Integer) ThreadLocalUtil.getUserInfo().get("id");
        return categoryMapper.getCategoryList(id);
    }


    public Category getCategoryById(Integer id) {
        return categoryMapper.getCategoryById(id);
    }


    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }


    public void delete(Integer id) {
        //先判断是否有文章关联
        List<Article> articleList = articleMapper.getArticleListByCategoryId(id);
        if (!articleList.isEmpty()) {
            throw new RuntimeException("该分类下有文章，无法删除");
        }
        categoryMapper.delete(id);
    }
}
