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
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//public class UserEditController {
//    @Autowired
//    UserService userService;
//    @Autowired
//    HttpSession session;
//
//    /*
//     *編集画面表示
//     */
//    @GetMapping(value={"/userEdit/{id}"})
//    @ResponseBody
//    public ModelAndView editContent(@PathVariable Integer id) {
//        List<String> errorMessages = new ArrayList<>();
//        ModelAndView mav = new ModelAndView();
//
////        if (!id.matches("^[0-9]*$") || id.equals(null)) {
////            session.setAttribute("errorMessages", "・不正なパラメータです");
////            return new ModelAndView("redirect:/");
////        }
//
////        Integer intId = Integer.parseInt(id);
//        //編集する投稿を取得
//        UserForm users = userService.editUser(id);
//
//        if (users == null) {
//            session.setAttribute("errorMessages", "・不正なパラメータです");
//            return new ModelAndView("redirect:/");
//        }
//
//        mav.setViewName("/userEdit");
//        // 編集内容を保管
//        mav.addObject("formModel", users);
//        //エラー表示
//        //setErrorMessage(mav);
//        return mav;
//    }
//
//
//    /*
//     * 新規ユーザー編集処理
//     */
//    @PostMapping("/userEditUpdate")
//    public ModelAndView userEditUpdateContent(@Validated @ModelAttribute("formModel") UserForm userForm,
//                                   BindingResult result) throws ParseException {
//        //バリデーション
//        if (result.hasErrors()) {
//            List<String> errorMessages = new ArrayList<>();
//            for (ObjectError error : result.getAllErrors()) {
//                // ここでメッセージを取得する。
//                errorMessages.add(error.getDefaultMessage());
//
//                session.setAttribute("errorMessages", errorMessages);
//                return new ModelAndView("redirect:/userEdit");
//            }
//        }
//        userService.saveUser(userForm);
//        return new ModelAndView("redirect:/userManage");
//    }
//}
