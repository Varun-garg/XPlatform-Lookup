package StudentManagementSystem.Model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by rishabh on 11/12/2016.
 */
public class ExamsResult extends Exams {
    private final SimpleStringProperty subjectcode;
    private final SimpleStringProperty grade;


    public ExamsResult(String subjectcode, String grade) {
        this.subjectcode = new SimpleStringProperty(subjectcode);
        this.grade = new SimpleStringProperty(grade);

    }


    public String getSubjectcode() {
        return subjectcode.get();
    }

    public void setSubjectcode(String subjectcode) {
        this.subjectcode.set(subjectcode);
    }


    public String getGrade() {
        return grade.get();
    }

    public void setGrade(String grade) {
        this.grade.set(grade);
    }


}
