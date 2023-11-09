package jp.gihyo.pro.java.tasklist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//コントローラの生成
@Controller
public class HomeController {

  private final TaskListDao dao;

  //タスク情報を保持するための入れ物
  record TaskItem(String id, String task, String deadline, boolean done) {

  }

  private List<TaskItem> taskItems = new ArrayList<>();

  //"/hello"はURLの記述の事
  @RequestMapping(value = "/hello")
  //ModelはJavaプログラムとHTMLテンプレート間で値を受け渡す役割がある。
  String hello(Model model) {
    model.addAttribute("time", LocalDateTime.now());
    //return "hello"はhello.htmlの事
    //ここでの返り値はhello.htmlになる。
    return "hello";
  }

  @GetMapping("/list")
  String listItems(Model model) {
    //findAllメソッドを呼び出してデータベースから情報を取得
    List<TaskItem> taskItems = dao.findAll();
    model.addAttribute("taskList", taskItems);
    return "home";
  }

  //表示するページを指定のパスにリダイレクトする
  //listItemsメソッドのエンドポイントにリダイレクトされる
  @GetMapping("/add")
  String addItem(@RequestParam("task") String task,
      @RequestParam("deadline") String deadline) {
    String id = UUID.randomUUID().toString().substring(0, 8);
    TaskItem item = new TaskItem(id, task, deadline, false);
    dao.add(item);
    return "redirect:/list";
  }

  //タスク情報を削除するエンドポイント
  //IDを引数にしてTaskListDaoクラスのdeleteメソッドを呼び出す。
  @GetMapping("/delete")
  String deleteItem(@RequestParam("id") String id) {
    dao.delete(id);
    return "redirect:/list";
  }

  @Autowired
  HomeController(TaskListDao dao) {
    this.dao = dao;
  }
}
