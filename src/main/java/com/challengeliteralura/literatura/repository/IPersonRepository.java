package com.challengeliteralura.literatura.repository;

import com.challengeliteralura.literatura.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IPersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByName(String name);

    @Query("SELECT p FROM Person p WHERE :year >= p.birth_year AND (:year <= p.death_year OR p.death_year IS NULL)")
    List<Person> authorForYear(int year);


}
