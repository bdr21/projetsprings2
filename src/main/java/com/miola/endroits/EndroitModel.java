package com.miola.endroits;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miola.reviews.ReviewModel;
import com.miola.villes.VilleModel;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter @Setter
@Entity
@Table(name = "endroits")
public class EndroitModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Le nom ne peut pas être vide !")
    private String name;
    //@NotBlank(message = "La description ne peut pas être vide !")
    private String description;
    //@NotBlank(message = "Le message ne peut pas être vide !")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ville_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private VilleModel ville;

    @OneToMany(mappedBy = "endroit", cascade = CascadeType.ALL)
    private List<ReviewModel> reviews;


}
