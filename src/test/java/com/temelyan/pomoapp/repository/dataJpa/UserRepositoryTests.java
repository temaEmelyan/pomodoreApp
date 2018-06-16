package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.Resolver;
import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.repository.ProjectRepository;
import com.temelyan.pomoapp.repository.UserRepopsitory;
import com.temelyan.pomoapp.util.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@Sql("/db/drop_db.sql")
@ActiveProfiles(resolver = Resolver.class)
@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepopsitory userRepository;
    @Autowired
    private ProjectRepository projectRepository;

    private static void assertMatch(User one, User another) {
        Assertions.assertThat(one)
                .isEqualToIgnoringGivenFields(another, "projects");
    }

    @Test
    public void test1() {
        String email = "test@gmail.com";
        User user = new User(null, email, "password");
        user = userRepository.save(user);
        User byEmail = userRepository.getByEmail(email);
        assertMatch(byEmail, user);
    }

    @Test(expected = NotFoundException.class)
    public void test11() {
        String email = "test@gmail.com";
        User user = new User(null, email, "password");
        userRepository.save(user);
        userRepository.getByEmail(email + "LOL");
    }

    @Test
    public void test2() {
        String email = "test@gmail.com";
        User user = new User(null, email, "password");
        userRepository.save(user);
        User one = userRepository.get(user.getId());
        assertMatch(one, user);
    }

    @Test
    public void test3() {
        String email = "test@gmail.com";
        User user = new User(null, email, "password");
        Project project = new Project(null, "Work", Collections.emptySet(), user);
        user.setProjects(Collections.singleton(project));
        userRepository.save(user);
        User one = userRepository.get(user.getId());
        assertMatch(one, user);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void test4() {
        String email = "test@gmail.com";
        User user = new User(null, email, "password");
        userRepository.save(user);
        User user1 = new User(null, email, "password");
        userRepository.save(user1);
    }

    @Test
    public void saveUserSaveProject_testGetUserWithProjects() {
        String email = "test@gmail.com";
        User user = new User(null, email, "password");

        Project p0 = new Project(null, "Work", Collections.emptySet(), user);
        Project p1 = new Project(null, "PhD", Collections.emptySet(), user);
        Project p2 = new Project(null, "Paper", Collections.emptySet(), user);
        Project p3 = new Project(null, "Paper2", Collections.emptySet(), user);
        Project p4 = new Project(null, "Paper3", Collections.emptySet(), user);

        user.setProjects(new HashSet<>(Arrays.asList(p0, p1, p2, p3, p4)));

        userRepository.save(user);

        user.getProjects().forEach(p -> projectRepository.save(p, p.getUser().getId()));

        Set<Project> projects = projectRepository.getAllForUserWithTasks(user.getId());

        Set<Project> projects1 = user.getProjects();

        Assertions.assertThat(new HashSet<>(projects))
                .usingElementComparatorIgnoringFields("tasks", "user")
                .isEqualTo(new HashSet<>(projects1));
    }
}