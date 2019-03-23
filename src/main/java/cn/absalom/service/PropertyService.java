package cn.absalom.service;
/*
 * 分类表
 * */

import cn.absalom.pojo.Property;

import java.util.List;

public interface PropertyService {
    /*
     * 新增CRUD
     * */
    void add(Property property);
    void delete(int id);
    void update(Property property);
    Property get(int id);
    List list(int cid);  //因为在业务上需要查某个分类的id,所以带上cid

}
