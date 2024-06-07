package recipes.service;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.model.Recipe;
import recipes.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class RecipeService {
    @Autowired
    public RecipeRepository recipeRepository;


    public Optional<Recipe> getRecipe(Long id) {
        return recipeRepository.findById(id);
    }

    public Recipe saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
        return recipe;
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<Recipe> findByCategory(String category) {
        Optional<List<Recipe>> recipes = recipeRepository.findByCategory(category);
        return recipes.orElseGet(ArrayList::new);
    }

    public List<Recipe> findByName(String name) {
        Optional<List<Recipe>> recipes = recipeRepository.findByName(name);
        return recipes.orElseGet(ArrayList::new);
    }


}
