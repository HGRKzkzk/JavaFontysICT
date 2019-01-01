package persistentie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.sound.midi.MidiSystem;
import logica.Project;

public class ProjectBestandSchrijver implements IkKanProjectBestandenOpslaan {

	Project project;

	@Override
	public void projectOpslaanAlsSerializedData(Project project, File file) {

		String naam = project.getNaam();

		// bij null terugval naar standaard locatie en bestandsnaam
		if (file == null) {
			file = new File(naam + ".wsb");
		}

		project.setNaam(file.getName());

		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjectOutputStream objectOutputStream = null;
		try {
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(project);
			objectOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {

			try {
				objectOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	public void projectOpslaanAlsMidiFile(Project project, File file) {

		if (project.getMijnsequencer().getSequence().getTracks().length == 0) {
			// lege file kan niet opgeslagen worden.
			return;
		}

		// File f = new File(naam + ".mid");
		try {
			project.sporenDoorlopen();
			MidiSystem.write(project.getMijnsequencer().getSequence(), 1, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

}
