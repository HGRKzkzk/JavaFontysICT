package logica;

import java.io.Serializable;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;

/**
 * De Project klasse is een belangrijk aanmaakpunt van de andere onderdelen van
 * de applicatie en ook het punt waarop deze onderdelen samengevoegd worden om
 * af te spelen. De klasse draagt zorg voor het aanmaken van het juiste aantal
 * te gebruiken sporen, en biedt de mogelijkheid om deze sporen vervolgens in de
 * sequencer te laden en af te spelen.
 * 
 */

public class Project implements Serializable {
	private static final long serialVersionUID = 3540137627950242539L;

	private transient SimpeleSequencer mijnsequencer;
	private String naam;
	private int bpm;
	ArrayList<Spoor> sporen = new ArrayList<Spoor>();

	@SuppressWarnings("unused")
	public Project(String naam, int bpm, int aantaldrumsporen, int aantalsynthsporen, SimpeleSequencer seq) {

		this.naam = naam;
		this.bpm = bpm;
		this.mijnsequencer = seq;
		this.mijnsequencer.setSequencerBPM(bpm);

		for (int teller = 0; teller < aantaldrumsporen; teller++) {
			Drumspoor drumspoor = new Drumspoor(sporen, teller, mijnsequencer.getSequence());
		}

		for (int teller = 0; teller < aantalsynthsporen; teller++) {
			Synthspoor synthspoor = new Synthspoor(sporen, teller, mijnsequencer.getSequence());
		}

	}

	public void sporenBouwen() {

		int midikanaal;
		int volume;
		int positie;
		int midicommand;
		int instrument;

		for (Spoor spoor : sporen) {
			midikanaal = spoor.getKanaalnummer();
			instrument = spoor.getInstrument();

			for (Stap stap : spoor.getStappen()) {
				volume = stap.getVolume();
				positie = stap.getMijnplek();
				midicommand = stap.getMidicommand();

				spoor.getTrack().add(midiHelpers.midiEvent(midicommand, midikanaal, instrument, volume, positie));

			}

		}

	}

	/**
	 * Deze methode verwijdert alle Tracks in de verzameling sporen, en maakt ze
	 * opnieuw aan, om te voorkomen dat MIDI-data overelkaar geschreven wordt en
	 * voor dubbele playback zou zorgen.
	 */

	public void alleTracksVernieuwen() {

		for (Spoor spoor : sporen) {
			mijnsequencer.getSequence().deleteTrack(spoor.getTrack());
			spoor.setTrack(mijnsequencer.getSequence().createTrack());
		}

	}

	/**
	 * De methode alleSporenLeegMaken zorgt ervoor dat alle Stappen in een Spoor
	 * "uit" worden gezet, dat wil zeggen dat ze een status krijgen waaraan geen
	 * MIDI-output verbonden is.
	 */

	public void alleSporenLeegmaken() {

		for (Spoor spoor : sporen) {
			spoor.stappenUitzetten();
		}

		// sequencer opnieuw opbouwen
		sporenBouwen();

	}

	/**
	 * tempoInstellen wordt gebruikt om het afspeeltempo in te stellen op Project
	 * niveau, en dit door te zetten naar de Sequencer.
	 * 
	 * @param bpm Het opgegeven tempo in beats per minute
	 * @return True als het tempo kon worden aangepast, False als het tempo buiten
	 *         de gestelde randwaarden valt.
	 */

	public boolean tempoInstellen(float bpm) {
		int maxbpm = 420;
		int minbpm = 1;
		if (bpm > maxbpm || bpm < minbpm) {

			return false;
		} else {

			this.bpm = (int) bpm;
			this.mijnsequencer.getSequencer().setTempoInBPM(this.bpm);
			return true;
		}

	}

	/**
	 * Methode om de informatie in de verschillende sporen naar MIDI-data om te
	 * zetten en af te spelen. Roept eerst alleTrackVernieuwen aan omdat er anders
	 * MIDI-noten opelkaar gestapeld zouden worden, vervolgens worden op basis van
	 * de ingegeven data nieuwe tracks in de sequencer geladen. Daarna worden deze
	 * afgespeeld. Als de sequencer al aan het afspelen is, stopt het afspelen.
	 * 
	 * @return true als de Sequencer is begonnen met afspelen. false als de
	 *         Sequencer is gestopt met afspelen.
	 */
	public boolean afspelen() {

		// dit is nodig om dubbele playback te voorkomen
		alleTracksVernieuwen();

		// sequence opbouwen
		sporenBouwen();

		// sequence afspelen
		if (!mijnsequencer.getSequencer().isRunning()) {

			try {
				mijnsequencer.getSequencer().setSequence(mijnsequencer.getSequence());
			} catch (InvalidMidiDataException e) {
				// als de sequence leeg is;
				e.printStackTrace();
			}
			mijnsequencer.getSequencer().start();
			return true;
		}

		// of sequence stoppen
		else {

			mijnsequencer.getSequencer().stop();
			mijnsequencer.getSequencer().setTickPosition(0);

		}
		return false;

	}

	public void geladenProjectToepassen(Project geladenproject) {
		alleSporenLeegmaken();
		this.naam = geladenproject.naam;
		this.bpm = geladenproject.bpm;
		this.sporen = geladenproject.sporen;
		System.out.println(this.getNaam());
		tempoInstellen(this.bpm);

	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public ArrayList<Spoor> getSporen() {
		return sporen;
	}

	public String getNaam() {
		return this.naam;
	}

	public SimpeleSequencer getMijnsequencer() {
		return mijnsequencer;
	}

}
