package com.example.spring_boot.controller;

import com.example.spring_boot.controller.form.CommentForm;
import com.example.spring_boot.controller.form.MessageForm;
import com.example.spring_boot.controller.form.UserForm;
import com.example.spring_boot.repository.MessageRepository;
import com.example.spring_boot.repository.entity.User;
import com.example.spring_boot.service.CommentService;
import com.example.spring_boot.service.MessageService;
import com.example.spring_boot.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TopController {
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    HttpSession session;


    /*
     * TOP画面表示処理
     */
    @GetMapping("/top")
    //required = falseで@RequestParamで値の入力が何もなくてもエラーにならない
    public ModelAndView top(@RequestParam(name = "start_date", required = false) String startDate,
                            @RequestParam(name = "end_date", required = false) String endDate,
                            @RequestParam(name = "category", required = false) String category) {

        ModelAndView mav = new ModelAndView();
        //セッションから取得
        User loginUser = (User) session.getAttribute("loginUser");
        // messagesテーブルの情報を全件取得
        List<MessageForm> contentData = messageService.findAllMessages(startDate, endDate, "%" + category + "%");
        // 画面遷移先を指定
        mav.setViewName("/top");
        // 投稿データオブジェクトを保管
        mav.addObject("contentData", contentData);
        mav.addObject("startDate", startDate);
        mav.addObject("endDate", endDate);
        mav.addObject("category", category);
        mav.addObject("loginUser",loginUser);

        List<UserForm> userData = userService.findAllUser();
        // 画面遷移先を指定
        mav.setViewName("/top");
        //空の箱を準備
        MessageForm messageForm = new MessageForm();
        mav.addObject("formModel", messageForm);
        // 投稿データオブジェクトを保管
        mav.addObject("userData", userData);

        //コメント内容表示処理
        // 投稿を全件取得
        List<CommentForm> commentData = commentService.findAllComment();
        // 画面遷移先を指定
        mav.setViewName("/top");
        // コメント返信オブジェクトを保管
        mav.addObject("commentData", commentData);
        mav.addObject("MessageId", session.getAttribute("MessageId"));
        mav.addObject("commentErrorMessage", session.getAttribute("commentErrorMessage"));
        mav.addObject("errorMessages", session.getAttribute("errorMessages"));
        //コメント返信用に空のcommentFormを準備

        session.removeAttribute("errorMessages");
        return mav;
    }

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/new")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        // 画面遷移先を指定
        mav.setViewName("/new");
        // 準備した空のFormを保管
        MessageForm messageForm = (MessageForm) session.getAttribute("messageForm");
        if(messageForm == null) {
            messageForm = new MessageForm();
        }
        mav.addObject("formModel", messageForm);
        return mav;
    }


    /*
     * 新規投稿処理
     */
    @PostMapping("/add")
    public ModelAndView addContent(@Validated @ModelAttribute("formModel") MessageForm messageForm,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) throws ParseException {
        //バリデーション
        if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                // ここでメッセージを取得する。
                errorMessages.add(error.getDefaultMessage());
            }
            session.setAttribute("messageForm",messageForm);
            redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
            return new ModelAndView("redirect:/new");
        }

        //セッションに登録されているユーザ情報から、ユーザID取得しmessageFormにセット
//        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
//        messageForm.setUserId(loginUser.getId());

        // 投稿をテーブルに格納
        messageService.saveMessages(messageForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/top");
    }

    /*
     * コメント返信投稿処理
     */
    @PostMapping("/commentAdd")
    public ModelAndView addComment(@Validated @ModelAttribute("formModel") CommentForm commentForm,
                                   BindingResult result) {
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                String commentErrorMessage = error.getDefaultMessage();

                ModelAndView mav = new ModelAndView();
                //MessageIdはメッセージとコメントの紐づけのため直接送るcommentsテーブルのmessageId
                int MessageId = commentForm.getMessageId();
                session.setAttribute("MessageId", MessageId);
                session.setAttribute("commentErrorMessage", commentErrorMessage);
                // rootへリダイレクト
                return new ModelAndView("redirect:/top");
            }
        }
        // 投稿をテーブルに格納
        commentService.saveComment(commentForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/top");
    }

    /*
     *コメント削除処理
     */
    @DeleteMapping("/commentDelete/{id}")
    public ModelAndView deleteComment(@PathVariable Integer id) {
        // 投稿をテーブルに格納
        commentService.deleteComment(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/top");
    }

    /*
     * 投稿削除処理
     */
    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteContent(@PathVariable Integer id) {
        // 投稿をテーブルに格納
        messageService.deleteMessages(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/top");
    }

}
