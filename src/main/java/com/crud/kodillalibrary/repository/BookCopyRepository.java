package com.crud.kodillalibrary.repository;

import com.crud.kodillalibrary.domain.BookCopy;
import com.crud.kodillalibrary.domain.BookStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BookCopyRepository extends CrudRepository <BookCopy, Long> {

    @Override
    List<BookCopy> findAll();

    @Override
    BookCopy save(BookCopy bookCopy);

    Optional<BookCopy> findById(Long id);

    List<BookCopy> findByBookStatus(BookStatus bookStatus);

    void deleteById(Long id);
}
