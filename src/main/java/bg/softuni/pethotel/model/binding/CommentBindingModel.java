package bg.softuni.pethotel.model.binding;

public class CommentBindingModel {
    private String description;
    private String author;
    private Integer rating;

    public String getDescription() {
        return description;
    }

    public CommentBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public CommentBindingModel setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public CommentBindingModel setRating(Integer rating) {
        this.rating = rating;
        return this;
    }
}