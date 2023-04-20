package is.hi.hbv501g.group_project.task;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;

/***
 * This class implements a task for the projects. The task has an ID, project ID, name, user ID, starting time, deadline, and task status.
 */
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long projectId;
    private String name;
    private String description;
    private Long ownerUserId;
    private Date deadline;
    private String status;

    public Task(){
    }

    /***
     * This Constructs a task with a specific ID, project ID, name, user ID, start time, deadline, and status of the task.
     * @param projectId The ID of the project.
     * @param name The name of the task.
     * @param ownerUserId The ID of the user.
     * @param deadline The deadline for the task
     * @param status The status for the task.
     */
    public Task(long projectId, String name, String description, Long ownerUserId, Date deadline, String status) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.ownerUserId = ownerUserId;
        this.deadline = deadline;
        this.status = status;
    }

    @JsonCreator
    public Task(@JsonProperty("id") long id,
                @JsonProperty("projectId") long projectId,
                @JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("deadline") String deadline,
                @JsonProperty("ownerUserId") long ownerId,
                @JsonProperty("status") String status) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.deadline = Date.from(LocalDate.parse(deadline).atStartOfDay().toInstant(ZoneOffset.UTC));
        this.ownerUserId = ownerId;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long userId) {
        this.ownerUserId = userId;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
