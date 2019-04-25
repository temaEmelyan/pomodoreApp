package com.temelyan.pomoapp.repository.dataJpa

import com.temelyan.pomoapp.model.User
import com.temelyan.pomoapp.repository.UserRepopsitory
import com.temelyan.pomoapp.util.exception.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class DataJpaUserRepositoryImpl(private val crudRepository: CrudUserRepository) : UserRepopsitory {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun save(user: User): User {
        logger.info("Saving user {} to the database", user)
        return crudRepository.save(user)
    }

    override fun get(id: Int): User {
        return crudRepository.findById(id)
                .orElseThrow { NotFoundException("User with this id does not exist") }
    }

    override fun getByEmail(email: String): User {
        return crudRepository.getByEmail(email)
                .orElseThrow { NotFoundException("User with this email does not exist") }
    }

    override fun findUserByResetToken(token: String): User {
        return crudRepository.findByResetToken(token).orElseThrow<RuntimeException> { RuntimeException() }
    }
}
