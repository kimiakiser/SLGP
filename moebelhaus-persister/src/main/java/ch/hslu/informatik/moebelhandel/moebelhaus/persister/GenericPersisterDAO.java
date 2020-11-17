package ch.hslu.informatik.moebelhandel.moebelhaus.persister;

import java.util.List;

/**
 * Interface für CRUD-Basisoperationen.
 *
 * @author jsucur
 * @version 1.0
 *
 * @param <T>
 *
 */
public interface GenericPersisterDAO<T> {

	/**
	 * Speichert die übergebene Entity.
	 *
	 * @param entity
	 * @throws Exception
	 */
	T save(T entity) throws Exception;

	/**
	 * Updatet die übergebene Entity.
	 *
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	T update(T entity) throws Exception;

	/**
	 * Löscht die übergebene Entity.
	 *
	 * @param entity
	 * @throws Exception
	 */
	void delete(T entity) throws Exception;

	/**
	 * Löscht die Entity für den übergebenen Id-Wert.
	 *
	 * @param id
	 * @throws Exception
	 */
	void deleteById(long id) throws Exception;

	/**
	 * Liefert die Entity für den übergebenen Id-Wert zurück.
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	T findById(long id) throws Exception;

	/**
	 * Liefert alle Entity-Objekte zurück.
	 *
	 * @return
	 * @throws Exception
	 */
	List<T> findAll() throws Exception;

}
