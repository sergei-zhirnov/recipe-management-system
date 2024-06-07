package recipes.model;


import jdk.jfr.Name;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class RecipeIdResponse {
    private Long id;

    public RecipeIdResponse(Long id) {
        this.id = id;
    }


}
