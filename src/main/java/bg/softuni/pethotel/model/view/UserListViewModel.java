package bg.softuni.pethotel.model.view;

public class UserListViewModel {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;

    public Long getId() {
        return id;
    }

    public UserListViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserListViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserListViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserListViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
