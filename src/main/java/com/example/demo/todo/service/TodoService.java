package com.example.demo.todo.service;

import com.example.demo.todo.entity.Todo;

/* Todoサービス処理：Service */
public interface TodoService {

    /** タスクの情報を全件取得する */
    Iterable<Todo> selectAll();

    /** タスクを登録する */
    void insertTodo(Todo todo);

    /** タスクを更新する */
    void updateTodo(Todo todo);

    /** タスクを削除する */
    void deleteTodoById(Integer id);

    /** 優先度を高くする */
    void markPriority(Integer id);

    /** タスクを完了済みにする */
    void markDone(Integer id);
}