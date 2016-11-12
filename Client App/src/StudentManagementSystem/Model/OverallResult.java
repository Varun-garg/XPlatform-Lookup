
package StudentManagementSystem.Model;

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
        "roll_num",
        "tc",
        "tgp",
        "sgpa",
        "result",
        "semester"
})
public class OverallResult {

    @JsonProperty("roll_num")
    private String rollNum;
    @JsonProperty("tc")
    private Integer tc;
    @JsonProperty("tgp")
    private Integer tgp;
    @JsonProperty("sgpa")
    private Double sgpa;
    @JsonProperty("result")
    private String result;
    @JsonProperty("semester")
    private Integer semester;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The rollNum
     */
    @JsonProperty("roll_num")
    public String getRollNum() {
        return rollNum;
    }

    /**
     *
     * @param rollNum
     * The roll_num
     */
    @JsonProperty("roll_num")
    public void setRollNum(String rollNum) {
        this.rollNum = rollNum;
    }

    /**
     *
     * @return
     * The tc
     */
    @JsonProperty("tc")
    public Integer getTc() {
        return tc;
    }

    /**
     *
     * @param tc
     * The tc
     */
    @JsonProperty("tc")
    public void setTc(Integer tc) {
        this.tc = tc;
    }

    /**
     *
     * @return
     * The tgp
     */
    @JsonProperty("tgp")
    public Integer getTgp() {
        return tgp;
    }

    /**
     *
     * @param tgp
     * The tgp
     */
    @JsonProperty("tgp")
    public void setTgp(Integer tgp) {
        this.tgp = tgp;
    }

    /**
     *
     * @return
     * The sgpa
     */
    @JsonProperty("sgpa")
    public Double getSgpa() {
        return sgpa;
    }

    /**
     *
     * @param sgpa
     * The sgpa
     */
    @JsonProperty("sgpa")
    public void setSgpa(Double sgpa) {
        this.sgpa = sgpa;
    }

    /**
     *
     * @return
     * The result
     */
    @JsonProperty("result")
    public String getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    @JsonProperty("result")
    public void setResult(String result) {
        this.result = result;
    }

    /**
     *
     * @return
     * The semester
     */
    @JsonProperty("semester")
    public Integer getSemester() {
        return semester;
    }

    /**
     *
     * @param semester
     * The semester
     */
    @JsonProperty("semester")
    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString()
    {
        return "ClassPojo [result = "+getResult()+", sgpa = "+getSgpa()+", tgp = "+getTgp()+", tc = "+getTc()+", roll_num = "+getRollNum()+", semester = "+getSemester()+"]";
    }

}