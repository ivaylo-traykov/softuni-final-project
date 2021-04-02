package bg.softuni.pethotel.model.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String imageUrl;
    private Set<AnimalEntity> animals = new TreeSet<>();
    private List<RoleEntity> roles = new ArrayList<>();
    List<ReservationEntity> reservations = new ArrayList<>();

    public UserEntity() {
    }

    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Column(nullable = false)
    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    @Column(columnDefinition = "TEXT")
    public String getImageUrl() {
        return imageUrl;
    }

    public UserEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    public Set<AnimalEntity> getAnimals() {
        return animals;
    }

    public UserEntity setAnimals(Set<AnimalEntity> animals) {
        this.animals = animals;
        return this;
    }

    public UserEntity addAnimal(AnimalEntity animal) {
        this.animals.add(animal);
        return this;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public List<RoleEntity> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<RoleEntity> roles) {
        this.roles = roles;
        return this;
    }

    public UserEntity addRole(RoleEntity role) {
        this.roles.add(role);
        return this;
    }

    @OneToMany(mappedBy = "owner")
    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    public UserEntity setReservations(List<ReservationEntity> reservations) {
        this.reservations = reservations;
        return this;
    }
}
