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
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

//    public Book show(int id) {
//        return booksRepository.findById(id).get();
//    }

    public Optional<Person> showPerson(int id) {
        Book book = booksRepository.findById(id).get();
        return Optional.ofNullable(book.getOwner());
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void release(int id) {
        Book book = booksRepository.findById(id).get();
        book.setOwner(null);
    }

    @Transactional
    public void assignBook(int idBook, int idPerson) {
        Book book = booksRepository.findById(idBook).get();
        book.setOwner(peopleRepository.findById(idPerson).get());
    }
}
