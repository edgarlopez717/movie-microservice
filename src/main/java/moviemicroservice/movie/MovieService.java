package moviemicroservice.movie;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
	@Autowired
	MovieRepository movieRepository;
	
	public List<Movie> getAllMovies(Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		List<Movie> movies = movieRepository.findAll(pageable).getContent();
		return movies;
	}
	
	public Optional<Movie> findMovieById(Integer id) {
		return movieRepository.findById(id);
	}
	
	public Optional<Movie> findMovieByName(String title) {
		return movieRepository.findByTitleIgnoreCase(title);
	}
	
	public List<Movie> getMoviesReleasedInYear(Integer year, Integer pageNumber, Integer pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("releaseDate").descending());
		List<Movie> movies = movieRepository.findByReleaseDateStartsWith(String.valueOf(year), pageable);
		return movies;
	}
}
