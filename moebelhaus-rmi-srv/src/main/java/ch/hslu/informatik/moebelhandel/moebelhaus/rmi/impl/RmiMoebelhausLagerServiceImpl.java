package ch.hslu.informatik.moebelhandel.moebelhaus.rmi.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.MoebelhausLagerService;
import ch.hslu.informatik.moebelhandel.moebelhaus.business.lager.MoebelhausLagerManager;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Moebelhaus;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Produkt;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Regal;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Ruecksendung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Tablar;
import ch.hslu.informatik.moebelhandel.moebelhaus.rmi.api.RmiMoebelhausLagerService;

public class RmiMoebelhausLagerServiceImpl extends UnicastRemoteObject implements RmiMoebelhausLagerService {

	private static final long serialVersionUID = 6786675851138673916L;
	
	private MoebelhausLagerService moebelhausLagerService;

	public RmiMoebelhausLagerServiceImpl() throws RemoteException {

	}

	public MoebelhausLagerService getMoebelhausLagerService() {

		if (moebelhausLagerService == null) {
			moebelhausLagerService = new MoebelhausLagerManager();
		}

		return moebelhausLagerService;
	}

	@Override
	public Bestellung bestellen(Bestellung bestellung) throws Exception {
		return getMoebelhausLagerService().bestellen(bestellung);
	}

	@Override
	public List<Produkt> produktBestand(ProduktTyp produktTyp) throws Exception {
		return getMoebelhausLagerService().produktBestand(produktTyp);
	}

	@Override
	public List<Produkt> produktBestand(String produktTypCode) throws Exception {
		return getMoebelhausLagerService().produktBestand(produktTypCode);
	}

	@Override
	public void produktEinlagern(ProduktTyp produktTyp, int anzahl) throws Exception {
		getMoebelhausLagerService().produktEinlagern(produktTyp, anzahl);
		return;
	}

	@Override
	public void produktRetournieren(Produkt produkt) throws Exception {
		getMoebelhausLagerService().produktRetournieren(produkt);
		return;
	}

	@Override
	public void produktAuslagern(List<Produkt> produktListe) throws Exception {
		getMoebelhausLagerService().produktAuslagern(produktListe);
		return;
	}

	@Override
	public Regal regalHinzufuegen(int nummer, int anzahlTablare) throws Exception {
		return getMoebelhausLagerService().regalHinzufuegen(nummer, anzahlTablare);
	}

	@Override
	public Regal regalAktualisieren(Regal regal) throws Exception {
		return getMoebelhausLagerService().regalAktualisieren(regal);
	}

	@Override
	public void regalLoeschen(Regal regal) throws Exception {
		getMoebelhausLagerService().regalLoeschen(regal);
		return;
	}

	@Override
	public Regal regalByBezeichnung(String bezeichnung) throws Exception {
		return getMoebelhausLagerService().regalByBezeichnung(bezeichnung);
	}

	@Override
	public Regal regalByNummer(int nummer) throws Exception {
		return getMoebelhausLagerService().regalByNummer(nummer);
	}

	@Override
	public List<Regal> alleRegale() throws Exception {
		return getMoebelhausLagerService().alleRegale();
	}

	@Override
	public List<Lieferant> alleLieferanten() throws Exception {
		return getMoebelhausLagerService().alleLieferanten();
	}

	@Override
	public Regal regalByTablar(Tablar tablar) throws Exception {
		return getMoebelhausLagerService().regalByTablar(tablar);
	}

	@Override
	public List<ProduktTyp> produktTypAlle() throws Exception {
		return getMoebelhausLagerService().produktTypAlle();
	}

	@Override
	public void produktTypLoeschen(ProduktTyp produktTyp) throws Exception {
		getMoebelhausLagerService().produktTypLoeschen(produktTyp);
		return;
	}

	@Override
	public ProduktTyp produktTypHinzufuegen(ProduktTyp produktTyp) throws Exception {
		return getMoebelhausLagerService().produktTypHinzufuegen(produktTyp);
	}

	@Override
	public ProduktTyp produktTypUpdaten(ProduktTyp produktTyp) throws Exception {
		return getMoebelhausLagerService().produktTypUpdaten(produktTyp);
	}

	@Override
	public Lieferant lieferantenHinzufuegen(Lieferant lieferant) throws Exception {
		return getMoebelhausLagerService().lieferantenHinzufuegen(lieferant);
	}

	@Override
	public Lieferant lieferantenAktualisieren(Lieferant lieferant) throws Exception {
		return getMoebelhausLagerService().lieferantenAktualisieren(lieferant);
	}

	@Override
	public void lieferantenLoeschen(Lieferant lieferant) throws Exception {
		getMoebelhausLagerService().lieferantenLoeschen(lieferant);
		return;
	}

	@Override
	public List<ProduktTyp> findeByLieferant(Lieferant lieferant) throws Exception {
		return getMoebelhausLagerService().findeByLieferant(lieferant);
	}

	@Override
	public List<ProduktTyp> findeByProduktname(String name) throws Exception {
		return getMoebelhausLagerService().findeByProduktname(name);
	}

	@Override
	public ProduktTyp findeByProduktTypCode(String produktTypCode) throws Exception {
		return getMoebelhausLagerService().findeByProduktTypCode(produktTypCode);
	}

	@Override
	public Moebelhaus getMoebelhaus() throws Exception {
		return getMoebelhausLagerService().getMoebelhaus();
	}

	@Override
	public long getProduktCode() throws Exception {
		return getMoebelhausLagerService().getProduktCode();
	}

	@Override
	public List<Bestellung> alleBestellungen() throws Exception {
		return getMoebelhausLagerService().alleBestellungen();
	}

	@Override
	public Bestellung findeBestellungById(long id) throws Exception {
		return getMoebelhausLagerService().findeBestellungById(id);
	}

	@Override
	public void ruecksendungHinzufuegen(Ruecksendung ruecksendung) throws Exception {
		getMoebelhausLagerService().ruecksendungHinzufuegen(ruecksendung);
		return;
	}
}