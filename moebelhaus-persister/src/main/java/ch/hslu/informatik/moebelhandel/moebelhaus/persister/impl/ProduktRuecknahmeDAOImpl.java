package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktRuecknahme;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.ProduktRuecknahmeDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class ProduktRuecknahmeDAOImpl extends GenericPersisterDAOImpl<ProduktRuecknahme>
        implements ProduktRuecknahmeDAO {

    public ProduktRuecknahmeDAOImpl() {
        super(ProduktRuecknahme.class);
    }

    public List<ProduktRuecknahme> findByRechnung(Rechnung rechnung) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<ProduktRuecknahme> query = em.createNamedQuery("ProduktRuecknahme.findByRechnung",
                ProduktRuecknahme.class);

        query.setParameter("rechnung", rechnung);

        List<ProduktRuecknahme> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<ProduktRuecknahme>();
    }

    public List<ProduktRuecknahme> findByDatum(GregorianCalendar datum) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<ProduktRuecknahme> query = em.createNamedQuery("ProduktRuecknahme.findByDatum",
                ProduktRuecknahme.class);

        query.setParameter("datum", datum);

        List<ProduktRuecknahme> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<ProduktRuecknahme>();
    }

    public List<ProduktRuecknahme> findByProduktTyp(ProduktTyp produktTyp) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<ProduktRuecknahme> query = em.createNamedQuery("ProduktRuecknahme.findByProduktTyp",
                ProduktRuecknahme.class);

        query.setParameter("produktTyp", produktTyp);

        List<ProduktRuecknahme> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<ProduktRuecknahme>();
    }

}
