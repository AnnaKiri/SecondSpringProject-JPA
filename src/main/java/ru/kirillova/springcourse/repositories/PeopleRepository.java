package ru.kirillova.springcourse.repositories;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import ru.kirillova.springcourse.models.*;

import java.util.*;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    List<Person> findByName(String name);
}
