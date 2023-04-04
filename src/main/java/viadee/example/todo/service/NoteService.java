package viadee.example.todo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import viadee.example.todo.data.model.Note;
import viadee.example.todo.persistence.NoteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    static final String NOT_FOUND = "Note with this name was not found";
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> retrieveNotes(String status) {
        if (status.equals("pending")) {
            return ((List<Note>) noteRepository.findAll()).stream()
                    .filter(n -> !n.getDone())
                    .toList();
        } else if (status.equals("finished")) {
            return ((List<Note>) noteRepository.findAll()).stream()
                    .filter(Note::getDone)
                    .toList();
        }
        return (List<Note>) noteRepository.findAll();
    }

    public void createNote(Note note) {
        noteRepository.save(note);
    }

    public void changeNoteStatus(long noteID) {
        Optional<Note> note = noteRepository.findById(noteID);
        if (note.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND);
        }
        Note noteInstance = note.get();
        noteInstance.changeStatus();
        noteRepository.save(noteInstance);
    }

    public Note retrieveSpecificNote(long id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()) {
            return note.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND);
    }

    public void updateNote(Long id, Note noteUpdate) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND);
        }
        Note noteInstance = note.get();
        noteInstance.update(noteUpdate);
        noteRepository.save(noteInstance);
    }

    public void deleteNote(Long id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND);
        }
        noteRepository.deleteById(note.get().getId());
    }

    public List<Note> retrieveDashboardNotes() {
        return (List<Note>) noteRepository.findByDueDateGreaterThanEqual(LocalDate.now());
    }

}
