package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.BestellungDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class BestellungDAOImpl extends GenericPersisterDAOImpl<Bestellung> implements BestellungDAO {

    public BestellungDAOImpl() {
        super(Bestellung.class);
    }

    public List<Bestellung> findByDatum(GregorianCalendar datum) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Bestellung> query = em.createNamedQuery("Bestellung.findByDatum", Bestellung.class);

        query.setParameter("datum", datum);

        List<Bestellung> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<Bestellung>();
    }
}
