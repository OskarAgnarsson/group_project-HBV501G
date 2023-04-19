package is.hi.hbv501g.group_project;

import is.hi.hbv501g.group_project.project.Project;
import is.hi.hbv501g.group_project.requests.AddProjectRequest;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class ProjectRestControllerTest extends AbstractTest {

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testGetProjectsNotEmpty() throws Exception {
        String uri = "/api/projects/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        Project[] projects = super.mapFromJson(content,Project[].class);
        assertTrue(projects.length > 0);
    }

    @Test
    public void testGetProjectsEmpty() throws Exception {
        String uri = "/api/projects/4";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        Project[] projects = super.mapFromJson(content,Project[].class);
        assertEquals(0, projects.length);
    }

    @Test
    public void testGetProjectsUserNotExists() throws Exception {
        String uri = "/api/projects/20";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404,status);
    }

    @Test
    public void testAddProject() throws Exception {
        String uri = "/api/addproject/1";
        AddProjectRequest addProjectRequest = new AddProjectRequest("yes","no");
        String inputJson = super.mapToJson(addProjectRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content,"true");
    }
}
