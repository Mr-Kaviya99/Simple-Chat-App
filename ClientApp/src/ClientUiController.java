import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientUiController {

    public JFXTextArea messageArea;
    public JFXTextField txtMessage;

    static Socket socket;

    static DataOutputStream dataOutputStream;
    static DataInputStream dataInputStream;

    public void initialize() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    socket = new Socket("localhost", 5000);

                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());

                    String messageIn = "";

                    while (!messageIn.equals("exit")) {
                        messageIn = dataInputStream.readUTF();
                        messageArea.appendText("\n" + messageIn.trim());
                        Clear();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void Clear() {
                txtMessage.setText("");
            }

        }).start();
    }

    private void Clear() {
        txtMessage.setText("");
    }

    public void sendOnAction(ActionEvent actionEvent) throws IOException {
        String messageOut = "";
        messageOut = txtMessage.getText();
        dataOutputStream.writeUTF(messageOut);

    }
}
