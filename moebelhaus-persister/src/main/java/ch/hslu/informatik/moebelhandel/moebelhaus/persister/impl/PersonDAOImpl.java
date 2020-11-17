package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Person;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.PersonDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class PersonDAOImpl extends GenericPersisterDAOImpl<Person> implements PersonDAO {

    private static final Logger logger = LogManager.getLogger(PersonDAOImpl.class);

    public PersonDAOImpl() {
        super(Person.class);
    }

    public List<Person> findByNachname(String nachname) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Person> query = em.createNamedQuery("Person.findByNachname", Person.class);

        query.setParameter("nachname", nachname);

        List<Person> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<Person>();
    }

    public List<Person> findByVorname(String vorname) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Person> query = em.createNamedQuery("Person.findByVorname", Person.class);

        query.setParameter("vorname", vorname);

        List<Person> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<Person>();
    }

    public List<Person> findByNachnameUndVorname(String nachname, String vorname) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Person> query = em.createNamedQuery("Person.findByNachnameUndVorname", Person.class);

        query.setParameter("nachname", nachname);
        query.setParameter("vorname", vorname);

        List<Person> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<Person>();
    }

    public Person findByEmail(String email) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Person> query = em.createNamedQuery("Person.findByEmail", Person.class);

        query.setParameter("email", email);

        List<Person> liste = query.getResultList();

        em.close();

        if (liste.isEmpty()) {
            return null;
        } else if (liste.size() == 1) {
            return liste.get(0);
        } else {
            String message = "Mehr als eine Person-Entity mit E-Mail \'" + email + "\' gefunden";
            logger.error(message);
            throw new IllegalStateException(message);
        }
    }

}
