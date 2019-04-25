package com.temelyan.pomoapp.service

import com.temelyan.pomoapp.AuthorizedUser
import com.temelyan.pomoapp.model.Project
import com.temelyan.pomoapp.model.Task
import com.temelyan.pomoapp.model.User
import com.temelyan.pomoapp.repository.ProjectRepository
import com.temelyan.pomoapp.repository.TaskRepository
import com.temelyan.pomoapp.repository.UserRepopsitory
import com.temelyan.pomoapp.to.UserTo
import com.temelyan.pomoapp.util.UserUtil
import com.temelyan.pomoapp.util.UserUtil.prepareToSave
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("userService")
class UserServiceImpl(
        private val userRepository: UserRepopsitory,
        private val projectRepository: ProjectRepository,
        private val taskRepository: TaskRepository,
        private val passwordEncoder: PasswordEncoder) : UserService {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): AuthorizedUser {
        val user = loadByEmail(username)
        return AuthorizedUser(user)
    }

    override fun loadByEmail(email: String): User {
        return userRepository.getByEmail(email.toLowerCase())
    }

    override fun update(user: User): User {
        return userRepository.save(user)
    }

    @Transactional
    override fun update(userTo: UserTo): User {
        val user = get(userTo.getId()!!)
        return userRepository.save(
                prepareToSave(
                        UserUtil.updateFromTo(user, userTo), passwordEncoder
                )
        )
    }

    override fun findUserByResetToken(token: String): User {
        return userRepository.findUserByResetToken(token)
    }

    override fun create(user: User): User {
        userRepository.save(prepareToSave(user, passwordEncoder))
        val project = Project("Work")
        projectRepository.save(project, user.getId()!!)
        val task = Task("Do work")
        taskRepository.save(task, project.getId()!!)
        logger.info("New user {} created", user)
        return user
    }

    private fun get(id: Int): User {
        return userRepository.get(id)
    }
}
