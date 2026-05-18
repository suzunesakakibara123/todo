package com.example.demo.todo.form;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Form */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoForm {

    /** 識別ID */
    private Integer id;

    /** tasuku の内容 */
    @NotBlank
    private String contents;
    
    /**締切日*/
    private LocalDate deadln;

    /** タスクの優先度 */
    private Boolean priority;

    /** 実施状況*/
    private Boolean done;
    
    /** 「登録」or「変更」判定用 */
    private Boolean newTodo;
}