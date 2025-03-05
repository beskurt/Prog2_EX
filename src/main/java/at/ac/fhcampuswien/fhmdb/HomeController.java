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
    @FXML
    public JFXButton clearBtn;

    public List<Movie> allMovies = Movie.initializeMovies();
    private boolean isAscending = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);

        //For Filter and Sort Logic
        FilteredList<Movie> filteredMovies = new FilteredList<>(observableMovies, movie -> true);
        SortedList<Movie> sortedMovies = new SortedList<>(filteredMovies);

        movieListView.setItems(sortedMovies);
        movieListView.setCellFactory(movieListView -> new MovieCell());

        //Adds all genre
        genreComboBox.getItems().addAll(
                Arrays.stream(Genre.values())
                        .map(Enum::name)
                        .toArray(String[]::new)
        );

        //Search button
        genreComboBox.setPromptText("Filter by Genre");
        searchBtn.setText("Search");
        searchBtn.setOnAction(event -> {
            search(filteredMovies, genreComboBox.getSelectionModel().getSelectedItem(),
                    searchField.getText());
        });

        //Clear button
        clearBtn.setText("Clear");
        clearBtn.setOnAction(event -> {
            clearFilters(filteredMovies, sortedMovies);
        });

        //Sort button asc/desc
        sortBtn.setOnAction(actionEvent -> {
            if (isAscending) {
                sortedMovies.setComparator(Comparator.comparing(Movie::getTitle));
                sortBtn.setText("Sort (desc)");
            } else {
                sortedMovies.setComparator(Comparator.comparing(Movie::getTitle).reversed());
                sortBtn.setText("Sort (asc)");
            }
            isAscending = !isAscending;
        });

    }

    //Method for search button
    public void search(FilteredList<Movie> filteredMovies, String genre, String query) {
        filteredMovies.setPredicate(movie -> {
            boolean matchesQuery = (query == null || query.isEmpty()) ||
                    movie.getTitle().toLowerCase().contains(query.toLowerCase());
            boolean matchesGenre = (genre == null || genre.isEmpty()) ||
                    movie.getGenres().contains(Genre.valueOf(genre));
            return matchesQuery && matchesGenre;
        });
    }

    //Method for clear button
    private void clearFilters(FilteredList<Movie> filteredList, SortedList<Movie> sortedList) {
        searchField.clear();
        genreComboBox.getSelectionModel().clearSelection();
        filteredList.setPredicate(movie -> true);
        isAscending = true;
        sortBtn.setText("Sort (asc)");
        sortedList.setComparator(Comparator.comparing(Movie::getTitle));
    }

}