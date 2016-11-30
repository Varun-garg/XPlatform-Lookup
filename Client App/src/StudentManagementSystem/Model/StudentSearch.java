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
        "search"
})
public class StudentSearch {

    @JsonProperty("search")
    private List<Student> search = new ArrayList<Student>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The search
     */
    @JsonProperty("search")
    public List<Student> getSearch() {
        return search;
    }

    /**
     * @param search The search
     */
    @JsonProperty("search")
    public void setSearch(List<Student> search) {
        this.search = search;
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