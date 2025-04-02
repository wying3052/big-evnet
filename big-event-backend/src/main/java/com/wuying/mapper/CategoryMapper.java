package com.wuying.mapper;

import com.wuying.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CategoryMapper {

    void add(Category category);

    @Select("select * from category where create_user =#{id}")
    List<Category> getCategoryList(Integer id);

    @Select("select * from category where id = #{id}")
    Category getCategoryById(Integer id);

    @Update("update category set category_name = #{categoryName},category_alias = #{categoryAlias},update_time =#{updateTime} where id = #{id}")
    void update(Category category);

    @Delete("delete from category where id = #{id}")
    void delete(Integer id);
}

