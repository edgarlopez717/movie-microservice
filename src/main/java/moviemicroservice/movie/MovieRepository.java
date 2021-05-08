package moviemicroservice.movie;

import org.springframework.data.repository.PagingAndSortingRepository;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete
public interface MovieRepository extends PagingAndSortingRepository<Movie, Integer> {

}
