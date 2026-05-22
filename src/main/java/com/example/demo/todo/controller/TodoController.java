package com.example.demo.todo.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String showList(
            TodoForm todoForm,
            Model model) {

        todoForm.setNewTodo(true);

        Iterable<Todo> list = service.selectNotDoneTodo();

        model.addAttribute("list", list);
        model.addAttribute("todoForm", todoForm);
        model.addAttribute("title", "登録タスク一覧");

        return "crud";
    }
    
    
    /** タスク追加画面を表示する */
    @GetMapping("/create")
    public String showCreate(TodoForm todoForm, Model model) {

        todoForm.setNewTodo(true);
        todoForm.setPriority(false);
        todoForm.setDone(false);

        model.addAttribute("todoForm", todoForm);
        model.addAttribute("title", "タスク追加");

        return "create";
    }
    
    /** 期限が今日のタスク一覧を表示する */
    @GetMapping("/today")
    public String showTodayList(TodoForm todoForm, Model model) {

        todoForm.setNewTodo(true);

        Iterable<Todo> list = service.selectTodayTodo();

        model.addAttribute("list", list);
        model.addAttribute("todoForm", todoForm);
        model.addAttribute("title", "今日が期限のタスク一覧");

        return "crud";
    }

    /** 期限が近い順でタスク一覧を表示する */
    @GetMapping("/sort/deadline")
    public String showDeadlineSortedList(TodoForm todoForm, Model model) {

        todoForm.setNewTodo(true);

        Iterable<Todo> list = service.selectNotDoneTodoOrderByDeadline();

        model.addAttribute("list", list);
        model.addAttribute("todoForm", todoForm);
        model.addAttribute("title", "期限が近い順のタスク一覧");

        return "crud";
    }
    
    /** 完了済タスク履歴一覧を表示します */
    @GetMapping("/done")
    public String showDoneList(
            TodoForm todoForm,
            Model model) {

        Iterable<Todo> doneList = service.selectDoneTodo();

        model.addAttribute("doneList", doneList);
        model.addAttribute("todoForm", todoForm);
        model.addAttribute("title", "完了済タスク履歴一覧");

        return "done";
    }
    
    /** Todoデータを1件挿入 */
    @PostMapping("/insert")
    public String insert(
            @Validated TodoForm todoForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        // FormからEntityへ変換
        Todo todo = new Todo();

        todo.setContent(todoForm.getContent());
        todo.setUser(todoForm.getUser());
        todo.setDeadline(todoForm.getDeadline());
        todo.setPriority(todoForm.getPriority());
        todo.setDone(todoForm.getDone());
        todo.setCreatedAt(LocalDateTime.now());

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

    /** TodoFormからTodoに変換して戻り値として返します */
    private Todo makeTodo(TodoForm todoForm) {

        Todo todo = new Todo();

        todo.setId(todoForm.getId());
        todo.setContent(todoForm.getContent());
        todo.setUser(todoForm.getUser());
        todo.setDeadline(todoForm.getDeadline());
        todo.setPriority(todoForm.getPriority());
        todo.setDone(todoForm.getDone());
        /** 更新の際は、作成日時を変更しないようにするため、既存のTodoデータを取得して作成日時をセットする */
        Optional<Todo> oldTodoOpt =
                service.selectOneById(todoForm.getId());

        if (oldTodoOpt.isPresent()) {
            todo.setCreatedAt(oldTodoOpt.get().getCreatedAt());
        }

        return todo;
    }

    /** TodoからTodoFormに変換して戻り値として返します */
    private TodoForm makeTodoForm(Todo todo) {

        TodoForm form = new TodoForm();

        form.setId(todo.getId());
        form.setContent(todo.getContent());
        form.setUser(todo.getUser());
        form.setDeadline(todo.getDeadline());
        form.setPriority(todo.getPriority());
        form.setDone(todo.getDone());

        form.setNewTodo(false);

        return form;
    }
    
    /** idをKeyにしてTodoデータを1件取得し、編集画面を表示する */
    @GetMapping("/{id}")
    public String showUpdate(
            @PathVariable Integer id,
            Model model) {

        Optional<Todo> todoOpt = service.selectOneById(id);

        if (todoOpt.isPresent()) {

            TodoForm todoForm = makeTodoForm(todoOpt.get());

            makeUpdateModel(todoForm, model);

            return "crud";

        } else {

            return "redirect:/todo";
        }
    }
    
    /** 優先度を切り替える */
    @PostMapping("/priority")
    public String priority(
            @RequestParam("id") Integer id) {

        service.togglePriority(id);

        return "redirect:/todo";
    }
    
    /** 完了状態を切り替える */
    @PostMapping("/done")
    public String done(
            @RequestParam("id") Integer id) {
    	
        service.toggleDone(id);
        
        return "redirect:/todo";
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