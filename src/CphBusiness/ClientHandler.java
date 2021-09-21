package CphBusiness;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;



public class ClientHandler implements Runnable{
    private Socket client;
    private PrintWriter pw;
    private Scanner sc;

    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        this.pw = new PrintWriter(client.getOutputStream(),true);
        this.sc = new Scanner(client.getInputStream());
    }

    public void protocol() throws IOException {
        String msg = "";

        while(!msg.equals("CLOSE")) {
            msg = sc.nextLine();

            String[] split = msg.split("#");

            String action = split[0];
            String word = split[1];

            //TODO: split string on #
            //TODO: Switch på første del og procces anden del

            switch(action){
                case "UPPER":
                    pw.println(word.toUpperCase());
                    break;
                case "LOWER":
                    pw.println(word.toLowerCase());
                    break;
                case "REVERSE":
                    char[] charArray = word.toCharArray();
                    String outPut = "";
                    for (int i = charArray.length-1; i > -1 ; i--) {
                        outPut = outPut + charArray[i];
                    }
                    pw.println(outPut);
                    break;
                default:
                    pw.println("Incorrect action");
            }
        }
        client.close();
    }

    @Override
    public void run() {
        while(true) {
            try {
                this.protocol();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
