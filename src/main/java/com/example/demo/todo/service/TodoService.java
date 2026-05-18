package com.example.demo.todo.service;

import java.util.Optional;

import com.example.demo.todo.entity.Todo;

/* Todoサービス処理：Service */
public interface TodoService {

    /** 未実施のタスクの情報を全件取得する */
    Iterable<Todo> selectNotDoneTodo();

    /** タスクを登録する */
    void insertTodo(Todo todo);

    /** タスクを更新する */
    void updateTodo(Todo todo);
    
    /** タスクを1件取得する */
    Optional<Todo> selectOneById(Integer id);
    
    /** タスクの優先度を切り替える */
    void togglePriority(Integer id);
    
    /** タスクの実施状況を切り替える */
    void toggleDone(Integer id);

    /** タスクを削除する */
    void deleteTodoById(Integer id);

    /** 優先度を高くする */
    void markPriority(Integer id);

    /** タスクを完了済みにする */
    void markDone(Integer id);
    
    /**完了済タスクを取得する*/
    Iterable<Todo> selectDoneTodo();
}