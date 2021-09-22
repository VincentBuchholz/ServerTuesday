package CphBusiness;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Quiz quiz = new Quiz();

	EchoServer echoServer = new EchoServer(8285,quiz);
        try {
            echoServer.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
