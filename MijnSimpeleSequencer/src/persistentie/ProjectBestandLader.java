package persistentie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import logica.Project;

public class ProjectBestandLader {

	Project project;

	public ProjectBestandLader(Project project) {

		this.project = project;

	}

	public void projectLadenVanuitSerializedData(File file)
			throws FileNotFoundException, ClassNotFoundException, InvalidClassException {
		FileInputStream fileInputStream = null;

		fileInputStream = new FileInputStream(file);

		ObjectInputStream objectInputStream = null;

		Project geladenproject = null;
		try {
			objectInputStream = new ObjectInputStream(fileInputStream);
			geladenproject = (Project) objectInputStream.readObject();

		} catch (IOException e) {
			// e.printStackTrace();
			throw new InvalidClassException(null);

		} finally {

			try {
				objectInputStream.close();
			} catch (IOException e) {
				throw new RuntimeException("Dit zou nooit voor moeten kunnen komen", e);

			}

		}

		// //de in het project aanwezige transients ondervangen
		// Sequence sequence = mijnsequencer.getSequence();
		// geladenproject.setMijnsequencer(mijnsequencer);
		// for (Spoor spoor : geladenproject.getSporen()) {
		// spoor.setTrack(sequence.createTrack());
		// }
		//

		// ter controle de eigenschapen van het net geladen project weergeven:
		// System.out.println(geladenproject.getNaam());
		// System.out.println(geladenproject.getMijnsequencer());

		// for (Spoor spoor : geladenproject.getSporen()) {
		//
		// System.out.println(spoor.getNaam());
		// }

		// de geladen data toepassen
		project.gelaadProjectToepassen(geladenproject);
	}

}