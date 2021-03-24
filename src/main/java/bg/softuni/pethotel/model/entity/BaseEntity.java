package bg.softuni.pethotel.model.entity;

import javax.persistence.*;
import java.time.Instant;

@MappedSuperclass
public abstract class BaseEntity {
    private Long id;
    private Instant created;
    private Instant modified;

    public BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreated() {
        return created;
    }

    @Column(nullable = false)
    public void setCreated(Instant created) {
        this.created = created;
    }

    @Column(nullable = false)
    public Instant getModified() {
        return modified;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    @PrePersist
    public void prePersist() {
        setCreated(Instant.now());
        setModified(Instant.now());
    }

    @PreUpdate
    public void preUpdate() {
        setModified(Instant.now());
    }
}
