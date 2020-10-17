package chatClient;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ReceiveRunnable implements Runnable {

	Socket clientSocket;
	VBox messagesArea;
	PrintStream writerStream;
	Scanner readerStream;
	
	
	
	public ReceiveRunnable(Socket clientSocket, VBox messagesArea) {
		super();
		this.clientSocket = clientSocket;
		this.messagesArea = messagesArea;
	}



	@Override
	public void run() {
		try {
			writerStream = new PrintStream(clientSocket.getOutputStream());
			readerStream = new Scanner(clientSocket.getInputStream());
			
			while(true) {
				String textMessage = readerStream.nextLine();
				Platform.runLater(()->messagesArea.getChildren().add(getMessageNodeFromText(textMessage)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private HBox getMessageNodeFromText(String textMessage) {
		HBox messageContainer = new HBox();
		Label messageLabel = new Label(textMessage);
		messageContainer.getChildren().add(messageLabel);
		
		/*Styling*/
		messageContainer.setStyle("-fx-background-color: #FFF");
		messageContainer.setAlignment(Pos.CENTER_LEFT);
		messageContainer.setPadding(new Insets(5,0,5,10));
		messageContainer.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		messageContainer.setPrefSize(600, 54);
		messageContainer.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		
		messageLabel.setStyle("-fx-background-color:  #e6e5eb; -fx-font-size:14px; -fx-background-radius: 10px");
		messageLabel.setWrapText(true);
		messageLabel.setFont(Font.font("Calibri"));
		messageLabel.setPadding(new Insets(10));
		messageLabel.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_PREF_SIZE);
		messageLabel.setPrefSize(583, Region.USE_COMPUTED_SIZE);
		messageLabel.setMaxSize(350, 50);
		/*End Styling*/
		
		
		return messageContainer;
	}

}
