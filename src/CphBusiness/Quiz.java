package CphBusiness;

import java.util.HashMap;

public class Quiz {

    private HashMap<Integer, String> capitalQuestions = new HashMap<>();
    private HashMap<Integer, String> capitalAnswers = new HashMap<>();

    public Quiz(){
        this.capitalQuestions.put(200, "Capital of Denmark");
        this.capitalQuestions.put(300, "Capital of England");
        this.capitalQuestions.put(400, "Capital of Sweden");
        this.capitalQuestions.put(500, "Capital of Germany");
        this.capitalQuestions.put(600, "Capital of China");

        this.capitalAnswers.put(200,"Copenhagen");
        this.capitalAnswers.put(300,"London");
        this.capitalAnswers.put(400,"Stockholm");
        this.capitalAnswers.put(500,"Berlin");
        this.capitalAnswers.put(600,"Bejing");
    }

    public synchronized String getQuestion(int key){
        String question = "No question";
        System.out.println(Thread.currentThread().getName() +" " + key);
        if(capitalAnswers.containsKey(key)){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            question = capitalQuestions.get(key);
            System.out.println(Thread.currentThread().getName()+ " is trying to get question" + question + " " + key);
            capitalQuestions.remove(key);
        } else{
            System.out.println(Thread.currentThread().getName()+ " could not get" + key);
        }

        return question;
    }

    public String getAnswer(int key){
        return this.capitalAnswers.get(key);
    }

    public Boolean listEmpty(){
        return capitalQuestions.isEmpty();
    }

    public Boolean questionTaken(int key){

        if (capitalQuestions.get(key) == null){
            return true;
        }
        return false;
    }

    public String getAvailableQuestions(){
        String available ="";

        for (int i = 2; i < capitalQuestions.size()+2; i++) {
            if(questionTaken(i)){
                available += (i*100) + " ";
            }
        }
        return available;
    }

}
