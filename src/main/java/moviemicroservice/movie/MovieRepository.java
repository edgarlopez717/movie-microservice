package moviemicroservice.movie;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete
public interface MovieRepository extends PagingAndSortingRepository<Movie, Integer> {
	List<Movie> findByReleaseDateStartsWith(String year, Pageable pageable);
	Optional<Movie> findByTitleIgnoreCase(String title);
}
