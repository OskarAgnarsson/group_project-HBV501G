package is.hi.hbv501g.group_project.task;


import is.hi.hbv501g.group_project.requests.AddTaskRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/***
 * This class implements a service for the tasks. The service adds tasks to the repository of tasks.
 */
@Service
@AllArgsConstructor
//public interface TaskService {
public class TaskService {


    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    /***
     * Adds a new task to the project.
     * @param request The name, deadline, and owner of the task.
     * @param projectId The ID of the project.
     */
    @SneakyThrows
    public void saveTask(AddTaskRequest request, Long projectId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        taskRepository.save(
                new Task(projectId,
                        request.getName(),
                        request.getDescription(),
                        request.getOwnerUserId(),
                        dateFormat.parse(request.getDeadline()),
                        request.getStatus())
        );
    }
    public void editTask(Task newTask) {
        taskRepository.save(newTask);
    }

    /**
     *
     * @param id the project id
     * @return a list of all tasks belonging to the project
     */
    public List<Task> findByProjectId(Long id){
        return taskRepository.findByProjectId(id);
    }

    /**
     *
     * @param id the task id
     * @return the task with the id
     */
    public Task findByTaskId(long id){
        return taskRepository.findById(id).get();
    }

    /**
     *
     * @param id
     * @return
     */
    public List<Comment> findCommentByTaskId(long id) {
        return commentRepository.findByTaskId(id);
    }

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }
}
