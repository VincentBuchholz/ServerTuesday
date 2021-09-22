package CphBusiness;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class EchoServer {
    private int port;
    BlockingQueue<String> msgQueue = new ArrayBlockingQueue<>(10);
    CopyOnWriteArrayList<ClientHandler> clientHandlerList = new CopyOnWriteArrayList<>();



    public EchoServer(int port){
        this.port = port;
    }

    public void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);

        ExecutorService es = Executors.newFixedThreadPool(10);
        while(true) {
            Socket client = serverSocket.accept();
            ClientHandler cl = new ClientHandler(client, msgQueue);
            clientHandlerList.add(cl);
            Dispatcher disp = new Dispatcher(clientHandlerList,msgQueue);
            //cl.start();
            es.execute(cl);
            es.execute(disp);
        }
    }
}
