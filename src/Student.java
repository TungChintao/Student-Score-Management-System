public class Student{
    private String studentID;
    private double score;

    Student(){

    }

    Student(String id, double s){
        studentID = id;
        score = s;
    }

    public String getStudentID() {
        return studentID;
    }

    public double getScore() {
        return score;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public boolean setScore(double score) {
        if(score < 0 || score > 100)
            return false;
        this.score = score;
        return true;
    }
}
