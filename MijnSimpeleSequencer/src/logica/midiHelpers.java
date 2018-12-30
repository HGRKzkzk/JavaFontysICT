package logica;

import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;

public class midiHelpers {

	public static MidiEvent midiEvent(int command, int channel, int dataone, int datatwo, int tick) {
		MidiEvent event = null;

		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(command, channel, dataone, datatwo);
			event = new MidiEvent(a, tick);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return event;
	}

}
