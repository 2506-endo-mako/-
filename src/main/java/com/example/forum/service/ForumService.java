package com.example.forum.service;

import com.example.forum.controller.form.usersForm;
import com.example.forum.repository.ForumRepository;
import com.example.forum.repository.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForumService {
    @Autowired
    ForumRepository forumRepository;

//    /*
//     * レコード全件取得処理
//     */
//    public List<ReportForm> findAllReport() {
//        List<Report> results = reportRepository.findAll();
//        List<ReportForm> reports = setReportForm(results);
//        return reports;
//    }
//    /*
//     * DBから取得したデータをFormに設定
//     */
//    private List<ReportForm> setReportForm(List<Report> results) {
//        List<ReportForm> reports = new ArrayList<>();
//
//        for (int i = 0; i < results.size(); i++) {
//            ReportForm report = new ReportForm();
//            Report result = results.get(i);
//            report.setId(result.getId());
//            report.setContent(result.getContent());
//            reports.add(report);
//        }
//        return reports;
//    }
}

