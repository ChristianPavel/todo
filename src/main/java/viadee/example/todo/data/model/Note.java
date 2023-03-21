package viadee.example.todo.data.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String name;

    String description;

    String comments;

    Boolean done;

    LocalDate created;

    LocalDate dueDate;

    LocalDate finished;

    public Note(String name, String description){
        this.name = name;
        this.description = description;
        this.done = false;
        this.created = LocalDate.now();
    }

    public Note(String name, String description, LocalDate dueDate){
        this.name = name;
        this.description = description;
        this.done = false;
        this.created = LocalDate.now();
        this.dueDate = dueDate;
    }

    public Note() {
        this.done = false;
        this.created = LocalDate.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public void changeStatus() {
        setDone(!getDone());
        if (getDone()){
            setFinished(LocalDate.now());
        } else {
            setFinished(null);
        }
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getFinished() {
        return finished;
    }

    public void setFinished(LocalDate finished) {
        this.finished = finished;
    }

    public void update(Note note){
        setDone(note.getDone());
        setName(note.getName());
        setDescription(note.getDescription());
        setComments(note.getComments());
        setDueDate(note.getDueDate());
    }
}
