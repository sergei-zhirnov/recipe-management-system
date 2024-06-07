package recipes.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;


@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotBlank
    private String description;

    @NotEmpty
    private List<String> ingredients;

    @NotEmpty
    private List<String> directions;

}