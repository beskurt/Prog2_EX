package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (homeController.matchesQuery(movie, "Life")) {
                result.add(movie);
            }
        }
        assertEquals(1, result.size());
        assertEquals("Life Is Beautiful", result.get(0).getTitle());
    }

    @Test
    void testSearchMovies_ByTitle2() {
        int counter = 0;
        for (Movie movie : movies) {
            if (homeController.matchesQuery(movie, "a")) {
                counter++;
            }
        }
        assertEquals(4, counter);
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

    @Test
    void testSearchMovies_ByGenre1() {
        for (Movie movie : movies) {
            if (homeController.matchesGenre(movie, "FAMILY")) {
                assertEquals("Puss in Boots", movie.getTitle());
            }
        }
    }

    @Test
    void testSearchMovies_ByGenre2() {
        int counter = 0;
        for (Movie movie : movies) {
            if (homeController.matchesGenre(movie, "DRAMA")) {
                counter++;
            }
        }
        assertEquals(4, counter);
    }

    @Test
    void testSearchMovies_ByGenre3() {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (homeController.matchesGenre(movie, "ROMANCE")) {
                result.add(movie);
            }
        }
        assertEquals(2, result.size());
        assertEquals("Life Is Beautiful", result.get(0).getTitle());
        assertEquals("The Wolf of Wall Street", result.get(1).getTitle());
    }
}