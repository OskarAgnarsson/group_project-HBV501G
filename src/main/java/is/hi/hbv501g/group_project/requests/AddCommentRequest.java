package is.hi.hbv501g.group_project.requests;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddCommentRequest {
    private long taskId;
    private String commenter;
    private String text;
}
