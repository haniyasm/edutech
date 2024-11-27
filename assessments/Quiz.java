package assessments;

import java.util.ArrayList;

public class Quiz extends Assessment {

    // Constructor
	private int duration;
	private ArrayList<Question> questions;
	
    public Quiz(String assessmentName, int assessmentCode, int duration, int maxScore,ArrayList<Question> questions) {
        super(assessmentName, assessmentCode, maxScore);
        this.duration=duration;
        this.questions=questions;
    }

    // Implementing abstract methods
    @Override
    public void startAssessment() {
        // Logic for starting the quiz
        System.out.println("Starting Quiz: " + getAssessmentName());
    }

    @Override
    public void evaluateAssessment() {
        // Logic for evaluating the quiz
        System.out.println("Evaluating Quiz: " + getAssessmentName());
    }
    
    public ArrayList<Question> getQuestions() {
    	
    	return questions; 
    	
    }
    
    public int getDuration() {
    	
    	return duration; 
    	
    }
    

    
    public void printDetails() {
        System.out.println("Quiz Name: " + getAssessmentName());
        System.out.println("Quiz Code: " + getAssessmentCode());
        System.out.println("duration: " + duration);
        System.out.println("Maximum Score: " + getMaxScore());
        System.out.println("Questions:");

        if (questions != null && !questions.isEmpty()) {
            for (Question question : questions) {
                System.out.println("  - Topic: " + question.getTopic());
                System.out.println("    Question: " + question.getQuestion());
                System.out.println("    Answer: " + question.getAnswer());
                System.out.println("    Marks: " + question.getMarks());
            }
        } else {
            System.out.println("  No questions available.");
        }
    }
    
    public void printQuestions() {
        System.out.println("Questions:");
        if (questions != null && !questions.isEmpty()) {
            for (Question question : questions) {
                System.out.println("  - Topic: " + question.getTopic());
                System.out.println("    Question: " + question.getQuestion());
                System.out.println("    Answer: " + question.getAnswer());
                System.out.println("    Marks: " + question.getMarks());
            }
        } else {
            System.out.println("  No questions available.");
        }
    }
}
