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
        "cd",
        "cdname",
        "cname",
        "roll",
        "fname",
        "mname",
        "dob",
        "gender",
        "cat",
        "state",
        "rank",
        "mobile",
        "emailid",
        "ddbank",
        "ddno",
        "dddate",
        "isdd",
        "isfee",
        "isreg"
})
public class Student {

    @JsonProperty("cd")
    private String cd;
    @JsonProperty("cdname")
    private String cdname;
    @JsonProperty("cname")
    private String cname;
    @JsonProperty("roll")
    private String roll;
    @JsonProperty("fname")
    private String fname;
    @JsonProperty("mname")
    private String mname;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("cat")
    private String cat;
    @JsonProperty("state")
    private String state;
    @JsonProperty("rank")
    private Integer rank;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("emailid")
    private String emailid;
    @JsonProperty("ddbank")
    private String ddbank;
    @JsonProperty("ddno")
    private String ddno;
    @JsonProperty("dddate")
    private String dddate;
    @JsonProperty("isdd")
    private Boolean isdd;
    @JsonProperty("isfee")
    private Boolean isfee;
    @JsonProperty("isreg")
    private Boolean isreg;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The cd
     */
    @JsonProperty("cd")
    public String getCd() {
        return cd;
    }

    /**
     *
     * @param cd
     * The cd
     */
    @JsonProperty("cd")
    public void setCd(String cd) {
        this.cd = cd;
    }

    /**
     *
     * @return
     * The cdname
     */
    @JsonProperty("cdname")
    public String getCdname() {
        return cdname;
    }

    /**
     *
     * @param cdname
     * The cdname
     */
    @JsonProperty("cdname")
    public void setCdname(String cdname) {
        this.cdname = cdname;
    }

    /**
     *
     * @return
     * The cname
     */
    @JsonProperty("cname")
    public String getCname() {
        return cname;
    }

    /**
     *
     * @param cname
     * The cname
     */
    @JsonProperty("cname")
    public void setCname(String cname) {
        this.cname = cname;
    }

    /**
     *
     * @return
     * The roll
     */
    @JsonProperty("roll")
    public String getRoll() {
        return roll;
    }

    /**
     *
     * @param roll
     * The roll
     */
    @JsonProperty("roll")
    public void setRoll(String roll) {
        this.roll = roll;
    }

    /**
     *
     * @return
     * The fname
     */
    @JsonProperty("fname")
    public String getFname() {
        return fname;
    }

    /**
     *
     * @param fname
     * The fname
     */
    @JsonProperty("fname")
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     *
     * @return
     * The mname
     */
    @JsonProperty("mname")
    public String getMname() {
        return mname;
    }

    /**
     *
     * @param mname
     * The mname
     */
    @JsonProperty("mname")
    public void setMname(String mname) {
        this.mname = mname;
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
     * The gender
     */
    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The cat
     */
    @JsonProperty("cat")
    public String getCat() {
        return cat;
    }

    /**
     *
     * @param cat
     * The cat
     */
    @JsonProperty("cat")
    public void setCat(String cat) {
        this.cat = cat;
    }

    /**
     *
     * @return
     * The state
     */
    @JsonProperty("state")
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     * The state
     */
    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     * The rank
     */
    @JsonProperty("rank")
    public Integer getRank() {
        return rank;
    }

    /**
     *
     * @param rank
     * The rank
     */
    @JsonProperty("rank")
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     *
     * @return
     * The mobile
     */
    @JsonProperty("mobile")
    public String getMobile() {
        return mobile;
    }

    /**
     *
     * @param mobile
     * The mobile
     */
    @JsonProperty("mobile")
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     *
     * @return
     * The emailid
     */
    @JsonProperty("emailid")
    public String getEmailid() {
        return emailid;
    }

    /**
     *
     * @param emailid
     * The emailid
     */
    @JsonProperty("emailid")
    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    /**
     *
     * @return
     * The ddbank
     */
    @JsonProperty("ddbank")
    public String getDdbank() {
        return ddbank;
    }

    /**
     *
     * @param ddbank
     * The ddbank
     */
    @JsonProperty("ddbank")
    public void setDdbank(String ddbank) {
        this.ddbank = ddbank;
    }

    /**
     *
     * @return
     * The ddno
     */
    @JsonProperty("ddno")
    public String getDdno() {
        return ddno;
    }

    /**
     *
     * @param ddno
     * The ddno
     */
    @JsonProperty("ddno")
    public void setDdno(String ddno) {
        this.ddno = ddno;
    }

    /**
     *
     * @return
     * The dddate
     */
    @JsonProperty("dddate")
    public String getDddate() {
        return dddate;
    }

    /**
     *
     * @param dddate
     * The dddate
     */
    @JsonProperty("dddate")
    public void setDddate(String dddate) {
        this.dddate = dddate;
    }

    /**
     *
     * @return
     * The isdd
     */
    @JsonProperty("isdd")
    public Boolean getIsdd() {
        return isdd;
    }

    /**
     *
     * @param isdd
     * The isdd
     */
    @JsonProperty("isdd")
    public void setIsdd(Boolean isdd) {
        this.isdd = isdd;
    }

    /**
     *
     * @return
     * The isfee
     */
    @JsonProperty("isfee")
    public Boolean getIsfee() {
        return isfee;
    }

    /**
     *
     * @param isfee
     * The isfee
     */
    @JsonProperty("isfee")
    public void setIsfee(Boolean isfee) {
        this.isfee = isfee;
    }

    /**
     *
     * @return
     * The isreg
     */
    @JsonProperty("isreg")
    public Boolean getIsreg() {
        return isreg;
    }

    /**
     *
     * @param isreg
     * The isreg
     */
    @JsonProperty("isreg")
    public void setIsreg(Boolean isreg) {
        this.isreg = isreg;
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
