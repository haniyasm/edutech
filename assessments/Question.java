package assessments;

public class Question {
    
    private String topic;
    private String question;
    private String answer;
    private int marks;

    // Default constructor
    public Question() {
    }

    // Parameterized constructor
    public Question(String topic, String question, String answer, int marks) {
        this.topic = topic;
        this.question = question;
        this.answer = answer;
        this.marks = marks;
    }

    // Getter and Setter methods

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    // toString method for easy display
    @Override
    public String toString() {
        return "Question {" +
                "Topic='" + topic + '\'' +
                ", Question='" + question + '\'' +
                ", Answer='" + answer + '\'' +
                ", Marks=" + marks +
                '}';
    }
}
