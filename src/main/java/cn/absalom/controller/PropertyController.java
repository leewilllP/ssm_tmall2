package cn.absalom.controller;

import cn.absalom.pojo.Category;
import cn.absalom.pojo.Property;
import cn.absalom.service.CategoryService;
import cn.absalom.service.PropertyService;
import cn.absalom.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/*void add(Property property);
    void delete(int id);
    void update(Property property);
    Property get(int id);
    List list(int cid);
* */
@Controller
@RequestMapping("")
public class PropertyController {
    @Autowired
    PropertyService propertyService;
    @Autowired
    CategoryService categoryService;
    @RequestMapping("admin_property_add")
    public String add(Model model, Property property){
        propertyService.add(property);
        return "redirect:admin_property_list?cid="+property.getCid();//查询产品号
    }
    @RequestMapping("admin_property_delete")
    public String delete(int id){
        Property p = propertyService.get(id);   //获取分类id
        propertyService.delete(id);             //操作删除id
        return "redirect:admin_property_list?cid="+p.getCid(); //返回删除链接
    }
    @RequestMapping("admin_property_edit")
    public String edit(Model model,int id){
        Property p = propertyService.get(id);          //取id
        Category c = categoryService.get(p.getCid());   //取外键id
        p.setCategory(c);                               //新增产品分类
        model.addAttribute("p",p);                  //传输数据给editProperty
        return "admin/editProperty";
    }
    @RequestMapping("admin_property_update")
    public String update(Property property){
        propertyService.update(property);
        return "redirect:admin_property_list?cid="+property.getCid(); //改+号
    }
    @RequestMapping("admin_property_list")
    public String list(int cid, Model model, Page page){
        Category c = categoryService.get(cid);
        List<Property> ps = propertyService.list(cid);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+c.getId());

        model.addAttribute("ps",ps);
        model.addAttribute("c",c);
        model.addAttribute("page",page);

        return "admin/listProperty";
    }

}
