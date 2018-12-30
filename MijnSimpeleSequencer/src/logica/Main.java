package logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import persistentie.ProjectBestandLader;
import persistentie.ProjectBestandSchrijver;

public class Main {

	public static void main(String[] args) throws InvalidClassException, FileNotFoundException, ClassNotFoundException {
		SimpeleSequencer mijnsequencer = new SimpeleSequencer();
		Project project = new Project("nieuw project", 120, 8, 1, mijnsequencer);
		

	}

}
