package com.example.spring_boot.controller;

import com.example.spring_boot.controller.form.UserForm;
import com.example.spring_boot.repository.entity.User;
import com.example.spring_boot.service.UserService;
import com.example.spring_boot.util.PasswordHashEncode;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    /*
     * ログイン画面
     */
    @GetMapping
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        UserForm userForm = new UserForm();
        mav.setViewName("/login");
        mav.addObject("formModel", userForm);
        mav.addObject("errorMessages", session.getAttribute("errorMessages"));
        session.invalidate();
        return mav;
    }

    /*
     * ログイン処理
     */
    @PostMapping("/login")
    public ModelAndView login(@Validated @ModelAttribute("formModel") UserForm userForm, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        //バリデーション
        if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                // ここでメッセージを取得する。
                errorMessages.add(error.getDefaultMessage());
            }
            session.setAttribute("errorMessages", errorMessages);
            return new ModelAndView("redirect:/");
        }
        String account = userForm.getAccount();
        String password = userForm.getPassword();
        //パスワード暗号化
        //String encPassword = PasswordHashEncode.encode(password);
        User user = userService.selectUser(account, password);

        if (user == null) {
            session.setAttribute("errorMessages", "ログインに失敗しました");
            return new ModelAndView("redirect:/");
        }

        session.setAttribute("loginUser", user);
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        return new ModelAndView("redirect:/top");
    }

    /*
     * ログアウト処理
     */
    @GetMapping("/logout")
    public ModelAndView logout() {
        // セッションの破棄
        session.invalidate();
        return new ModelAndView("redirect:/");
    }
}
