package is.hi.hbv501g.group_project.registration;

import is.hi.hbv501g.group_project.appuser.AppUser;
import is.hi.hbv501g.group_project.appuser.AppUserRole;
import is.hi.hbv501g.group_project.appuser.AppUserService;
import is.hi.hbv501g.group_project.requests.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/***
 * This class implements a service for the registration of users. The service adds new user to the app user repository.
 */
@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    /***
     * Creates a new account with given information in request and the role of the user.
     * @param request  First name, last name, email, and password.
     */
    public void register(RegistrationRequest request) {
        appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getPhoneNumber(),
                        AppUserRole.USER)
        );
    }
}
