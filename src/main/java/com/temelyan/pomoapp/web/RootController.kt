package com.temelyan.pomoapp.web

import com.temelyan.pomoapp.model.Project
import com.temelyan.pomoapp.model.Task
import com.temelyan.pomoapp.service.AuthorisedUserService
import com.temelyan.pomoapp.service.ProjectService
import com.temelyan.pomoapp.service.UserService
import com.temelyan.pomoapp.to.UserTo
import com.temelyan.pomoapp.util.UserUtil
import com.temelyan.pomoapp.validator.UserUpdateValidator
import com.temelyan.pomoapp.validator.UserValidator
import com.temelyan.pomoapp.web.user.AbstractUserController
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*

@Controller
class RootController(
        userService: UserService,
        private val userValidator: UserValidator,
        private val userUpdateValidator: UserUpdateValidator,
        authorisedUserService: AuthorisedUserService,
        private val projectService: ProjectService)
    : AbstractUserController(userService, authorisedUserService) {

    @GetMapping("/")
    fun root(model: Model): String {
        val authorizedUser = authorisedUserService.get()
        logger.info("redirect from root to pomo.html for user {}", authorizedUser)
        val allForUser = ArrayList(projectService.getAllForUserWithTasks(authorizedUser.id()))
        allForUser.sortWith(Comparator.comparing<Project, String> { project -> project.name!!.toLowerCase() })
        model.addAttribute("projects", allForUser)
        val tasks = ArrayList(allForUser[0].tasks)
        tasks.sortWith(Comparator.comparing<Task, String> { task -> task.name!!.toLowerCase() })
        model.addAttribute("tasks", tasks)
        return "pomo"
    }

    @GetMapping("/login")
    fun login(): String {
        val auth = SecurityContextHolder.getContext().authentication
        return if (auth !is AnonymousAuthenticationToken) {
            "redirect:/"
        } else "login"
    }

    @GetMapping("/profile")
    fun profile(model: Model): String {
        val userTo = UserTo()
        userTo.email = authorisedUserService.get().userTo.email
        model.addAttribute("userTo", userTo)
        return "profile"
    }

    @PostMapping("/profile")
    fun updateProfile(userTo: UserTo, bindingResult: BindingResult): String {
        userUpdateValidator.validate(userTo, bindingResult)
        if (bindingResult.hasErrors()) {
            return "profile"
        } else {
            userTo.password = userTo.newPassword
            val authorizedUser = authorisedUserService.get()
            super.update(userTo, authorizedUser.id())
            return "redirect:/"
        }
    }

    @GetMapping("/registration")
    fun register(model: ModelMap): String {
        model.addAttribute("userTo", UserTo())
        model.addAttribute("register", true)
        return "registration"
    }

    @PostMapping("/registration")
    @ResponseBody
    fun saveRegister(userTo: UserTo, bindingResult: BindingResult) {
        userValidator.validate(userTo, bindingResult)
        if (bindingResult.hasErrors()) {
            throw RuntimeException(bindingResult.toString())
        } else {
            super.create(UserUtil.createNewFromTo(userTo))
        }
    }

    @GetMapping("/log")
    fun openLog(): String {
        logger.info("opening log page")
        return "pomos"
    }
}
