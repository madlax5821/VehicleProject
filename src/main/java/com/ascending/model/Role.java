package com.ascending.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "allowed_resource")
    private String allowedResource;

    @Column(name = "allowed_read")
    private boolean ifAllowedRead;

    @Column(name = "allowed_create")
    private boolean ifAllowedCreate;

    @Column(name = "allowed_update")
    private boolean ifAllowedUpdate;

    @Column(name = "allowed_delete")
    private boolean ifAllowedDelete;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    //@ManyToMany(mappedBy = "roles",fetch = FetchType.EAGER)
    private Set<User> users;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllowedResource() {
        return allowedResource;
    }

    public void setAllowedResource(String allowedResource) {
        this.allowedResource = allowedResource;
    }

    public boolean isIfAllowedRead() {
        return ifAllowedRead;
    }

    public void setIfAllowedRead(boolean ifAllowedRead) {
        this.ifAllowedRead = ifAllowedRead;
    }

    public boolean isIfAllowedCreate() {
        return ifAllowedCreate;
    }

    public void setIfAllowedCreate(boolean ifAllowedCreate) {
        this.ifAllowedCreate = ifAllowedCreate;
    }

    public boolean isIfAllowedUpdate() {
        return ifAllowedUpdate;
    }

    public void setIfAllowedUpdate(boolean ifAllowedUpdate) {
        this.ifAllowedUpdate = ifAllowedUpdate;
    }

    public boolean isIfAllowedDelete() {
        return ifAllowedDelete;
    }

    public void setIfAllowedDelete(boolean ifAllowedDelete) {
        this.ifAllowedDelete = ifAllowedDelete;
    }

    public Set<User> getUsers() {
        if (users==null){
            users=new HashSet<User>();
        }
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user){
        user.getRoles().add(this);
        this.getUsers().add(user);
    }

    public void removeUser(User user){
        user.getRoles().remove(this);
        this.getUsers().remove(user);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", allowedResource='" + allowedResource + '\'' +
                ", ifAllowedRead=" + ifAllowedRead +
                ", ifAllowedCreate=" + ifAllowedCreate +
                ", ifAllowedUpdate=" + ifAllowedUpdate +
                ", ifAllowedDelete=" + ifAllowedDelete +
                '}';
    }
}
