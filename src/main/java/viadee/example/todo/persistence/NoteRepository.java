package viadee.example.todo.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import viadee.example.todo.data.model.Note;

import java.util.Optional;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

    Optional<Note> findByName(String name);
}
