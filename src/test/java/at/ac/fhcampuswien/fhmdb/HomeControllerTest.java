package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeControllerTest {
    private List<Movie> movies;
    private List<Movie> moviesOrder;
    private List<Movie> moviesFilter;


    @BeforeEach
    void setUp() {
    }

    @Test
    void testSearchBySearchItem_caseInsensitive_All() {
        String searchItem = "A";
        List<Movie> result = movies
                .stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(searchItem.toLowerCase()) || movie.getDescription().toLowerCase().contains(searchItem.toLowerCase()))
                .toList();

        assertEquals(18, result.size());
    }

    @Test
    void testSearchByDescriptionOnly_caseInsensitive() {
        String searchItem = "a betraYED Roman generAl Seeks revenge";
        List<Movie> result = movies
                .stream().
                filter(movie -> movie.getDescription().toLowerCase().contains(searchItem.toLowerCase()))
                .toList();

        assertEquals(1, result.size());

    }

    @Test
    void testSearchByTitelOnly_caseInsensitive() {
        String searchItem = "matRIx";
        List<Movie> result = movies
                .stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(searchItem.toLowerCase()))
                .toList();

        assertEquals(1, result.size());
        assertEquals("The Matrix", result.get(0).getTitle());
    }

    @Test
    void testSearchBySearchItem_caseInsensitive_Some() {
        String searchItem = "Life";
        List<Movie> result = movies
                .stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(searchItem.toLowerCase()) || movie.getDescription().toLowerCase().contains(searchItem.toLowerCase()))
                .toList();

        assertEquals(3, result.size());
        assertEquals("Life of Pi", result.get(0).getTitle());
        assertEquals("Forrest Gump", result.get(1).getTitle());
        assertEquals("Toy Story", result.get(2).getTitle());
    }

    @Test
    void testSearchBySearchItem_caseSensitive_Some() {
        String searchItem = "Life";

        List<Movie> result = movies
                .stream()
                .filter(movie -> movie.getTitle().contains(searchItem) || movie.getDescription().contains(searchItem))
                .toList();

        assertEquals(1, result.size());
        assertEquals("Life of Pi", result.get(0).getTitle());
    }

    @Test
    void testSearchBySearchItem_caseSensitive_None() {
        String searchItem = "MAfia";

        List<Movie> result = movies
                .stream()
                .filter(movie -> movie.getTitle().contains(searchItem) || movie.getDescription().contains(searchItem))
                .toList();

        assertEquals(0, result.size());
    }

    @Test
    void testFilterByGenre() {
        // Assume a genre filter method exists
        HomeController homeController = new HomeController();

        //List<Movie> filteredMovies = homeController.filterByGenre(movies, "DRAMA");
        List<Movie> filteredMovies = new ArrayList<>();
        /*
        assertEquals(3, filteredMovies.size());
        assertTrue(filteredMovies.stream().anyMatch(m -> m.getTitle().equals("The Godfather")));
        assertTrue(filteredMovies.stream().anyMatch(m -> m.getTitle().equals("Life of Pi")));
        assertTrue(filteredMovies.stream().anyMatch(m -> m.getTitle().equals("Titanic")));

         */
    }

    @Test
    void testSortMoviesAscending() {
        List<Movie> sortedMovies = movies
                .stream()
                .sorted((m1, m2) -> m1.getTitle().compareToIgnoreCase(m2.getTitle()))
                .toList();

        assertEquals("Finding Nemo", sortedMovies.get(0).getTitle());
        assertEquals("Toy Story", sortedMovies.get(sortedMovies.size() - 1).getTitle());
    }

    @Test
    void testSortMoviesDescending() {
        List<Movie> sortedMovies = movies
                .stream()
                .sorted((m1, m2) -> m2.getTitle().compareToIgnoreCase(m1.getTitle()))
                .toList();

        assertEquals("Toy Story", sortedMovies.get(0).getTitle());
        assertEquals("Finding Nemo", sortedMovies.get(sortedMovies.size() - 1).getTitle());
    }

    @Test
    void testSortMoviesDescending_ManipulatedList() {
        List<Movie> sortedMovies = moviesOrder
                .stream()
                .sorted((m1, m2) -> m2.getTitle().compareToIgnoreCase(m1.getTitle()))
                .toList();

        assertEquals("Toy Story1 ", sortedMovies.get(0).getTitle());
        assertEquals("Joker1", sortedMovies.get(sortedMovies.size() - 1).getTitle());
    }

}