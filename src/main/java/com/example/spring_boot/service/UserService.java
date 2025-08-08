package com.example.spring_boot.service;

import com.example.spring_boot.controller.form.UserForm;
import com.example.spring_boot.repository.UserRepository;
import com.example.spring_boot.repository.entity.User;
import com.example.spring_boot.util.PasswordHashEncode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    /*
     * ログイン情報を1件取得
     */
    public User selectUser(String account, String password) {
        List<User> users = userRepository.findByAccountAndPassword(account, password);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
}

//    /*
//     * レコード全件取得処理
//     */
//    public List<UserForm> findAllUser() {
//        List<User> results = userRepository.findAll();
//        List<UserForm> users = setAllUser(results);
//        return users;
//    }
//
//    /*
//     * DBから取得したデータをFormに設定
//     */
//    private List<UserForm> setAllUser(List<User> results) {
//        List<UserForm> users = new ArrayList<>();
//        for (int i = 0; i < results.size(); i++) {
//            UserForm userForm = new UserForm();
//            User result = results.get(i);
//            userForm.setId(result.getId());
//            userForm.setAccount(result.getAccount());
//            userForm.setName(result.getName());
//            userForm.setBranchId(result.getBranchId());
//            userForm.setDepartmentId(result.getDepartmentId());
//            userForm.setBranchId(result.getBranchId());
//            userForm.setDepartmentId(result.getDepartmentId());
//            userForm.setIsStopped(result.getIsStopped());
//            users.add(userForm);
//        }
//        return users;
//    }
//}


