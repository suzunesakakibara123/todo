package com.example.demo.todo.entity;

import java.time.LocalDate;

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
    private String contents;

    /** 締切日 */
    private LocalDate deadln;

    /** 優先度 */
    private Boolean priority;
    
    /**実施済チェック*/
    private Boolean done;
}