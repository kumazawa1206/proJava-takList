package jp.gihyo.pro.java.tasklist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeRestController {

  //タスク情報を保持するための入れ物
  record TaskItem(String id, String task, String deadline, boolean done) {

  }

  private List<TaskItem> taskItems = new ArrayList<>();


  //クライアントからのリクエストを処理するメソッド
  //http:~/resthelloを入力すると返り値を返す
  @RequestMapping(value = "/resthello")
  String hello() {
    return """
        Hello.
        It works!
        現在時刻は %s です。
        """.formatted(LocalDateTime.now());
  }

  //エンドポイントの作成
  //タスクを追加したり、登録されているタスクの一覧表示
  //
  @GetMapping("/restadd")
  String addItem(@RequestParam("task") String task,
      @RequestParam("deadline") String deadline) {
    String id = UUID.randomUUID().toString().substring(0, 8);
    TaskItem item = new TaskItem(id, task, deadline, false);
    taskItems.add(item);
    return "タスクを追加しました。";
  }

  @GetMapping("/restlist")
  String listItems() {
    String result = taskItems.stream()
        .map(TaskItem::toString)
        .collect(Collectors.joining(","));
    return result;
  }

}
