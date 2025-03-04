package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {
    private List<Movie> movies;
    private List<Movie> moviesOrder;
    private List<Movie> moviesFilter;


    @BeforeEach
    void setUp() {
        movies = List.of(
                //ChatGPT movies
                new Movie("Inception", "A mind-bending thriller about dreams."),
                new Movie("The Godfather", "A mafia family drama."),
                new Movie("Life of Pi", "A survival story of a boy and a tiger."),
                new Movie("Titanic", "A tragic love story on a sinking ship."),
                new Movie("Interstellar", "A space exploration journey."),
                new Movie("The Dark Knight", "A Batman movie with the Joker."),
                new Movie("Schindler's List", "A true story of a businessman saving Jews during WWII."),
                new Movie("The Shawshank Redemption", "A man imprisoned for a crime he didn't commit."),
                new Movie("Forrest Gump", "A slow-witted man’s extraordinary life journey."),
                new Movie("Gladiator", "A betrayed Roman general seeks revenge."),
                new Movie("The Matrix", "A hacker discovers the truth about reality."),
                new Movie("Pulp Fiction", "A series of interconnected crime stories."),
                new Movie("The Lion King", "A young lion prince flees his kingdom."),
                new Movie("Finding Nemo", "A father fish searches for his lost son."),
                new Movie("The Silence of the Lambs", "A young FBI agent seeks help from a cannibal."),
                new Movie("Saving Private Ryan", "A WWII mission to save a paratrooper."),
                new Movie("Joker", "An origin story of the infamous villain."),
                new Movie("Toy Story", "Toys come to life when no one is looking."),
                new Movie("Frozen", "A princess with icy powers embraces her true self."));
        moviesOrder = List.of(
                new Movie("Joker4", "An origin story of the infamous villain."),
                new Movie("Joker2", "An origin story of the infamous villain."),
                new Movie("Joker1", "An origin story of the infamous villain."),
                new Movie("Joker11", "An origin story of the infamous villain."),
                new Movie("Toy Story 4", "Toys come to life when no one is looking."),
                new Movie("Toy Story1 ", "Toys come to life when no one is looking."),
                new Movie("Toy Story 1", "Toys come to life when no one is looking."),
                new Movie("Toy Story 3", "Toys come to life when no one is looking."));
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