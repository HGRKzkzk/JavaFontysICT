package logica;

import java.io.Serializable;
import java.util.ArrayList;

import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

/** 
 * De abstratce klasse Spoor is de basis voor Drumspoor en Synthspoor en is verantwoordelijk
 * voor de in de sequencer te laden tracks en de onderverdeling hiervan in een x-aantal Stappen. 
 * De informatie die op Spoor-niveau wordt opgeslagen is ook verantwoordelijk voor het bepalen
 * van de instrumenten die gebruikt worden.* 
 */



public abstract class Spoor implements Serializable {
	private static final long serialVersionUID = 2449129869558050215L;
	
	private String naam;
	private String omschrijving;
	private int kanaalnummer;
	
	private int instrument;
	
	private int aantalstappen = 16;
	private transient Track track;
	private ArrayList<Stap> stappen = new ArrayList<Stap>();

	public Spoor(ArrayList<Spoor> sporen, int nummer, Sequence sequence) {
		sporen.add(this);
		track = sequence.createTrack();

		for (int i = 0; i < aantalstappen; i++) {
			@SuppressWarnings("unused")
			Stap stap = new Stap(stappen, i);
		}

	}

	public void stappenUitzetten() {
		for (Stap stap : stappen) {

			stap.uitzetten();

		}

	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public int getKanaalnummer() {
		return kanaalnummer;
	}

	public void setKanaalnummer(int kanaalnummer) {
		this.kanaalnummer = kanaalnummer;
	}

	public ArrayList<Stap> getStappen() {
		return stappen;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public int getInstrument() {
		return instrument;
	}

	public void setInstrument(int instrument) {
		this.instrument = instrument;
	}

}
