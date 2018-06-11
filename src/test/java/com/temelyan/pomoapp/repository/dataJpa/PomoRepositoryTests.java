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
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@Sql(statements = {"DELETE FROM POMOS", "DELETE FROM TASKS", "DELETE FROM PROJECTS", "DELETE FROM USERS"})
@ActiveProfiles(resolver = Resolver.class)
@SpringBootTest
public class PomoRepositoryTests {
    private static LocalDateTime localDateTime =
            LocalDateTime.of(2018, Month.JANUARY, 1, 8, 0);
    @Autowired
    private PomoRepository pomoRepository;
    @Autowired
    private UserRepopsitory userRepopsitory;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ProjectRepository projectRepository;

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
    public void test1() {
        User user = new User(null, "test@gmail.com", "password");
        populateWIthFakeData(user);
        resetTimeInstance();
        User user1 = new User(null, "test1@gmail.com", "password");
        populateWIthFakeData(user1);

        List<Pomo> allForUserInDateRange = pomoRepository.getAllForUserInDateRange(
                LocalDate.of(2018, 1, 3),
                LocalDate.of(2018, 1, 6),
                user.getId()
        );

        List<Pomo> allForUser1InDateRange = pomoRepository.getAllForUserInDateRange(
                LocalDate.of(2018, 1, 3),
                LocalDate.of(2018, 1, 6),
                user1.getId()
        );

        Assert.assertEquals(allForUser1InDateRange.size(), 16);

        Assert.assertEquals(
                allForUser1InDateRange.get(0).getTask().getProject().getUser(),
                allForUser1InDateRange.get(new Random().nextInt(16)).getTask().getProject().getUser());

        Assertions.assertThat(
                allForUserInDateRange.get(0).getTask().getProject().getUser())
                .isEqualToIgnoringGivenFields(user, "projects");
    }

    @Test
    public void test2() {
        resetTimeInstance();
        User save = userRepopsitory.save(new User(null, "test@gmail.com", "password"));
        Project work = projectRepository.save(new Project("work"), save.getId());
        Task workTask = taskRepository.save(new Task("work task"), work.getId());
        LocalDateTime nextTimeInstance = getNextTimeInstance();
        List<Pomo> pomos = new ArrayList<>();
        pomos.add(pomoRepository.save(new Pomo(nextTimeInstance, 60), workTask.getId()));
        pomos.add(pomoRepository.save(new Pomo(nextTimeInstance.plusMinutes(1), 60), workTask.getId()));
        List<Pomo> allForUserInDateRange = pomoRepository.getAllForUserInDateRange(
                nextTimeInstance.toLocalDate(),
                nextTimeInstance.toLocalDate(),
                save.getId());

        pomos.sort(Comparator.comparing(Pomo::getFinish).reversed());

        Assertions.assertThat(pomos).isEqualTo(allForUserInDateRange);
    }
}
