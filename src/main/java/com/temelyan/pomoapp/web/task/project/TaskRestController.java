package com.temelyan.pomoapp.web.task.project;

import com.temelyan.pomoapp.model.Task;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ajax/task/")
public class TaskRestController extends AbstractTaskController {

    @PostMapping(path = "add")
    public void addTask(String name, int projectId) {
        super.create(new Task(name), projectId);
    }

    @GetMapping(path = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getAllTasks(int projectId) {
        return super.getAll(projectId);
    }
}
