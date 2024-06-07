package recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import recipes.model.Recipe;

import java.util.List;
import java.util.Optional;


@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Override
    void deleteById(Long id);


    @Query("SELECT r FROM Recipe r WHERE category ILIKE ?1 " +
            "ORDER BY timestamp DESC")
    Optional<List<Recipe>> findByCategory(String category);


    @Query("SELECT r FROM Recipe r WHERE name ILIKE %?1% " +
            "ORDER BY timestamp DESC")
    Optional<List<Recipe>> findByName(String name);



}
