package StudentManagementSystem.Model;

/**
 * Created by rishabh on 12/1/2016.
 */

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "message",
        "permit"
})
public class ClientStatus {

    @JsonProperty("message")
    private String message;
    @JsonProperty("permit")
    private String permit;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The message
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The permit
     */
    @JsonProperty("permit")
    public String getPermit() {
        return permit;
    }

    /**
     * @param permit The permit
     */
    @JsonProperty("permit")
    public void setPermit(String permit) {
        this.permit = permit;
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