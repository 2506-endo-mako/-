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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserEditController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    /*
     *ユーザー編集画面表示
     */
    @GetMapping("/userEdit/{id}")
    //@ResponseBody
    public ModelAndView editContent(@PathVariable(required = false) Integer id) {
        //List<String> errorMessages = new ArrayList<>();
        ModelAndView mav = new ModelAndView();
        //編集するユーザー情報を取得
        UserForm user = userService.editUser(id);
        if (user == null) {
            session.setAttribute("errorMessages", "・不正なパラメータです");
            return new ModelAndView("redirect:/top");
        }

        mav.setViewName("/userEdit");
        // 編集内容を保管
        mav.addObject("formModel", user);
        mav.addObject("errorMessages",session.getAttribute("errorMessages"));
        //エラー表示
//        setErrorMessage(mav);
        session.invalidate();
        return mav;
    }


    /*
     * ユーザー編集処理
     */
    @PutMapping("/userEditUpdate/{id}")
    public ModelAndView userEditUpdateContent(@PathVariable Integer id,
                                              @Validated @ModelAttribute("formModel") UserEditForm userEditForm,
                                              BindingResult result) throws ParseException {
        //バリデーション
        if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                // ここでメッセージを取得する。
                errorMessages.add(error.getDefaultMessage());

                session.setAttribute("errorMessages", errorMessages);
                return new ModelAndView("redirect:/userEdit/{id}");
            }
        }
        userEditForm.setId(id);
        userService.updateUser(userEditForm);
        return new ModelAndView("redirect:/userManage");
    }
}