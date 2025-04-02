package com.wuying.service;

import com.wuying.pojo.Category;

import java.util.List;

public interface CategoryService {
    void add(Category category);

    List<Category> categoryList();

    Category getCategoryById(Integer id);

    void update(Category category);

    void delete(Integer id);
}
