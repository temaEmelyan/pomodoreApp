package com.temelyan.pomoapp.web.pomo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.temelyan.pomoapp.AuthorizedUser;
import com.temelyan.pomoapp.Resolver;
import com.temelyan.pomoapp.WebApplication;
import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.model.Task;
import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.repository.PomoRepository;
import com.temelyan.pomoapp.repository.ProjectRepository;
import com.temelyan.pomoapp.repository.TaskRepository;
import com.temelyan.pomoapp.repository.UserRepopsitory;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SuppressWarnings({"unused", "Duplicates"})
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WebApplication.class)
@SpringBootTest
@ActiveProfiles(resolver = Resolver.class)
@Sql("/db/drop_db.sql")
public class PomoRestControllerTests {

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
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private Filter springSecurityFilterChain;
    private MockMvc mvc;

    private static void resetTimeInstance() {
        localDateTime =
                LocalDateTime.of(2018, Month.JANUARY, 1, 8, 0);
    }

    private static LocalDateTime getNextTimeInstance() {
        localDateTime = localDateTime.plus(6, ChronoUnit.HOURS);
        return localDateTime;
    }

    private static <T> T fromJSON(final TypeReference<T> type,
                                  final String jsonPacket) {
        T data = null;
        try {
            data = new ObjectMapper()
                    .registerModule(new Jdk8Module())
                    .registerModule(new JavaTimeModule())
                    .readValue(jsonPacket, type);
        } catch (Exception ignored) {
        }
        return data;
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

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();

        User user = new User(null, "test@gmail.com", "password");
        populateWIthFakeData(user);
        resetTimeInstance();
        User user1 = new User(null, "test1@gmail.com", "password");
        populateWIthFakeData(user1);
    }

    @Test
    public void fetchUserWithPomosFromController_andSeeWhatIsUpWithJsonSerialization() throws Exception {
        User byEmail = userRepopsitory.getByEmail("test@gmail.com");
        String from = "2018-01-03";
        String until = "2018-04-11";
        String contentAsString = mvc.perform(get("/api/pomo/get?from=" + from + "&to=" + until)
                .with(csrf())
                .with(authentication(
                        new UsernamePasswordAuthenticationToken(
                                new AuthorizedUser(byEmail),
                                null,
                                new ArrayList<>()))))
                .andReturn().getResponse().getContentAsString();

        Set<Project> fromDb = projectRepository.getAllForUserWithTasksAndPomos(
                byEmail.getId(),
                LocalDateTime.of(LocalDate.parse(from), LocalTime.MIN),
                LocalDateTime.of(LocalDate.parse(until), LocalTime.MAX));

        Set<Project> fromJSON = fromJSON(new TypeReference<Set<Project>>() {
        }, contentAsString);

        List<Pomo> pomosFromJson = new ArrayList<>();
        List<Pomo> pomosFromDb = new ArrayList<>();
        fromDb.forEach(project -> project.getTasks().forEach(task -> pomosFromDb.addAll(task.getPomos())));
        fromJSON.forEach(project -> project.getTasks().forEach(task -> pomosFromJson.addAll(task.getPomos())));

        Set<Task> collect = getTasks(fromDb);
        Set<Task> collect1 = getTasks(fromJSON);

        Assertions.assertThat(collect).isEqualTo(collect1);
    }

    @NotNull
    private Set<Task> getTasks(Set<Project> projects) {
        return projects.stream().flatMap(project -> Objects.requireNonNull(project.getTasks()).stream()).collect(Collectors.toSet());
    }
}
