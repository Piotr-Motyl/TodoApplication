package projectTests;

import com.github.piotrmotyl.project.Project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectTest {
    @Test
    public void testUpdateProjectFrom() {
        // Arrange: tworzymy dwa obiekty Project
        Project originalProject = new Project();
        originalProject.setDescription("Old description");

        Project newProject = new Project();
        newProject.setDescription("New description");

        // Act: aktualizujemy projekt
        originalProject.updateProjectFrom(newProject);

        // Assert: sprawdzamy, czy opis projektu został zaktualizowany
        assertEquals("New description", originalProject.getDescription());
    }
}