package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.repository.PomoRepository;
import com.temelyan.pomoapp.repository.ProjectRepository;
import com.temelyan.pomoapp.to.PomoToFactory;
import com.temelyan.pomoapp.to.ProjectTo;
import com.temelyan.pomoapp.to.ProjectToFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    private final PomoRepository pomoRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, PomoRepository pomoRepository) {
        this.projectRepository = projectRepository;
        this.pomoRepository = pomoRepository;
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<ProjectTo> getAll(int userId) {
        return projectRepository
                .getAll(userId)
                .stream()
                .map(project -> new ProjectTo(
                        project.getId(),
                        project.getName(),
                        Collections.emptyList())
                ).collect(Collectors.toList());
    }

    @Override
    public List<ProjectTo> getAllWithPomosInDateRange(LocalDate from, LocalDate to, int userId) {
        List<Pomo> allForUserInDateRange = pomoRepository.getAllForUserInDateRange(from, to, userId);

        Map<Integer, ProjectTo> integerProjectToMap = new HashMap<>();

        allForUserInDateRange.forEach(pomo -> {
            Project project = pomo.getProject();

            ProjectTo projectTo = integerProjectToMap.computeIfAbsent(
                    project.getId(), integer ->
                            ProjectToFactory.fromEntity(project));

            projectTo.getPomoTos().add(PomoToFactory.fromEntity(pomo, null));
        });

        ArrayList<ProjectTo> projectTos = new ArrayList<>(integerProjectToMap.values());
        projectTos.sort(Comparator.comparing(o -> o.getName().toLowerCase()));
        return projectTos;
    }
}