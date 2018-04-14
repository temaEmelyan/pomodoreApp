package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CrudUserRepository extends JpaRepository<User, Integer> {
    Optional<User> getByEmail(String email);

    @Override
    Optional<User> findById(Integer id);

    @Override
    @Transactional
    User save(User user);

    Optional<User> findByResetToken(String resetToken);

    @Query("select u from User u " +
            "join fetch u.projects pr " +
            "join fetch pr.tasks t " +
            "join fetch t.pomos p " +
            "where u.id=:user_id and (p.finish between :from_date and :to_date)")
    User getUserByIdCompleteGraphForPomosInDateRange(
            @Param("from_date") LocalDateTime from,
            @Param("to_date") LocalDateTime to,
            @Param("user_id") int userId
    );
}
