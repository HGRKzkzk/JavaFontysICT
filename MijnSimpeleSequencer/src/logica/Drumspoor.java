package logica;

import java.io.Serializable;
import java.util.ArrayList;

import javax.sound.midi.Sequence;

@SuppressWarnings("serial")
public class Drumspoor extends Spoor implements Serializable {

	private int drumkanaal = 9;
	private int instrument;

	public Drumspoor(ArrayList<Spoor> sporen, int nummer, Sequence sequence) {
		super(sporen, nummer, sequence);
		super.setNaam("Drumspoor" + nummer);
		super.setKanaalnummer(drumkanaal);

		switch (nummer + 1) {

		case 1:
			this.setInstrument(midiDrums.KICK.midiKey);
			this.setOmschrijving(midiDrums.KICK.omschrijving);
			break;
		case 2:
			this.setInstrument(midiDrums.SNARE.midiKey);
			this.setOmschrijving(midiDrums.SNARE.omschrijving);
			break;
		case 3:
			this.setInstrument(midiDrums.CHIHAT.midiKey);
			this.setOmschrijving(midiDrums.CHIHAT.omschrijving);
			break;
		case 4:
			this.setInstrument(midiDrums.OHIHAT.midiKey);
			this.setOmschrijving(midiDrums.OHIHAT.omschrijving);
			break;
		case 5:
			this.setInstrument(midiDrums.COWBELL.midiKey);
			this.setOmschrijving(midiDrums.COWBELL.omschrijving);
			break;
		case 6:
			this.setInstrument(midiDrums.CRASH.midiKey);
			this.setOmschrijving(midiDrums.CRASH.omschrijving);
			break;

		// bij meer kanalen standaard kickdrum als instrument hanteren
		default:
			this.setInstrument(midiDrums.KICK.midiKey);
			this.setOmschrijving(midiDrums.KICK.omschrijving);
		}

	}

	public int getInstrument() {
		return instrument;
	}

	public void setInstrument(int instrument) {
		this.instrument = instrument;
	}

}
