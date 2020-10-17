package message;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MessageController {

    @FXML
    private HBox messageContainer;

    @FXML
    private Label messageId;
    
    private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
	@FXML
	private void initialize() {
		
		
		messageId.setWrapText(true);

		Platform.runLater(() -> {
			if(message != null) {
				messageId.setText(message);
			} else {
				System.out.println("No message");
			}
	    });
		

	}

}
