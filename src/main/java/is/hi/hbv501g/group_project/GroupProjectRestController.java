package is.hi.hbv501g.group_project;

import is.hi.hbv501g.group_project.project.Project;
import is.hi.hbv501g.group_project.requests.AddProjectRequest;
import is.hi.hbv501g.group_project.responses.UserInfoResponse;
import is.hi.hbv501g.group_project.requests.RegistrationRequest;
import is.hi.hbv501g.group_project.responses.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import is.hi.hbv501g.group_project.requests.LoginRequest;
import is.hi.hbv501g.group_project.appuser.AppUser;
import is.hi.hbv501g.group_project.appuser.AppUserService;
import is.hi.hbv501g.group_project.task.TaskService;
import is.hi.hbv501g.group_project.registration.RegistrationService;
import is.hi.hbv501g.group_project.project.ProjectMembersService;
import is.hi.hbv501g.group_project.project.ProjectService;

import java.util.List;

@RestController
public class GroupProjectRestController {
    @Autowired
    AuthenticationManager authenticationManager;

    private RegistrationService registrationService;
    private AppUserService appUserService;
    private ProjectService projectService;
    private TaskService taskService;
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

    @GetMapping("/api/projects")
    public List<Project> getProjects(@RequestParam long userId) {
        return projectService.findByUserId(userId);
    }

    @PostMapping("/api/addproject")
    public boolean addProject(@RequestBody AddProjectRequest addProjectRequest, @RequestParam long userId) {
        AppUser user = appUserService.findById(userId);
        projectService.addProject(addProjectRequest,user);
        return true;
    }


}
