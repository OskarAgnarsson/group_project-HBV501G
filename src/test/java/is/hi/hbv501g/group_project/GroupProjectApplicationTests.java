package is.hi.hbv501g.group_project;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AppUserRestControllerTest.class,
        ProjectRestControllerTest.class,
        TaskRestControllerTest.class
})
class GroupProjectApplicationTests {

}
