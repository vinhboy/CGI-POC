/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgi.poc.dw.dao;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.junit.DropwizardAppRule;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;

/**
 *
 * @author dawna.floyd
 */
public class DaoUnitTestBase {
    private  SessionFactory sessionFactory;
    private Session session;
    Validator validator;
    JSONParser parser;
    HibernateUtil dbUtil;


    @Before
    public void setUp() throws Exception {
        dbUtil = HibernateUtil.getInstance();
        sessionFactory = dbUtil.getSessionFactory();
        session = dbUtil.getOpenSession();
         
        Transaction beginTransaction = sessionFactory.getCurrentSession().beginTransaction();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        JSONParser parser = new JSONParser();

    }
    
    @After
    public void tearDown() {
        
        sessionFactory.getCurrentSession().close();
    }
    
    public Session getSession(){
        return session;
    }
    public void flush(){
        sessionFactory.getCurrentSession().flush();
    }   
    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }       
}
