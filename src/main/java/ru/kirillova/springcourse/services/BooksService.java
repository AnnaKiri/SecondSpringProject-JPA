package ru.kirillova.springcourse.services;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
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

    public List<Book> findAll(String page, String booksPerPage, String sortByYear) {
        int pageInt;
        int booksPerPageInt;
        boolean paginationIsEnabled;

        try {
            pageInt = Integer.parseInt(page);
            booksPerPageInt = Integer.parseInt(booksPerPage);
            paginationIsEnabled = booksPerPageInt > 0;
        } catch (NumberFormatException e) {
            pageInt = 0;
            booksPerPageInt = 0;
            paginationIsEnabled = false;
        }

        boolean sortByYearBool = Boolean.parseBoolean(sortByYear);

        if (paginationIsEnabled && sortByYearBool) {
            return booksRepository.findAll(PageRequest.of(pageInt, booksPerPageInt, Sort.by("year"))).getContent();
        } else if (paginationIsEnabled) {
            return booksRepository.findAll(PageRequest.of(pageInt, booksPerPageInt)).getContent();
        } else if (sortByYearBool) {
            return booksRepository.findAll(Sort.by("year"));
        } else {
            return booksRepository.findAll();
        }
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public Optional<Person> showPerson(int id) {
        Book book = booksRepository.findById(id).get();
        Hibernate.initialize(book.getOwner());
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

    public List<Book> search(String query) {
        List<Book> books = booksRepository.findByTitleStartingWith(query);
        for (Book book : books) {
            Hibernate.initialize(book.getOwner());
        }

        return books;
    }
}
