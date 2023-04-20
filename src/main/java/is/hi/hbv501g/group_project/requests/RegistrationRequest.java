package is.hi.hbv501g.group_project.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/***
 * This class implements a request to register a new user. The request has a first name, last name, password, and email.
 */
@Getter
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private int phoneNumber;

    @JsonCreator
    public RegistrationRequest(@JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName, @JsonProperty("password") String password, @JsonProperty("email") String email, @JsonProperty("phoneNumber") int phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
