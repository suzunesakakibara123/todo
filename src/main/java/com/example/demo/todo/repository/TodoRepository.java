package com.example.demo.todo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.todo.entity.Todo;

/*Todoテーブル：RepositoryImpl*/
/**「Spring Data」が提供する「CrudRepository」を継承、<保存対象のオブジェクトの型と保存対象のオブジェクトの主キーの型を指定する。*/
public interface TodoRepository extends CrudRepository<Todo, Integer> {
}
