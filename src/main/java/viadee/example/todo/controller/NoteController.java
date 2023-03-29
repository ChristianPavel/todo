package viadee.example.todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import viadee.example.todo.data.model.Note;
import viadee.example.todo.persistence.NoteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteRepository noteRepository;

    static final String NOT_FOUND = "Note with this name was not found";

    NoteController(final NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping
    public List<Note> retrieveAllNotes(@RequestParam(required = false) String status) {
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


    @PostMapping
    public void createNote(@RequestBody Note note) {
        noteRepository.save(note);
    }

    // TODO: Refactor to use a path variable "ID" instead of transmitting the entire note

    /*public void changeNoteStatus(@RequestBody Note noteUpdate) {
        Optional<Note> note = noteRepository.findById(noteUpdate.getId());
        if (note.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND);
        }
        Note noteInstance = note.get();
        noteInstance.changeStatus();
        noteRepository.save(noteInstance);
    }*/
    @PatchMapping
    public void changeNoteStatus(@RequestBody Long noteID) {
        Optional<Note> note = noteRepository.findById(noteID);
        if (note.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND);
        }
        Note noteInstance = note.get();
        noteInstance.changeStatus();
        noteRepository.save(noteInstance);
    }


    @GetMapping("/{id}")
    public Note accessNote(@PathVariable long id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()) {
            return note.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public void changeNoteStatus(@PathVariable Long id, @RequestBody Note noteUpdate) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND);
        }
        Note noteInstance = note.get();
        noteInstance.update(noteUpdate);
        noteRepository.save(noteInstance);
    }

    @DeleteMapping("/{id}")
    public void deleteEntry(@PathVariable Long id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND);
        }
        noteRepository.deleteById(note.get().getId());
    }


    @GetMapping("/dashboard")
    public List<Note> retrieveDashboardNotes() {
        return (List<Note>) noteRepository.findByDueDateGreaterThanEqual(LocalDate.now());
    }
}
