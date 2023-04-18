package is.hi.hbv501g.group_project;

import is.hi.hbv501g.group_project.project.Project;
import is.hi.hbv501g.group_project.project.ProjectMembers;
import is.hi.hbv501g.group_project.requests.*;
import is.hi.hbv501g.group_project.responses.UserInfoResponse;
import is.hi.hbv501g.group_project.responses.MessageResponse;
import is.hi.hbv501g.group_project.task.Comment;
import is.hi.hbv501g.group_project.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/user")
    public AppUser getUser(@RequestParam long userId) {
        return appUserService.findById(userId);
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

    @GetMapping("/api/tasks")
    public List<Task> getTasks(@RequestParam long projectId) {
        return taskService.findByProjectId(projectId);
    }

    @PostMapping("/api/addtask")
    public boolean addTask(@RequestBody AddTaskRequest addTaskRequest, @RequestParam long projectId) {
        taskService.saveTask(addTaskRequest,projectId);
        return true;
    }

    @GetMapping("/api/projectmembers")
    public List<AppUser> getProjectMembers(@RequestParam long projectId) {
        return projectMembersService.findMembersByProjectId(projectId);
    }

    @PostMapping("/api/addprojectmember")
    public boolean addTask(@RequestBody AddProjectMemberRequest addProjectMemberRequest, @RequestParam long projectId) {
        projectMembersService.addMember(new ProjectMembers(
                projectId,
                ((AppUser) appUserService.findByEmail(addProjectMemberRequest.getEmail()).get()).getId(),
                "Contributor"
        ));
        return true;
    }

    @GetMapping("/api/comments")
    public List<Comment> getComments(@RequestParam long taskId) {
        return taskService.findCommentByTaskId(taskId);
    }

    @PostMapping("/api/addcomment")
    public boolean addComment(@RequestBody AddCommentRequest addCommentRequest) {
        taskService.saveComment(new Comment(
                addCommentRequest.getTaskId(),
                addCommentRequest.getCommenter(),
                addCommentRequest.getText()
        ));
        return true;
    }


}
