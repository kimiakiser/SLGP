package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Ruecksendung;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.RuecksendungDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class RuecksendungDAOImpl extends GenericPersisterDAOImpl<Ruecksendung> implements RuecksendungDAO {

    public RuecksendungDAOImpl() {
        super(Ruecksendung.class);
    }

    public List<Ruecksendung> findByDatum(GregorianCalendar datum) throws Exception {

        /* Datum 'von' */
        GregorianCalendar startDatum = new GregorianCalendar(datum.get(Calendar.YEAR), datum.get(Calendar.MONTH),
                datum.get(Calendar.DAY_OF_MONTH));

        /* Datum 'bis' */
        GregorianCalendar endDatum = new GregorianCalendar(datum.get(Calendar.YEAR), datum.get(Calendar.MONTH),
                datum.get(Calendar.DAY_OF_MONTH) + 1);

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Ruecksendung> query = em.createNamedQuery("Ruecksendung.findByDatum", Ruecksendung.class);

        query.setParameter("startDatum", startDatum);
        query.setParameter("endDatum", endDatum);

        List<Ruecksendung> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<Ruecksendung>();
    }

}
