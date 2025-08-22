package com.example.spring_boot.controller;

import com.example.spring_boot.controller.form.UserForm;
//import com.example.spring_boot.dto.UserWithDetailsDto;
import com.example.spring_boot.repository.UserRepository;
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
public class UserManageController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;
    @Autowired
    UserRepository userRepository;

    /*
     * ユーザー管理画面表示処理
     */
    @GetMapping("/userManage")
    public ModelAndView userManageContent() {
        ModelAndView mav = new ModelAndView();
        // ユーザー情報を全件取得
        List<UserForm> userData = userService.findAllUser();
        // form用の空のentityを準備
        UserForm userForm = new UserForm();
        // 画面遷移先を指定
        mav.setViewName("/userManage");
        mav.addObject("userData", userData);
        // 準備した空のFormを保管
        mav.addObject("formModel", userForm);
        mav.addObject("errorMessages", session.getAttribute("errorMessages"));

//        // リポジトリから結合されたデータを取得
//        List<UserWithDetailsDto> users = userRepository.findAllUsersWithDetails();
//        // データをビューに渡す
//        mav.addObject("users", users);

        session.removeAttribute("errorMessages");
        return mav;
    }

    /*
     * ユーザー管理処理
     */
    @PostMapping("/manage")
    public ModelAndView manage(@Validated @ModelAttribute("formModel") UserForm userForm,
                                   BindingResult result) throws ParseException {
        //バリデーション
        if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                // ここでメッセージを取得する。
                errorMessages.add(error.getDefaultMessage());

                session.setAttribute("errorMessages", errorMessages);
                return new ModelAndView("redirect:/userManage");
            }
        }
        userService.findAllUser();
        return new ModelAndView("redirect:/userManage");
    }

    /*
     *ステータス変更処理
     */
    @PutMapping("/updateStatus/{id}")
    public ModelAndView updateStatus(@PathVariable Integer id,
                                     @RequestParam(name = "status", required = false) Integer isStopped,
                                     @ModelAttribute("formModel") UserForm userForm) {
        // ステータスを更新
        userService.updateUser(id, isStopped);
        // rootへリダイレクト
        return new ModelAndView("redirect:/userManage");
    }
}
