package StudentManagementSystem.Model;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"roll_num", "room_num", "warden_name", "warden_mob", "caretaker_name", "caretaker_num"})
public class Hostel {

    @JsonProperty("roll_num")
    private String rollNum;
    @JsonProperty("room_num")
    private String roomNum;
    @JsonProperty("warden_name")
    private String wardenName;
    @JsonProperty("warden_mob")
    private String wardenMob;
    @JsonProperty("caretaker_name")
    private String caretakerName;
    @JsonProperty("caretaker_num")
    private String caretakerNum;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The rollNum
     */
    @JsonProperty("roll_num")
    public String getRollNum() {
        return rollNum;
    }

    /**
     * @param rollNum The roll_num
     */
    @JsonProperty("roll_num")
    public void setRollNum(String rollNum) {
        this.rollNum = rollNum;
    }

    /**
     * @return The roomNum
     */
    @JsonProperty("room_num")
    public String getRoomNum() {
        return roomNum;
    }

    /**
     * @param roomNum The room_num
     */
    @JsonProperty("room_num")
    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    /**
     * @return The wardenName
     */
    @JsonProperty("warden_name")
    public String getWardenName() {
        return wardenName;
    }

    /**
     * @param wardenName The warden_name
     */
    @JsonProperty("warden_name")
    public void setWardenName(String wardenName) {
        this.wardenName = wardenName;
    }

    /**
     * @return The wardenMob
     */
    @JsonProperty("warden_mob")
    public String getWardenMob() {
        return wardenMob;
    }

    /**
     * @param wardenMob The warden_mob
     */
    @JsonProperty("warden_mob")
    public void setWardenMob(String wardenMob) {
        this.wardenMob = wardenMob;
    }

    /**
     * @return The caretakerName
     */
    @JsonProperty("caretaker_name")
    public String getCaretakerName() {
        return caretakerName;
    }

    /**
     * @param caretakerName The caretaker_name
     */
    @JsonProperty("caretaker_name")
    public void setCaretakerName(String caretakerName) {
        this.caretakerName = caretakerName;
    }

    /**
     * @return The caretakerNum
     */
    @JsonProperty("caretaker_num")
    public String getCaretakerNum() {
        return caretakerNum;
    }

    /**
     * @param caretakerNum The caretaker_num
     */
    @JsonProperty("caretaker_num")
    public void setCaretakerNum(String caretakerNum) {
        this.caretakerNum = caretakerNum;
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