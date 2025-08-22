package com.example.spring_boot.controller;

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
        UserEditForm userEditForm = (UserEditForm) session.getAttribute("userEditForm");
        if(userEditForm == null) {
            userEditForm = new UserEditForm();
        }
        // form用の空のentityを準備
        //UserEditForm userEditForm = new UserEditForm();
        // 画面遷移先を指定
        mav.setViewName("/signUp");
        // 準備した空のFormを保管
        mav.addObject("formModel", userEditForm);
        mav.addObject("errorMessages", session.getAttribute("errorMessages"));
        session.removeAttribute("errorMessages");
        return mav;
    }

    /*
     * 新規ユーザー登録処理
     */
    @PostMapping("/signUpAdd")
    public ModelAndView addContent(@Validated @ModelAttribute("formModel") UserEditForm userEditForm,
                                   BindingResult result) throws ParseException {
        //バリデーション
        if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                // ここでメッセージを取得する。
                errorMessages.add(error.getDefaultMessage());
            }
                session.setAttribute("userEditForm",userEditForm);
                session.setAttribute("errorMessages", errorMessages);
                return new ModelAndView("redirect:/signUp");

        }
        List<String> errorMessages = new ArrayList<>();
        //パスワードと確認パスワードが違う場合
        String password = userEditForm.getPassword();
        String confirmPassword = userEditForm.getConfirmPassword();
        if(!(password.equals(confirmPassword))) {
            errorMessages.add("パスワードと確認用パスワードが一致しません");
            //session.setAttribute("errorMessages", "パスワードと確認用パスワードが一致しません");
            //return new ModelAndView("redirect:/userEdit/{id}");
        }
        //支社と部署の組み合わせが不正の時
        if((userEditForm.getBranchId() == 1 && userEditForm.getDepartmentId() == 3)
                || (userEditForm.getBranchId() == 1 && userEditForm.getDepartmentId() == 4)
                || (userEditForm.getBranchId() == 1 && userEditForm.getDepartmentId() == 5)
                || (userEditForm.getBranchId() == 2 && userEditForm.getDepartmentId() == 1)
                || (userEditForm.getBranchId() == 2 && userEditForm.getDepartmentId() == 2)
                || (userEditForm.getBranchId() == 3 && userEditForm.getDepartmentId() == 1)
                || (userEditForm.getBranchId() == 3 && userEditForm.getDepartmentId() == 2)
                || (userEditForm.getBranchId() == 4 && userEditForm.getDepartmentId() == 1)
                || (userEditForm.getBranchId() == 4 && userEditForm.getDepartmentId() == 2)
                || (userEditForm.getBranchId() == 5 && userEditForm.getDepartmentId() == 1)
                || (userEditForm.getBranchId() == 5 && userEditForm.getDepartmentId() == 2)) {
            errorMessages.add("支社と部署の組み合わせが不正です");
        }
        if(!(errorMessages.isEmpty())){
            session.setAttribute("userEditForm",userEditForm);
            session.setAttribute("errorMessages",errorMessages);
            return new ModelAndView("redirect:/signUp");
        }
        //アカウント重複チェック
        try {
            // Serviceの更新メソッドを呼び出す
            userService.updateUser(userEditForm);
        } catch (Exception e) {
            // Serviceから重複エラーがスローされた場合
            session.setAttribute("userEditForm",userEditForm);
            session.setAttribute("errorMessages", e.getMessage());
            return new ModelAndView("redirect:/signUp");
        }

        //userService.saveUser(userEditForm);
        //userService.registerUser(userEditForm.getAccount(), userEditForm.getPassword());
        userService.registerUser(userEditForm, userEditForm.getPassword());
        return new ModelAndView("redirect:/userManage");
    }
}
