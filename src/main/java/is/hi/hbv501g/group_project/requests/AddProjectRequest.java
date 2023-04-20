package is.hi.hbv501g.group_project.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/***
 * This class implements a request to add a project. The request has a name, and description.
 */
@Getter
@EqualsAndHashCode
@ToString
public class AddProjectRequest {
    private String name;
    private String description;

    @JsonCreator
    public AddProjectRequest(@JsonProperty("name") String name, @JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
    }
}
