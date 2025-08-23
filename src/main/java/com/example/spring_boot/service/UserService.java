package com.example.spring_boot.service;

import com.example.spring_boot.controller.form.SignUpForm;
import com.example.spring_boot.controller.form.UserEditForm;
import com.example.spring_boot.controller.form.UserForm;
import com.example.spring_boot.repository.UserRepository;
import com.example.spring_boot.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * ユーザーのレコード全件取得処理
     */
    public List<UserEditForm> findAllUser() {
        List<User> results = userRepository.findAll();
        List<UserEditForm> users = setAllUser(results);
        return users;
    }

    /*
     * レコード1件取得処理
     */
    public UserEditForm editUser(Integer id) {
        List<User> results = new ArrayList<>();
        results.add((User) userRepository.findById(id).orElse(null));
        List<UserEditForm> users = setAllUser(results);
        if(users == null){
            return null;
        }
        return users.get(0);
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<UserEditForm> setAllUser(List<User> results) {
        List<UserEditForm> users = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            UserEditForm userEditForm = new UserEditForm();
            User result = results.get(i);
            if(result == null){
                return null;
            }
            userEditForm.setId(result.getId());
            userEditForm.setAccount(result.getAccount());
            userEditForm.setPassword(result.getPassword());
            //userForm.setConfirmPassword(result.getConfirmPassword());
            userEditForm.setName(result.getName());
            userEditForm.setBranchId(result.getBranchId());
            userEditForm.setDepartmentId(result.getDepartmentId());

            //userForm.setBranchName(result.getBranchName());
            //userForm.setDepartmentName(result.getDepartmentName());

            userEditForm.setIsStopped(result.getIsStopped());
            users.add(userEditForm);
        }
        return users;
    }

    // ユーザー登録メソッド
    public User registerUser(SignUpForm reqUser, String rawPassword) {
        // まずsetUserEditEntityを呼び出して、パスワード以外の情報を設定したUserオブジェクトを取得
        User user = setSignUpEntity(reqUser);
        //パスワードのハッシュ化
        String hashedPassword = passwordEncoder.encode(rawPassword);
        // ハッシュ化されたパスワードをUserオブジェクトに設定
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    // ログイン処理に使用するメソッド
    public Optional<User> authenticateUser(String account, String rawPassword) {
        // 1. アカウント名でユーザーを検索
        Optional<User> userOptional = userRepository.findByAccount(account);

        // ユーザーが存在する場合、パスワードを照合
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // 2. パスワードを照合
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return Optional.of(user);
            }
        }
        // ユーザーが見つからない、またはパスワードが一致しない場合はOptional.empty()を返す
        return Optional.empty();
    }

    /*
     * レコード追加
     */
    public void saveUser(UserEditForm reqUser) {
        User saveUser = setUserEditEntity(reqUser);
        userRepository.save(saveUser);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
//    private User setUserEntity(UserForm reqUser) {
//        //現在日時を取得
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//
//        //変数endにsdfからformatメソッドで引数dateを渡したものを代入して
//        //現在日時をendDateに入れている
//        String strDate = sdf.format(date);
//        Date nowDate;
//        try {
//            nowDate = sdf.parse(strDate);
//        } catch (ParseException e) {
//            //例外が発生した場所や原因をより詳細に把握できる
//            e.printStackTrace();
//            return null;
//        }
//
//        User user = new User();
//        user.setId(reqUser.getId());
//        user.setAccount(reqUser.getAccount());
//        user.setPassword(reqUser.getPassword());
//        user.setName(reqUser.getName());
//        user.setBranchId(reqUser.getBranchId());
//        user.setDepartmentId(reqUser.getDepartmentId());
//        if(reqUser.getIsStopped() == null) {
//            user.setIsStopped(0);
//        } else {
//            user.setIsStopped(reqUser.getIsStopped());
//        }
//        user.setCreatedDate(nowDate);
//        user.setUpdatedDate(nowDate);
//        return user;
//    }

    /*
     * 更新したusersテーブルのレコード追加(ユーザー編集の時使用)
     */
    public void editUpdateUser(UserEditForm reqUser) throws Exception {
        // 1. フォームから送られてきたアカウント名で、既存のレコードを検索する
        Optional<User> existingUserOptional = userRepository.findByAccount(reqUser.getAccount());

        // 2. 検索結果が存在する場合、重複チェックを行う
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // 3. 取得したレコードのIDが、更新対象のIDと一致するかどうかを比較する
            //    IDが一致する場合、それは自分自身のレコードなので重複ではない
            if (existingUser.getId() != (reqUser.getId())) {
                // IDが一致しない場合、別のアカウントが同じアカウント名を持っているため重複エラー
                throw new Exception("アカウントが重複しています");
            }
        }
            User saveUsers = setUserEditEntity(reqUser);
            userRepository.save(saveUsers);

    }

    /*
     * 更新したusersテーブルのレコード追加（新規登録の時使用）
     */
    public void updateUser(SignUpForm reqUser) throws Exception {
        // 1. フォームから送られてきたアカウント名で、既存のレコードを検索する
        Optional<User> existingUserOptional = userRepository.findByAccount(reqUser.getAccount());

        // 2. 検索結果が存在する場合、重複チェックを行う
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // 3. 取得したレコードのIDが、更新対象のIDと一致するかどうかを比較する
            //    IDが一致する場合、それは自分自身のレコードなので重複ではない
            if (existingUser.getId() != (reqUser.getId())) {
                // IDが一致しない場合、別のアカウントが同じアカウント名を持っているため重複エラー
                throw new Exception("アカウントが重複しています");
            }
        User saveUsers = setSignUpEntity(reqUser);
        userRepository.save(saveUsers);
        }
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private User setSignUpEntity(SignUpForm reqUser) {
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


    /*
     * リクエストから取得した情報をEntityに設定
     */
    private User setUserEditEntity(UserEditForm reqUser) {
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

    /*
     * ユーザ稼働状態（ステータス）更新
     */
    public void updateUser(Integer id, Integer isStopped) {
        userRepository.updateStatusById(id, isStopped);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private User updateSetUserEntity(UserForm reqUser,User saveUser) {

        User user = new User();

        user.setId(saveUser.getId());
        user.setAccount(saveUser.getAccount());
        user.setPassword(reqUser.getPassword());
        user.setName(reqUser.getName());
        user.setBranchId(saveUser.getBranchId());
        user.setDepartmentId(saveUser.getDepartmentId());
        user.setIsStopped(saveUser.getIsStopped());
        //user.setName(saveBranch.getName());
        //user.setName(saveDepartment.getName());
        return user;
    }
}

