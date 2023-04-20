package is.hi.hbv501g.group_project;

import is.hi.hbv501g.group_project.requests.AddCommentRequest;
import is.hi.hbv501g.group_project.requests.AddTaskRequest;
import is.hi.hbv501g.group_project.task.Comment;
import is.hi.hbv501g.group_project.task.Task;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.thymeleaf.spring5.expression.Mvc;

public class TaskRestControllerTest extends AbstractTest{
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testGetTasksNotEmpty() throws Exception {
        String uri = "/api/tasks/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        Task[] tasks = super.mapFromJson(content,Task[].class);
        assertTrue(tasks.length > 0);
    }

    @Test
    public void testGetTasksEmpty() throws Exception {
        String uri = "/api/tasks/4";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        Task[] tasks = super.mapFromJson(content,Task[].class);
        assertEquals(0, tasks.length);
    }

    @Test
    public void testGetTasksProjectNotExists() throws Exception {
        String uri = "/api/tasks/2000";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404,status);
    }

    @Test
    public void testAddTask() throws Exception {
        String uri = "/api/addtask/1";
        AddTaskRequest addTaskRequest = new AddTaskRequest("do thing","yesm","2023-12-24",1L,"Not Real");
        String inputJson = super.mapToJson(addTaskRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content,"true");
    }

    @Test
    public void testGetCommentsNotEmpty() throws Exception {
        String uri = "/api/comments/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        Comment[] comments = super.mapFromJson(content, Comment[].class);
        assertTrue(comments.length > 0);
    }

    @Test
    public void testGetCommentsEmpty() throws Exception {
        String uri = "/api/comments/5";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        Comment[] comments = super.mapFromJson(content, Comment[].class);
        assertEquals(0, comments.length);
    }

    @Test
    public void testGetCommentsTaskNotExists() throws Exception {
        String uri = "/api/comments/2000";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404,status);
    }


    @Test
    public void testAddComment() throws Exception {
        String uri = "/api/addcomment";
        AddCommentRequest addCommentRequest = new AddCommentRequest(1L,"Tester","Yessir");
        String inputJson = super.mapToJson(addCommentRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content,"true");
    }
}
