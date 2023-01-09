package ru.kirillova.springcourse.repositories;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import ru.kirillova.springcourse.models.*;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
