package moviemicroservice.rating;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface RatingRepository extends PagingAndSortingRepository<Rating, Integer> {
	List<Rating> findByMovieId(@Param("movieid") Integer movieId);
}
