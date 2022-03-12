import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static java.awt.AlphaComposite.Clear;

public class ServerUiController {


    public JFXTextField txtMessage;
    public JFXTextArea messageArea;

    static ServerSocket serverSocket;
    static Socket socket;

    static DataOutputStream dataOutputStream;
    static DataInputStream dataInputStream;

    String messageIn;

    public void initialize(){

        new Thread(()->{
            try {
                serverSocket = new ServerSocket(5000);
                socket = serverSocket.accept();

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                String messageIn = "";

                while (!messageIn.equals("exit")){
                    messageIn = dataInputStream.readUTF();
                    messageArea.appendText("\n"+messageIn.trim());
                    Clear();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }



        }).start();


    }

    private void Clear() {
        txtMessage.setText("");
    }

    public void sendOnAction(ActionEvent actionEvent) throws IOException {
        String reply = "";
        reply = txtMessage.getText();
        dataOutputStream.writeUTF(reply);
    }
}
