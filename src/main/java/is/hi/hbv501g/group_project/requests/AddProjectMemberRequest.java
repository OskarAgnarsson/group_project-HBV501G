package is.hi.hbv501g.group_project.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/***
 * This class implements a request to add a member to a project. The request has only an email.
 */
@Getter
@EqualsAndHashCode
@ToString
public class AddProjectMemberRequest {
    private String email;

    @JsonCreator
    public AddProjectMemberRequest(@JsonProperty("email") String email) {
        this.email = email;
    }
}
