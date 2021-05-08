package moviemicroservice.movie;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.apache.commons.text.TextStringBuilder;

import com.google.gson.Gson;

public class MovieUtils {
	
	//TODO Find a better way to format the output.
	public static String moviePageToString(List<Movie> movies) {
		TextStringBuilder sb = new TextStringBuilder();
		sb.appendln("IMDB ID, Title, Genres, Release Date, Budget");
		movies.stream()
			.forEach(movie -> {
				sb.append(movie.getImdbId() + ",");
				sb.append(movie.getTitle() + ",");
				sb.append(jsonToGenreString(movie.getGenres()) + ",");
				sb.append(movie.getReleaseDate() +  ",");
				sb.appendln(NumberFormat.getCurrencyInstance().format(movie.getBudget()));
			});
		
		return sb.toString();
	}
	
	//TODO Find a better way to format the output.
	public static String getMovieDetails(Movie movie, List<Double> ratings) {
		OptionalDouble averageRating = averageRating(ratings);

		TextStringBuilder sb = new TextStringBuilder();
		sb.appendln("Details For Movie");
		sb.appendln("IMDB ID: " + movie.getImdbId());
		sb.appendln("Title: " + movie.getTitle());
		sb.appendln("Description: " + movie.getOverview());
		sb.appendln("Release Date: " + movie.getReleaseDate());
		sb.appendln("Budget: " + NumberFormat.getCurrencyInstance().format(movie.getBudget()));
		sb.appendln("Runtime: " + movie.getRuntime());
		sb.appendln("Ratings average: " + (averageRating.isPresent() ? averageRating.getAsDouble() : "No ratings for movie"));
		sb.appendln("Genres: " + jsonToGenreString(movie.getGenres()));
		sb.appendln("Original Language: " + movie.getLanguage());
		sb.appendln("Production companies: " + movie.getProductionCompanies());
		return sb.toString();
	}
	
	private static final OptionalDouble averageRating(List<Double> ratings) {
		return ratings.stream()
			.mapToDouble(val -> val)
			.average();
	}
	
	public static String jsonToGenreString(String jsonString) {
		Gson gson = new Gson();
		List<Genre> genres = Arrays.asList(gson.fromJson(jsonString, Genre[].class));
		
		List<String> genreNames = genres.stream()
			.map(genre -> genre.getName())
			.collect(Collectors.toList());
		
		return genreNames.toString();
	}
}
