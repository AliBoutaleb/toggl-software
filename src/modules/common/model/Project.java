package modules.common.model;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Project {

    @JsonProperty("_id")
    String id;
    @JsonProperty("title")
    String title;
    @JsonProperty("creator")
    String creator;
    @JsonProperty("status")
    boolean status;
    @JsonProperty("members")
    String[] members;
    @JsonProperty("tasks")
    String[] tasks;

}
