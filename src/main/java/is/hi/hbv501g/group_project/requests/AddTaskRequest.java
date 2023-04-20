package is.hi.hbv501g.group_project.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/***
 * This class implements a request to add a task to a project. The request has a name, status of task, start time, and deadline.
 */
@Getter
@EqualsAndHashCode
@ToString
public class AddTaskRequest {
    private String name;
    private String deadline;
    private Long ownerUserId;
    private String status;

    @JsonCreator
    public AddTaskRequest(@JsonProperty("name") String name, @JsonProperty("deadline") String deadline, @JsonProperty("ownerUserId") long ownerUserId, @JsonProperty("status") String status) {
        this.name = name;
        this.deadline = deadline;
        this.ownerUserId = ownerUserId;
        this.status = status;
    }
}
