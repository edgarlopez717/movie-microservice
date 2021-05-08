package moviemicroservice.movie;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import moviemicroservice.rating.RatingService;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTests {
	private static final int NUMBER_OF_MOVIES = 50;
	private static final String TITLE_OF_MOVIE = "Test title ";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MovieService movieService;
	@MockBean
	private RatingService ratingService;
	
	private static List<Movie> allMovies = new ArrayList<>();
	
	@BeforeClass
	public static void populateMovieRepository() {
		for(int i = 0; i < NUMBER_OF_MOVIES; i++) {
			Movie movie = new Movie();
			movie.setMovieId(i);
			movie.setTitle(TITLE_OF_MOVIE + i);
			allMovies.add(movie);
		}
	}
	
	@Test
	public void testGetAllMovies_ReturnsMovieInformation_IfAllExist() throws Exception {
		
		given(movieService.getAllMovies(0, 50))
			.willReturn(allMovies);
		
		MvcResult result = mockMvc.perform(get("/movie/all")
				.contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		assertEquals(NUMBER_OF_MOVIES, content.split("\n").length - 1);
	}
	
	@Test
	public void testGetMovieDetails_ReturnsDetails_IfFound() throws Exception {
		int id = 1;
		given(movieService.findMovieById(id)).willReturn(Optional.of(allMovies.get(id)));
		
		MvcResult result = mockMvc.perform(get("/movie/details?id=" + id)
				.contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		List<String> lines = Arrays.asList(content.split("\r\n"));
		
		String movieTitle = "";
		for(String line: lines) {
			if(line.contains("Title")) {
				movieTitle = line.split(":")[1].trim();
				break;
			}
		}
		
		assertEquals(TITLE_OF_MOVIE + id, movieTitle);
	}
	
	@Test
	public void testGetMovieDetails_ReturnsError_IfNotFound() throws Exception {
		int id = 61;
		given(movieService.findMovieById(id)).willReturn(Optional.empty());
		
		mockMvc.perform(get("/movie/details?id=" + id)
				.contentType(MediaType.TEXT_PLAIN))
				.andExpect(status().isNotFound());
	}
	
 }
