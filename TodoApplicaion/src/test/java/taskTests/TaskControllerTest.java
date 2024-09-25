package taskTests;

import com.github.piotrmotyl.task.Task;
import com.github.piotrmotyl.task.TaskController;
import com.github.piotrmotyl.task.TaskRepository;
import com.github.piotrmotyl.task.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskControllerTest {

    @Test
    public void testReadTaskSuccess() {

        // Arrange: mockowanie repozytorium
        TaskRepository mockRepo = mock(TaskRepository.class);
        TaskService mockService = mock(TaskService.class);
        TaskController taskController = new TaskController(mockRepo, null, mockService);
        Task task = new Task();
        task.setId(1);
        task.setDescription("Test task");

        when(mockRepo.findById(1)).thenReturn(Optional.of(task));

        // Act: wywołanie metody
        ResponseEntity<Task> response = taskController.readTask(1);

        // Assert: sprawdzenie odpowiedzi
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test task", Objects.requireNonNull(response.getBody()).getDescription());
    }

    @Test
    public void testReadTaskNotFound() {
        // Arrange: mockowanie repozytorium
        TaskRepository mockRepo = mock(TaskRepository.class);
        TaskService mockService = mock(TaskService.class);
        TaskController taskController = new TaskController(mockRepo, null, mockService);

        when(mockRepo.findById(1)).thenReturn(Optional.empty());

        // Act: wywołanie metody
        ResponseEntity<Task> response = taskController.readTask(1);

        // Assert: sprawdzenie odpowiedzi
        assertEquals(404, response.getStatusCodeValue());
    }


}
