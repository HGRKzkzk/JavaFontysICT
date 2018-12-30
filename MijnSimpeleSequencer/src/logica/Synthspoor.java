package logica;

import java.io.Serializable;
import java.util.ArrayList;

import javax.sound.midi.Sequence;

@SuppressWarnings("serial")
public class Synthspoor extends Spoor implements Serializable {

	private int instrument;
	private String omschrijving;

	public Synthspoor(ArrayList<Spoor> sporen, int nummer, Sequence sequence) {
		super(sporen, nummer, sequence);
		super.setNaam("Synthspoor" + nummer);
		super.setOmschrijving("Generiek synthspoor");
		super.setKanaalnummer(12);

	}

}
