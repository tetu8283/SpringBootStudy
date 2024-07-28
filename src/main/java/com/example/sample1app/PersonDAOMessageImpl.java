package com.example.sample1app;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDAOMessageImpl implements PersonDAO<Message>{
    private static final long serialVersionUID= 1L;

    @PersistenceContext
    private EntityManager entityManager;

    public PersonDAOMessageImpl() {
        super();
    }

    @Override
    public List<Message> getAll() {
        List<Message> list = null;
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = builder.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        query.select(root).orderBy(builder.desc(root.get("datetime")));
        list = (List<Message>) entityManager.createQuery(query).getResultList();
        return list;
    }

    @Override
    public List<Message> getPage(int page, int limit) {
        int offset = page * limit; // 取り出す位置の指定
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = builder.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        query.select(root);
        return (List<Message>) entityManager.createQuery(query)
        .setFirstResult(offset)
        .setMaxResults(limit)
        .getResultList();
    }

    @Override
    // public Message findById(Long id) {
    //     return (Message) entityManager.createQuery("from Message where id = " + id).getSingleResult();
    // }
    //修正したもの
    public Message findById(Long id) {
        return entityManager.find(Message.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    // public List<Message> findByName(String name) {
    //     return (List<Message>) entityManager.createQuery("from Message where name = '" + name + "'").getResultList();
    // }

    public List<Message> findByName(String name) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = builder.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        query.select(root).where(builder.equal(root.get("content"), name));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Message> find(String fstr) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = builder.createQuery(Message.class);
        Root<Message> root = query.from(Message.class);
        query.select(root).where(builder.equal(root.get("content"), fstr));
        List<Message> list = (List<Message>) entityManager.createQuery(query).getResultList();
        return list;
    }
}