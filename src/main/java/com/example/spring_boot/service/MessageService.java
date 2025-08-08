package com.example.spring_boot.service;

import com.example.spring_boot.controller.form.MessageForm;
import com.example.spring_boot.repository.MessageRepository;
import com.example.spring_boot.repository.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    /*
     * レコード全件取得処理
     */
    public List<MessageForm> findAllMessages(String startDate, String endDate, String category) {

        if (!StringUtils.isEmpty(startDate)) {
            startDate += " 00:00:00.000";
        } else {
            startDate = "2020-01-01 00:00:00.000";
        }
        //もしendDateに値があったらその値 + " 23:59:59"をDaoに渡したい
        if (!StringUtils.isEmpty(endDate)) {
            endDate += " 23:59:59.999";
        } else {
            endDate = "2100-12-31 23:59:59.999";
        }

        Date start = null;
        Date end = null;
        try {
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            start = sdFormat.parse(startDate);
            end = sdFormat.parse(endDate);

        } catch (ParseException e) {
            //例外が発生した場所や原因をより詳細に把握できる
            e.printStackTrace();
            return null;
        }

        List<Message> results = null;

        if(StringUtils.isEmpty(category)){
            results = messageRepository.findByCreatedDateBetweenOrderByCreatedDateAsc(start,end);
        } else {
            results = messageRepository.findByCreatedDateBetweenAndCategoryOrderByCreatedDateAsc(start,end,category);
        }

        List<MessageForm> Messages = setMessagesForm(results);
        return Messages;
    }
    /*
     * DBから取得したデータをFormに設定
     */
    private List<MessageForm> setMessagesForm(List<Message> results) {
        List<MessageForm> Messages = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            MessageForm messages = new MessageForm();
            Message result = results.get(i);
            messages.setId(result.getId());
            messages.setTitle(result.getTitle());
            messages.setText(result.getText());
            messages.setCategory(result.getCategory());
            messages.setUserId(result.getUserId());
            messages.setCreatedDate(result.getCreatedDate());
            messages.setUpdatedDate(result.getUpdatedDate());
            Messages.add(messages);
        }
        return Messages;

    }

    /*
     * レコード追加
     */
    public void saveMessages(MessageForm reqMessages) {
        Message saveMessages = setMessagesEntity(reqMessages);
        messageRepository.save(saveMessages);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Message setMessagesEntity(MessageForm reqMessages) {
        Message messages = new Message();
        messages.setId(reqMessages.getId());
        messages.setTitle(reqMessages.getTitle());
        messages.setText(reqMessages.getText());
        messages.setCategory(reqMessages.getCategory());
        messages.setUserId(reqMessages.getUserId());
        messages.setCreatedDate(reqMessages.getCreatedDate());
        messages.setUpdatedDate(reqMessages.getUpdatedDate());
        return messages;
    }


}

