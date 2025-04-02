package com.wuying.controller;


import com.wuying.mapper.ArticleMapper;
import com.wuying.pojo.Article;
import com.wuying.pojo.Category;
import com.wuying.pojo.Result;
import com.wuying.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 文章分类相关接口
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public Result add(@RequestBody @Validated(Category.Add.class) Category category) {
        categoryService.add(category);
        return Result.success();
    }

    @GetMapping()
    public Result<List<Category>> list() {
        return Result.success(categoryService.categoryList());
    }

    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
        return Result.success(categoryService.getCategoryById(id));
    }

    @PutMapping()
    public Result update(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping()
    public Result delete(Integer id) {
        categoryService.delete(id);
        return Result.success();
    }


}
