package su.library.BookType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PracticeDocuments {

    private String practiceId;

    private String practiceName;

    private String practiceLink;

    private String practiceDescribe;

    public PracticeDocuments(String practiceId, PracticeDocuments newPractice) {
        super();
        this.practiceId = practiceId;
        this.practiceName = newPractice.practiceName;
        this.practiceLink = newPractice.practiceLink;
        this.practiceDescribe = newPractice.practiceDescribe;
    }

    public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    public String getPracticeLink() {
        return practiceLink;
    }

    public void setPracticeLink(String practiceLink) {
        this.practiceLink = practiceLink;
    }

    public String getPracticeDescribe() {
        return practiceDescribe;
    }

    public void setPracticeDescribe(String practiceDescribe) {
        this.practiceDescribe = practiceDescribe;
    }

}
