package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.BestellungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.BestellungPositionDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class BestellungPositionDAOImpl extends GenericPersisterDAOImpl<BestellungPosition>
        implements BestellungPositionDAO {

    public BestellungPositionDAOImpl() {
        super(BestellungPosition.class);
    }

    public List<BestellungPosition> findByProduktTyp(ProduktTyp produktTyp) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();
        TypedQuery<BestellungPosition> query = em.createNamedQuery("BestellungPosition.findByProduktTyp",
                BestellungPosition.class);
        query.setParameter("produktTyp", produktTyp);

        List<BestellungPosition> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<BestellungPosition>();
    }

}
