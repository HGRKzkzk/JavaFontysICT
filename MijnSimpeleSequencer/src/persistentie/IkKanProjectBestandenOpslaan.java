package persistentie;

import java.io.File;

import logica.Project;

public interface IkKanProjectBestandenOpslaan {

	void projectOpslaanAlsSerializedData(Project project, File file);

	void projectOpslaanAlsMidiFile(Project project, File file);

}
