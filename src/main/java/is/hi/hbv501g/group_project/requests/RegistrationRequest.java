package is.hi.hbv501g.group_project.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/***
 * This class implements a request to register a new user. The request has a first name, last name, password, and email.
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
