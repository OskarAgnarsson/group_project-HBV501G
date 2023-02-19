package is.hi.hbv501g.group_project.project;

import is.hi.hbv501g.group_project.appuser.AppUser;
import is.hi.hbv501g.group_project.appuser.AppUserEmail;
import is.hi.hbv501g.group_project.appuser.AppUserRepository;
import is.hi.hbv501g.group_project.appuser.AppUserService;
import is.hi.hbv501g.group_project.requests.AddProjectMemberRequest;
import is.hi.hbv501g.group_project.security.config.CustomPermissionEvaluatorService;
import is.hi.hbv501g.group_project.task.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/***
 * This class implements a controller for the project view and holds the Model and View.
 */
@RestController
@AllArgsConstructor
public class ProjectViewController {

    //Logger logger = LoggerFactory.getLogger(ProjectViewController.class);

    private final ProjectService projectService;
    private final AppUserRepository appUserRepository;
    private final ProjectMembersService projectMembersService;
    private final TaskService taskService;
    private final CustomPermissionEvaluatorService customPermissionEvaluatorService;
    private final AppUserService userService;

    /***
     * Model and View to display projects by ID.
     * @param id The ID of the project.
     * @return
     */
    @RequestMapping(value = {"/{projectId}"}, method = RequestMethod.GET)
    public ModelAndView showProject(@PathVariable("projectId") long id) {
        ModelAndView modelAndView = new ModelAndView();
        if (!customPermissionEvaluatorService.hasPermission(SecurityContextHolder.getContext().getAuthentication(), id)){
            modelAndView.setViewName("notAuthorized");
        }
        else {

            modelAndView.setViewName("project");
            modelAndView.addObject("project", projectService.findByProjectId(id));
            modelAndView.addObject("tasks", taskService.findByProjectId(id));
        }
        return modelAndView;
    }

    /***
     * Model and View to be able to add members to projects.
     * @param id The ID of the project.
     * @return
     */
    @RequestMapping(value = {"/{projectId}/addMemberToProject"}, method = RequestMethod.GET)
    public ModelAndView addMemberToProject(@PathVariable("projectId") long id){
        ModelAndView modelAndView = new ModelAndView();
        if (!customPermissionEvaluatorService.hasPermission(SecurityContextHolder.getContext().getAuthentication(), id)){
            modelAndView.setViewName("notAuthorized");
        }
        else {
            AppUserEmail email = new AppUserEmail();
            modelAndView.addObject("email", email);
            modelAndView.addObject("project", projectService.findByProjectId(id));
            modelAndView.setViewName("addMemberToProject"); // resources/template/register.html
        }
        return modelAndView;
    }

    /***
     * Adds a member to project if email is valid and member does not already have access to the project. Otherwise,
     * displays error message.
     * @param projectId The ID of the project.
     * @param request Email of the member that is to be added to the project.
     * @param bindingResult Details
     * @param modelMap
     * @return
     */
    @RequestMapping(value = {"/{projectId}/addMemberToProject"}, method = RequestMethod.POST)
    public ModelAndView addMemberToProject(@PathVariable("projectId") long projectId, AddProjectMemberRequest request, BindingResult bindingResult, ModelMap modelMap){
        ModelAndView modelAndView = new ModelAndView();
        boolean userExists = appUserRepository.findByEmail(request.getEmail()).isPresent();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("project", projectService.findByProjectId(projectId));
        AppUser user = (AppUser) authentication.getPrincipal();
        List <AppUser> users = projectMembersService.findMembersByProjectId(projectId);
        List <String> emails = userService.getEmailsByUser(users);
        if(request.getEmail().equals("")){
            modelAndView.addObject("successMessage", "Please enter the email of the user you wish to add" + request.getEmail());
        }
        else if (!userExists) {
            modelAndView.addObject("successMessage", "No user with email: " + request.getEmail());
        }
        else if(emails.contains(request.getEmail())){
            modelAndView.addObject("successMessage", "User " + request.getEmail() + " already has access to this project");
        }
        else if(bindingResult.hasErrors()){
            modelAndView.addObject("successMessage", "Please add correct details!");
            modelMap.addAttribute("bindingResult", bindingResult);
        }
        else {
            projectMembersService.addMember(new ProjectMembers(
                    projectId,
                    ((AppUser) appUserRepository.findByEmail(request.getEmail()).get()).getId(),
                    "Contributor"
            ));
            modelAndView.addObject("successMessage", "User added to project!");
        }
        modelAndView.addObject("email", new AppUserEmail());
        modelAndView.setViewName("addMemberToProject");
        return modelAndView;
    }


}
