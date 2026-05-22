package com.example.demo.todo.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.todo.entity.Todo;

/** Todoテーブル：Repository */
public interface TodoRepository extends CrudRepository<Todo, Integer> {

    /** doneの値でTodoを取得する */
    Iterable<Todo> findByDone(Boolean done);

    /** 未完了かつ締切日が今日のTodoを取得する */
    Iterable<Todo> findByDoneAndDeadline(Boolean done, LocalDate deadline);

    /** 未完了Todoを締切日が近い順で取得する */
    Iterable<Todo> findByDoneOrderByDeadlineAsc(Boolean done);
}