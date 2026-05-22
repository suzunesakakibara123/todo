package com.example.demo.todo.service;

import java.util.Optional;

import com.example.demo.todo.entity.Todo;

/* Todoサービス処理：Service */
public interface TodoService {

    /** 未実施のタスクの情報を全件取得する */
    Iterable<Todo> selectNotDoneTodo();
    
    /** 期限が今日の未完了タスクを取得する */
    Iterable<Todo> selectTodayTodo();

    /** 未完了タスクを期限が近い順で取得する */
    Iterable<Todo> selectNotDoneTodoOrderByDeadline();

    /** 完了済タスクを取得する */
    Iterable<Todo> selectDoneTodo();

    /** タスクを1件取得する */
    Optional<Todo> selectOneById(Integer id);

    /** タスクを登録する */
    void insertTodo(Todo todo);

    /** タスクを更新する */
    void updateTodo(Todo todo);

    /** タスクを削除する */
    void deleteTodoById(Integer id);

    /** タスクの優先度を切り替える */
    void togglePriority(Integer id);

    /** タスクの実施状況を切り替える */
    void toggleDone(Integer id);
    
    
}