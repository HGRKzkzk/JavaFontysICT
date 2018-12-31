package logica;

import java.io.Serializable;
import java.util.ArrayList;

import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

@SuppressWarnings("serial")
public abstract class Spoor implements Serializable {

	private String omschrijving;
	private int kanaalnummer;
	private int instrument;
	private String naam;
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
