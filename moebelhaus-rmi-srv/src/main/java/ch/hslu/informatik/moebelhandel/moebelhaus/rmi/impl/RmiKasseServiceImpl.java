package ch.hslu.informatik.moebelhandel.moebelhaus.rmi.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.GregorianCalendar;
import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.KasseService;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Benutzer;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktRuecknahme;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Rechnung;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api.RmiKasseService;
import ch.hslu.informatik.moebelhandel.verteiler.business.kasse.KasseManager;

public class RmiKasseServiceImpl extends UnicastRemoteObject implements RmiKasseService {

	private static final long serialVersionUID = -2773447565867707068L;
	
	private KasseService kasseService;
	
	public RmiKasseServiceImpl() throws RemoteException {

	}
	
	public KasseService getKasseService() {
		
		if (kasseService == null) {
			kasseService = new KasseManager();
		}
		
		return kasseService;
	}

	@Override
	public Rechnung verkaufen(List<Produkt> produktListe, Benutzer benutzer) throws Exception {
		return getKasseService().verkaufen(produktListe, benutzer);
	}

	@Override
	public void produktZuruecknehmen(ProduktRuecknahme produktRuecknahme) throws Exception {
		getKasseService().produktZuruecknehmen(produktRuecknahme);
		return;
	}

	@Override
	public Rechnung findByRechnungNummer(long nummer) throws Exception {
		return getKasseService().findByRechnungNummer(nummer);
	}

	@Override
	public ProduktTyp findByProduktTypCode(String produktTypCode) throws Exception {
		return getKasseService().findByProduktTypCode(produktTypCode);
	}

	@Override
	public Produkt findByProduktCode(long produktCode) throws Exception {
		return getKasseService().findByProduktCode(produktCode);
	}

	@Override
	public List<Rechnung> findByBenutzerUndDatum(Benutzer benutzer, GregorianCalendar datum) throws Exception {
		return getKasseService().findByBenutzerUndDatum(benutzer, datum);
	}

	@Override
	public List<Produkt> findAlleAmLagerVerfuegbareProdukte() throws Exception {
		return getKasseService().findAlleAmLagerVerfuegbareProdukte();
	}
}
