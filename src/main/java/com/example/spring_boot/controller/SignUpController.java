package com.example.spring_boot.controller;

import com.example.spring_boot.controller.form.SignUpForm;
import com.example.spring_boot.controller.form.UserEditForm;
import com.example.spring_boot.controller.form.UserForm;
import com.example.spring_boot.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SignUpController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    /*
     * ユーザー登録画面表示
     */
    @GetMapping("/signUp")
    public ModelAndView signUpContent() {
        ModelAndView mav = new ModelAndView();
        SignUpForm signUpForm = (SignUpForm) session.getAttribute("signUpForm");
        if(signUpForm == null) {
            signUpForm = new SignUpForm();
        }
        // 画面遷移先を指定
        mav.setViewName("/signUp");
        // 準備した空のFormを保管
        mav.addObject("formModel", signUpForm);
        mav.addObject("errorMessages", session.getAttribute("errorMessages"));
        session.removeAttribute("errorMessages");
        return mav;
    }

    /*
     * 新規ユーザー登録処理
     */
    @PostMapping("/signUpAdd")
    public ModelAndView addContent(@Validated @ModelAttribute("formModel") SignUpForm signUpForm,
                                   BindingResult result) throws ParseException {
        //バリデーション
        if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                // ここでメッセージを取得する。
                errorMessages.add(error.getDefaultMessage());
            }
            session.setAttribute("signUpForm",signUpForm);
            session.setAttribute("errorMessages", errorMessages);
            return new ModelAndView("redirect:/signUp");
        }
        List<String> errorMessages = new ArrayList<>();
        //パスワードと確認パスワードが違う場合
        String password = signUpForm.getPassword();
        String confirmPassword = signUpForm.getConfirmPassword();
        if(!(password.equals(confirmPassword))) {
            errorMessages.add("パスワードと確認用パスワードが一致しません");
        }
        //支社と部署の組み合わせが不正の時
        if((signUpForm.getBranchId() == 1 && signUpForm.getDepartmentId() == 3)
                || (signUpForm.getBranchId() == 1 && signUpForm.getDepartmentId() == 4)
                || (signUpForm.getBranchId() == 1 && signUpForm.getDepartmentId() == 5)
                || (signUpForm.getBranchId() == 2 && signUpForm.getDepartmentId() == 1)
                || (signUpForm.getBranchId() == 2 && signUpForm.getDepartmentId() == 2)
                || (signUpForm.getBranchId() == 3 && signUpForm.getDepartmentId() == 1)
                || (signUpForm.getBranchId() == 3 && signUpForm.getDepartmentId() == 2)
                || (signUpForm.getBranchId() == 4 && signUpForm.getDepartmentId() == 1)
                || (signUpForm.getBranchId() == 4 && signUpForm.getDepartmentId() == 2)
                || (signUpForm.getBranchId() == 5 && signUpForm.getDepartmentId() == 1)
                || (signUpForm.getBranchId() == 5 && signUpForm.getDepartmentId() == 2)) {
            errorMessages.add("支社と部署の組み合わせが不正です");
        }
        if(!(errorMessages.isEmpty())){
            session.setAttribute("signUpForm",signUpForm);
            session.setAttribute("errorMessages",errorMessages);
            return new ModelAndView("redirect:/signUp");
        }
        //アカウント重複チェック
        try {
            // Serviceの更新メソッドを呼び出す
            userService.updateUser(signUpForm);
        } catch (Exception e) {
            // Serviceから重複エラーがスローされた場合
            session.setAttribute("signUpForm",signUpForm);
            session.setAttribute("errorMessages", e.getMessage());
            return new ModelAndView("redirect:/signUp");
        }

        userService.registerUser(signUpForm, signUpForm.getPassword());
        return new ModelAndView("redirect:/userManage");
    }
}
