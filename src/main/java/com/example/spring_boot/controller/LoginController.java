package com.example.spring_boot.controller;

import com.example.spring_boot.controller.form.UserForm;
import com.example.spring_boot.repository.entity.User;
import com.example.spring_boot.service.UserService;
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
import java.util.Optional;

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
        mav.addObject("account",session.getAttribute("account"));
        mav.addObject("password",session.getAttribute("password"));
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

        mav.addObject("account",userForm.getAccount());
        mav.addObject("password",userForm.getPassword());

        //バリデーション
        if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                // ここでメッセージを取得する。
                errorMessages.add(error.getDefaultMessage());
            }
            session.setAttribute("account",userForm.getAccount());
            session.setAttribute("password",userForm.getPassword());
            session.setAttribute("errorMessages", errorMessages);
            return new ModelAndView("redirect:/");
        }
        String account = userForm.getAccount();
        String password = userForm.getPassword();
        // UserServiceのauthenticateUserメソッドを呼び出す
        Optional<User> userOptional = userService.authenticateUser(account, password);
        if (userOptional.isPresent()) {
            // OptionalからUserオブジェクトを取り出す
            User user = userOptional.get();
            // ログイン成功
            session.setAttribute("account", user.getAccount());
            session.setAttribute("password", user.getPassword());
            session.setAttribute("departmentId", user.getDepartmentId());
            session.setAttribute("loginUser", user);  // Userエンティティをセッションに保存
            return new ModelAndView("redirect:/top");
        } else {
            // ログイン失敗
            session.setAttribute("errorMessages", "ログインに失敗しました");
            return new ModelAndView("redirect:/");
        }
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
