package StudentManagementSystem.Model;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "subject_code",
        "grade"
})
public class MarksSummary {

    @JsonProperty("subject_code")
    private String subjectCode;
    @JsonProperty("grade")
    private String grade;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The subjectCode
     */
    @JsonProperty("subject_code")
    public String getSubjectCode() {
        return subjectCode;
    }

    /**
     * @param subjectCode The subject_code
     */
    @JsonProperty("subject_code")
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    /**
     * @return The grade
     */
    @JsonProperty("grade")
    public String getGrade() {
        return grade;
    }

    /**
     * @param grade The grade
     */
    @JsonProperty("grade")
    public void setGrade(String grade) {
        this.grade = grade;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}