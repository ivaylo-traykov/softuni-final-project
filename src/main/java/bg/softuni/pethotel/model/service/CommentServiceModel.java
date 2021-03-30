package bg.softuni.pethotel.model.service;

public class CommentServiceModel {
    private Long id;
    private String description;
    private String author;
    private int rating;

    public Long getId() {
        return id;
    }

    public CommentServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CommentServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public CommentServiceModel setAuthor(String author) {
        this.author = author;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public CommentServiceModel setRating(int rating) {
        this.rating = rating;
        return this;
    }
}
