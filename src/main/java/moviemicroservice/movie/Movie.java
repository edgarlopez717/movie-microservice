package moviemicroservice.movie;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {
	@Id
	@Column(name = "movieid")
	private Integer movieId;
	
	@Column(name = "imdbid")
	private String imdbId;
	
	private String title;
	
	private String overview;
	
	@Column(name = "productioncompanies")
	private String productionCompanies;
	
	@Column(name = "releasedate")
	private String releaseDate;
	
	private Integer budget;
	
	private Integer revenue;
	
	private Double runtime;
	
	private String language;
	
	private String genres;
	
	private String status;
	
	
	public Integer getMovieId() {
		return movieId;
	}
	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}
	public String getImdbId() {
		return imdbId;
	}
	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getProductionCompanies() {
		return productionCompanies;
	}
	public void setProductionCompanies(String productionCompanies) {
		this.productionCompanies = productionCompanies;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Integer getBudget() {
		return budget;
	}
	public void setBudget(Integer budget) {
		this.budget = budget;
	}
	public Integer getRevenue() {
		return revenue;
	}
	public void setRevenue(Integer revenue) {
		this.revenue = revenue;
	}
	public Double getRuntime() {
		return runtime;
	}
	public void setRuntime(Double runtime) {
		this.runtime = runtime;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getGenres() {
		return genres;
	}
	public void setGenres(String genres) {
		this.genres = genres;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
