package logica;

public enum midiDrums {

	KICK(35, "Kickdrum"), SNARE(38, "Snaredrum"), CHIHAT(42, "Closed hi-hat"), OHIHAT(46, "Open hi-hat"), COWBELL(56,
			"Cowbell"), CRASH(49, "Crash cymbal");

	int midiKey;
	String omschrijving;

	midiDrums(int midiValue, String omschrijving) {
		this.midiKey = midiValue;
		this.omschrijving = omschrijving;
	}

}
