package com.example.spring_boot.service;

import com.example.spring_boot.controller.form.UserForm;
import com.example.spring_boot.repository.UserRepository;
import com.example.spring_boot.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /*
     * レコード1件取得処理
     */
    public List<UserForm> loginUser() {
        List<User> results = userRepository.findByAccountAndPassword(String account, String password);
        List<UserForm> users = setUserForm(results);
        return users;
    }
    /*
     * DBから取得したデータをFormに設定
     */
    private List<UserForm> setUserForm(List<User> results) {
        List<UserForm> users = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            UserForm user = new UserForm();
            User result = results.get(i);
            user.setId(result.getId());
            user.setContent(result.getContent());
            users.add(user);
        }
        return users;
    }
}

