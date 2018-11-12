package com.crud.kodillalibrary.repository;

import com.crud.kodillalibrary.domain.BookTitle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BookTitleRepository extends CrudRepository<BookTitle, Long> {
    @Override
    List<BookTitle> findAll();

    @Override
    BookTitle save(BookTitle bookTitle);

    Optional<BookTitle> findById(Long id);

    List<BookTitle> findByTitle(String title);

    void deleteById(Long id);
}
