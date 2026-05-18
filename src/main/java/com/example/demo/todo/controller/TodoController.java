package com.example.demo.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.todo.entity.Todo;
import com.example.demo.todo.form.TodoForm;
import com.example.demo.todo.service.TodoService;

/** Todoコントローラ */
@Controller
@RequestMapping("/todo")
public class TodoController {

    /** DI対象 */
    @Autowired
    TodoService service;

    /** 「form-backing bean」の初期化 */
    @ModelAttribute
    public TodoForm setUpForm() {

        TodoForm form = new TodoForm();

        // 優先度の初期値
        form.setPriority(false);

        // 完了状態の初期値
        form.setDone(false);

        return form;
    }

    /** Todoの一覧を表示します */
    @GetMapping
    public String showList(TodoForm todoForm, Model model) {

        // 新規登録設定
        todoForm.setNewTodo(true);

        // 掲示板の一覧を取得する
        Iterable<Todo> list = service.selectAll();

        // 表示用「Model」への格納
        model.addAttribute("list", list);
        model.addAttribute("title", "登録用フォーム");

        return "crud";
    }
    
    /** Todoデータを1件挿入 */
    @PostMapping("/insert")
    public String insert(
            @Validated TodoForm todoForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        // FormからEntityへの詰め替え
        Todo todo = new Todo();

        todo.setContents(todoForm.getContents());
        todo.setDeadln(todoForm.getDeadln());
        todo.setPriority(todoForm.getPriority());
        todo.setDone(todoForm.getDone());

        // 入力チェック
        if (!bindingResult.hasErrors()) {

            service.insertTodo(todo);

            redirectAttributes.addFlashAttribute(
                    "complete",
                    "登録が完了しました");

            return "redirect:/todo";

        } else {

            // エラーがある場合は、一覧表示処理を呼びます
            return showList(todoForm, model);
        }
    }

    /** 更新用のModelを作成する */
    private void makeUpdateModel(
            TodoForm todoForm,
            Model model) {

        model.addAttribute("id", todoForm.getId());

        todoForm.setNewTodo(false);

        model.addAttribute("todoForm", todoForm);

        model.addAttribute("title", "更新用フォーム");
    }

    /** idをKeyにしてデータを更新する */
    @PostMapping("/update")
    public String update(
            @Validated TodoForm todoForm,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        // TodoFormからTodoに詰め直す
        Todo todo = makeTodo(todoForm);

        // 入力チェック
        if (!result.hasErrors()) {

            // 更新処理、フラッシュスコープの使用、リダイレクト
            service.updateTodo(todo);

            redirectAttributes.addFlashAttribute(
                    "complete",
                    "更新が完了しました");

            // 更新画面を表示する
            return "redirect:/todo/" + todo.getId();

        } else {

            // 更新用のModelを作成する
            makeUpdateModel(todoForm, model);

            return "crud";
        }
    }

    /* ---------- 以下はFormとDomainObjectの詰めなおし ---------- */

    /** TodoFormからTodoに詰め直して戻り値として返します */
    private Todo makeTodo(TodoForm todoForm) {

        Todo todo = new Todo();

        todo.setId(todoForm.getId());
        todo.setContents(todoForm.getContents());
        todo.setDeadln(todoForm.getDeadln());
        todo.setPriority(todoForm.getPriority());
        todo.setDone(todoForm.getDone());

        return todo;
    }

    /** TodoからTodoFormに詰め直して戻り値として返します */
    private TodoForm makeTodoForm(Todo todo) {

        TodoForm form = new TodoForm();

        form.setId(todo.getId());
        form.setContents(todo.getContents());
        form.setDeadln(todo.getDeadln());
        form.setPriority(todo.getPriority());
        form.setDone(todo.getDone());

        form.setNewTodo(false);

        return form;
    }
    /** idをKeyにしてデータを削除する */
    @PostMapping("/delete")
    public String delete(
            @RequestParam("id") String id,
            Model model,
            RedirectAttributes redirectAttributes) {

        // タスクを1件削除してリダイレクト
        service.deleteTodoById(Integer.parseInt(id));

        redirectAttributes.addFlashAttribute(
                "delcomplete",
                "削除が完了しました");

        return "redirect:/todo";
    }

}