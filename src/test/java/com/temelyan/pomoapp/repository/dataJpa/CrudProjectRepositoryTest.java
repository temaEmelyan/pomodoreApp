package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.Resolver;
import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.model.Task;
import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.repository.PomoRepository;
import com.temelyan.pomoapp.repository.ProjectRepository;
import com.temelyan.pomoapp.repository.TaskRepository;
import com.temelyan.pomoapp.repository.UserRepopsitory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@Sql("/db/drop_db.sql")
@ActiveProfiles(resolver = Resolver.class)
@SpringBootTest
public class CrudProjectRepositoryTest {
    private static LocalDateTime localDateTime =
            LocalDateTime.of(2018, Month.JANUARY, 1, 8, 0);
    @Autowired
    CrudProjectRepository crudProjectRepository;

    @Autowired
    UserRepopsitory userRepopsitory;
    @Autowired
    PomoRepository pomoRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TaskRepository taskRepository;

    private static void resetTimeInstance() {
        localDateTime =
                LocalDateTime.of(2018, Month.JANUARY, 1, 8, 0);
    }

    private static LocalDateTime getNextTimeInstance() {
        localDateTime = localDateTime.plus(6, ChronoUnit.HOURS);
        return localDateTime;
    }

    private void populateWIthFakeData(User user) {
        userRepopsitory.save(user);

        Project project1 = new Project("work");
        Project project2 = new Project("phd");

        project1 = projectRepository.save(project1, user.getId());
        project2 = projectRepository.save(project2, user.getId());

        Task task1 = new Task("work task 1", project1);
        Task task2 = new Task("work task 2", project1);
        Task task3 = new Task("work task 3", project1);
        Task task4 = new Task("phd task 1", project2);

        List<Task> tasks = Arrays.asList(task1, task2, task3, task4);

        List<Task> tasksWithIds = tasks.stream()
                .map(task -> taskRepository.save(task))
                .collect(Collectors.toList());

        tasksWithIds
                .forEach(task -> {
                    for (int i = 0; i < 10; i++) {
                        pomoRepository.save(new Pomo(getNextTimeInstance(), 60 * 25), task.getId());
                    }
                });
    }

    @Test
    public void test_GetAllForUserWithTasks() {
        User user = new User(null, "test@gmail.com", "password");
        populateWIthFakeData(user);
        resetTimeInstance();
        User user1 = new User(null, "test1@gmail.com", "password");
        populateWIthFakeData(user1);

        Set<Project> allPomos = crudProjectRepository.findAllByUserIdFetchWithTasksAndPomosInDateRange(
                user.getId(),
                LocalDateTime.of(2018, Month.JANUARY, 1, 8, 0),
                LocalDateTime.of(2018, Month.APRIL, 1, 8, 0));
        Assert.assertTrue(allPomos.size() > 0);
    }
}