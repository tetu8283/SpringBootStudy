package com.example.sample1app;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;

import com.example.sample1app.repositories.PersonRepository;

import jakarta.transaction.Transactional;

@Controller
public class HelloController {

    // PersonRepositoryをフィールドに関連付けるために@Autowiredアノテーションを使用
    @Autowired
    PersonRepository repository;

    // "/"にアクセスした際の処理
    @RequestMapping("/")
    // ModelAttributeはエンティティのインスタンスを自動的に作成する
    public ModelAndView index(@ModelAttribute("formModel") Person Person, ModelAndView mav){
        // ビュー名を"index"に設定
        mav.setViewName("index");
        // タイトルを設定
        mav.addObject("title", "Hello page");
        // メッセージを設定
        mav.addObject("msg", "This is JPA sample data");
        // データベースから全Personエンティティを取得
        List<Person> list = repository.findAll();
        // 取得したデータをビューに渡す
        mav.addObject("data", list);
        return mav;
    }

    // フォームの送信を処理するメソッド
    @PostMapping("/")  // formの送信
    @Transactional // このメソッドを一つのトランザクションとして一括に処理する
    public ModelAndView form(@ModelAttribute("formModel") Person Person, ModelAndView mav) {
        // 新しいPersonエンティティをデータベースに保存
        repository.saveAndFlush(Person);  // saveAndFlushは引数のエンティティを永続化(保存)する
        // 処理が終わった後、ルートページにリダイレクト
        return new ModelAndView("redirect:/");
    }

    // "/edit/{id}"にアクセスした際の処理
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@ModelAttribute Person Person, @PathVariable int id, ModelAndView mav){
        mav.setViewName("edit");
        mav.addObject("title", "Edit Person");
        // findByIdはselect * from テーブル名 where idと同じような処理
        Optional<Person> data = repository.findById((long)id);
        // 取得したPersonをformModelでedit.htmlに渡す
        mav.addObject("formModel", data.get());
        return mav;
    }

    // 編集した内容を保存するメソッド
    @PostMapping("/edit")
    @Transactional // このメソッドを一つのトランザクションとして一括に処理する
    public ModelAndView update(@ModelAttribute Person Person, ModelAndView mav){
        // 編集したPersonエンティティをデータベースに保存
        repository.saveAndFlush(Person);  // saveAndFlushで保存している
        // 処理が終わった後、ルートページにリダイレクト
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable int id, ModelAndView mav){
        mav.setViewName("delete");
        mav.addObject("title", "Delete Person");
        mav.addObject("msg", "Can I delete this record ?");
        // where idと同じ意味のdeleteのSQL文と同じ
        Optional<Person> data = repository.findById((long)id);
        mav.addObject("formModel", data.get());
        return mav;
    }

    @PostMapping("/delete")
    @Transactional
    public ModelAndView remove(@RequestParam long id, ModelAndView mav){
        repository.deleteById(id);
        return new ModelAndView("redirect:/");
    }


    // インスタンスが生成された後に一度だけ呼び出される初期化メソッド
    @PostConstruct
    public void init(){
        // 1つ目のダミーデータ作成
        Person p1 = new Person();
        p1.setName("taro");
        p1.setAge(39);
        p1.setMail("taro@yamada");
        p1.setMemo("ダミー１");
        // データベースに保存
        repository.saveAndFlush(p1);

        // 2つ目のダミーデータ作成
        Person p2 = new Person();
        p2.setName("hanako");
        p2.setAge(28);
        p2.setMail("hanako@flower");
        p2.setMemo("ダミー2");
        // データベースに保存
        repository.saveAndFlush(p2);

        // 3つ目のダミーデータ作成
        Person p3 = new Person();
        p3.setName("sachiko");
        p3.setAge(17);
        p3.setMail("sachico@happy");
        p3.setMemo("ダミー3");
        // データベースに保存
        repository.saveAndFlush(p3);
    }
}

