package com.crud.kodillalibrary.repository;

import com.crud.kodillalibrary.domain.LibraryReader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReaderRepository extends CrudRepository<LibraryReader, Long> {

    @Override
    List<LibraryReader> findAll();

    @Override
    LibraryReader save(LibraryReader libraryReader);

    Optional<LibraryReader> findById(Long id);

    void deleteById(Long id);
}