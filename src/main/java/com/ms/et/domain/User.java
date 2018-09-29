package com.ms.et.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String firstName;
    private String lastName;
    private String password;
    @Column(unique = true)
    private String email;
    private boolean enabled;

    public Long getId() { return id; }

    public void setId(Long _id) { id = _id; }

    public String getName() { return name; }

    public void setName(String _name) { name = _name; }

    public String getPassword() { return password; }

    public void setPassword(String _password) { password = _password; }

    public String getEmail() { return email; }

    public void setEmail(String _email) { email = _email; }

    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean _enabled) { enabled = _enabled; }

    public List<Role> getRoles() { return roles; }

    public void setRoles(List<Role> _roles) { roles = _roles; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    private Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> _items) { items = _items; }

    @OneToMany(mappedBy = "user", cascade = CascadeType.DETACH)
    private Set<Item> items;

    @OneToMany(mappedBy = "user", cascade = CascadeType.DETACH)
    private Set<Project> projects;

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}