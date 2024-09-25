package taskTests;

import com.github.piotrmotyl.TodoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TodoApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("Integration")
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createAndReadTask() throws Exception {
        // Act: stworzenie nowego zadania przez POST
        String newTaskJson = "{\"description\":\"Integration test task\", \"taskDone\":false}";

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTaskJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Integration test task"));

        // Act: odczytanie wszystkich zadań przez GET
        mockMvc.perform(get("/tasks/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Integration test task"));
    }
}