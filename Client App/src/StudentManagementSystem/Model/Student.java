package StudentManagementSystem.Model;

//credits http://www.jsonschema2pojo.org/

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "full_name",
        "enroll_no",
        "program_name",
        "school",
        "roll_no",
        "father_name",
        "mother_name",
        "dob",
        "sex",
        "email",
        "phone"
})
public class Student {

    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("enroll_no")
    private String enrollNo;
    @JsonProperty("program_name")
    private String programName;
    @JsonProperty("school")
    private String school;
    @JsonProperty("roll_no")
    private String rollNo;
    @JsonProperty("father_name")
    private String fatherName;
    @JsonProperty("mother_name")
    private String motherName;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("sex")
    private String sex;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The fullName
     */
    @JsonProperty("full_name")
    public String getFullName() {
        return fullName;
    }

    /**
     *
     * @param fullName
     * The full_name
     */
    @JsonProperty("full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     *
     * @return
     * The enrollNo
     */
    @JsonProperty("enroll_no")
    public String getEnrollNo() {
        return enrollNo;
    }

    /**
     *
     * @param enrollNo
     * The enroll_no
     */
    @JsonProperty("enroll_no")
    public void setEnrollNo(String enrollNo) {
        this.enrollNo = enrollNo;
    }

    /**
     *
     * @return
     * The programName
     */
    @JsonProperty("program_name")
    public String getProgramName() {
        return programName;
    }

    /**
     *
     * @param programName
     * The program_name
     */
    @JsonProperty("program_name")
    public void setProgramName(String programName) {
        this.programName = programName;
    }

    /**
     *
     * @return
     * The school
     */
    @JsonProperty("school")
    public String getSchool() {
        return school;
    }

    /**
     *
     * @param school
     * The school
     */
    @JsonProperty("school")
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     *
     * @return
     * The rollNo
     */
    @JsonProperty("roll_no")
    public String getRollNo() {
        return rollNo;
    }

    /**
     *
     * @param rollNo
     * The roll_no
     */
    @JsonProperty("roll_no")
    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    /**
     *
     * @return
     * The fatherName
     */
    @JsonProperty("father_name")
    public String getFatherName() {
        return fatherName;
    }

    /**
     *
     * @param fatherName
     * The father_name
     */
    @JsonProperty("father_name")
    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    /**
     *
     * @return
     * The motherName
     */
    @JsonProperty("mother_name")
    public String getMotherName() {
        return motherName;
    }

    /**
     *
     * @param motherName
     * The mother_name
     */
    @JsonProperty("mother_name")
    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    /**
     *
     * @return
     * The dob
     */
    @JsonProperty("dob")
    public String getDob() {
        return dob;
    }

    /**
     *
     * @param dob
     * The dob
     */
    @JsonProperty("dob")
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     *
     * @return
     * The sex
     */
    @JsonProperty("sex")
    public String getSex() {
        return sex;
    }

    /**
     *
     * @param sex
     * The sex
     */
    @JsonProperty("sex")
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     *
     * @return
     * The email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The phone
     */
    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone
     * The phone
     */
    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
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