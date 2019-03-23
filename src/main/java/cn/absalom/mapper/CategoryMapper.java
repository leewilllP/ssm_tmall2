package cn.absalom.mapper;

import cn.absalom.pojo.Category;
import cn.absalom.util.Page;

import java.util.List;

public interface CategoryMapper {
    List<Category> list(Page page);
    int total();
    void add(Category category);
    void delete(int id);
    Category get(int id);
    void update(Category category);
}
