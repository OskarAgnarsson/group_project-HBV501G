package is.hi.hbv501g.group_project.requests;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class AddCommentRequest {
    private long taskId;
    private String commenter;
    private String text;

    @JsonCreator
    public AddCommentRequest(@JsonProperty("taskId") long taskId, @JsonProperty("commenter") String commenter, @JsonProperty("text") String text) {
        this.taskId = taskId;
        this.commenter = commenter;
        this.text = text;
    }
}
