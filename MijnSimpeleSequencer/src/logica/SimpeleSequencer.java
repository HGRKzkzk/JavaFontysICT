package logica;

import java.io.Serializable;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;


public class SimpeleSequencer implements Serializable {
	private static final long serialVersionUID = 6702857710611810142L;
	
	private transient Sequencer sequencer;
	private transient Sequence sequence;

	public SimpeleSequencer() {

		try {
			this.sequencer = MidiSystem.getSequencer();
			this.sequencer.open();
		} catch (MidiUnavailableException e) {
			// MIDI-device niet beschikbaar
			e.printStackTrace();
		}

		this.sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		this.sequencer.setMasterSyncMode(null);
		

		try {
			this.sequence = new Sequence(Sequence.PPQ, 4);
		} catch (InvalidMidiDataException e) {
			// zou op basis van deze waardes eigenlijk al nooit voor moeten kunnen komen
			throw new RuntimeException("Dit zou nooit voor moeten kunnen komen", e);
		}

	}

	public void setSequencerBPM(int bpm) {
		this.sequencer.setTempoInBPM(bpm);
	}

	public Sequencer getSequencer() {

		return this.sequencer;

	}

	public Sequence getSequence() {

		return this.sequence;

	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

}
