package com.bloodbank.dao;

import com.bloodbank.exception.InsufficientStockException;
import com.bloodbank.model.BloodRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.util.List;

public class RequestDAO {

    public void save(BloodRequest r) {
        Transaction tx = null;
        try (Session s = HibernateUtil.get().openSession()) {
            tx = s.beginTransaction();
            s.save(r);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Could not save request", e);
        }
    }

    public List<BloodRequest> byHospital(int hospitalId) {
        try (Session s = HibernateUtil.get().openSession()) {
            return s.createQuery("from BloodRequest where hospitalId = :h order by id desc",
                    BloodRequest.class).setParameter("h", hospitalId).list();
        }
    }

    public List<BloodRequest> all() {
        try (Session s = HibernateUtil.get().openSession()) {
            return s.createQuery("from BloodRequest order by id desc", BloodRequest.class).list();
        }
    }

    public void reject(int requestId) {
        Transaction tx = null;
        try (Session s = HibernateUtil.get().openSession()) {
            tx = s.beginTransaction();
            BloodRequest r = s.get(BloodRequest.class, requestId);
            if (r != null && "PENDING".equals(r.getStatus())) {
                r.setStatus("REJECTED");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Could not reject request", e);
        }
    }

    /** Approves a request and removes units from the oldest non-expired stock first. */
    public void approve(int requestId) throws InsufficientStockException {
        Transaction tx = null;
        try (Session s = HibernateUtil.get().openSession()) {
            tx = s.beginTransaction();
            BloodRequest r = s.get(BloodRequest.class, requestId);
            if (r == null || !"PENDING".equals(r.getStatus())) {
                tx.rollback();
                return;
            }
            List<com.bloodbank.model.BloodStock> rows = s.createQuery(
                "from BloodStock where bloodGroup = :g and expiresOn >= :t and units > 0 order by expiresOn",
                com.bloodbank.model.BloodStock.class)
                .setParameter("g", r.getBloodGroup())
                .setParameter("t", LocalDate.now()).list();
            int available = 0;
            for (com.bloodbank.model.BloodStock b : rows) available += b.getUnits();
            if (available < r.getUnits()) {
                tx.rollback();
                throw new InsufficientStockException("Only " + available + " unit(s) of "
                        + r.getBloodGroup() + " available, but " + r.getUnits() + " requested.");
            }
            int needed = r.getUnits();
            for (com.bloodbank.model.BloodStock b : rows) {
                if (needed == 0) break;
                int take = Math.min(needed, b.getUnits());
                b.setUnits(b.getUnits() - take);
                needed -= take;
            }
            r.setStatus("APPROVED");
            tx.commit();
        } catch (InsufficientStockException e) {
            throw e;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw new RuntimeException("Could not approve request", e);
        }
    }
}