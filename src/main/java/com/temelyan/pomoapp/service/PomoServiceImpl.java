package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.repository.PomoRepository;
import com.temelyan.pomoapp.to.PomoTo;
import com.temelyan.pomoapp.to.PomoToFactory;
import com.temelyan.pomoapp.to.ProjectTo;
import com.temelyan.pomoapp.to.ProjectToFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PomoServiceImpl implements PomoService {
    private final PomoRepository pomoRepository;

    @Autowired
    public PomoServiceImpl(PomoRepository pomoRepository) {
        this.pomoRepository = pomoRepository;
    }

    @Override
    public void add(Pomo pomo, int projectId) {
        pomoRepository.save(pomo, projectId);
    }

    @Override
    public List<PomoTo> getAll(int projectId) {
        List<Pomo> allForProject = pomoRepository.getAllForUser(projectId);
        ProjectTo projectTo = null;

        if (allForProject.size() > 0) {
            Pomo pomo = allForProject.get(0);
            Project project = pomo.getProject();
            projectTo = new ProjectTo(project.getId(), project.getName(), Collections.emptyList());
        }
        ProjectTo finalProjectTo = projectTo;

        return allForProject
                .stream()
                .map(pomo -> PomoToFactory.fromEntity(pomo, finalProjectTo)).collect(Collectors.toList());
    }

    @Override
    public List<PomoTo> getAllForUser(int userId) {
        List<Pomo> allForUser = pomoRepository.getAllForUser(userId);

        return allForUser
                .stream()
                .map(pomo -> {
                    Project project = pomo.getProject();
                    ProjectTo projectTo = ProjectToFactory.fromEntity(project);
                    return PomoToFactory.fromEntity(pomo, projectTo);
                }).collect(Collectors.toList());
    }

    @Override
    public List<PomoTo> getAllForUserInDateRange(LocalDate fromDate, LocalDate toDate, int id) {
        List<Pomo> pomos = pomoRepository.getAllForUserInDateRange(fromDate, toDate, id);

        return pomos.stream()
                .map(pomo -> {
                    Project project = pomo.getProject();
                    ProjectTo projectTo = new ProjectTo(project.getId(), project.getName(), Collections.emptyList());
                    return PomoToFactory.fromEntity(pomo, projectTo);
                }).collect(Collectors.toList());
    }
}
