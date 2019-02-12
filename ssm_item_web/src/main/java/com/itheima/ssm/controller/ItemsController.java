package com.itheima.ssm.controller;


import com.itheima.ssm.domain.Items;
import com.itheima.ssm.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    private ItemsService itemsService;

    @RequestMapping("/queryItems.action")
    public ModelAndView queryItems() throws Exception{
        ModelAndView mv = new ModelAndView();
        List<Items> itemsList = itemsService.queryItems();
        mv.addObject("itemsList",itemsList);
        mv.setViewName("itemsList");
        System.out.println(itemsList);
        return mv;
    }
    @RequestMapping("/editItems.action")
    public String save(Items items) throws Exception{

        itemsService.save(items);
        return "redirect:queryItems.action";
    }
    @RequestMapping("/updateItems.action")
    public String fileuoload2(HttpServletRequest request, MultipartFile pictureFile) throws Exception {


        // 使用fileupload组件完成文件上传
        // 上传的位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        // 判断，该路径是否存在
        File file = new File(path);
        if(!file.exists()){
            // 创建该文件夹
            file.mkdirs();
        }

        // 说明上传文件项
        // 获取上传文件的名称
        String filename = pictureFile.getOriginalFilename();
        // 把文件的名称设置唯一值，uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid+"_"+filename;
        // 完成文件上传
        pictureFile.transferTo(new File(path,filename));

        return "success";
    }
}
