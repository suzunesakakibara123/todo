package com.example.demo.todo.service;

import java.time.LocalDate;
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
	public Iterable<Todo> selectNotDoneTodo() {
		return repository.findByDone(false);
	}
	
	@Override
	public Iterable<Todo> selectTodayTodo() {
	    return repository.findByDoneAndDeadline(false, LocalDate.now());
	}

	@Override
	public Iterable<Todo> selectNotDoneTodoOrderByDeadline() {
	    return repository.findByDoneOrderByDeadlineAsc(false);
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
	public Optional<Todo> selectOneById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public void togglePriority(Integer id) {
		Optional<Todo> todoOpt = repository.findById(id);

	    if (todoOpt.isPresent()) {
	        Todo todo = todoOpt.get();

	        todo.setPriority(!Boolean.TRUE.equals(todo.getPriority()));

	        repository.save(todo);
	    }
		
	}

	@Override
	public void toggleDone(Integer id) {
	    Optional<Todo> todoOpt = repository.findById(id);

	    if (todoOpt.isPresent()) {
	        Todo todo = todoOpt.get();

	        todo.setDone(!Boolean.TRUE.equals(todo.getDone()));

	        repository.save(todo);
	    }
	}
	
	@Override
	public Iterable<Todo> selectDoneTodo() {
	    return repository.findByDone(true);
	}
	
	

}
