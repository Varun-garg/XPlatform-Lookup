package StudentManagementSystem.Model;

/**
 * Created by rishabh on 11/29/2016.
 */


import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "student",
        "section",
        "comment",
        "user",
        "date"
})
public class Review {

    @JsonProperty("student")
    private String student;
    @JsonProperty("section")
    private String section;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("user")
    private String user;
    @JsonProperty("date")
    private String date;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    /**
     * @return The student
     */
    @JsonProperty("student")
    public String getStudent() {
        return student;
    }

    /**
     * @param student The student
     */
    @JsonProperty("student")
    public void setStudent(String student) {
        this.student = student;
    }

    /**
     * @return The section
     */
    @JsonProperty("section")
    public String getSection() {
        return section;
    }

    /**
     * @param section The section
     */
    @JsonProperty("section")
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return The comment
     */
    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    /**
     * @param comment The comment
     */
    @JsonProperty("comment")
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return The user
     */
    @JsonProperty("user")
    public String getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    @JsonProperty("user")
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return The date
     */
    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
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