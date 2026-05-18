package com.example.demo.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.todo.entity.Todo;
import com.example.demo.todo.repository.TodoRepository;
import com.example.demo.todo.service.TodoService;

@SpringBootApplication
public class TodoApplication {
    /**起動メソッド*/
	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}
	/**注入*/
  	@Autowired
  	TodoService service;
  	@Autowired
  	TodoRepository repository;
  	/**実行メソッド*/
  	public void execute() {
  		//登録処理
  		setup();
  		//全件取得
  		showList();
  		//更新処理
  		//updateTodo();
  		//削除処理
  		//deleteTodo();

  	}
  	/**登録処理*/
  	public void setup() {
  		//エンティティ生成
  		Todo todo1 = new Todo(null, "ストレッチをする", null, false, false);
  		//登録実行
  		todo1 = repository.save(todo1);
  		//エンティティの内容を確認
  		System.out.println("登録したデータ："+todo1);
  		//エンティティ生成2
  		Todo todo2 = new Todo(null, "溜まっている仕事を書き出す", null, false, false);
  		//登録実行
  		todo2 = repository.save(todo2);
  		//エンティティの内容を確認
  		System.out.println("登録したデータ："+todo2);
  	}
  	
  	/**==全件取得==*/
  	private void showList() {
  		System.out.println("---全件取得---");
  		//リポジトリを使用して全件取得を実施、結果を取得
  		Iterable<Todo> todos = repository.findAll();
  		for(Todo todo : todos) {
  			System.out.println(todo);
  			
  		}
  		System.out.println("---全件取得終了---");
  	}
  	
  	/*更新処理*/
  	private void updateTodo() {
  		System.out.println("更新処理");
  		//変更したいエンティティを生成する
  		Todo todo1 = new Todo(2, "腕のストレッチをする", null, false, false);
  		//更新実行
  		todo1 = repository.save(todo1);
  		System.out.println("更新したデータ："+todo1);
  		System.out.println("更新処理終了");
  	}
  	
  	/**削除処理*/
  	private void deleteTodo() {
  		System.out.println("削除処理");
  		//削除実行
  		repository.deleteById(1);
  		repository.deleteById(2);
  		repository.deleteById(3);
  		repository.deleteById(4);
  		repository.deleteById(5);
  		System.out.println("削除処理終了");
  	}

}
