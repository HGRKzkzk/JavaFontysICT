package logica;

import java.io.Serializable;
import java.util.ArrayList;

import javax.sound.midi.Sequence;

/**
 * De Drumspoor klasse omvat de data en functionaliteit die nodig is om drumsporen aan projecten toe te voegen en deze te gebruiken. 
 * Op basis van hoeveel drumsporen er zijn / worden aangemaakt in een nieuwe project krijgt ieder spoor een instrument en daarmee
 * samenhangende omschrijving toegewezen. 
 * 
 */
public class Drumspoor extends Spoor implements Serializable {
	private static final long serialVersionUID = 7976856862495514977L;
	
	private int kanaalnummer = 9;
	

	public Drumspoor(ArrayList<Spoor> sporen, int nummer, Sequence sequence) {
		super(sporen, nummer, sequence);
		super.setNaam("Drumspoor" + nummer);
		super.setKanaalnummer(kanaalnummer);

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



	

}
