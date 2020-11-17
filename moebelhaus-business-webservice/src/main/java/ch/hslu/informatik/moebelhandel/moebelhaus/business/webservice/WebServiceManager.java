package ch.hslu.informatik.moebelhandel.moebelhaus.business.webservice;

import java.util.ArrayList;
import java.util.List;
import java.util.GregorianCalendar;
import javax.xml.ws.Service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.hslu.informatik.moebelhandel.moebelhaus.api.WebService;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Bestellung;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.BestellungPosition;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferant;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.Moebelhaus;
import ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp;

public class WebServiceManager implements WebService {

	private static Logger logger = LogManager.getLogger(WebServiceManager.class);

	private static final String FISCHER = "Holzmöbel Fischer AG";
	private static final String WALKER = "Möbelfabrik Walker AG";
	private static final String ZWISSIG = "Möbelfabrik Zwissig GmbH";

	@Override
	public List<ch.hslu.informatik.moebelhandel.moebelhaus.model.ProduktTyp> produktKatalog() throws Exception {
		return null;
	}

	@Override
	public int produktBestand(ProduktTyp produktTyp) throws Exception {
		int anzahlExt = 0;
		
		if (produktTyp.getLieferant().getName().equals(FISCHER)) {
			try {
				Service serviceFischer = new ch.hslu.informatik.moebelhersteller.fischer.webservice.impl.MoebelBestellungImplService();
				ch.hslu.informatik.moebelhersteller.fischer.webservice.impl.MoebelBestellung stubFischer = serviceFischer.getPort(ch.hslu.informatik.moebelhersteller.fischer.webservice.impl.MoebelBestellung.class);

				anzahlExt = anzahlExt + stubFischer.lagerBestand(produktTyp.getTypCode());

			} catch (java.lang.Exception e) {
			}
			
		} else if (produktTyp.getLieferant().getName().equals(WALKER)) {
			try {
				Service serviceWalker = new ch.hslu.informatik.moebelhersteller.walker.webservice.impl.MoebelBestellungImplService();
				ch.hslu.informatik.moebelhersteller.walker.webservice.impl.MoebelBestellung stubWalker = serviceWalker.getPort(ch.hslu.informatik.moebelhersteller.walker.webservice.impl.MoebelBestellung.class);

				anzahlExt = anzahlExt + stubWalker.lagerBestand(produktTyp.getTypCode());

			} catch (java.lang.Exception e) {
			}
			
		} else if (produktTyp.getLieferant().getName().equals(ZWISSIG)) {
			try {
				Service serviceZwissig = new ch.hslu.informatik.moebelhersteller.zwissig.webservice.impl.MoebelBestellungImplService();
				ch.hslu.informatik.moebelhersteller.zwissig.webservice.impl.MoebelBestellung stubZwissig = serviceZwissig.getPort(ch.hslu.informatik.moebelhersteller.zwissig.webservice.impl.MoebelBestellung.class);

				anzahlExt = anzahlExt + stubZwissig.lagerBestand(produktTyp.getTypCode());

			} catch (java.lang.Exception e) {
			}
			
		} else {
			logger.error("Hersteller " + produktTyp.getLieferant().getName() + " nicht bekannt.");
			throw new RuntimeException("Hersteller " + produktTyp.getLieferant().getName() + " nicht bekannt.");
		}

		return anzahlExt;
	}

	@Override
	public void bestellen(Bestellung bestellung, String moebelhausCode) throws Exception {

		if (bestellung.getBestellungPositionListe().get(0).getProduktTyp().getLieferant().getName().equals(FISCHER)) {
			try {
				Service serviceFischer = new ch.hslu.informatik.moebelhersteller.fischer.webservice.impl.MoebelBestellungImplService();
				ch.hslu.informatik.moebelhersteller.fischer.webservice.impl.MoebelBestellung stubFischer = serviceFischer.getPort(ch.hslu.informatik.moebelhersteller.fischer.webservice.impl.MoebelBestellung.class);

				ch.hslu.informatik.moebelhersteller.fischer.webservice.api.WsBestellung wsBestellung = new ch.hslu.informatik.moebelhersteller.fischer.webservice.api.WsBestellung();

				for (int i = 0; i < bestellung.getBestellungPositionListe().size(); i++) {
					ch.hslu.informatik.moebelhersteller.fischer.webservice.api.WsBestellungPosition wsPos = new ch.hslu.informatik.moebelhersteller.fischer.webservice.api.WsBestellungPosition();
					wsPos.setAnzahl(bestellung.getBestellungPositionListe().get(i).getAnzahl());
					wsPos.setProduktCode(bestellung.getBestellungPositionListe().get(i).getProduktTyp().getTypCode());
					wsBestellung.getBestellungPositionen().add(wsPos);
				}

				wsBestellung.setMoebehlausId(moebelhausCode);
				stubFischer.bestellen(wsBestellung);

			} catch (java.lang.Exception e) {
				throw new RuntimeException(e);
			}
			
		} else if (bestellung.getBestellungPositionListe().get(0).getProduktTyp().getLieferant().getName().equals(WALKER)) {
			try {
				Service serviceWalker = new ch.hslu.informatik.moebelhersteller.walker.webservice.impl.MoebelBestellungImplService();
				ch.hslu.informatik.moebelhersteller.walker.webservice.impl.MoebelBestellung stubWalker = serviceWalker.getPort(ch.hslu.informatik.moebelhersteller.walker.webservice.impl.MoebelBestellung.class);

				ch.hslu.informatik.moebelhersteller.walker.webservice.api.WsBestellung wsBestellung = new ch.hslu.informatik.moebelhersteller.walker.webservice.api.WsBestellung();

				for (int i = 0; i < bestellung.getBestellungPositionListe().size(); i++) {
					ch.hslu.informatik.moebelhersteller.walker.webservice.api.WsBestellungPosition wsPos = new ch.hslu.informatik.moebelhersteller.walker.webservice.api.WsBestellungPosition();
					wsPos.setAnzahl(bestellung.getBestellungPositionListe().get(i).getAnzahl());
					wsPos.setProduktCode(bestellung.getBestellungPositionListe().get(i).getProduktTyp().getTypCode());
					wsBestellung.getBestellungPositionen().add(wsPos);
				}

				wsBestellung.setMoebehlausId(moebelhausCode);
				stubWalker.bestellen(wsBestellung);

			} catch (java.lang.Exception e) {
				throw new RuntimeException(e);
			}
			
		} else if (bestellung.getBestellungPositionListe().get(0).getProduktTyp().getLieferant().getName().equals(ZWISSIG)) {
			try {
				Service serviceZwissig = new ch.hslu.informatik.moebelhersteller.zwissig.webservice.impl.MoebelBestellungImplService();
				ch.hslu.informatik.moebelhersteller.zwissig.webservice.impl.MoebelBestellung stubZwissig = serviceZwissig.getPort(ch.hslu.informatik.moebelhersteller.zwissig.webservice.impl.MoebelBestellung.class);

				ch.hslu.informatik.moebelhersteller.zwissig.webservice.api.WsBestellung wsBestellung = new ch.hslu.informatik.moebelhersteller.zwissig.webservice.api.WsBestellung();

				for (int i = 0; i < bestellung.getBestellungPositionListe().size(); i++) {
					ch.hslu.informatik.moebelhersteller.zwissig.webservice.api.WsBestellungPosition wsPos = new ch.hslu.informatik.moebelhersteller.zwissig.webservice.api.WsBestellungPosition();
					wsPos.setAnzahl(bestellung.getBestellungPositionListe().get(i).getAnzahl());
					wsPos.setProduktCode(bestellung.getBestellungPositionListe().get(i).getProduktTyp().getTypCode());
					wsBestellung.getBestellungPositionen().add(wsPos);
				}

				wsBestellung.setMoebehlausId(moebelhausCode);
				stubZwissig.bestellen(wsBestellung);

			} catch (java.lang.Exception e) {
				throw new RuntimeException(e);
			}
			
		} else {
			logger.error("Hersteller " + bestellung.getBestellungPositionListe().get(0).getProduktTyp().getLieferant().getName() + " nicht bekannt.");
			throw new RuntimeException("Hersteller " + bestellung.getBestellungPositionListe().get(0).getProduktTyp().getLieferant().getName() + " nicht bekannt.");
		}
	}

	@Override
	public List<Bestellung> offeneBestellungen(Moebelhaus moebelhaus) throws Exception {

		List<Bestellung> bestellungList = new ArrayList<>();
		
		try {
			Service serviceFischer = new ch.hslu.informatik.moebelhersteller.fischer.webservice.impl.MoebelBestellungImplService();
			ch.hslu.informatik.moebelhersteller.fischer.webservice.impl.MoebelBestellung stubFischer = serviceFischer.getPort(ch.hslu.informatik.moebelhersteller.fischer.webservice.impl.MoebelBestellung.class);

			List<ch.hslu.informatik.moebelhersteller.fischer.webservice.api.Bestellung> wsBestellungList = stubFischer.offeneBestellungen(moebelhaus.getMoebelhausCode());
			
			for (ch.hslu.informatik.moebelhersteller.fischer.webservice.api.Bestellung wsBestellung : wsBestellungList) {
				
				Bestellung bestellung = new Bestellung(
						new GregorianCalendar(
								wsBestellung.getDatum().getYear(), 
								wsBestellung.getDatum().getMonth(), 
								wsBestellung.getDatum().getDay()), 
						moebelhaus);
				
				bestellung.setId(wsBestellung.getId());
				
				for (int i = 0; i < wsBestellung.getBestellungPositionListe().size(); i++) {
					BestellungPosition pos = new BestellungPosition(
							new ProduktTyp(
									wsBestellung.getBestellungPositionListe().get(i).getProduktTyp().getName(), 
									wsBestellung.getBestellungPositionListe().get(i).getProduktTyp().getBeschreibung(), 
									wsBestellung.getBestellungPositionListe().get(i).getProduktTyp().getPreis(), 
									wsBestellung.getBestellungPositionListe().get(i).getProduktTyp().getTypCode(), 
									new Lieferant()),
							wsBestellung.getBestellungPositionListe().get(i).getAnzahl());
					pos.getProduktTyp().getLieferant().setName(FISCHER);
					bestellung.getBestellungPositionListe().add(pos);
				}
				
				bestellungList.add(bestellung);
			}

		} catch (Exception e) {
		}
		
		try {
			Service serviceWalker = new ch.hslu.informatik.moebelhersteller.walker.webservice.impl.MoebelBestellungImplService();
			ch.hslu.informatik.moebelhersteller.walker.webservice.impl.MoebelBestellung stubWalker = serviceWalker.getPort(ch.hslu.informatik.moebelhersteller.walker.webservice.impl.MoebelBestellung.class);

			List<ch.hslu.informatik.moebelhersteller.walker.webservice.api.Bestellung> wsBestellungList = stubWalker.offeneBestellungen(moebelhaus.getMoebelhausCode());
			
			for (ch.hslu.informatik.moebelhersteller.walker.webservice.api.Bestellung wsBestellung : wsBestellungList) {
				
				Bestellung bestellung = new Bestellung(
						new GregorianCalendar(
								wsBestellung.getDatum().getYear(), 
								wsBestellung.getDatum().getMonth(), 
								wsBestellung.getDatum().getDay()), 
						moebelhaus);
				
				bestellung.setId(wsBestellung.getId());
				
				for (int i = 0; i < wsBestellung.getBestellungPositionListe().size(); i++) {
					BestellungPosition pos = new BestellungPosition(
							new ProduktTyp(
									wsBestellung.getBestellungPositionListe().get(i).getProduktTyp().getName(), 
									wsBestellung.getBestellungPositionListe().get(i).getProduktTyp().getBeschreibung(), 
									wsBestellung.getBestellungPositionListe().get(i).getProduktTyp().getPreis(), 
									wsBestellung.getBestellungPositionListe().get(i).getProduktTyp().getTypCode(), 
									new Lieferant()),
							wsBestellung.getBestellungPositionListe().get(i).getAnzahl());
					pos.getProduktTyp().getLieferant().setName(WALKER);
					bestellung.getBestellungPositionListe().add(pos);
				}
				
				bestellungList.add(bestellung);
			}

		} catch (Exception e) {
		}
		
		try {
			Service serviceZwissig = new ch.hslu.informatik.moebelhersteller.zwissig.webservice.impl.MoebelBestellungImplService();
			ch.hslu.informatik.moebelhersteller.zwissig.webservice.impl.MoebelBestellung stubZwissig = serviceZwissig.getPort(ch.hslu.informatik.moebelhersteller.zwissig.webservice.impl.MoebelBestellung.class);

			List<ch.hslu.informatik.moebelhersteller.zwissig.webservice.api.Bestellung> wsBestellungList = stubZwissig.offeneBestellungen(moebelhaus.getMoebelhausCode());
			
			for (ch.hslu.informatik.moebelhersteller.zwissig.webservice.api.Bestellung wsBestellung : wsBestellungList) {
				
				Bestellung bestellung = new Bestellung(
						new GregorianCalendar(
								wsBestellung.getDatum().getYear(), 
								wsBestellung.getDatum().getMonth(), 
								wsBestellung.getDatum().getDay()), 
						moebelhaus);
				
				bestellung.setId(wsBestellung.getId());
				
				for (int i = 0; i < wsBestellung.getBestellungPositionListe().size(); i++) {
					BestellungPosition pos = new BestellungPosition(
							new ProduktTyp(
									wsBestellung.getBestellungPositionListe().get(i).getProduktTyp().getName(), 
									wsBestellung.getBestellungPositionListe().get(i).getProduktTyp().getBeschreibung(), 
									wsBestellung.getBestellungPositionListe().get(i).getProduktTyp().getPreis(), 
									wsBestellung.getBestellungPositionListe().get(i).getProduktTyp().getTypCode(), 
									new Lieferant()),
							wsBestellung.getBestellungPositionListe().get(i).getAnzahl());
					pos.getProduktTyp().getLieferant().setName(ZWISSIG);
					bestellung.getBestellungPositionListe().add(pos);
				}
				
				bestellungList.add(bestellung);
			}

		} catch (Exception e) {
		}

		return bestellungList;
	}

	@Override
	public List<ch.hslu.informatik.moebelhandel.moebelhaus.model.Lieferung> lieferungenByMoebelhaus(String moebelhausCode) throws Exception {
		return null;
	}

	@Override
	public Bestellung lieferungByNummer(long lieferungNummer) throws Exception {
		
		Bestellung bestellung = null;
		
		try {
			Service serviceFischer = new ch.hslu.informatik.moebelhersteller.fischer.webservice.impl.MoebelBestellungImplService();
			ch.hslu.informatik.moebelhersteller.fischer.webservice.impl.MoebelBestellung stubFischer = serviceFischer.getPort(ch.hslu.informatik.moebelhersteller.fischer.webservice.impl.MoebelBestellung.class);

			ch.hslu.informatik.moebelhersteller.fischer.webservice.api.Lieferung wsLieferung = stubFischer.lieferungByNummer(lieferungNummer);
			
			if (wsLieferung != null) {
				
				bestellung = new Bestellung(
						new GregorianCalendar(
								wsLieferung.getBestellung().getDatum().getYear(), 
								wsLieferung.getBestellung().getDatum().getMonth(), 
								wsLieferung.getBestellung().getDatum().getDay()), 
						new Moebelhaus());
			
				bestellung.setId(wsLieferung.getBestellung().getId());
			
				for (int i = 0; i < wsLieferung.getBestellung().getBestellungPositionListe().size(); i++) {
					BestellungPosition pos = new BestellungPosition(
							new ProduktTyp(
									wsLieferung.getBestellung().getBestellungPositionListe().get(i).getProduktTyp().getName(), 
									wsLieferung.getBestellung().getBestellungPositionListe().get(i).getProduktTyp().getBeschreibung(), 
									wsLieferung.getBestellung().getBestellungPositionListe().get(i).getProduktTyp().getPreis(), 
									wsLieferung.getBestellung().getBestellungPositionListe().get(i).getProduktTyp().getTypCode(), 
									new Lieferant()),
							wsLieferung.getBestellung().getBestellungPositionListe().get(i).getAnzahl());
					pos.getProduktTyp().getLieferant().setName(FISCHER);
					bestellung.getBestellungPositionListe().add(pos);
				}
			}
			
		} catch (Exception e) {
		}
		
		try {
			Service serviceWalker = new ch.hslu.informatik.moebelhersteller.walker.webservice.impl.MoebelBestellungImplService();
			ch.hslu.informatik.moebelhersteller.walker.webservice.impl.MoebelBestellung stubWalker = serviceWalker.getPort(ch.hslu.informatik.moebelhersteller.walker.webservice.impl.MoebelBestellung.class);

			ch.hslu.informatik.moebelhersteller.walker.webservice.api.Lieferung wsLieferung = stubWalker.lieferungByNummer(lieferungNummer);
			
			if (wsLieferung != null) {
				
				bestellung = new Bestellung(
						new GregorianCalendar(
								wsLieferung.getBestellung().getDatum().getYear(), 
								wsLieferung.getBestellung().getDatum().getMonth(), 
								wsLieferung.getBestellung().getDatum().getDay()), 
						new Moebelhaus());
			
				bestellung.setId(wsLieferung.getBestellung().getId());
			
				for (int i = 0; i < wsLieferung.getBestellung().getBestellungPositionListe().size(); i++) {
					BestellungPosition pos = new BestellungPosition(
							new ProduktTyp(
									wsLieferung.getBestellung().getBestellungPositionListe().get(i).getProduktTyp().getName(), 
									wsLieferung.getBestellung().getBestellungPositionListe().get(i).getProduktTyp().getBeschreibung(), 
									wsLieferung.getBestellung().getBestellungPositionListe().get(i).getProduktTyp().getPreis(), 
									wsLieferung.getBestellung().getBestellungPositionListe().get(i).getProduktTyp().getTypCode(), 
									new Lieferant()),
							wsLieferung.getBestellung().getBestellungPositionListe().get(i).getAnzahl());
					pos.getProduktTyp().getLieferant().setName(WALKER);
					bestellung.getBestellungPositionListe().add(pos);
				}
			}
			
		} catch (Exception e) {
		}
		
		try {
			Service serviceZwissig = new ch.hslu.informatik.moebelhersteller.zwissig.webservice.impl.MoebelBestellungImplService();
			ch.hslu.informatik.moebelhersteller.zwissig.webservice.impl.MoebelBestellung stubZwissig = serviceZwissig.getPort(ch.hslu.informatik.moebelhersteller.zwissig.webservice.impl.MoebelBestellung.class);

			ch.hslu.informatik.moebelhersteller.zwissig.webservice.api.Lieferung wsLieferung = stubZwissig.lieferungByNummer(lieferungNummer);
			
			if (wsLieferung != null) {
				
				bestellung = new Bestellung(
						new GregorianCalendar(
								wsLieferung.getBestellung().getDatum().getYear(), 
								wsLieferung.getBestellung().getDatum().getMonth(), 
								wsLieferung.getBestellung().getDatum().getDay()), 
						new Moebelhaus());
			
				bestellung.setId(wsLieferung.getBestellung().getId());
			
				for (int i = 0; i < wsLieferung.getBestellung().getBestellungPositionListe().size(); i++) {
					BestellungPosition pos = new BestellungPosition(
							new ProduktTyp(
									wsLieferung.getBestellung().getBestellungPositionListe().get(i).getProduktTyp().getName(), 
									wsLieferung.getBestellung().getBestellungPositionListe().get(i).getProduktTyp().getBeschreibung(), 
									wsLieferung.getBestellung().getBestellungPositionListe().get(i).getProduktTyp().getPreis(), 
									wsLieferung.getBestellung().getBestellungPositionListe().get(i).getProduktTyp().getTypCode(), 
									new Lieferant()),
							wsLieferung.getBestellung().getBestellungPositionListe().get(i).getAnzahl());
					pos.getProduktTyp().getLieferant().setName(ZWISSIG);
					bestellung.getBestellungPositionListe().add(pos);
				}
			}
			
		} catch (Exception e) {
		}
		
		return bestellung;
	}
}