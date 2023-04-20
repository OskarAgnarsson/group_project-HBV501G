package is.hi.hbv501g.group_project.appuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.validation.constraints.Email;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

/***
 * This class implements a user for the app. The User has an ID, first name, last name, email, password, and role.
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table
@AllArgsConstructor
public class AppUser implements UserDetails {
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @JsonProperty
    private Long id;
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String email;
    @JsonProperty
    private String password;
    private int phoneNumber;
    @Enumerated(EnumType.STRING)
    @JsonProperty
    private AppUserRole appUserRole;
    @JsonProperty
    private Boolean locked = false;
    @JsonProperty
    private Boolean enabled = true;

    /***
     * This Constructs an app user with a specific first name, last name, email, password, and user role.
     * @param firstName First name of the user
     * @param lastName Last name of the user
     * @param email The user's email which also identifies the user
     * @param password A password for the user's account
     * @param appUserRole The user's role which signifies the access available to the user.
     */
    public AppUser(String firstName, String lastName, String email, String password,int phoneNumber, AppUserRole appUserRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.appUserRole = appUserRole;
    }

    /***
     * Grants access if the user's role is deemed correct.
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getId(){
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
