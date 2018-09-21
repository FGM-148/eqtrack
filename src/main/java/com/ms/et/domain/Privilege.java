package com.ms.et.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "priviliges")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public String getName() { return name; }

    public void setName(String _name) { name = _name; }

    public List<Role> getRoles() { return roles; }

    public void setRoles(List<Role> _roles) {
        roles = _roles;
    }
 
    @ManyToMany(mappedBy = "privileges")
    private List<Role> roles;
}