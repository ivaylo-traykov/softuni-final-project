package bg.softuni.pethotel.model.entity;

import bg.softuni.pethotel.model.enums.CommentStatusEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity {
    private String description;
    private UserEntity author;
    private Integer rating;
    private CommentStatusEnum status;

    public CommentEntity() {
    }

    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public CommentEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @ManyToOne
    public UserEntity getAuthor() {
        return author;
    }

    public CommentEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public CommentEntity setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    @Enumerated(EnumType.STRING)
    public CommentStatusEnum getStatus() {
        return status;
    }

    public CommentEntity setStatus(CommentStatusEnum status) {
        this.status = status;
        return this;
    }
}
