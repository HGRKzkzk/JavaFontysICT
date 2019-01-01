package gui;

import logica.Project;
import logica.SimpeleSequencer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Main extends Application {
	
	int	horizontaleresolutie = 880;
	int verticaleresolutie = 640; 
	
	SimpeleSequencer mijnsequencer = new SimpeleSequencer();
	Project project = new Project("Nieuw project", 120, 6, 0, mijnsequencer);

	Border rand = new Border(
			new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

	@Override
	public void start(Stage primaryStage) {
		try {

			Pane root = new Pane();
			Scene scene = new Scene(root, horizontaleresolutie, verticaleresolutie);

			@SuppressWarnings("unused")
			GUIbuilder mijnGUI= new GUIbuilder(root, project, primaryStage); 
			
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("HGRKzkzkboxx - " + project.getNaam());
			primaryStage.getIcons().add(new Image("file:Music.png"));

			

			primaryStage.setResizable(false);
			primaryStage.setScene(scene);

			primaryStage.show();

			
			// Hoe om te gaan met afsluiten?
			primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e -> {
				e.consume();
				Popup popup = new Popup();
				HBox buttons = new HBox();
				buttons.setBorder(rand);
				buttons.setStyle(" -fx-background-color: rgba(255, 255, 255, .9);");
				buttons.setFillHeight(true);
				Text afsluitentekst = new Text("Weet je zeker dat je af wilt sluiten?  \n \n \n " + "" + " ");

				Button close = new Button("Afsluiten");
				close.setTranslateY(100);
				close.setStyle("-fx-background-color: transparent;");
				close.setBorder(rand);
				Button cancel = new Button("Annuleren");
				cancel.setTranslateY(100);
				cancel.setTranslateX(20);
				cancel.setStyle("-fx-background-color: transparent;");

				buttons.getChildren().addAll(afsluitentekst, close, cancel);
				buttons.setPadding(new Insets(125, 125, 125, 125));
				popup.getContent().add(buttons);
				popup.show(primaryStage);
				close.setOnAction(ex -> {
					Platform.exit();
					System.exit(0);
				});
				cancel.setOnAction(ec -> {
					popup.hide();
				});
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	//Daar gaan we dan..!
	public static void main(String[] args) {
		launch(args);
	}



}
