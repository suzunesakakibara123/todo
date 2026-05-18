# todo-application
「Todoアプリ」をEclipseからコミット＆プッシュするためのリポジトリ。

～設計図～
1. 画面
- TODO一覧画面
- TODO登録画面
- TODO編集画面

2. 機能
- TODOを登録する
- TODOを一覧表示する
- TODOを1件編集する
- TODOを削除する
- 完了/未完了を切り替える

3. テーブル
todo
- id
- title
- detail
- deadline
- completed
- created_at
- updated_at

4. Javaのクラス
- TodoEntity
- TodoForm
- TodoRepository
- TodoService
- TodoServiceImpl
- TodoController

5. URL設計
GET  /todo        一覧表示
POST /todo/insert 登録
GET  /todo/{id}   編集画面表示
POST /todo/delete 削除
POST /todo/check  完了切替
