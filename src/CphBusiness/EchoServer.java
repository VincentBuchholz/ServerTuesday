package CphBusiness;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
    private int port;


    public EchoServer(int port){
        this.port = port;
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);

        ExecutorService es = Executors.newFixedThreadPool(10);
        while(true) {
            Socket client = serverSocket.accept();
            ClientHandler cl = new ClientHandler(client);
            //cl.start();
            es.execute(cl);
        }
    }
}
