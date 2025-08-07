package com.example.spring_boot.controller;

import com.example.spring_boot.controller.form.CommentForm;
import com.example.spring_boot.controller.form.MessageForm;
import com.example.spring_boot.repository.CommentRepository;
import com.example.spring_boot.repository.MessageRepository;
import com.example.spring_boot.repository.entity.Message;
import com.example.spring_boot.service.CommentService;
import com.example.spring_boot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TopController {
    @Autowired
    MessageService messageService;
    @Autowired
    CommentService commentService;
    @Autowired
    MessageRepository messageRepository;


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
            List<MessageForm> contentData = messageService.findAllMessages(startDate,endDate,"%" + category + "%");
            // 画面遷移先を指定
            mav.setViewName("/top");
            // 投稿データオブジェクトを保管
            mav.addObject("contentData", contentData);
            mav.addObject("startDate", startDate);
            mav.addObject("endDate", endDate);
            mav.addObject("category", category);

            //コメント内容表示処理
            // 投稿を全件取得
            List<CommentForm> commentData = commentService.findAllComment();
            // 画面遷移先を指定
            mav.setViewName("/top");
            // コメント返信オブジェクトを保管
            mav.addObject("commentData", commentData);
            //コメント返信用に空のcommentFormを準備
            mav.addObject("formModel", new CommentForm());
            return mav;
    }

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/new")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        MessageForm messagesForm = new MessageForm();
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
    public ModelAndView addContent(@ModelAttribute("formModel") MessageForm messagesForm){
        // 投稿をテーブルに格納
        messageService.saveMessages(messagesForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }


}
