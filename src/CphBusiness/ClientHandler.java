package CphBusiness;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;


public class ClientHandler implements Runnable{
    private Socket client;
    private PrintWriter pw;
    private Scanner sc;
    private BlockingQueue<String> msgQueue;
    private Quiz quiz;

    public ClientHandler(Socket client, BlockingQueue<String> msg, Quiz quiz) throws IOException {
        this.client = client;
        this.pw = new PrintWriter(client.getOutputStream(),true);
        this.sc = new Scanner(client.getInputStream());
        this.msgQueue = msg;
        this.quiz = quiz;

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
                case "QUIZ":
                    doQuiz();
                    break;
                case "ALL":
                    try {
                        msgQueue.put(word);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
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
                case "ALLREVERSE":
                    charArray = word.toCharArray();
                    outPut = "";
                    for (int i = charArray.length-1; i > -1 ; i--) {
                        outPut = outPut + charArray[i];
                    }
                    try {
                        msgQueue.put(outPut);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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

    public PrintWriter getPw() {
        return pw;
    }

    private void doQuiz(){
        int questionKey;
        String question;
        String answer;

        while(!quiz.listEmpty()) {
            pw.println("Choose an amount: " + quiz.getAvailableQuestions());
            questionKey = Integer.parseInt(sc.nextLine());
            while(quiz.questionTaken(questionKey)){
                pw.println("Question taken, choose new question");
                questionKey = Integer.parseInt(sc.nextLine());
            }
            question = quiz.getQuestion(questionKey);
            pw.println(question);
            answer = sc.nextLine();

            if (answer.equals(quiz.getAnswer(questionKey))) {
                pw.println("Correct");
            } else {
                pw.println("incorrect");
            }
        }
        msgQueue.add("Quiz is done");
    }

}
