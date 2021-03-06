package logica;

/**
 * De enum midiDrums bevat een vaste lijst van (drum)sintrumenten en hun
 * corresponderende MIDI-waardes.
 */

public enum midiDrums {

	KICK(35, "Kickdrum"), SNARE(38, "Snaredrum"), CHIHAT(42, "Closed hi-hat"), OHIHAT(46, "Open hi-hat"), COWBELL(56,
			"Cowbell"), CRASH(49, "Crash cymbal");

	/// nog aan te werken, voor huidige opzet project niet relevant
	public final static midiDrums[] waardes = values();

	public midiDrums next() {
		return waardes[(this.ordinal() + 1) % waardes.length];
	}

	int midiKey;
	String omschrijving;

	midiDrums(int midiValue, String omschrijving) {
		this.midiKey = midiValue;
		this.omschrijving = omschrijving;
	}

}
