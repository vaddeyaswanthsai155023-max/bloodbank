package com.bloodbank.dao;

import com.bloodbank.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDAO {
    public User findByEmail(String email) {
        try (Session s = HibernateUtil.get().openSession()) {
            return s.createQuery("from User where email = :e", User.class)
                    .setParameter("e", email).uniqueResult();
        }
    }

    public void save(User u) {
        Transaction tx = null;
        try (Session s = HibernateUtil.get().openSession()) {
            tx = s.beginTransaction();
            s.save(u);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Could not save user", e);
        }
    }
}