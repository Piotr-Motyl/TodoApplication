package projectTests;

import com.github.piotrmotyl.project.Project;
import com.github.piotrmotyl.project.ProjectRepository;
import com.github.piotrmotyl.project.ProjectService;
import com.github.piotrmotyl.task.Task;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectServiceTest {

    @Test
    public void testAddTaskToProject() {
        // Arrange: mockujemy repozytorium i tworzymy serwis
        ProjectRepository mockRepo = Mockito.mock(ProjectRepository.class);
        ProjectService projectService = new ProjectService(mockRepo);

        Project project = new Project();
        when(mockRepo.findById(1)).thenReturn(Optional.of(project));

        Task task = new Task();
        task.setDescription("New Task");

        // Act: wywołujemy metodę dodania zadania do projektu
        projectService.addTaskToProject(1, task);

        // Assert: sprawdzamy, czy zadanie zostało dodane do projektu i czy projekt został zapisany
        assertTrue(project.getTasks().contains(task));
        verify(mockRepo).save(project);
    }
}
