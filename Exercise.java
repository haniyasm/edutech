package assessments;

import java.util.ArrayList;

public class Exercise extends Assessment {

	private ArrayList<Question> questions;
    // Constructor
	
    public Exercise(String assessmentName, int assessmentCode,ArrayList<Question> questions) {
        super(assessmentName, assessmentCode, 0);
        this.questions=questions;
    }

    // Implementing abstract methods
    @Override
    public void startAssessment() {
        // Logic for starting the exercise
        System.out.println("Starting Exercise: " + getAssessmentName());
    }

    @Override
    public void evaluateAssessment() {
        // Logic for evaluating the exercise
        System.out.println("Evaluating Exercise: " + getAssessmentName());
    }
    
    public ArrayList<Question> getQuestions() {
    	
    	return questions; 
    	
    }
    
    public void printDetails()
    {
        System.out.println("Exercise Name: " + getAssessmentName());
        System.out.println("Exercise Code: " + getAssessmentCode());
        System.out.println("Questions:");

        if (questions != null && !questions.isEmpty()) {
            for (Question question : questions) {
                System.out.println("  - Topic: " + question.getTopic());
                System.out.println("    Question: " + question.getQuestion());
                System.out.println("    Answer: " + question.getAnswer());
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
            }
        } else {
            System.out.println("  No questions available.");
        }
    }
}
