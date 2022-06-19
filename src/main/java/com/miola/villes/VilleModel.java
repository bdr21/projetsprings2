package com.miola.villes;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.miola.endroits.EndroitModel;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "villes")
public class VilleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank
    @Column(name = "ville_name")
    private String villename;

    //@JsonManagedReference
    @OneToMany(mappedBy = "ville", cascade = CascadeType.ALL)
    private List<EndroitModel> endroits;

    @Override
    public String toString() {
        return "VilleModel{" +
                "id=" + id +
                ", villename='" + villename + '\'' +
                '}';
    }
}
