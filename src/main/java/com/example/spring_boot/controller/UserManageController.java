//package com.example.spring_boot.controller;
//
//import com.example.spring_boot.controller.form.UserForm;
//import com.example.spring_boot.service.UserService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.ObjectError;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//public class UserManageController {
//    @Autowired
//    UserService userService;
//    @Autowired
//    HttpSession session;
//
////    /*
////     * ユーザー管理画面表示処理
////     */
////    @GetMapping("/userManage")
////    public ModelAndView userManageContent() {
////        ModelAndView mav = new ModelAndView();
////        // form用の空のentityを準備
////        UserForm userForm = new UserForm();
////        // 画面遷移先を指定
////        mav.setViewName("/userManage");
////        // 準備した空のFormを保管
////        mav.addObject("formModel", userForm);
////        mav.addObject("errorMessages", session.getAttribute("errorMessages"));
////        session.invalidate();
////        return mav;
////    }
//
//
//    /*
//     * 新規ユーザー登録処理
//     */
//    @PostMapping("/signUpAdd")
//    public ModelAndView addContent(@Validated @ModelAttribute("formModel") UserForm userForm,
//                                   BindingResult result) throws ParseException {
//        //バリデーション
//        if (result.hasErrors()) {
//            List<String> errorMessages = new ArrayList<>();
//            for (ObjectError error : result.getAllErrors()) {
//                // ここでメッセージを取得する。
//                errorMessages.add(error.getDefaultMessage());
//
//                session.setAttribute("errorMessages", errorMessages);
//                return new ModelAndView("redirect:/signUp");
//            }
//        }
//        userService.saveUser(userForm);
//        return new ModelAndView("redirect:/");
//    }
//}
