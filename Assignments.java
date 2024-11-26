package assessments;

import java.time.LocalDate;
import java.util.*;

public class Assignments extends Assessment {
	
	private LocalDate deadline;
	private ArrayList<Question> questions;

    // Constructor
    public Assignments(String assessmentName, int assessmentCode, LocalDate deadline, int maxScore,ArrayList<Question> questions) {
        super(assessmentName, assessmentCode, maxScore);
        this.questions=questions;
        this.deadline=deadline;
    }

    // Implementing abstract methods
    @Override
    public void startAssessment() {
        // Logic for starting the assignment
        System.out.println("Starting Assignment: " + getAssessmentName());
    }

    @Override
    public void evaluateAssessment() {
        // Logic for evaluating the assignment
        System.out.println("Evaluating Assignment: " + getAssessmentName());
    }
    
    public LocalDate getDeadline() {
    	return this.deadline;
    }
    
    public List<Question> getQuestions() {
    	return this.questions;
    }
    
    public void printDetails() {
        System.out.println("Assignment Name: " + getAssessmentName());
        System.out.println("Assignment Code: " + getAssessmentCode());
        System.out.println("Deadline: " + deadline);
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
