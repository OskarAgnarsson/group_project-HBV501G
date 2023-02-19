package is.hi.hbv501g.group_project.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {
    private long id;
    private String firstname;
    private String lastname;
    private String email;
}
