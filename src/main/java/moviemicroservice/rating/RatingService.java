package moviemicroservice.rating;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
	@Autowired
	RatingRepository ratingRepository;
	
	public List<Double> getRatingsByMovieId(Integer movieId) {
		List<Rating> ratings = ratingRepository.findByMovieId(movieId);
		return ratings.stream()
			.map(rating -> rating.getRating())
			.collect(Collectors.toList());
	}
}
