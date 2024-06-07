package recipes.api;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.*;
import recipes.service.RecipeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/api/recipe")
public class RecipeApi {


    private final RecipeService recipeService;

    private static final Logger logger = LoggerFactory.getLogger(RecipeApi.class);


    @Autowired
    public RecipeApi(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipe(@PathVariable Long id) {

        logger.atInfo().log("GET /api/recipe/{id} received");

            Optional<Recipe> recipeOptional = recipeService.getRecipe(id);
            if (recipeOptional.isPresent()) {
                Recipe recipe = recipeOptional.get();
                RecipeResponse response = mapToResponse(recipe);
                return ResponseEntity.ok(response);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }


    }

    @PostMapping("/new")
    @ResponseBody
    public ResponseEntity<RecipeIdResponse> postRecipe(@Valid @RequestBody RecipeRequest newRecipeRequest,
                                                       Authentication auth) {
        try {
            Recipe newRecipe = mapToEntity(newRecipeRequest, auth);
            Recipe savedRecipe = recipeService.saveRecipe(newRecipe);
            RecipeIdResponse response = new RecipeIdResponse(savedRecipe.getId());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id, Authentication auth) {


        Optional<Recipe> optionalRecipe = recipeService.getRecipe(id);
        if (optionalRecipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if (userIsAuthor(auth, optionalRecipe.get())) {
            recipeService.deleteRecipe(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable Long id,
            @Valid @RequestBody RecipeRequest recipeRequest,
                                       Authentication auth) {
        Optional<Recipe> optionalRecipe = recipeService.getRecipe(id);
        if (optionalRecipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else if (!userIsAuthor(auth, optionalRecipe.get())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

            Recipe recipe = mapToEntity(recipeRequest, auth);
            recipe.setId(id);
            recipeService.saveRecipe(recipe);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/search/")
    public ResponseEntity<List<RecipeResponse>> searchRecipe(@RequestParam(required = false) @NotBlank String category,
                                                             @RequestParam(required = false) @NotBlank String name) {
        List<Recipe> recipes;
        if (category == null && name == null || category != null && name !=null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (category != null) {
            recipes = recipeService.findByCategory(category);
        } else {
            recipes = recipeService.findByName(name);
        }

        List<RecipeResponse> recipeResponses = new ArrayList<>();
        for (Recipe r : recipes) {
            recipeResponses.add(mapToResponse(r));
        }

        return ResponseEntity.ok(recipeResponses);


    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return ResponseEntity.ok(userDetails);
    }


    private RecipeResponse mapToResponse(Recipe recipe) {
        RecipeResponse response = new RecipeResponse();
        response.setName(recipe.getName());
        response.setCategory(recipe.getCategory());
        response.setDescription(recipe.getDescription());
        response.setIngredients(recipe.getIngredients());
        response.setDirections(recipe.getDirections());
        response.setDate(recipe.getTimestamp());
        return response;
    }

    private Recipe mapToEntity(RecipeRequest request, Authentication auth) {
        Recipe recipe = new Recipe();
        recipe.setName(request.getName());
        recipe.setCategory(request.getCategory());
        recipe.setDescription(request.getDescription());
        recipe.setIngredients(request.getIngredients());
        recipe.setDirections(request.getDirections());
        recipe.setUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
        return recipe;
    }


    private boolean userIsAuthor(Authentication auth, Recipe recipe) {
        return Objects.equals(((CustomUserDetails) auth.getPrincipal()).getUserId(), recipe.getUserId());
    }


}
