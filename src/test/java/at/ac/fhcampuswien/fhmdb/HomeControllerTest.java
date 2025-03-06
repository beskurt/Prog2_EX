package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {
    private HomeController homeController;
    private List<Movie> movies;

    @BeforeEach
    void setUp() {
        homeController = new HomeController();
        movies = Movie.initializeMovies();
    }

    /*** SEARCH TESTS ***/

    @Test
    void testSearchMovies_ByTitle1() {
        for (Movie movie : movies) {
            if (homeController.matchesQuery(movie, "Pi")) {
                assertEquals("Life Is Beautiful", movie.getTitle());
            }
        }
    }

    @Test
    void testSearchMovies_ByTitle2() {
        int counter = 0;
        for (Movie movie : movies) {
            if (homeController.matchesQuery(movie, "a")) {
                counter++;
            }
        }
        assertEquals(5, counter);
    }

    @Test
    void testSearchMovies_ByTitle3() {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (homeController.matchesQuery(movie, "The")) {
                result.add(movie);
            }
        }
        assertEquals(2, result.size());
        assertEquals("The Usual Suspects", result.get(0).getTitle());
        assertEquals("The Wolf of Wall Street", result.get(1).getTitle());
    }

}