package com.cgi.poc.dw.dao;

import com.cgi.poc.dw.dao.model.EventNotification;
import com.cgi.poc.dw.dao.model.EventNotificationUser;
import com.cgi.poc.dw.dao.model.User;
import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import javax.validation.Validator;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

public class EventNotificationDAO extends AbstractDAO<EventNotification> {

    Validator validator;

    @Inject
    public EventNotificationDAO(SessionFactory factory, Validator validator) {
        super(factory);
        this.validator = validator;
    }

    public EventNotification save(EventNotification eventNotification) {
        eventNotification = this.persist(eventNotification);
        return eventNotification;
    }

    public List<EventNotification> retrieveAll() {
        Criteria criteria = this.criteria();
        criteria.addOrder(Order.desc("generationDate"));
        List<EventNotification> resultList = criteria.list();
        return resultList;
    }
    public List<EventNotification> retrieveAllForUser(User user) {
         Criteria criteria = this.currentSession().createCriteria(EventNotification.class)
                      .createAlias("eventNotificationUser", "a")
                      .add(Restrictions.eq("a.userId.id", user.getId()));
        criteria.addOrder(Order.desc("generationDate"));

        List<EventNotification> resultList = criteria.list();
        return resultList;
    }
}
