package com.bloodbank.dao;

import com.bloodbank.model.BloodStock;
import com.bloodbank.model.Donor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.util.List;

public class StockDAO {

    public List<Donor> allDonors() {
        try (Session s = HibernateUtil.get().openSession()) {
            return s.createQuery("from Donor order by id", Donor.class).list();
        }
    }

    public Donor findDonor(int id) {
        try (Session s = HibernateUtil.get().openSession()) {
            return s.get(Donor.class, id);
        }
    }

    /** Saves the stock row and updates the donor's last donation date in one transaction. */
    public void recordDonation(Donor donor, int units, LocalDate collected) {
        Transaction tx = null;
        try (Session s = HibernateUtil.get().openSession()) {
            tx = s.beginTransaction();
            BloodStock b = new BloodStock();
            b.setDonorId(donor.getId());
            b.setBloodGroup(donor.getBloodGroup());
            b.setUnits(units);
            b.setCollectedOn(collected);
            b.setExpiresOn(collected.plusDays(35));
            s.save(b);
            donor.setLastDonationDate(collected);
            s.update(donor);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Could not record donation", e);
        }
    }

    /** Rows: [blood_group, total non-expired units]. */
    public List<Object[]> stockSummary() {
        try (Session s = HibernateUtil.get().openSession()) {
            return s.createQuery(
                "select b.bloodGroup, sum(b.units) from BloodStock b " +
                "where b.expiresOn >= :today group by b.bloodGroup order by b.bloodGroup",
                Object[].class)
                .setParameter("today", LocalDate.now()).list();
        }
    }
}