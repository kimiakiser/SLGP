package ch.hslu.informatik.moebelhandel.moebelhaus.persister.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	private static EntityManagerFactory entityManagerFactory = null;

	static {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("moebelhandel-moebelhaus-pu");
		} catch (Throwable e) {
			throw new RuntimeException();
		}
	}

	public static EntityManager createEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

	public static EntityManager createEntityManagerForDelition() {
		return Persistence.createEntityManagerFactory("delete-moebelhandel-moebelhaus-pu").createEntityManager();
	}
}
