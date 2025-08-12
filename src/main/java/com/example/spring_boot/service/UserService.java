package com.example.spring_boot.service;

import com.example.spring_boot.controller.form.UserForm;
import com.example.spring_boot.repository.UserRepository;
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
     * ログイン情報を1件取得
     */
    public User selectUser(String account, String password) {
        List<User> users = userRepository.findByAccountAndPassword(account, password);
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
     * ユーザーのレコード全件取得処理
     */
    public List<UserForm> findAllUser() {
        List<User> results = userRepository.findAll();
        List<UserForm> users = setAllUser(results);
        return users;
    }

    /*
     * 編集する投稿を１件取得
     */
    public UserForm editUser(Integer intId) {
        List<User> results = new ArrayList<>();
        results.add((User) userRepository.findById(intId).orElse(null)); //nullかもしれない（optional）
        //①resultsから0番目の要素を取り出して新しい変数に入れる
        User result = results.get(0);
        //②取り出した変数がnullかどうかチェックする→③nullだったらnullを返す
        if(result == null){
            return null;
        }
        List<UserForm> user = setAllUser(results);
        return user.get(0);
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
            userForm.setIsStopped(result.getIsStopped());
            users.add(userForm);
        }
        return users;
    }

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
        if(reqUser.getIsStopped() == null) {
         user.setIsStopped(0);
        } else {
            user.setIsStopped(reqUser.getIsStopped());
        }
        user.setCreatedDate(nowDate);
        user.setUpdatedDate(nowDate);
        return user;
    }
}

