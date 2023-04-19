package is.hi.hbv501g.group_project.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/***
 * This class implements a request to add a project. The request has a name, and description.
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddProjectRequest {
    private String name;
    private String description;
}
