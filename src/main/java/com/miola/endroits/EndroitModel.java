package com.miola.endroits;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.miola.reviews.ReviewModel;
import com.miola.reviews.ReviewRepository;
import com.miola.reviews.ReviewService;
import com.miola.villes.VilleModel;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@EntityListeners(EndroitListener.class)
@Entity
@Table(name = "endroits")
public class EndroitModel {

    private static Log log = LogFactory.getLog(EndroitModel.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank(message = "Le nom ne peut pas être vide !")
    private String name;
    //@NotBlank(message = "La description ne peut pas être vide !")
    private String description;
    //@NotBlank(message = "Le message ne peut pas être vide !")
    private String image;
    private String videoLink;
    @Transient
    private float ratingAvg;
    @Transient
    private int numberOfReviews;

    //@JsonBackReference
    //@JsonIgnoreProperties("ville")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ville_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private VilleModel ville;

    @OneToMany(mappedBy = "endroit", cascade = CascadeType.ALL)
    private List<ReviewModel> reviews;

    public EndroitModel(int id, String name, String description, String image, VilleModel ville, List<ReviewModel> reviews) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.ville = ville;
        this.reviews = reviews;
    }

    public EndroitModel(int id, String name, String description, String image, String videoLink , VilleModel ville, List<ReviewModel> reviews) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.ville = ville;
        this.reviews = reviews;
        this.videoLink = videoLink;
    }
}
