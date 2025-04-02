package com.wuying.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wuying.mapper.ArticleMapper;
import com.wuying.pojo.Article;
import com.wuying.pojo.PageBean;
import com.wuying.service.ArticleService;
import com.wuying.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    public void add(Article article) {
        //完善数据
        Integer id = (Integer) ThreadLocalUtil.getUserInfo().get("id");
        article.setCreateUser(id);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.add(article);
    }


    /**
     * 查询文章列表
     *
     * @param pageNum    页码
     * @param pageSize   每页大小
     * @param categoryId 文章分类ID
     * @param state      文章状态
     * @return 返回包含文章列表的PageBean对象
     */
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //1.创建PageBean对象
        PageBean<Article> pb = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum, pageSize);

        //3.调用mapper
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(userId, categoryId, state);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<Article> p = (Page<Article>) as;

        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public Article detail(Integer id) {
        return articleMapper.getArticleById(id);
    }

    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }
}
