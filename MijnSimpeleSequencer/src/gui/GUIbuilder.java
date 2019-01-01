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
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logica.Project;
import logica.Spoor;
import logica.Stap;
import persistentie.ProjectBestandLader;
import persistentie.ProjectBestandSchrijver;

public class GUIbuilder {

	private Pane root;
	private Project project;
	private Stage primaryStage;
	private ProjectBestandSchrijver BestandSchrijver;
	private ProjectBestandLader BestandLader;

	
	
	
	private final int fontsize = 10; 
	
	// Vaker gebruikte stijl-definities
	private final Border randaan = new Border(
			new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
	private final Border randuit = new Border(
			new BorderStroke(Color.DARKGRAY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT));

	// Positionering en styling van het sequencer blok
	private final int sequencerhorizontaal = 30; // px
	private final int sequencerverticaal = 93; // px
	private final int hoogtetussenrijen = 33; // px
	private final int horizontaleafstandtussenstappen = 40; // px
	private final int aftsandvanknoptovlabels = 125; // px
	private int btnx;
	
	private String stapknopstyle = "-fx-background-color: transparent;";
	private String stapknopmarkerstyle = "-fx-background-color: white;";
	// einde sequencerblok

	// Positionering en styling startknop
	private final int startknophorizontaal = 148; // px
	private final int startknopverticaal = sequencerverticaal - 23; // px
	private String afspelen = "Afspelen";
	private String stoppen = "Stoppen";
	// einde startknop

	// Positionering en styling BPM-veld
	private final int bpmverticaal = startknopverticaal; // px
	private final int bpmhorizontaal = 347; // px

	// einde BPM-veld;

	// Positionering en styling menubalk

	private final int menubalkxpos = 0;
	private final String labelOpslaan = "Bestand opslaan";
	private final String labelOpenen = "Bestand openen";
	
	private final String bestandOmschrijving = "WSB projecten (*.wsb)";
	private final String bestandExtensie = "*.wsb";
	private FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(bestandOmschrijving, bestandExtensie);

	// einde menubalk

	public GUIbuilder(Pane root, Project project, Stage primaryStage) {

		this.root = root;
		this.project = project;
		this.primaryStage = primaryStage;
		BestandSchrijver = new ProjectBestandSchrijver();
		BestandLader = new ProjectBestandLader(project);

		maakmijnGUI();
		
		// onderdelenGUIWeergeven();

	}
	
	

	private void maakmijnGUI() {
		
		sequencerMaken();
		bpmInvoerVeldMaken();
		menubalkMaken();
		startknopMaken();

	}
	
	private void onderdelenGUIWeergeven() {
		Object[] bla = root.getChildren().toArray();
		for (Object iets : bla) {
			System.out.println(iets);
			
		}
		
	}

	private void menubalkMaken() {
		
		knopBestandOpslaanMaken();
		knopBestandLadenMaken();	
		knopOpslaanNaarMidiMaken();

		
		Button info = new Button("info");
		info.setTranslateX(menubalkxpos + 933);
		info.setStyle("-fx-background-color: transparent;");
		root.getChildren().add(info);

	}
	
	
	private void knopBestandLadenMaken() {
		
		// Knop laden
				Button laden = new Button(labelOpenen);
				laden.setTranslateX(menubalkxpos);
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

						GUIopnieuwbouwen();

					}
				});

		
		
		
	}
	
	
	
	private void knopBestandOpslaanMaken() {
		
		

		// Knop opslaan
		Button opslaan = new Button(labelOpslaan);
		opslaan.setTranslateX(menubalkxpos + 100);
		opslaan.setStyle("-fx-background-color: transparent;");
		root.getChildren().add(opslaan);

		final FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(bestandOmschrijving, bestandExtensie);
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


		
		
	}
	
	
	
	private void knopOpslaanNaarMidiMaken() {
		
		// Knop exporteren naar midi
				Button midi = new Button("Midi exporteren");
				midi.setTranslateX(menubalkxpos + 200);
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

	}

	private void bpmInvoerVeldMaken() {

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
					project.tempoBepalen(ingevoerdewaarde);
					System.out.println(project.getMijnsequencer().getSequencer().getTempoInBPM());
					String nieuwebpm = String.valueOf(project.getMijnsequencer().getSequencer().getTempoInBPM());
					bpmveld.setText(nieuwebpm);
				}
			}
		});

	}

	private void startknopMaken() {

		Button startknop = new Button();
		startknop.setTranslateX(startknophorizontaal);
		startknop.setTranslateY(startknopverticaal);
		startknop.setText(afspelen);
		startknop.setStyle("-fx-background-color: transparent;");
		startknop.setStyle(startknop.getStyle() + "-fx-font-weight: bold;");

		root.getChildren().add(startknop);
		
		startknop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				
				if (startknop.getText().equals(afspelen)) {
					startknop.setText(stoppen);
				} else {
					startknop.setText(afspelen);
				}

				project.afspelen();
			}
		});

	}

	private void sequencerMaken() {

		int mijnypos = sequencerverticaal;
		String stapAan = "aan";
		String stapUit = "aan";
		
		// het drum gedeelte
		for (Spoor Drumspoor : project.getSporen()) {

			btnx = 0;
			Label spoorlabel = new Label(Drumspoor.getNaam());
			Label instrumentlabel = new Label(Drumspoor.getOmschrijving());
			instrumentlabel.setStyle("-fx-background-color: transparent;");
			instrumentlabel.setTextAlignment(TextAlignment.LEFT);
			
					
			

			spoorlabel.setTranslateX(sequencerhorizontaal);
			spoorlabel.setTranslateY(mijnypos);
			instrumentlabel.setTranslateX(spoorlabel.getTranslateX() + 0 );
			instrumentlabel.setTranslateY(spoorlabel.getTranslateY() + 12);
			instrumentlabel.setFont(new Font(fontsize));
			
			instrumentlabel.setTextAlignment(TextAlignment.LEFT);
			
						
			
			
			
			
			

			mijnypos += hoogtetussenrijen;
			root.getChildren().add(spoorlabel);
			root.getChildren().add(instrumentlabel);

			for (Stap stap : Drumspoor.getStappen()) {

				Button stapknop = new Button();
				stapknop.setId("stap");
				stapknop.setStyle(stapknopstyle);
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
								stapknop.setStyle(stapknop.getStyle() + stapknopmarkerstyle);
							} else {
								stapknop.setStyle(stapknopstyle);
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
		leegmaken.setLayoutY(startknopverticaal);
		leegmaken.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				project.alleSporenLeegmaken();
				

				for (Node node : root.getChildren()) {
					if (node instanceof Button && node.getId() == "stap") {

						((Button) node).setBorder(randuit);

					}
				}

			}
		});

		root.getChildren().add(leegmaken);

		primaryStage.setTitle("HGRKzkzkboxx - " + project.getNaam());
	}

	private void GUIopnieuwbouwen() {
		root.getChildren().clear();
		maakmijnGUI();
	}

}
