package cn.absalom.service;

import cn.absalom.pojo.Category;
import cn.absalom.util.Page;

import java.util.List;

public interface CategoryService {
    /*List<Category> list(Page page);
    int total();*/
    List<Category> list();

    void add(Category category);

    void delete(int id);

    Category get(int id);
    void update(Category category);
}
