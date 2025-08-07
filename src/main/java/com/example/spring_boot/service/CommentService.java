package com.example.spring_boot.service;

import com.example.spring_boot.controller.form.CommentForm;
import com.example.spring_boot.controller.form.MessageForm;
import com.example.spring_boot.repository.CommentRepository;
import com.example.spring_boot.repository.MessageRepository;
import com.example.spring_boot.repository.entity.Comment;
import com.example.spring_boot.repository.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    /*
     *コメント返信全件取得処理
     */
    public List<CommentForm> findAllComment() {
        List<Comment> results = commentRepository.findAllByOrderByIdAsc();
        List<CommentForm> comments = setCommentForm(results);
        return comments;
    }


    /*
     * DBから取得したデータをFormに設定
     */
    private List<CommentForm> setCommentForm(List<Comment> results) {
        List<CommentForm> comments = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            CommentForm comment = new CommentForm();
            Comment result = results.get(i);
            comment.setId(result.getId());
            comment.setText(result.getText());
            comment.setUserId(result.getUserId());
            comment.setMessageId(result.getMessageId());
            comment.setCreatedDate(result.getCreatedDate());
            comment.setUpdatedDate(result.getUpdatedDate());
            comments.add(comment);
        }
        return comments;
    }

    /*
     * コメント返信追加
     */
    public void saveComment(CommentForm reqComment) {
        Comment saveComment = setCommentEntity(reqComment);
        commentRepository.save(saveComment);
    }

    /*
     * コメント返信1件取得処理
     */
    public CommentForm editComment(Integer id) {
        List<Comment> results = new ArrayList<>();
        results.add((Comment) commentRepository.findById(id).orElse(null));
        List<CommentForm> comments = setCommentForm(results);
        return comments.get(0);
    }

    /*
     * コメント削除（Entityに詰めなくてよい）
     */
    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Comment setCommentEntity(CommentForm reqComment) {
        Comment comment = new Comment();
        comment.setId(reqComment.getId());
        comment.setText(reqComment.getText());
        comment.setUserId(reqComment.getUserId());
        comment.setMessageId(reqComment.getMessageId());
        comment.setCreatedDate(reqComment.getCreatedDate());
        comment.setUpdatedDate(reqComment.getUpdatedDate());
        return comment;
    }



}

