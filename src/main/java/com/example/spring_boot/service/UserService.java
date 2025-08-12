package com.example.spring_boot.service;

import com.example.spring_boot.controller.form.BranchForm;
import com.example.spring_boot.controller.form.DepartmentForm;
import com.example.spring_boot.controller.form.UserForm;
import com.example.spring_boot.repository.UserRepository;
import com.example.spring_boot.repository.entity.Branch;
import com.example.spring_boot.repository.entity.Department;
import com.example.spring_boot.repository.entity.User;
import com.example.spring_boot.util.PasswordHashEncode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    /*
     * レコード全件取得処理
     */
    public List<UserForm> findAllUser() {
        List<User> results = userRepository.findAll();
        List<UserForm> users = setAllUser(results);
        return users;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<UserForm> setAllUser(List<User> results) {
        List<UserForm> users = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            UserForm userForm = new UserForm();
            User result = results.get(i);
            userForm.setId(result.getId());
            userForm.setAccount(result.getAccount());
            userForm.setName(result.getName());
            userForm.setBranchId(result.getBranchId());
            userForm.setDepartmentId(result.getDepartmentId());
            //userForm.setBranchName(result.getBranchName());
            //userForm.setDepartmentName(result.getDepartmentName());
            users.add(userForm);
        }
        return users;
    }

    /*
     * ログイン情報を1件取得
     */
    public User selectUser(String account, String password) {
        List<User> users = userRepository.findByAccountAndPassword(account, password);
        //String encPassword = CipherUtil.encrypt(password);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    /*
     * レコード追加
     */
    public void saveUser(UserForm reqUser) {
        User saveUser = setUserEntity(reqUser);
        userRepository.save(saveUser);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private User setUserEntity(UserForm reqUser) {
        //現在日時を取得
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        //変数endにsdfからformatメソッドで引数dateを渡したものを代入して
        //現在日時をendDateに入れている
        String strDate = sdf.format(date);
        Date nowDate;
        try {
            nowDate = sdf.parse(strDate);
        } catch (ParseException e) {
            //例外が発生した場所や原因をより詳細に把握できる
            e.printStackTrace();
            return null;
        }

        User user = new User();
        user.setId(reqUser.getId());
        user.setAccount(reqUser.getAccount());
        user.setPassword(reqUser.getPassword());
        user.setName(reqUser.getName());
        user.setBranchId(reqUser.getBranchId());
        user.setDepartmentId(reqUser.getDepartmentId());
        user.setCreatedDate(reqUser.getCreatedDate());
        user.setUpdatedDate(nowDate);
        return user;
    }

//    /*
//     * ユーザ稼働状態（ステータス）更新
//     */
//    public void updateUser(UserForm reqUser) {
//        //select文流す　WHERE句はkEYのidのみ
//        User saveUser = new User();
//        saveUser = (userRepository.findById(reqUser.getId()).orElse(null));
//        saveUser = updateSetUserEntity(reqUser, saveUser);
//        userRepository.save(saveUser);
//    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private User updateSetUserEntity(UserForm reqUser,User saveUser, Branch saveBranch, Department saveDepartment) {

        User user = new User();

        user.setId(saveUser.getId());
        user.setAccount(saveUser.getAccount());
        user.setName(reqUser.getName());
        user.setBranchId(saveUser.getBranchId());
        user.setDepartmentId(saveUser.getDepartmentId());
        user.setIsStopped(saveUser.getIsStopped());
        user.setName(saveBranch.getName());
        user.setName(saveDepartment.getName());
        return user;
    }
}

