package logica;

import java.io.Serializable;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;

@SuppressWarnings("serial")
public class Project implements Serializable {

	private transient SimpeleSequencer mijnsequencer;

	private String naam;

	private int bpm;
	// private int aantaldrumsporen;
	// private int aantalsynthsporen;
	ArrayList<Spoor> sporen = new ArrayList<Spoor>(); // als je deze lijst heb, waarom zou je dan na het aanmaken die
														// sporen nog op een andere manier bijhouden?

	@SuppressWarnings("unused")
	public Project(String naam, int bpm, int aantaldrumsporen, int aantalsynthsporen, SimpeleSequencer seq) {

		this.naam = naam;
		this.bpm = bpm;
		// this.aantaldrumsporen = aantaldrumsporen;
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

				// note on (144) meegeven
				if (stap.maakIkGeluid()) {
					midicommand = 144;
				}
				// .. anders note off.
				else {
					midicommand = 128;
				}

				spoor.getTrack()
						.add(midiHelpers.midiEvent(midicommand, midikanaal, spoor.getInstrument(), volume, positie));

				// System.out.println(stap.getMijnplek() + " // " + stap.maakIkGeluid());

			}

		}

	}

	public void allesporenleegmaken() {

		for (Spoor spoor : sporen) {
			mijnsequencer.getSequence().deleteTrack(spoor.getTrack());
			spoor.setTrack(mijnsequencer.getSequence().createTrack());
			spoor.stappenuitzetten();
		}

		// sequencer opnieuw opbouwen
		sporenDoorlopen();

	}

	public boolean bpminstellen(float bpm) {
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
		for (Spoor spoor : sporen) {
			mijnsequencer.getSequence().deleteTrack(spoor.getTrack());
			spoor.setTrack(mijnsequencer.getSequence().createTrack());
		}

		// sequence opbouwen
		sporenDoorlopen();

		// sequence afspelen
		if (!mijnsequencer.getSequencer().isRunning()) {

			try {
				mijnsequencer.getSequencer().setSequence(mijnsequencer.getSequence());
			} catch (InvalidMidiDataException e) {
				// TODO Auto-generated catch block
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

	public void allesladen(Project geladenproject) {
		allesporenleegmaken();
		this.naam = geladenproject.naam;
		this.bpm = geladenproject.bpm;
		this.sporen = geladenproject.sporen;
		System.out.println(this.getNaam());
		bpminstellen(this.bpm);

	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public void setMijnsequencer(SimpeleSequencer mijnsequencer) {
		this.mijnsequencer = mijnsequencer;
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