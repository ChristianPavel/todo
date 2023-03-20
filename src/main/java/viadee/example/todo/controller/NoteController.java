package viadee.example.todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import viadee.example.todo.data.model.Note;
import viadee.example.todo.persistence.NoteRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/notes")
public class NoteController {
    NoteRepository noteRepository;

    NoteController(final NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    @GetMapping("")
    public List<Note> retrieveAllNotes(){
        return (List<Note>) noteRepository.findAll(); // todo: exception Handling
    }

    @PostMapping("")
    public void createNote(@RequestBody Note note){
        noteRepository.save(note);
    }

    @PatchMapping("")
    public void changeNoteStatus(@RequestBody Note noteUpdate){
        Optional<Note> note = noteRepository.findById(noteUpdate.getId());
        if(note.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note with this name was note found");
        }
        Note noteInstance = note.get();
        noteInstance.changeStatus();
        noteRepository.save(noteInstance);
    }

    @DeleteMapping("")
    public void deleteEntry(@RequestBody Note delNote){
        Optional<Note> note = noteRepository.findById(delNote.getId());
        if(note.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note with this name was note found");
        }
        noteRepository.deleteById(note.get().getId());
    }

    @GetMapping("/{name}")
    public Note accessNote(@PathVariable String name){
        Optional<Note> note = noteRepository.findByName(name);
        if(note.isPresent()){
            return note.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note with this name was note found");
    }
    @PatchMapping("/{name}")
    public void changeNoteStatus(@PathVariable String name, @RequestBody Note noteUpdate){
        Optional<Note> note = noteRepository.findByName(name);
        if(note.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note with this name was note found");
        }
        Note noteInstance = note.get();
        noteInstance.update(noteUpdate);
        noteRepository.save(noteInstance);
    }

    @DeleteMapping("/{name}")
    public void deleteEntry(@PathVariable String name){
        Optional<Note> note = noteRepository.findByName(name);
        if(note.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note with this name was note found");
        }
        noteRepository.deleteById(note.get().getId());
    }

    @GetMapping("/pending")
    public List<Note> retrievePendingNotes(){
        return retrieveAllNotes().stream()
                .filter(n -> !n.getDone())
                .toList();
    }

    @GetMapping("/finished")
    public List<Note> retrieveFinishedNotes(){
        return retrieveAllNotes().stream()
                .filter(Note::getDone)
                .toList();
    }
}
