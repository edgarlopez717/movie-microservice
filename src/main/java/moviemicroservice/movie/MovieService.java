package moviemicroservice.movie;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
}
