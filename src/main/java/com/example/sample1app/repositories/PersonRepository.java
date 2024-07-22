package com.example.sample1app.repositories;

import java.util.Optional;  //findByIdを使用するためにimport

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sample1app.Person;


//DB接続するためのクラスに記述する
@Repository 
public interface PersonRepository extends JpaRepository<Person, Long> {
    //findByIdはidを引数にして、インスタンスを取り出す
    public Optional <Person> findById(Long name);
}