package is.hi.hbv501g.group_project;

import is.hi.hbv501g.group_project.requests.AddCommentRequest;
import is.hi.hbv501g.group_project.requests.AddTaskRequest;
import is.hi.hbv501g.group_project.task.Comment;
import is.hi.hbv501g.group_project.task.Task;
import is.hi.hbv501g.group_project.task.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
public class TaskRestController {

    private TaskService taskService;

    @GetMapping("/api/tasks/{id}")
    public List<Task> getTasks(@PathVariable("id") long projectId) {
        return taskService.findByProjectId(projectId);
    }

    @PostMapping("/api/addtask/{id}")
    public boolean addTask(@RequestBody AddTaskRequest addTaskRequest, @PathVariable("id") long projectId) {
        taskService.saveTask(addTaskRequest,projectId);
        return true;
    }

    @GetMapping("/api/comments/{id}")
    public List<Comment> getComments(@PathVariable("id") long taskId) {
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
