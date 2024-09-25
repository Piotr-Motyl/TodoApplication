package projectTests;

import com.github.piotrmotyl.TodoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = TodoApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("Integration")
public class ProjectControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createAndReadProject() throws Exception {
        // Act: stworzenie nowego projektu przez POST
        String newProjectJson = "{\"description\":\"Integration test project\", \"projectDone\":false}";

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProjectJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Integration test project"));

        // Act: odczytanie wszystkich projektów przez GET
        mockMvc.perform(get("/projects/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].description", hasItem("Integration test project")));
    }
}