package com.ms.et.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long _id) {
        id = _id;
    }

    public String getName() {
      return name;
    }

    public void setName(String _name) {
      name = _name;
    }

    public List<Privilege> getPrivileges() {
      return privileges;
    }

    public void setPrivileges(List<Privilege> _privileges) {
      privileges = _privileges;
    }
 
    @ManyToMany
    @JoinTable(
        name = "roles_privileges", 
        joinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "privilege_id", referencedColumnName = "id"))
    private List<Privilege> privileges;

    @Override
    public String toString() {
        return name;
    }
}