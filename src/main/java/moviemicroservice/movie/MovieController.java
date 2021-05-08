package moviemicroservice.movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import moviemicroservice.rating.RatingService;
import moviemicroservice.utils.ErrorResponse;
import moviemicroservice.utils.RecordNotFoundException;

@Validated
@Controller // This means that this class is a Controller
@RequestMapping(path="/movie") // This means URL's start with /demo (after Application path)
public class MovieController {
	public static final int DEFAULT_ENTRIES_PER_PAGE = 50;
	
	@Autowired
	private MovieService movieService;
	@Autowired
	private RatingService ratingService;


	/**
	 * Endpoint to list all movies (paginated). If page number is not specified the first page will be returned. 
	 * Usage: localhost:8080/movie/all?page=<integer> 
	 * @param pageNumber The number of the page to return.
	 * @return A string with a header row, followed by a new line for each movie.
	 */
	@GetMapping(path="/all", produces="text/plain")
	@ResponseBody
	public String getAllMovies(@RequestParam(defaultValue = "0", name = "page") @Min(value = 0, message = "Page number must be greater than 0.") Integer pageNumber) {
		List<Movie> movies = movieService.getAllMovies(pageNumber, DEFAULT_ENTRIES_PER_PAGE);
		
		return MovieUtils.moviePageToString(movies);
		//return movies; //Will display json
	}
	
	/**
	 * Endpoint to get the details of the movie specified. Currently only by ID. 
	 * Usage: localhost:8080/movie/details?id=<integer>
	 * @param movieId The id of the movie to get details.
	 * @return A string with the movie details.
	 */
	@GetMapping(path="/details", produces="text/plain")
	@ResponseBody
	public String getMovieDetails(@RequestParam(name = "id", required = false) Integer movieId,
			@RequestParam(name = "title", required = false) String movieTitle) {
		Optional<Movie> movie;
		if(Objects.nonNull(movieId)) {
			movie = movieService.findMovieById(movieId);
		} else {
			movie = movieService.findMovieByName(movieTitle);
		}
		
		if(movie.isPresent()) {
			List<Double> ratingsValues = ratingService.getRatingsByMovieId(movieId);
			return MovieUtils.getMovieDetails(movie.get(), ratingsValues);
		} else {
			throw new RecordNotFoundException("Record not found for movie: " + movieId + "," + movieTitle);
		}
	}
	
	@GetMapping(path="/search", produces="text/plain")
	@ResponseBody
	public String searchMovie(@RequestParam(name = "year") Integer year, 
			@RequestParam(defaultValue = "0", name = "page") @Min(value = 0, message = "Page number must be greater than 0.") Integer pageNumber) {
		List<Movie> movies = movieService.getMoviesReleasedInYear(year, pageNumber, DEFAULT_ENTRIES_PER_PAGE);
		
		if(movies.isEmpty()) {
			//Throw an error message or just send an empty list?
			throw new RecordNotFoundException("No movies were produced in the year: " + year);
		} else {
			return MovieUtils.moviePageToString(movies);
		}
	}
	
	//TODO Move these exception handlers to a different class.
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		
		ErrorResponse error = new ErrorResponse("Constraint violation error.", details);
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleMismatchException(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		
		ErrorResponse error = new ErrorResponse("Method argument does not match.", details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleRecordNotFoundException(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		
		ErrorResponse error = new ErrorResponse("Record was not found.", details);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	
	
}
