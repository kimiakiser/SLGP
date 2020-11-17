package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.persister.GenericPersisterDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class GenericPersisterDAOImpl<T> implements GenericPersisterDAO<T> {

    private static final Logger logger = LogManager.getLogger(GenericPersisterDAOImpl.class);

    protected Class<T> classType;

    public GenericPersisterDAOImpl(Class<T> type) {
        this.classType = type;
    }

    public T save(T entity) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error("Fehler beim Speichern der Entity vom Typ \'" + classType.getName() + "\': ["
                    + entity.toString() + "]", e);
            throw e;

        } finally {
            em.close();
        }

        return entity;
    }

    public T update(T entity) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();
        em.getTransaction().begin();

        T eMerged = null;

        try {
            eMerged = em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error("Fehler beim Update der Entity vom Typ \'" + classType.getName() + "\': [" + entity + "]", e);
            throw e;

        } finally {
            em.close();
        }

        return eMerged;
    }

    public void delete(T entity) throws Exception {

        EntityManager em = JPAUtil.createEntityManager();
        em.getTransaction().begin();

        try {

            if (em.contains(entity)) {
                em.remove(entity);
            } else {
                em.remove(em.merge(entity));
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            logger.error("Fehler beim Löschen der Entity vom Typ \'" + classType.getName() + "\': [" + entity + "]", e);

            throw e;

        } finally {
            em.close();
        }
    }

    public void deleteById(long id) throws Exception {

        T entity = findById(id);

        if (entity != null) {
            delete(entity);
        }

    }

    public T findById(long id) throws Exception {
        return JPAUtil.createEntityManager().find(classType, id);
    }

    public List<T> findAll() throws Exception {

        String sql = "SELECT entity FROM " + classType.getSimpleName() + " entity";

        EntityManager em = JPAUtil.createEntityManager();
        TypedQuery<T> q = em.createQuery(sql, classType);

        List<T> liste = q.getResultList();

        em.close();

        return liste != null ? liste : new ArrayList<T>();
    }

}
