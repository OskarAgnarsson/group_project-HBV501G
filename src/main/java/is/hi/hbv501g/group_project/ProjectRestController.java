package is.hi.hbv501g.group_project;

import is.hi.hbv501g.group_project.appuser.AppUser;
import is.hi.hbv501g.group_project.appuser.AppUserService;
import is.hi.hbv501g.group_project.project.Project;
import is.hi.hbv501g.group_project.project.ProjectService;
import is.hi.hbv501g.group_project.requests.AddProjectRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProjectRestController {

    private AppUserService appUserService;
    private ProjectService projectService;

    @GetMapping("/api/projects/{id}")
    public List<Project> getProjects(@PathVariable("id") long userId) {
        return projectService.findByUserId(userId);
    }

    @PostMapping("/api/addproject/{id}")
    public boolean addProject(@RequestBody AddProjectRequest addProjectRequest, @PathVariable("id") long userId) {
        AppUser user = appUserService.findById(userId);
        projectService.addProject(addProjectRequest,user);
        return true;
    }
}
