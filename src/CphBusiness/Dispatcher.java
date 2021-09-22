package CphBusiness;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Dispatcher implements Runnable{
    CopyOnWriteArrayList<ClientHandler> clients;
    BlockingQueue<String> messages;

    public Dispatcher(CopyOnWriteArrayList<ClientHandler> client, BlockingQueue<String> messages){
        this.clients = client;
        this.messages = messages;
    }

    @Override
    public void run() {

        while(true){
            try {
                msgAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void msgAll() throws InterruptedException {
        String msg = messages.take();
        for (ClientHandler client: clients) {
            client.getPw().println(msg);
        }

    }
}
