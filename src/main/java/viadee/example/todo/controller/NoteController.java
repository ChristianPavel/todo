package viadee.example.todo.controller;

import org.springframework.web.bind.annotation.*;
import viadee.example.todo.data.model.Note;
import viadee.example.todo.service.NoteService;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {


    private final NoteService noteService;


    NoteController(final NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> retrieveNotes(@RequestParam(required = false) String status) {
        return noteService.retrieveNotes(status);
    }

    @PostMapping
    public void createNote(@RequestBody Note note) {
        noteService.createNote(note);
    }

    @PatchMapping
    public void changeNoteStatus(@RequestBody long noteID) {
        noteService.changeNoteStatus(noteID);
    }


    @GetMapping("/{id}")
    public Note accessNote(@PathVariable long id) {
        return noteService.retrieveSpecificNote(id);
    }

    @PatchMapping("/{id}")
    public void updateNote(@PathVariable Long id, @RequestBody Note noteUpdate) {
        noteService.updateNote(id, noteUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteEntry(@PathVariable Long id) {
        noteService.deleteNote(id);
    }


    @GetMapping("/dashboard")
    public List<Note> retrieveDashboardNotes() {
        return noteService.retrieveDashboardNotes();

    }
}
