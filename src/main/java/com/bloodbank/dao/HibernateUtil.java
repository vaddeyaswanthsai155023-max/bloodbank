package com.bloodbank.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory FACTORY = build();

    private static SessionFactory build() {
        Configuration cfg = new Configuration().configure();
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASSWORD");
        if (url != null)  cfg.setProperty("hibernate.connection.url", url);
        if (user != null) cfg.setProperty("hibernate.connection.username", user);
        if (pass != null) cfg.setProperty("hibernate.connection.password", pass);
        return cfg.buildSessionFactory();
    }

    public static SessionFactory get() { return FACTORY; }
}