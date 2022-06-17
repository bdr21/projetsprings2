package com.miola.endroit_crud.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Endroit {
@Id @GeneratedValue
    long id;@Column
String name;@Column
    String description;@Column
    String ville;@Column
    String name_photo;

    public Endroit(long id, String name, String description, String ville, String name_photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ville = ville;
        this.name_photo = name_photo;
    }

    public Endroit(String name) {
        this.name = name;
    }

    public Endroit() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getVille() {
        return ville;
    }

    public String getName_photo() {
        return name_photo;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setName_photo(String name_photo) {
        this.name_photo = name_photo;
    }
}
