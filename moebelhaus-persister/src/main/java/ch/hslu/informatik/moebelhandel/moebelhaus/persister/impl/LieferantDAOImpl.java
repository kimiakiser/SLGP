package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.LieferantDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class LieferantDAOImpl extends GenericPersisterDAOImpl<Lieferant> implements LieferantDAO {

    private static final Logger logger = LogManager.getLogger(LieferantDAOImpl.class);

    public LieferantDAOImpl() {
        super(Lieferant.class);
    }

    public Lieferant findByName(String name) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Lieferant> query = em.createNamedQuery("Lieferant.findByName", Lieferant.class);

        query.setParameter("name", name);

        List<Lieferant> liste = query.getResultList();

        em.close();

        if (liste.isEmpty()) {
            return null;
        } else if (liste.size() == 1) {
            return liste.get(0);
        } else {
            String message = "Mehr als eine Lieferant-Entity mit dem Namen \'" + name + "\' gefunden";
            logger.error(message);
            throw new IllegalStateException(message);
        }
    }

}
