package ru.kirillova.springcourse.services;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import ru.kirillova.springcourse.models.*;
import ru.kirillova.springcourse.repositories.*;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        List<Person> people = peopleRepository.findAll();
        return people;
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public List<Book> showBooksList(int id) {
        Person person = peopleRepository.findById(id).get();
        Hibernate.initialize(person.getBooks());
        return person.getBooks();
    }


//    public Person show(int id) {
//        return peopleRepository.findById(id).get();
//    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

}