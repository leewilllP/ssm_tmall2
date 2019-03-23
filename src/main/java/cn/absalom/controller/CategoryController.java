package cn.absalom.controller;

import cn.absalom.pojo.Category;
import cn.absalom.service.CategoryService;
import cn.absalom.util.ImageUtil;
import cn.absalom.util.Page;
import cn.absalom.util.UploadedImageFile;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @RequestMapping("admin_category_list")
    public String list(Model model,Page page){
        /*
        * PageHelper
        * */
        PageHelper.offsetPage(page.getStart(),page.getCount());//指定分页参数
        List<Category> cs = categoryService.list();//list
        /*int total = categoryService.total();//带页数*/
        int total = (int) new PageInfo<>(cs).getTotal();  //通过pageInfo获取总数
        page.setTotal(total);//设置页数
        model.addAttribute("cs",cs);//返回列表
        model.addAttribute("page",page);//返回页数
        return "admin/listCategory";
    }
    /*映射到add上*/
    @RequestMapping("admin_category_add")
    public String add(Category c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        System.out.println(c.getId());
        categoryService.add(c);
        System.out.println(c.getId());
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));//获取当前路径
        File file = new File(imageFolder,c.getId()+".jpg");//保存图片路径
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();  //检查是否存在文件夹
        System.out.println(uploadedImageFile);
        System.out.println(uploadedImageFile.getImage());
        System.out.println(file);
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage image = ImageUtil.change2jpg(file); //检查文件是否合法
        ImageIO.write(image,"jpg",file);     //写入图片
        return "redirect:/admin_category_list";             //重定向
    }
    @RequestMapping("admin_category_delete")
    public String delete(int id,HttpSession session){
        categoryService.delete(id);
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,id+".jpg");
        file.delete();
        return "redirect:/admin_category_list";
    }
    @RequestMapping("admin_category_edit")
    public String get(int id,Model model) throws IOException{
        Category c = categoryService.get(id);
        model.addAttribute("c",c);
        return "admin/editCategory";
    }
    @RequestMapping("admin_category_update")
    private String update(Category category,HttpSession session,UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.update(category);//接收分类名称 session获取路径 upload接收上传图片
        MultipartFile image = uploadedImageFile.getImage();
        if (null!=image && !image.isEmpty()){ //判断是否上传图片，有则用session判断路径 再存  没有就return
            File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder, category.getId()+"jpg");
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img,"jpg",file);

        }
        return "redirect:/admin_category_list";
    }
}
