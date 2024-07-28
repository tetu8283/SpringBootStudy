package com.example.sample1app;

import java.io.Serializable;
import java.util.List;

public interface PersonDAO <T> extends Serializable {
    //全部の情報を取得
    public List<T> getAll();
    //引数でStringの引数を受取、Listとして結果を受取
    public List<T> find(String fstr);

    //以下 MessageImplのメソッドの記述
    public List<Message> getPage(int page, int limit);

    public Message findById(Long id);

    public List<Message> findByName(String name);



}
