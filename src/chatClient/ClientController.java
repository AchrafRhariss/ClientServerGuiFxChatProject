package chatClient;

import java.io.IOException;
import java.net.Socket;

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

public class ClientController {

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
    
    ReceiveRunnable rr;

    @FXML
    void sendMessage(MouseEvent event) {
    	try {
			String textMessage = messageTf.getText();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/message/message.fxml"));     

			HBox root = (HBox)fxmlLoader.load();
			MessageController controller = fxmlLoader.getController();
			controller.setMessage(textMessage.trim());
			messageArea.getChildren().add(root);
			messageTf.setText("");
			
			rr.writerStream.println(textMessage);
			
		} catch (IOException e) {
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
			Socket clientSocket = new Socket("127.0.0.1",9000);
			rr = new ReceiveRunnable(clientSocket, messageArea);
			Thread rrt = new Thread(rr);
			rrt.start();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    
    

}
