package per.liuchengyin.alipay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName ThymeleafController
 * @Description Thymeleaf-Controller
 * @Author 柳成荫
 * @Date 2021/3/23
 */
@Controller
public class ThymeleafController {
    @RequestMapping("/hello")
    public String hello(Model model){
        return "index";
    }

    @RequestMapping("/success.html")
    public String success(Model model){
        model.addAttribute("name", "柳成荫");
        return "success";
    }
}
