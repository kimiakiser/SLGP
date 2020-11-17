package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lager;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.LagerDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class LagerDAOImpl extends GenericPersisterDAOImpl<Lager> implements LagerDAO {

    private static final Logger logger = LogManager.getLogger(LagerDAOImpl.class);

    public LagerDAOImpl() {
        super(Lager.class);
    }

    public Lager findByName(String name) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Lager> query = em.createNamedQuery("Lager.findByName", Lager.class);

        query.setParameter("name", name);

        List<Lager> liste = query.getResultList();

        em.close();

        if (liste.isEmpty()) {
            return null;
        } else if (liste.size() == 1) {
            return liste.get(0);
        } else {
            String message = "Mehr als eine Lager-Entity mit dem Namen \'" + name + "\' gefunden";
            logger.error(message);
            throw new IllegalStateException(message);
        }
    }

}
