package com.ms.et.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String password;
    @Column(unique = true)
    private String email;

    public Long getId() { return id; }

    public void setId(Long _id) { id = _id; }

    public String getName() { return name; }

    public void setName(String _name) { name = _name; }

    public String getPassword() { return password; }

    public void setPassword(String _password) { password = _password; }

    public List<Role> getRoles() { return roles; }

    public void setRoles(List<Role> _roles) { roles = _roles; }

    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;
}