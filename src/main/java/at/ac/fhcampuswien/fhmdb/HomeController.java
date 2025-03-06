package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    @FXML
    private JFXButton searchBtn, sortBtn, clearBtn;

    @FXML
    private TextField searchField;

    @FXML
    private JFXListView<Movie> movieListView;

    @FXML
    private JFXComboBox<String> genreComboBox;

    private boolean isAscending = true;
    private List<Movie> allMovies;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allMovies = Movie.initializeMovies();
        observableMovies.addAll(allMovies);

        // Create filter & sort lists
        FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies, movie -> true);
        SortedList<Movie> sortedMovies = new SortedList<>(filteredMovies);
        movieListView.setItems(sortedMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        // Initialize UI elements
        initializeGenreComboBox();
        initializeButtonHandlers(filteredMovies, sortedMovies);
    }

    /**
     * Populates genre filter dropdown.
     */
    private void initializeGenreComboBox() {
        genreComboBox.getItems().addAll(Arrays.stream(Genre.values())
                .map(Enum::name)
                .toList());
        genreComboBox.setPromptText("Filter by Genre");
    }

    /**
     * Sets up button event handlers.
     */
    private void initializeButtonHandlers(FilteredList<Movie> filteredMovies, SortedList<Movie> sortedMovies) {
        searchBtn.setText("Search");
        searchBtn.setOnAction(event -> searchMovies(filteredMovies));

        clearBtn.setText("Clear");
        clearBtn.setOnAction(event -> clearFilters(filteredMovies));

        sortBtn.setOnAction(event -> {
            toggleSorting(sortedMovies);
        });
    }

    /**
     * Searches movies based on genre and query.
     */
    private void searchMovies(FilteredList<Movie> filteredMovies) {
        String genre = genreComboBox.getSelectionModel().getSelectedItem();
        String query = searchField.getText();
        filteredMovies.setPredicate(movie -> matchesQuery(movie, query) && matchesGenre(movie, genre));
    }

    /**
     * Clears filters and resets the movie list.
     */
    private void clearFilters(FilteredList<Movie> filteredMovies) {
        searchField.clear();
        genreComboBox.getSelectionModel().clearSelection();
        filteredMovies.setPredicate(movie -> true);
    }

    /**
     * Toggles sorting between ascending & descending order.
     */
    protected void toggleSorting(SortedList<Movie> sortedMovies) {
        sortedMovies.setComparator(isAscending ?
                Comparator.comparing(Movie::getTitle) :
                Comparator.comparing(Movie::getTitle).reversed());

        sortBtn.setText(isAscending ? "Sort (desc)" : "Sort (asc)");
        isAscending = !isAscending;
    }

    /**
     * Checks if a movie matches the search query.
     */
    protected boolean matchesQuery(Movie movie, String query) {
        return query == null || query.isEmpty() || movie.getTitle().toLowerCase().contains(query.toLowerCase());
    }

    /**
     * Checks if a movie matches the selected genre.
     */
    protected boolean matchesGenre(Movie movie, String genre) {
        return genre == null || genre.isEmpty() || movie.getGenres().contains(Genre.valueOf(genre));
    }

}
