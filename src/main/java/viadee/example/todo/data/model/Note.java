package viadee.example.todo.data.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.time.LocalDate;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    private String comments;

    private Boolean done;

    private LocalDate created;

    private LocalDate dueDate;

    private LocalDate finished;

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
        handleDone(!getDone());
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

    public void update(Note note) {
        handleDone(note.done);
        setName(note.getName());
        setDescription(note.getDescription());
        setComments(note.getComments());
        setDueDate(note.getDueDate());
    }

    private void handleDone(boolean status) {
        setDone(status);
        if (getDone()) {
            setFinished(LocalDate.now());
        } else {
            setFinished(null);
        }
    }
}
