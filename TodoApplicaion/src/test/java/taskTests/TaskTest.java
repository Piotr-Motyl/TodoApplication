package taskTests;

import com.github.piotrmotyl.task.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TaskTest {

    @Test
    public void testUpdateTaskFrom() {
        //Arrange: stworzenie dwóch obiektów
        Task originalTask = new Task();
        originalTask.setDescription("Old description");
        originalTask.setTaskDone(false);
        originalTask.setTaskDeadline(LocalDateTime.of(2024, 10, 10, 10, 10));

        Task newTask = new Task();
        newTask.setDescription("New description");
        newTask.setTaskDone(true);
        newTask.setTaskDeadline(LocalDateTime.of(2024, 1, 1, 1, 1));

        //Act: zaktualizowanie oryginalnego zadania za pomoca nowego
        originalTask.updateTaskFrom(newTask);

        //Assert:sprawdzenie czy wartośći zostały poprawnie zaktualizowane
        assertEquals("New description", originalTask.getDescription());
        assertTrue(originalTask.getTaskDone());
        assertEquals(LocalDateTime.of(2024, 1, 1, 1, 1), originalTask.getTaskDeadline());

    }
}
