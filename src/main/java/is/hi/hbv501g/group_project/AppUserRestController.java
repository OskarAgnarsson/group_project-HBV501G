package is.hi.hbv501g.group_project;

import java.util.ArrayList;
import is.hi.hbv501g.group_project.appuser.AppUser;
import is.hi.hbv501g.group_project.appuser.AppUserService;
import is.hi.hbv501g.group_project.project.ProjectMembers;
import is.hi.hbv501g.group_project.project.ProjectMembersService;
import is.hi.hbv501g.group_project.registration.RegistrationService;
import is.hi.hbv501g.group_project.requests.AddProjectMemberRequest;
import is.hi.hbv501g.group_project.requests.LoginRequest;
import is.hi.hbv501g.group_project.requests.RegistrationRequest;
import is.hi.hbv501g.group_project.responses.AppUserResponse;
import is.hi.hbv501g.group_project.responses.MessageResponse;
import is.hi.hbv501g.group_project.responses.UserInfoResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class AppUserRestController {

    @Autowired
    AuthenticationManager authenticationManager;

    private RegistrationService registrationService;
    private AppUserService appUserService;
    private ProjectMembersService projectMembersService;

    @PostMapping("/api/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUser appUser = (AppUser) authentication.getPrincipal();

        return ResponseEntity.ok()
                .body(new UserInfoResponse(appUser.getId(),appUser.getFirstName(), appUser.getLastName(), appUser.getEmail()));
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        if (appUserService.existsByEmail(registrationRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken"));
        }

        registrationService.register(registrationRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @GetMapping("/api/user/{id}")
    public AppUserResponse getUser(@PathVariable("id") long userId) {
        AppUser user = appUserService.findById(userId);
        return new AppUserResponse(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail());
    }

    @GetMapping("/api/user")
    public AppUserResponse getUserByEmail(@RequestBody AddProjectMemberRequest addProjectMemberRequest) {
        AppUser user = appUserService.findByEmail(addProjectMemberRequest.getEmail()).get();
        return new AppUserResponse(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail());
    }

    @GetMapping("/api/projectmembers/{id}")
    public List<AppUserResponse> getProjectMembers(@PathVariable("id") long projectId) {
        List<AppUserResponse> responses = new ArrayList<>();
        List<AppUser> userList = projectMembersService.findMembersByProjectId(projectId);
        for (AppUser a: userList) {
            responses.add(new AppUserResponse(a.getId(),a.getFirstName(),a.getLastName(),a.getEmail()));
        }
        return responses;
    }

    @PostMapping("/api/addprojectmember/{id}")
    public boolean addProjectMember(@RequestBody AddProjectMemberRequest addProjectMemberRequest, @PathVariable("id") long projectId) {
        projectMembersService.addMember(new ProjectMembers(
                projectId,
                ((AppUser) appUserService.findByEmail(addProjectMemberRequest.getEmail()).get()).getId(),
                "Contributor"
        ));
        return true;
    }
}
