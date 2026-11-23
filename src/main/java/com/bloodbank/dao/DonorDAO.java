package com.bloodbank.dao;

import com.bloodbank.model.Donor;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class DonorDAO {
    public Donor findByUserId(int userId) {
        try (Session s = HibernateUtil.get().openSession()) {
            return s.createQuery("from Donor where userId = :u", Donor.class)
                    .setParameter("u", userId).uniqueResult();
        }
    }

    public void saveOrUpdate(Donor d) {
        Transaction tx = null;
        try (Session s = HibernateUtil.get().openSession()) {
            tx = s.beginTransaction();
            s.saveOrUpdate(d);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Could not save donor", e);
        }
    }
}