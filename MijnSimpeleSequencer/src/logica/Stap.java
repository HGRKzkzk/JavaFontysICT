package logica;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Stap implements Serializable {
	// dit is een grappige waarde om bij te houden, vooral op macro niveau;
	private int hoevaakveranderdeikvanstatus;
	private int mijnplek;
	private int volume;
	private boolean maakIkGeluid;
	// private Noot noot;

	public Stap(ArrayList<Stap> stappen, int mijnplek) {
		this.hoevaakveranderdeikvanstatus = 0;
		this.mijnplek = mijnplek + 1;
		this.setVolume(127);
		this.maakIkGeluid = false;
		stappen.add(this);

	}

	public void aanuit() {

		if (maakIkGeluid) {
			maakIkGeluid = false;
		} else {
			maakIkGeluid = true;
		}

		System.out.println(maakIkGeluid);
		hoevaakveranderdeikvanstatus++;
		System.out.println(hoevaakveranderdeikvanstatus);

	}

	public void uitzetten() {

		this.maakIkGeluid = false;

	}

	public boolean maakIkGeluid() {
		return maakIkGeluid;
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