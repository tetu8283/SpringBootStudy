package com.example.sample1app.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sample1app.Person;

//DB接続するためのクラスに記述する
@Repository 
public interface PersonRepository extends JpaRepository<Person, Long> {

}