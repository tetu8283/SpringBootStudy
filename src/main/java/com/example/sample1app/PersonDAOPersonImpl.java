package com.example.sample1app;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDAOPersonImpl implements PersonDAO<Person> {
    private static final long serialVersionUID = 1L;

    @PersistenceContext
    private EntityManager entityManager;

    public PersonDAOPersonImpl() {
        super();
    }

    @Override
    public List<Person> getAll() {
        Query query = entityManager.createQuery("from Person");
        @SuppressWarnings("unchecked")
        List<Person> list = query.getResultList();
        return list;
    }

    @Override
    public List<Person> find(String fstr) {
        List<Person> list = null;
        String qstr = "from Person where id = :fid or name like :fname or mail like :fmail";
        Long fid = 0L;
        try {
            fid = Long.parseLong(fstr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Query query = entityManager.createQuery(qstr)
            .setParameter("fid", fid)
            .setParameter("fname", "%" + fstr + "%")
            .setParameter("fmail", fstr + "%@%");
        list = query.getResultList();
        return list;
    }

    @Override
    public List<Message> getPage(int page, int limit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPage'");
    }

    @Override
    public Message findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Message> findByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByName'");
    }
}
