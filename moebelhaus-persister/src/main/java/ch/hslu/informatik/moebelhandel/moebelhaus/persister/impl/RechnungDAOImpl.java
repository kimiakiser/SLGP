package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.RechnungDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class RechnungDAOImpl extends GenericPersisterDAOImpl<Rechnung> implements RechnungDAO {

    public RechnungDAOImpl() {
        super(Rechnung.class);
    }

    public List<Rechnung> findByDatum(GregorianCalendar datum) throws Exception {

        /* Datum 'von' */
        GregorianCalendar startDatum = new GregorianCalendar(datum.get(Calendar.YEAR), datum.get(Calendar.MONTH),
                datum.get(Calendar.DAY_OF_MONTH));

        /* Datum 'bis' */
        GregorianCalendar endDatum = new GregorianCalendar(datum.get(Calendar.YEAR), datum.get(Calendar.MONTH),
                datum.get(Calendar.DAY_OF_MONTH) + 1);

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Rechnung> query = em.createNamedQuery("Rechnung.findByDatum", Rechnung.class);

        query.setParameter("startDatum", startDatum);
        query.setParameter("endDatum", endDatum);

        List<Rechnung> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<Rechnung>();
    }

    public List<Rechnung> findByBenutzer(Benutzer benutzer) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Rechnung> query = em.createNamedQuery("Rechnung.findByBenutzer", Rechnung.class);

        query.setParameter("benutzer", benutzer);

        List<Rechnung> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<Rechnung>();

    }

    public List<Rechnung> findByBenutzerUndDatum(Benutzer benutzer, GregorianCalendar datum) throws Exception {

        /* Datum 'von' */
        GregorianCalendar startDatum = new GregorianCalendar(datum.get(Calendar.YEAR), datum.get(Calendar.MONTH),
                datum.get(Calendar.DAY_OF_MONTH));

        /* Datum 'bis' */
        GregorianCalendar endDatum = new GregorianCalendar(datum.get(Calendar.YEAR), datum.get(Calendar.MONTH),
                datum.get(Calendar.DAY_OF_MONTH) + 1);

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Rechnung> query = em.createNamedQuery("Rechnung.findByBenutzerUndDatum", Rechnung.class);

        query.setParameter("benutzer", benutzer);
        query.setParameter("startDatum", startDatum);
        query.setParameter("endDatum", endDatum);

        List<Rechnung> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<Rechnung>();
    }

}
