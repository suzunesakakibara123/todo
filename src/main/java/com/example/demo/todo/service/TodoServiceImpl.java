package com.example.demo.todo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.todo.entity.Todo;
import com.example.demo.todo.repository.TodoRepository;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {
	/**Repositoryの注入*/
	@Autowired
	TodoRepository repository;
	
	@Override
	public Iterable<Todo> selectAll() {
		return repository.findAll();
	}

	@Override
	public void insertTodo(Todo todo) {
		repository.save(todo);

	}

	@Override
	public void updateTodo(Todo todo) {
		repository.save(todo);

	}

	@Override
	public void deleteTodoById(Integer id) {
		repository.deleteById(id);

	}

	@Override
	// 優先度が高いタスクにチェックが付くと以下のメソッドが起動する。
	public void markPriority(Integer id) {
	    // 画面から送られてきたidのタスクを探す。
	    Optional<Todo> todoOpt = repository.findById(id);
	    // エラー回避のため、タスクがちゃんと存在するかを確認する。
	    if (todoOpt.isPresent()) {
	        Todo todo = todoOpt.get();
	        // priorityを反転する(念のためnullはtrueになる設計。）
	        todo.setPriority(!Boolean.TRUE.equals(todo.getPriority()));
	        // 保存する。
	        repository.save(todo);
	    }
	}


	@Override
	// 「完了」にチェックが付くと以下のメソッドが起動する。
	public void markDone(Integer id) {
		// 画面から送られてきたidのタスクを探す。
	    Optional<Todo> todoOpt = repository.findById(id);
		// エラー回避のため、タスクがちゃんと存在するかを確認する。
		if (todoOpt.isPresent()) {
		     Todo todo = todoOpt.get();
		     // doneを反転する(念のためnullはtrueになる設計。）
		     todo.setDone(!Boolean.TRUE.equals(todo.getDone()));
		     // 保存する。
		     repository.save(todo);
		}
	}

}
