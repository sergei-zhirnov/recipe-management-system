package recipes.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecipeResponse {

    private String name;
    private String category;
    private String description;
    private List<String> ingredients;
    private List<String> directions;

    private LocalDateTime date;

}
