package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Moebelhaus;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.LieferungDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class LieferungDAOImpl extends GenericPersisterDAOImpl<Lieferung> implements LieferungDAO {

    public LieferungDAOImpl() {
        super(Lieferung.class);
    }

    public List<Lieferung> findByDatum(GregorianCalendar datum) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Lieferung> query = em.createNamedQuery("Lieferung.findByDatum", Lieferung.class);

        query.setParameter("datum", datum);

        List<Lieferung> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<Lieferung>();
    }

    public List<Lieferung> findByFiliale(Moebelhaus filiale) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Lieferung> query = em.createNamedQuery("Lieferung.findByFiliale", Lieferung.class);

        query.setParameter("filiale", filiale);

        List<Lieferung> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<Lieferung>();
    }

}
