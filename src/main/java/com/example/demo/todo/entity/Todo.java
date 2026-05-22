package com.example.demo.todo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** todoテーブル用：Entity */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    /** 識別ID */
    @Id
    private Integer id;

    /** ToDoの内容 */
    private String content;
    
    /** タスクの担当者 */
    private String user;

    /** 締切日 */
    private LocalDate deadline;
    
    /** 登録日時 */
    private LocalDateTime createdAt;

    /** 優先度 */
    private Boolean priority;
    
    /**実施済チェック*/
    private Boolean done;
    
}