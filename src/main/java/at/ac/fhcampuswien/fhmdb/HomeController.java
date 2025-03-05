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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes
    @FXML
    public JFXButton searchBtn;
    @FXML
    public TextField searchField;
    @FXML
    public JFXListView<Movie> movieListView;
    @FXML
    public JFXComboBox<String> genreComboBox;
    @FXML
    public JFXButton sortBtn;
    public List<Movie> allMovies = Movie.initializeMovies();

    private FilteredList<Movie> filteredMovies;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list

        filteredMovies = new FilteredList<>(observableMovies, movie -> true);
        movieListView.setItems(filteredMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        genreComboBox.getItems().addAll(
                Arrays.stream(Genre.values())
                        .map(Enum::name)
                        .toArray(String[]::new)
        );

        genreComboBox.setPromptText("Filter by Genre");
        searchBtn.setOnAction(event -> {
            applyAllFilters(searchField.getText().toLowerCase().trim(),
                    Genre.valueOf(genreComboBox.getSelectionModel().getSelectedItem()));
        });


        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                sortBtn.setText("Sort (desc)");
                FXCollections.sort(observableMovies, Comparator.comparing(Movie::getTitle));
            } else {
                sortBtn.setText("Sort (asc)");
                FXCollections.sort(observableMovies, Comparator.comparing(Movie::getTitle).reversed());
            }
        });

    }

    public void applyAllFilters(String searchQuery, Genre genre) {
        List<Movie> filteredMovies = allMovies;
        if (!searchQuery.isEmpty()) {
            filteredMovies = filterByQuery(filteredMovies, searchQuery);
        }
        if (genre != null && !genre.equals(Genre.ALL)) {
            filteredMovies = filterByGenre(filteredMovies, Genre.valueOf(genre.toString()));
        }
        observableMovies.clear();
        observableMovies.addAll(filteredMovies);
    }


    public List<Movie> filterByQuery(List<Movie> movies, String query) {
        if (query == null || query.isEmpty()) return movies;
        return movies.stream()
                .filter(movie ->
                        movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                                movie.getDescription().toLowerCase().contains(query.toLowerCase())
                )
                .toList();
    }

    public List<Movie> filterByGenre(List<Movie> movies, Genre genre) {
        if (genre == null) return movies;
        return movies.stream()
                .filter(movie -> movie.getGenres().contains(genre))
                .toList();
    }

}