package projectTests;

import com.github.piotrmotyl.project.Project;
import com.github.piotrmotyl.project.ProjectController;
import com.github.piotrmotyl.project.ProjectRepository;
import com.github.piotrmotyl.project.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProjectControllerTest {

    @Test
    public void testReadProjectSuccess() {
        // Arrange: mockujemy repozytorium i tworzymy kontroler
        ProjectRepository mockRepo = mock(ProjectRepository.class);
        ProjectService mockService = mock(ProjectService.class);
        ProjectController projectController = new ProjectController(mockService, mockRepo, null);

        Project project = new Project();
        project.setDescription("Test Project");

        when(mockRepo.findById(1)).thenReturn(Optional.of(project));

        // Act: wywołujemy metodę
        ResponseEntity<Project> response = projectController.readProject(1);

        // Assert: sprawdzamy odpowiedź
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test Project", Objects.requireNonNull(response.getBody()).getDescription());
    }

    @Test
    public void testReadProjectNotFound() {
        // Arrange: mockujemy repozytorium
        ProjectRepository mockRepo = mock(ProjectRepository.class);
        ProjectService mockService = mock(ProjectService.class);
        ProjectController projectController = new ProjectController(mockService, mockRepo, null);

        when(mockRepo.findById(1)).thenReturn(Optional.empty());

        // Act: wywołujemy metodę
        ResponseEntity<Project> response = projectController.readProject(1);

        // Assert: sprawdzamy odpowiedź
        assertEquals(404, response.getStatusCodeValue());
    }

}
