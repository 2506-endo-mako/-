package com.example.forum.controller;

import com.example.forum.controller.form.usersForm;
//import com.example.forum.service.ForumService;
import com.example.forum.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ForumController {
    @Autowired
    ForumService forumService;

    /*
     * 投稿内容表示処理
     */
    @GetMapping
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();
        // 投稿を全件取得
        List<usersForm> contentData = forumService.findAllUsers();
        // 画面遷移先を指定
        mav.setViewName("/top");
        // 投稿データオブジェクトを保管
        mav.addObject("contents", contentData);
        return mav;
    }
/*
 * 新規投稿画面表示
 */
@GetMapping("/new")
public ModelAndView newContent() {
    ModelAndView mav = new ModelAndView();
    // form用の空のentityを準備
    MessagesForm usersForm = new MessagesForm();
    // 画面遷移先を指定
    mav.setViewName("/new");
    // 準備した空のFormを保管
    mav.addObject("formModel", usersForm);
    return mav;
}
}
