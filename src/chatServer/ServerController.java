package chatServer;

import java.io.IOException;
import java.net.ServerSocket;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import message.MessageController;

public class ServerController {

	@FXML
    private JFXTextField messageTf;

    @FXML
    private JFXButton sendBtn;

    @FXML
    private Circle status;

    @FXML
    private VBox messageArea;
    
    @FXML
    private ScrollPane scrollPane;
    
    AcceptAndRecieveRunnable aarr;

    @FXML
    void sendMessage(MouseEvent event) {
    	try {
			String textMessage = messageTf.getText().trim();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/message/message.fxml"));     

			HBox root = (HBox)fxmlLoader.load();
			MessageController controller = fxmlLoader.getController();
			controller.setMessage(textMessage);
			messageArea.getChildren().add(root);
			messageTf.setText("");
			aarr.printStream.println(textMessage);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    private void initialize() {
    	sendBtn.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/send-button.png"))));
    	sendBtn.setDisable(true);
    	sendBtn.disableProperty().bind(Bindings.isEmpty(messageTf.textProperty()));
    	scrollPane.vvalueProperty().bind(messageArea.heightProperty());
    	
    	try {
			ServerSocket serverSocket = new ServerSocket(9000);
			aarr = new AcceptAndRecieveRunnable(serverSocket, messageArea);
			Thread aarrt = new Thread(aarr);
			aarrt.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    
}
