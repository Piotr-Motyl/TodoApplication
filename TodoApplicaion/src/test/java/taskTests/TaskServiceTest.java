package taskTests;

import com.github.piotrmotyl.task.Task;
import com.github.piotrmotyl.task.TaskRepository;
import com.github.piotrmotyl.task.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TaskServiceTest {

    @Test
    public void testSaveTask() {

        //Arrange: mockowanie repo i stworzenie servisu
        TaskRepository mockRepo = Mockito.mock(TaskRepository.class);
        TaskService taskService = new TaskService(mockRepo);
        Task task = new Task();

        task.setDescription("Test Task");

        //Act: wywolanie metody save
        taskService.save(task);

        //Assert: werydikacja czy metoda save w repo została wywolana
        verify(mockRepo, times(1)).save(task);
    }


}