package viadee.example.todo.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import viadee.example.todo.data.model.Note;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

    Iterable<Note> findByDueDateGreaterThanEqual(LocalDate dueDate);

    Optional<Note> findById(long noteID);

    void deleteById(long id);
}
