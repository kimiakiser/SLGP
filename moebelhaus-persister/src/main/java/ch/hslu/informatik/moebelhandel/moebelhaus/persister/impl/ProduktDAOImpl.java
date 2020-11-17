package ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.ProduktDAO;
import ch.hslu.informatik.moebelhandel.moebelhaus.persister.util.JPAUtil;

public class ProduktDAOImpl extends GenericPersisterDAOImpl<Produkt> implements ProduktDAO {

	private static final Logger logger = LogManager.getLogger(ProduktDAOImpl.class);

	public ProduktDAOImpl() {
		super(Produkt.class);
	}

	public List<Produkt> findByProduktTyp(ProduktTyp produktTyp) throws Exception {

		EntityManager em = JPAUtil.createEntityManager();

		TypedQuery<Produkt> query = em.createNamedQuery("Produkt.findByProduktTyp", Produkt.class);

		query.setParameter("produktTyp", produktTyp);

		List<Produkt> liste = query.getResultList();

		em.close();

		return liste != null ? liste : new ArrayList<Produkt>();
	}

	public List<Produkt> findByProduktTypCode(String typCode) throws Exception {

		EntityManager em = JPAUtil.createEntityManager();

		TypedQuery<Produkt> query = em.createNamedQuery("Produkt.findByProduktTypCode", Produkt.class);

		query.setParameter("typCode", typCode);

		List<Produkt> liste = query.getResultList();

		em.close();

		return liste != null ? liste : new ArrayList<Produkt>();
	}

	public Produkt findByCode(long code) throws Exception {

		EntityManager em = JPAUtil.createEntityManager();

		TypedQuery<Produkt> query = em.createNamedQuery("Produkt.findByCode", Produkt.class);

		query.setParameter("code", code);

		List<Produkt> liste = query.getResultList();

		em.close();

		if (liste.isEmpty()) {
			return null;
		} else if (liste.size() == 1) {
			return liste.get(0);
		} else {
			String message = "Mehr als eine Produkt-Entity mit dem Code \'" + code + "\' gefunden";
			logger.error(message);
			throw new IllegalStateException(message);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.
	 * GenericPersisterDAOImpl#findById(long)
	 */
	@Override
	public Produkt findById(long id) throws Exception {

		EntityManager em = JPAUtil.createEntityManager();

		TypedQuery<Produkt> query = em.createQuery("SELECT e FROM Produkt e WHERE e.id=:id AND e.verkauft=FALSE",
				Produkt.class);

		query.setParameter("id", id);

		List<Produkt> liste = query.getResultList();

		em.close();

		if (liste.isEmpty()) {
			return null;
		} else if (liste.size() == 1) {
			return liste.get(0);
		} else {
			String message = "Mehr als eine Produkt-Entity mit dem Id-Wert \'" + id + "\' gefunden";
			logger.error(message);
			throw new IllegalStateException(message);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.hslu.informatik.moebelhandel.moebelhaus.persister.impl.
	 * GenericPersisterDAOImpl#findAll()
	 */
	@Override
	public List<Produkt> findAll() throws Exception {

		EntityManager em = JPAUtil.createEntityManager();

		TypedQuery<Produkt> query = em.createQuery("SELECT e FROM Produkt e WHERE e.verkauft=FALSE", Produkt.class);

		List<Produkt> liste = query.getResultList();

		em.close();

		return liste != null ? liste : new ArrayList<Produkt>();
	}

}
