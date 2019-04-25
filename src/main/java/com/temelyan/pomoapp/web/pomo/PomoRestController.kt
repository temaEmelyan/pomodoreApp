package com.temelyan.pomoapp.web.pomo

import com.temelyan.pomoapp.model.Project
import com.temelyan.pomoapp.service.AuthorisedUserService
import com.temelyan.pomoapp.service.PomoService
import com.temelyan.pomoapp.service.ProjectService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pomo/")
class PomoRestController(authorisedUserService: AuthorisedUserService,
                         projectService: ProjectService,
                         pomoService: PomoService)
    : AbstractPomoController(authorisedUserService, projectService, pomoService) {

    @PostMapping(path = ["add"])
    fun addPomo(@RequestParam("length") length: Int,
                @RequestParam("taskId") taskId: Int,
                @RequestParam("clientTimeZone") clientTimeZone: Int) {
        //todo add validation that the task belongs to the authenticated user
        super.add(length, taskId, clientTimeZone)
    }

    @GetMapping(path = ["get"], produces = [MediaType.APPLICATION_JSON_VALUE])
    public override fun getUserWithPomosInDateRange(@RequestParam("from") from: String,
                                                    @RequestParam("to") to: String): Set<Project> {
        return super.getUserWithPomosInDateRange(from, to)
    }
}
