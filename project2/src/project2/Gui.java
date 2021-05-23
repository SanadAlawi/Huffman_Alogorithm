package project2;

import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Gui extends Application{
	
	//Buttons object
	Button EncodeButton = new Button("Encode Page");
	Button HuffmanButton = new Button("Huffman Code");
	Button HeaderButton = new Button("Header");
	
	//Scene object
	Scene EncodeScene;
	Scene HuffmanScene;
	Scene HeaderScene;
	
	// Radio Button
	RadioButton EncodeRadio = new RadioButton("Encode");
	RadioButton DecodeRadio = new RadioButton("Decode");
	Button GoButton = new Button("Go");
	
	// Labels And Button objects
	Label SourceLabel = new Label("Source file: ");
	Button OpenButton = new Button("Open");
	Label DestinationLabel = new Label("Destination file: ");
	Button DopenButton = new Button("Open");
	
	// TextField objects
	TextField SourceField = new TextField();
	TextField DestinationField = new TextField();
	
	// TextArea objects
	TextArea Sourceta = new TextArea();
	TextArea Destinationta = new TextArea();
	
	// FileChooser objects
	FileChooser chose1 = new FileChooser();
	FileChooser chose2 = new FileChooser();
	
	// Huffman Scene 
	Button HuEncodeButton = new Button("Encode Page");
	Button HuHuffmanButton = new Button("Huffman Code");
	Button HuHeaderButton = new Button("Header");
	
	TextArea Huffmanta = new TextArea();
	
	// Header Scene
	Button HEncodeButton = new Button("Encode Page");
	Button HHuffmanButton = new Button("Huffman Code");
	Button HHeaderButton = new Button("Header");
	
	TextArea Headerta = new TextArea();

	@Override
	public void start(Stage Stage) throws Exception {
		
		// Toggle group object
		ToggleGroup Toogle = new ToggleGroup();
		EncodeRadio.setToggleGroup(Toogle);
		DecodeRadio.setToggleGroup(Toogle);
		
		EncodeRadio.setUserData(true);
		DecodeRadio.setUserData(false);
		
		// Objects size
		EncodeButton.setPrefWidth(120);
		HuffmanButton.setPrefWidth(120);
		HeaderButton.setPrefWidth(120);
		HuEncodeButton.setPrefWidth(120);
		HuHuffmanButton.setPrefWidth(120);
		HuHeaderButton.setPrefWidth(120);
		HEncodeButton.setPrefWidth(120);
		HHuffmanButton.setPrefWidth(120);
		HHeaderButton.setPrefWidth(120);
		GoButton.setPrefSize(120, 30);
		SourceField.setPrefSize(220, 30);
		DestinationField.setPrefSize(220, 30);
		Sourceta.setPrefHeight(170);
		Sourceta.setMaxWidth(220);
		Destinationta.setMaxWidth(220);
		GoButton.setCursor(Cursor.CLOSED_HAND);
		GoButton.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, new CornerRadii(5), Insets.EMPTY)));
		
		
		HBox MainButtons = new HBox();
		MainButtons.getChildren().addAll(EncodeButton, HuffmanButton, HeaderButton);
		
		HBox RadioButtons = new HBox(10);
		RadioButtons.getChildren().addAll(EncodeRadio, DecodeRadio, GoButton);
		RadioButtons.setTranslateX(40);
		GoButton.setTranslateX(50);
		
		HBox SourceLabelAndButton = new HBox(40);
		SourceLabelAndButton.getChildren().addAll(SourceLabel, OpenButton, DestinationLabel, DopenButton);
		SourceLabelAndButton.setTranslateX(20);
		OpenButton.setTranslateX(50);
		DestinationLabel.setTranslateX(75);
		DopenButton.setTranslateX(110);
		
		HBox TextFieldSourceAndDestination = new HBox(40);
		TextFieldSourceAndDestination.getChildren().addAll(SourceField, DestinationField);
		TextFieldSourceAndDestination.setTranslateX(20);
		
		HBox TextAreaSounceAndDestination = new HBox(40);
		TextAreaSounceAndDestination.getChildren().addAll(Sourceta, Destinationta);
		TextAreaSounceAndDestination.setTranslateX(20);
		
		VBox Mainhb = new VBox(10);
		Mainhb.getChildren().addAll(MainButtons, RadioButtons, SourceLabelAndButton, TextFieldSourceAndDestination, TextAreaSounceAndDestination);
		
		EncodeScene = new Scene(Mainhb, 510, 400);
		
		Stage.setTitle("Project #2");
		Stage.setScene(EncodeScene);
		Stage.show();
		
		HuffmanButton.setOnAction(e -> {
			Stage.setScene(HuffmanScene);
		});
		
		HeaderButton.setOnAction(e -> {
			Stage.setScene(HeaderScene);
		});
		
		OpenButton.setOnAction(e -> {
			File FileName = chose1.showOpenDialog(Stage);
			SourceField.setText(FileName.getAbsoluteFile().toString());
		});
		
		DopenButton.setOnAction(e -> {
			File FileName = chose1.showOpenDialog(Stage);
			DestinationField.setText(FileName.getAbsoluteFile().toString());
		});
		
		GoButton.setOnAction(e -> {
			try {
				if(!SourceField.getText().isEmpty()) {
					boolean CompressOrNo = (boolean)Toogle.getSelectedToggle().getUserData();
					String FileName = SourceField.getText().trim();
					Main main = new Main(FileName);
					if(CompressOrNo == true) {
						main.Compress();
						Sourceta.setText("\t Source File Information\n\n"
								+ "\t\tFile Length:\n\t\t   "+main.getNumberOfByte()+"\n\n"
								+"\t# of Distinguished Character:\n\t\t\t  "+main.getHuffmanTable().size());
						Headerta.setText(main.getHeader());
						Huffmanta.setText(main.getHuffmanTale());
						
					}
					else 
						main.Decompress();
				}
				else
					new Alert(AlertType.WARNING, "Chosse File To Compress!!!").show();
			}
			catch(Exception ex) {
				new Alert(AlertType.WARNING, "File Not Found Exception!!!").show();
			}
		});
		
		// Huffman Scene
		HBox MainHuffmanButtons = new HBox();
		MainHuffmanButtons.getChildren().addAll(HuEncodeButton, HuHuffmanButton, HuHeaderButton);
		
		VBox Huvb = new VBox();
		Huvb.getChildren().addAll(MainHuffmanButtons, Huffmanta);
		HuffmanScene = new Scene(Huvb, 510, 400);
		
		HuEncodeButton.setOnAction(e -> { Stage.setScene(EncodeScene); });
		HuHuffmanButton.setOnAction(e -> { Stage.setScene(HuffmanScene); });
		HuHeaderButton.setOnAction(e -> { Stage.setScene(HeaderScene); });
		
		// Header Scene
		HBox MainHeaderButtons = new HBox();
		MainHeaderButtons.getChildren().addAll(HEncodeButton, HHuffmanButton, HHeaderButton);
		
		VBox Hvb = new VBox();
		Hvb.getChildren().addAll(MainHeaderButtons, Headerta);
		HeaderScene = new Scene(Hvb, 510, 400);
		
		HEncodeButton.setOnAction(e -> { Stage.setScene(EncodeScene); });
		HHuffmanButton.setOnAction(e -> { Stage.setScene(HuffmanScene); });
		HHeaderButton.setOnAction(e -> { Stage.setScene(HeaderScene); });
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
