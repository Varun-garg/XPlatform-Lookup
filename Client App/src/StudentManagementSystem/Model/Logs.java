package StudentManagementSystem.Model;

/**
 * Created by rishabh on 11/30/2016.
 */

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
        "uname",
        "ugroup",
        "action",
        "datetime",
        "ipAddress",
        "system"
})
public class Logs {

    @JsonProperty("uname")
    private String uname;
    @JsonProperty("ugroup")
    private String ugroup;
    @JsonProperty("action")
    private String action;
    @JsonProperty("datetime")
    private String datetime;
    @JsonProperty("ipAddress")
    private String ipAddress;
    @JsonProperty("system")
    private String system;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The uname
     */
    @JsonProperty("uname")
    public String getUname() {
        return uname;
    }

    /**
     *
     * @param uname
     * The uname
     */
    @JsonProperty("uname")
    public void setUname(String uname) {
        this.uname = uname;
    }

    /**
     *
     * @return
     * The ugroup
     */
    @JsonProperty("ugroup")
    public String getUgroup() {
        return ugroup;
    }

    /**
     *
     * @param ugroup
     * The ugroup
     */
    @JsonProperty("ugroup")
    public void setUgroup(String ugroup) {
        this.ugroup = ugroup;
    }

    /**
     *
     * @return
     * The action
     */
    @JsonProperty("action")
    public String getAction() {
        return action;
    }

    /**
     *
     * @param action
     * The action
     */
    @JsonProperty("action")
    public void setAction(String action) {
        this.action = action;
    }

    /**
     *
     * @return
     * The datetime
     */
    @JsonProperty("datetime")
    public String getDatetime() {
        return datetime;
    }

    /**
     *
     * @param datetime
     * The datetime
     */
    @JsonProperty("datetime")
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    /**
     *
     * @return
     * The ipAddress
     */
    @JsonProperty("ipAddress")
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     *
     * @param ipAddress
     * The ipAddress
     */
    @JsonProperty("ipAddress")
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     *
     * @return
     * The system
     */
    @JsonProperty("system")
    public String getSystem() {
        return system;
    }

    /**
     *
     * @param system
     * The system
     */
    @JsonProperty("system")
    public void setSystem(String system) {
        this.system = system;
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
