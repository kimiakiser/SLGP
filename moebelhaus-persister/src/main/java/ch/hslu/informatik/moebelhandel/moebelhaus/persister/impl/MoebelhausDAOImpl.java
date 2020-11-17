package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Moebelhaus;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.MoebelhausDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class MoebelhausDAOImpl extends GenericPersisterDAOImpl<Moebelhaus> implements MoebelhausDAO {

    private static final Logger logger = LogManager.getLogger(MoebelhausDAOImpl.class);

    public MoebelhausDAOImpl() {
        super(Moebelhaus.class);
    }

    public Moebelhaus findByName(String name) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Moebelhaus> query = em.createNamedQuery("Moebelhaus.findByName", Moebelhaus.class);

        query.setParameter("name", name);

        List<Moebelhaus> liste = query.getResultList();

        em.close();

        if (liste.isEmpty()) {
            return null;
        } else if (liste.size() == 1) {
            return liste.get(0);
        } else {
            String message = "Mehr als eine Moebelhaus-Entity mit dem Namen \'" + name + "\' gefunden";
            logger.error(message);
            throw new IllegalStateException(message);
        }
    }

    public Moebelhaus findByMoebelhausCode(String moebelhausCode) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Moebelhaus> query = em.createNamedQuery("Moebelhaus.findByMoebelhausCode", Moebelhaus.class);

        query.setParameter("moebelhausCode", moebelhausCode);

        List<Moebelhaus> liste = query.getResultList();

        em.close();

        if (liste.isEmpty()) {
            return null;
        } else if (liste.size() == 1) {
            return liste.get(0);
        } else {
            String message = "Mehr als eine Moebelhaus-Entity mit dem Code \'" + moebelhausCode + "\' gefunden";
            logger.error(message);
            throw new IllegalStateException(message);
        }
    }

}
