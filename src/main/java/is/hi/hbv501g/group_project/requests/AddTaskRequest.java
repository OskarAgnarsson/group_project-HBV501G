package is.hi.hbv501g.group_project.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/***
 * This class implements a request to add a task to a project. The request has a name, status of task, start time, and deadline.
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddTaskRequest {
    private String name;
    private String deadline;
    private Long ownerUserId;
    private String status;
}
