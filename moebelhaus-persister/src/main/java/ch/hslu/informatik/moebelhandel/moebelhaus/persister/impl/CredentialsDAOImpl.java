package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Credentials;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.CredentialsDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class CredentialsDAOImpl extends GenericPersisterDAOImpl<Credentials> implements CredentialsDAO {

    private static final Logger logger = LogManager.getLogger(CredentialsDAOImpl.class);

    public CredentialsDAOImpl() {
        super(Credentials.class);
    }

    public Credentials findByBenutzername(String benutzername) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        TypedQuery<Credentials> query = em.createNamedQuery("Credentials.findByBenutzername", Credentials.class);

        query.setParameter("benutzername", benutzername);

        List<Credentials> liste = query.getResultList();

        em.close();

        if (liste.isEmpty()) {
            return null;
        } else if (liste.size() == 1) {
            return liste.get(0);
        } else {
            String message = "Mehr als eine Credentials-Entity mit dem Benutzernamen \'" + benutzername + "\' gefunden";
            logger.error(message);
            throw new IllegalStateException(message);
        }
    }

}
