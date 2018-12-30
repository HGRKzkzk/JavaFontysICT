package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InvalidClassException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logica.Project;
import logica.Spoor;
import logica.Stap;
import persistentie.ProjectBestandLader;
import persistentie.ProjectBestandSchrijver;

public class GUIbuilder {

	@SuppressWarnings("unused")
	public static void menubalkmaken(Project project, Pane root, Stage primaryStage) {

		Border randaan = new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

		Border randuit = new Border(
				new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT));

		ProjectBestandSchrijver BestandSchrijver = new ProjectBestandSchrijver();
		ProjectBestandLader BestandLader = new ProjectBestandLader(project);

		int naarrechts = 0;

		Button opslaan = new Button("Bestand opslaan");
		opslaan.setTranslateX(naarrechts + 100);
		opslaan.setStyle("-fx-background-color: transparent;");
		root.getChildren().add(opslaan);

		final FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("WSB projecten (*.wsb)", "*.wsb");
		fileChooser.getExtensionFilters().add(extFilter);

		opslaan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = fileChooser.showSaveDialog(null);
				if (file != null) {
					BestandSchrijver.projectOpslaanAlsSerializedData(project, file);
				}
				primaryStage.setTitle("HGRKzkzkboxx - " + project.getNaam());
			}
		});

		Button laden = new Button("Bestand openen");
		laden.setTranslateX(naarrechts);
		laden.setStyle("-fx-background-color: transparent;");
		root.getChildren().add(laden);

		final FileChooser fileChooser2 = new FileChooser();
		fileChooser2.getExtensionFilters().add(extFilter);

		laden.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = fileChooser2.showOpenDialog(null);
				if (file != null) {
					try {

						BestandLader.projectLadenVanuitSerializedData(file);

					} catch (InvalidClassException e1) {

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setGraphic(null);
						alert.setTitle("Kan bestand niet openen");
						String s = "Het gekozen bestand is niet compatibel met de versie van HGRKzkzkboxx die je gebruikt, of het is corrupt.";
						alert.setContentText(s);
						alert.show();

					}

					catch (FileNotFoundException e1) {

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setGraphic(null);
						alert.setTitle("Bestand niet gevonden");
						String s = "Het opgegeven bestand kon niet gevonden worden.";
						alert.setContentText(s);
						alert.show();

						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

				root.getChildren().clear();
				Sporenenstappenmaken(root, project, primaryStage);

			}
		});

		Button midi = new Button("Midi exporteren");
		midi.setTranslateX(naarrechts + 200);
		midi.setStyle("-fx-background-color: transparent;");
		root.getChildren().add(midi);

		final FileChooser fileChooser3 = new FileChooser();

		FileChooser.ExtensionFilter MIDIextFilter = new FileChooser.ExtensionFilter("MIDI bestanden (*.mid)", "*.mid");

		fileChooser3.getExtensionFilters().add(MIDIextFilter);

		midi.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = fileChooser3.showSaveDialog(null);
				if (file != null) {
					BestandSchrijver.projectOpslaanAlsMidiFile(project, file);
				}
			}
		});

		Button info = new Button("info");
		info.setTranslateX(naarrechts + 933);
		info.setStyle("-fx-background-color: transparent;");
		root.getChildren().add(info);

	}

	public static void bpminvoer(Project project, Pane root, Stage primaryStage) {

		int bpmverticaal = 93;
		int bpmhorizontaal = 347;

		TextField bpmveld = new TextField();
		String bpm = String.valueOf(project.getMijnsequencer().getSequencer().getTempoInBPM());
		bpmveld.setTranslateX(bpmhorizontaal);
		bpmveld.setTranslateY(bpmverticaal);
		bpmveld.setText(bpm);
		bpmveld.setStyle("-fx-background-color: transparent;");
		root.getChildren().add(bpmveld);

		bpmveld.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					float ingevoerdewaarde = Float.valueOf(bpmveld.getText());
					project.bpminstellen(ingevoerdewaarde);
					System.out.println(project.getMijnsequencer().getSequencer().getTempoInBPM());
					String nieuwebpm = String.valueOf(project.getMijnsequencer().getSequencer().getTempoInBPM());
					bpmveld.setText(nieuwebpm);
				}
			}
		});

	}

	public static void startknopmaken(Project project, Pane root) {
		int startknophorizontaal = 148;
		int startknopverticaal = 93;
		String tekst1 = "Afspelen";
		String tekst2 = "Stoppen";

		Button startknop = new Button();
		startknop.setTranslateX(startknophorizontaal);
		startknop.setTranslateY(startknopverticaal);
		startknop.setText(tekst1);
		startknop.setStyle("-fx-background-color: transparent;");
		startknop.setStyle(startknop.getStyle() + "-fx-font-weight: bold;");

		root.getChildren().add(startknop);

		startknop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				if (startknop.getText().equals(tekst1)) {
					startknop.setText(tekst2);
				} else {
					startknop.setText(tekst1);
				}

				project.afspelen();
			}
		});

	}

	public static void Sporenenstappenmaken(Pane root, Project project, Stage primaryStage) {

		Border randaan = new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

		Border randuit = new Border(
				new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT));

		int hoogtetussenrijen = 43;
		int horizontaleafstandtussenstappen = 40;
		int startxpos = 30;
		int startypos = 120;
		int aftsandvanknoptovlabels = 125;
		int btnx;

		String stapAan = "aan";
		String stapUit = "aan";

		// int uiteindelijkehoogte = startypos + (hoogtetussenrijen *
		// project.getSporen().size());

		for (Spoor spoor : project.getSporen()) {

			btnx = 0;
			Label spoorlabel = new Label(spoor.getNaam());
			Label instrumentlabel = new Label(spoor.getOmschrijving());

			spoorlabel.setTranslateX(startxpos);
			spoorlabel.setTranslateY(startypos);

			instrumentlabel.setTranslateX(spoorlabel.getTranslateX());
			instrumentlabel.setTranslateY(spoorlabel.getTranslateY() + 12);
			instrumentlabel.setFont(new Font(10));

			startypos += hoogtetussenrijen;
			root.getChildren().add(spoorlabel);
			root.getChildren().add(instrumentlabel);

			for (Stap stap : spoor.getStappen()) {

				Button stapknop = new Button();
				stapknop.setId("stap");
				stapknop.setStyle("-fx-background-color: transparent;");
				stapknop.setTextFill(null);
				if (stap.maakIkGeluid()) {
					stapknop.setText(stapAan);
					stapknop.setBorder(randaan);
				} else {
					stapknop.setText(stapUit);
					stapknop.setBorder(randuit);
				}

				// 1ste tel markeren
				if (stap.getMijnplek() == 1 || (stap.getMijnplek() - 1) % 4 == 0) {
					stapknop.setStyle(stapknop.getStyle() + "-fx-background-color: white;");

				}

				stapknop.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {
						stap.aanuit();

						if (stap.maakIkGeluid()) {

							stapknop.setBorder(randaan);
							stapknop.setText(stapAan);

						} else {
							stapknop.setBorder(randuit);

							if (stap.getMijnplek() == 1 || (stap.getMijnplek() - 1) % 4 == 0) {
								stapknop.setStyle(stapknop.getStyle() + "-fx-background-color: white;");
							} else {
								stapknop.setStyle("-fx-background-color: transparent;");
							}

							stapknop.setText(stapUit);
						}

					}
				});

				stapknop.setTranslateX(spoorlabel.getTranslateX() + (aftsandvanknoptovlabels + btnx));
				stapknop.setTranslateY(spoorlabel.getTranslateY());
				root.getChildren().add(stapknop);

				btnx += horizontaleafstandtussenstappen;

			}

		}

		Button leegmaken = new Button("Patroon leegmaken");
		leegmaken.setStyle("-fx-background-color: transparent;");
		leegmaken.setLayoutX(680);
		leegmaken.setLayoutY(93);
		leegmaken.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				project.allesporenleegmaken();

				for (Node node : root.getChildren()) {
					if (node instanceof Button && node.getId() == "stap") {

						((Button) node).setBorder(randuit);

					}
				}

			}
		});

		root.getChildren().add(leegmaken);

		startknopmaken(project, root);
		bpminvoer(project, root, primaryStage);
		menubalkmaken(project, root, primaryStage);
		primaryStage.setTitle("HGRKzkzkboxx - " + project.getNaam());
	}

}
