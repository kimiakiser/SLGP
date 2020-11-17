package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.LieferungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.LieferungPositionDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class LieferungPositionDAOImpl extends GenericPersisterDAOImpl<LieferungPosition>
        implements LieferungPositionDAO {

    public LieferungPositionDAOImpl() {
        super(LieferungPosition.class);
    }

    public List<LieferungPosition> findByProduktTyp(ProduktTyp produktTyp) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<LieferungPosition> query = em.createNamedQuery("LieferungPosition.findByProduktTyp",
                LieferungPosition.class);

        query.setParameter("produktTyp", produktTyp);

        List<LieferungPosition> liste = query.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<LieferungPosition>();
    }

}
