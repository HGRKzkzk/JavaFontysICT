package logica;

import java.io.Serializable;

/**
 * De klasse Stap omvat van iedere stap hun plek, volume en de informatie of de betreffende stap aan of  
 * uit staat, en of deze dus in de sequencer wel of niet een geluid voort moet brengen. 
 * 
 */

import java.util.ArrayList;


public class Stap implements Serializable {
	private static final long serialVersionUID = 5372151320352886645L;
	
	private int mijnplek;
	private int volume;
	private boolean maakIkGeluid;
	private int midicommand = 0;
	// private Noot noot;

	

	public Stap(ArrayList<Stap> stappen, int mijnplek) {
		
		this.mijnplek = mijnplek + 1;
		this.setVolume(127);
		this.maakIkGeluid = false;
		this.midicommand = 128;
		stappen.add(this);

	}

	public void aanuit(int volume) {

		if (maakIkGeluid) {
			maakIkGeluid = false;
			midicommand = 128;
		} else {
			maakIkGeluid = true;
			midicommand = 144;
		}
		
		this.volume = volume;
	
		

	}

	public void uitzetten() {
		this.maakIkGeluid = false;
		this.midicommand = 128;

	}

	public boolean maakIkGeluid() {
		return maakIkGeluid;
	}
	
	public int getMidicommand() {
		return midicommand;
	}

	public int getMijnplek() {
		return mijnplek;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

}
