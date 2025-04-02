package com.wuying.service;

import com.wuying.pojo.Article;
import com.wuying.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    Article detail(Integer id);

    void update(Article article);

    void delete(Integer id);
}
