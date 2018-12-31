package logica;

import java.io.Serializable;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;

@SuppressWarnings("serial")
public class Project implements Serializable {

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

	public void sporenDoorlopen() {

		int midikanaal;
		int volume;
		int positie;
		int midicommand;

		// van ieder spoor..
		for (Spoor spoor : sporen) {
			midikanaal = spoor.getKanaalnummer();

			// ..iedere stap langsgaan
			for (Stap stap : spoor.getStappen()) {
				volume = stap.getVolume();
				positie = stap.getMijnplek();
				midicommand = stap.getMidicommand();	
				spoor.getTrack().add(midiHelpers.midiEvent(midicommand, midikanaal, spoor.getInstrument(), volume, positie));

				

			}

		}

	}
	
	public void alleTracksVernieuwen() {
		
		for (Spoor spoor : sporen) {
			mijnsequencer.getSequence().deleteTrack(spoor.getTrack());
			spoor.setTrack(mijnsequencer.getSequence().createTrack());
		}
		
	}

	public void alleSporenLeegmaken() {
		
		for (Spoor spoor : sporen) {
		spoor.stappenUitzetten();
		}

		// sequencer opnieuw opbouwen
		sporenDoorlopen();

	}

	public boolean tempoBepalen(float bpm) {
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

	public void afspelen() {

		// dit is nodig om dubbele playback te voorkomen
		alleTracksVernieuwen();

		// sequence opbouwen
		sporenDoorlopen();

		// sequence afspelen
		if (!mijnsequencer.getSequencer().isRunning()) {

			try {
				mijnsequencer.getSequencer().setSequence(mijnsequencer.getSequence());
			} catch (InvalidMidiDataException e) {
				// als de sequence leeg is;
				e.printStackTrace();
			}
			mijnsequencer.getSequencer().start();
		}

		// of sequence stoppen
		else {

			mijnsequencer.getSequencer().stop();
			mijnsequencer.getSequencer().setTickPosition(0);

		}

	}

	public void geladenProjectToepassen(Project geladenproject) {
		alleSporenLeegmaken();
		this.naam = geladenproject.naam;
		this.bpm = geladenproject.bpm;
		this.sporen = geladenproject.sporen;
		System.out.println(this.getNaam());
		tempoBepalen(this.bpm);

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
