package com.example.spring_boot.controller;

import com.example.spring_boot.controller.form.UserEditForm;
import com.example.spring_boot.controller.form.UserForm;
import com.example.spring_boot.repository.entity.LoginUserEdit;
import com.example.spring_boot.repository.entity.User;
import com.example.spring_boot.service.UserService;
import com.example.spring_boot.validation.validation.group.OtherUserValidation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
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
    @Autowired
    private LocalValidatorFactoryBean validator;

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
        User loginUser = (User) session.getAttribute("loginUser");
        mav.setViewName("/userEdit");
        // 編集内容を保管
        mav.addObject("formModel", user);
        mav.addObject("loginUser",loginUser);
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
        // ログインユーザーのIDを取得（セッションから取得する想定）
        User loginUser = (User) session.getAttribute("loginUser");
        Integer loginUserId = loginUser.getId();

        try{
            // ログインユーザーと編集対象ユーザーが同じ場合loginUserEditメソッドへ飛ぶ
            if (loginUserId.equals(id)) {
                userService.loginUserEdit(userEditForm,userEditForm.getPassword());
            }
        } catch (Exception e) {
            // Serviceから重複エラーがスローされた場合
            session.setAttribute("errorMessages", e.getMessage());
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
        // 更新後のユーザー情報をDBから再取得
        if (loginUserId.equals(id)) {
            UserEditForm updatedUser = userService.editUser(userEditForm.getId());
            //支社と部署の組み合わせが不正の時
            if ((updatedUser.getBranchId() == 1 && updatedUser.getDepartmentId() == 3)
                    || (updatedUser.getBranchId() == 1 && updatedUser.getDepartmentId() == 4)
                    || (updatedUser.getBranchId() == 1 && updatedUser.getDepartmentId() == 5)
                    || (updatedUser.getBranchId() == 2 && updatedUser.getDepartmentId() == 1)
                    || (updatedUser.getBranchId() == 2 && updatedUser.getDepartmentId() == 2)
                    || (updatedUser.getBranchId() == 3 && updatedUser.getDepartmentId() == 1)
                    || (updatedUser.getBranchId() == 3 && updatedUser.getDepartmentId() == 2)
                    || (updatedUser.getBranchId() == 4 && updatedUser.getDepartmentId() == 1)
                    || (updatedUser.getBranchId() == 4 && updatedUser.getDepartmentId() == 2)
                    || (updatedUser.getBranchId() == 5 && updatedUser.getDepartmentId() == 1)
                    || (updatedUser.getBranchId() == 5 && updatedUser.getDepartmentId() == 2)) {
                errorMessages.add("支社と部署の組み合わせが不正です");
            }
        }else {
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
        }

            // 他のバリデーションエラーが存在するかチェック
            if (result.hasErrors()) {
                //List<String> errorMessages = new ArrayList<>();
                for (ObjectError error : result.getAllErrors()) {
                    errorMessages.add(error.getDefaultMessage());
                }
                session.setAttribute("errorMessages", errorMessages);
                return new ModelAndView("redirect:/userEdit/{id}");
            }

        if(!errorMessages.isEmpty()) {
            session.setAttribute("errorMessages",errorMessages);
            return new ModelAndView("redirect:/userEdit/{id}");
        }
        if (loginUserId.equals(id)){
            return new ModelAndView("redirect:/userManage");
        }
        //アカウント重複チェック
        try {
            // Serviceの更新メソッドを呼び出す
            // 更新後のユーザー情報をDBから再取得
            // updatedUser2 = userService.editUser(userEditForm.getId());
            userService.editUpdateUser(userEditForm, userEditForm.getPassword());
            } catch (Exception e) {
                // Serviceから重複エラーがスローされた場合
                session.setAttribute("errorMessages", e.getMessage());
                return new ModelAndView("redirect:/userEdit/{id}");
            }

        return new ModelAndView("redirect:/userManage");
    }
}