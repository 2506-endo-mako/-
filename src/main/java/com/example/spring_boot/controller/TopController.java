package com.example.spring_boot.controller;

import com.example.spring_boot.controller.form.MessagesForm;
import com.example.spring_boot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TopController {
    @Autowired
    MessageService messageService;

    /*
     * TOP画面表示処理
     */
    @GetMapping
    //required = falseで@RequestParamで値の入力が何もなくてもエラーにならない
    public ModelAndView top(@RequestParam(name = "start_date", required = false) String startDate,
                            @RequestParam(name = "end_date", required = false) String endDate,
                            @RequestParam(name = "category", required = false) String category) {

            ModelAndView mav = new ModelAndView();
            // タスクを全件取得
            List<MessagesForm> contentData = messageService.findAllMessages(startDate,endDate,category);
            // 画面遷移先を指定
            mav.setViewName("/top");
            // 投稿データオブジェクトを保管
            mav.addObject("contentData", contentData);
        return mav;

    }

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/new")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        MessagesForm messagesForm = new MessagesForm();
        // 画面遷移先を指定
        mav.setViewName("/new");
        // 準備した空のFormを保管
        mav.addObject("formModel", messagesForm);
        return mav;
    }

    /*
     * 新規投稿処理
     */
    @PostMapping("/add")
    public ModelAndView addContent(@ModelAttribute("formModel") MessagesForm messagesForm){
        // 投稿をテーブルに格納
        messageService.saveMessages(messagesForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }


}
