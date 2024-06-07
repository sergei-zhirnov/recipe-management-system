package recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.Name;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="recipes", schema="recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_seq")
    @SequenceGenerator(name = "recipe_seq", sequenceName = "recipes.recipes_id_seq", allocationSize = 1)
    @JsonIgnore
    private Long id;

    @NotBlank
    @Column(name="name")
    private String name;

    @NotBlank
    @Column(name="category")
    private String category;

    @NotBlank
    @Column(name="description")
    private String description;

    @ElementCollection
    @CollectionTable(name="recipe_ingredients", schema = "recipes",
            joinColumns = @JoinColumn(name="recipe_id_fk"))
    @Column(name="name")
    private List<String> ingredients = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name="recipe_directions", schema = "recipes",
            joinColumns = @JoinColumn(name="recipe_id_fk"))
    @Column(name="direction")
    private List<String> directions = new ArrayList<>();

    @Column(name="timestamp")
    @UpdateTimestamp
    private LocalDateTime timestamp;

    @Column(name="user_id_fk")
    private Long userId;


}
