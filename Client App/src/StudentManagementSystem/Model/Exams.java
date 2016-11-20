package StudentManagementSystem.Model;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "Overall Result",
        "Marks Summary"
})
public class Exams {

    @JsonProperty("Overall Result")
    private List<OverallResult> overallResult = new ArrayList<OverallResult>();
    @JsonProperty("Marks Summary")
    private List<MarksSummary> marksSummary = new ArrayList<MarksSummary>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The overallResult
     */
    @JsonProperty("Overall Result")
    public List<OverallResult> getOverallResult() {
        return overallResult;
    }

    /**
     * @param overallResult The Overall Result
     */
    @JsonProperty("Overall Result")
    public void setOverallResult(List<OverallResult> overallResult) {
        this.overallResult = overallResult;
    }

    /**
     * @return The marksSummary
     */
    @JsonProperty("Marks Summary")
    public List<MarksSummary> getMarksSummary() {
        return marksSummary;
    }

    /**
     * @param marksSummary The Marks Summary
     */
    @JsonProperty("Marks Summary")
    public void setMarksSummary(List<MarksSummary> marksSummary) {
        this.marksSummary = marksSummary;
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