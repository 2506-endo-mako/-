package com.example.spring_boot.controller;

import com.example.spring_boot.controller.form.UserEditForm;
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

import javax.security.auth.login.AccountException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserEditController {
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    /*
     *ユーザー編集画面表示
     */
    @GetMapping(value={"/userEdit", "/userEdit/{id}"})
    //@ResponseBody
    public ModelAndView editContent(@PathVariable(required = false) Optional<String> id) {

        String Id = id.orElse(null);

        if (Id == null || !Id.matches("^[0-9]*$")) {
            session.setAttribute("errorMessages", "不正なパラメータが入力されました");
            return new ModelAndView("redirect:/userManage");
        }

        ModelAndView mav = new ModelAndView();
        //編集するユーザー情報を取得
        UserEditForm user = userService.editUser(Integer.valueOf(Id));

        if (user == null) {
            session.setAttribute("errorMessages", "不正なパラメータが入力されました");
            return new ModelAndView("redirect:/userManage");
        }
        mav.setViewName("/userEdit");
        // 編集内容を保管
        mav.addObject("formModel", user);
        mav.addObject("loginUser",session.getAttribute("loginUser"));
        mav.addObject("errorMessages",session.getAttribute("errorMessages"));
        //エラー表示
//        setErrorMessage(mav);
        session.removeAttribute("errorMessages");
        return mav;
    }


    /*
     * ユーザー編集処理
     */
    @PutMapping("/userEditUpdate/{id}")
    public ModelAndView userEditUpdateContent(@PathVariable Integer id,
                                              @Validated @ModelAttribute("formModel") UserEditForm userEditForm,
                                              BindingResult result) throws Exception {
        //バリデーション
        if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                // ここでメッセージを取得する。
                errorMessages.add(error.getDefaultMessage());
            }
                session.setAttribute("errorMessages", errorMessages);
                return new ModelAndView("redirect:/userEdit/{id}");

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
                || (userEditForm.getBranchId() == 2 && userEditForm.getDepartmentId() == 3)
                || (userEditForm.getBranchId() == 2 && userEditForm.getDepartmentId() == 4)
                || (userEditForm.getBranchId() == 2 && userEditForm.getDepartmentId() == 5)
                || (userEditForm.getBranchId() == 3 && userEditForm.getDepartmentId() == 1)
                || (userEditForm.getBranchId() == 3 && userEditForm.getDepartmentId() == 2)
                || (userEditForm.getBranchId() == 4 && userEditForm.getDepartmentId() == 1)
                || (userEditForm.getBranchId() == 4 && userEditForm.getDepartmentId() == 2)
                || (userEditForm.getBranchId() == 5 && userEditForm.getDepartmentId() == 1)
                || (userEditForm.getBranchId() == 5 && userEditForm.getDepartmentId() == 2)) {
                    errorMessages.add("支社と部署の組み合わせが不正です");
        }
        if(!(errorMessages.isEmpty())){
            session.setAttribute("errorMessages",errorMessages);
            return new ModelAndView("redirect:/userEdit/{id}");
        }
        //アカウント重複チェック
        try {
            // Serviceの更新メソッドを呼び出す
            userService.editUpdateUser(userEditForm);
            } catch (Exception e) {
                // Serviceから重複エラーがスローされた場合
                session.setAttribute("errorMessages", e.getMessage());
                return new ModelAndView("redirect:/userEdit/{id}");
            }

        return new ModelAndView("redirect:/userManage");
    }
}