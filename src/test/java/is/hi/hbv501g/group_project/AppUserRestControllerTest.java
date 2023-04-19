package is.hi.hbv501g.group_project;

import is.hi.hbv501g.group_project.appuser.AppUser;
import is.hi.hbv501g.group_project.requests.AddProjectMemberRequest;
import is.hi.hbv501g.group_project.requests.LoginRequest;
import is.hi.hbv501g.group_project.requests.RegistrationRequest;
import is.hi.hbv501g.group_project.responses.AppUserResponse;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

public class AppUserRestControllerTest extends AbstractTest{
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testAuthUser() throws Exception {
        String uri = "/api/login";
        LoginRequest loginRequest = new LoginRequest("osa16@hi.is","lollypop");
        String inputJson = super.mapToJson(loginRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "{\"id\":1,\"firstname\":\"Ã\u0093skar\",\"lastname\":\"Agnarsson\",\"email\":\"osa16@hi.is\"}");
    }

    @Test
    public void testRegistration() throws Exception {
        String uri = "/api/register";
        RegistrationRequest registrationRequest = new RegistrationRequest("Oskar","Agnarsson","lol","oskaragnarson@gmail.com");
        String inputJson = super.mapToJson(registrationRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400,status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content,"{\"message\":\"Error: Email is already taken\"}");
    }

    @Test
    public void testGetUser() throws Exception {
        String uri = "/api/user?userId=1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        String content = mvcResult.getResponse().getContentAsString();
        AppUserResponse user = super.mapFromJson(content, AppUserResponse.class);
        assertEquals(user.getLastName(),"Agnarsson");
    }

    @Test
    public void testGetUserNotExists() throws Exception {
        String uri = "/api/user?userId=2000";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404,status);
    }

    @Test
    public void testGetProjectMembers() throws Exception {
        String uri = "/api/projectmembers/1";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        AppUserResponse[] users = super.mapFromJson(content,AppUserResponse[].class);
        assertTrue(users.length > 0);
    }

    @Test
    public void testAddProjectMember() throws Exception {
        String uri = "/api/addprojectmember/1";
        AddProjectMemberRequest addProjectMemberRequest = new AddProjectMemberRequest("oskaragnarson@gmail.com");
        String inputJson = super.mapToJson(addProjectMemberRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content,"true");
    }
}
