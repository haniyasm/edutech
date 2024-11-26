package assessments;

public abstract class Assessment {

    
    private String assessmentName;
    private int assessmentCode;
    private int maxScore;
    
    // Constructor
    public Assessment(String assessmentName, int assessmentCode, int maxScore) {
        this.assessmentName = assessmentName;
        this.assessmentCode = assessmentCode;
        this.maxScore = maxScore;
    }

    // Getter and Setter methods
    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public int getAssessmentCode() {
        return assessmentCode;
    }

    public void setAssessmentCode(int assessmentCode) {
        this.assessmentCode = assessmentCode;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    // Abstract method to be implemented by subclasses
    public abstract void startAssessment();
    
    public abstract void evaluateAssessment();
}

