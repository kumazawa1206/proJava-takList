package jp.gihyo.pro.java.tasklist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//コントローラの生成
@Controller
public class HomeController {

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
    model.addAttribute("taskList", taskItems);
    return "home";
  }
}
