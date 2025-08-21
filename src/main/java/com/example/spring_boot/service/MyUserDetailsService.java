//package com.example.spring_boot.service;
//import com.example.spring_boot.repository.UserRepository;
////import com.example.spring_boot.repository.entity.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//
//@Service
//public class MyUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
//        // `account` を識別子としてデータベースからユーザー情報を取得
//        com.example.spring_boot.repository.entity.User user = userRepository.findByAccount(account)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with account: " + account));
//
//        // Spring SecurityのUserDetailsオブジェクトを作成
//        // このコンストラクタには、ユーザー名（識別子）、パスワード、権限のリストを渡す
//        return new User(
//                user.getAccount(),
//                user.getPassword(),
//                Collections.emptyList()
//        );
//    }
//}
