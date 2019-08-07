package co.grandcircus.Lab26;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.grandcircus.Lab26.dao.MovieDao;
import co.grandcircus.Lab26.entity.MovieData;

@RestController
public class MovieApiController {

	@Autowired
	private MovieDao dao;

	// #1/2/8
	@GetMapping("/movies")
	public List<MovieData> listMovies(@RequestParam(value = "category", required = false) String cat,
			@RequestParam(value = "title", required = false) String title) {
		if (((cat == null || cat.isEmpty()) && ((title == null || title.isEmpty()))) ) {
			return dao.findAll();
		} else if (cat == null || cat.isEmpty()) {
			return dao.findByTitleContainsIgnoreCase(title);

		} else {
			return dao.findByCategoryContainsIgnoreCase(cat);
		}
	}

	// #3/4
	@GetMapping("/movies/random")
	public MovieData randomMovie(@RequestParam(value = "category", required = false) String cat) {
		if (cat == null || cat.isEmpty()) {
			long id = 1 + (long) (Math.random() * 9);
			return dao.findById(id).get();
		} else {
			List<MovieData> movies = dao.findByCategoryContainsIgnoreCase(cat);
			int catSize = movies.size();
			int randMovie = (int) (Math.random() * catSize);
			return movies.get(randMovie);
		}
	}

	// #5
	@GetMapping("/movies/random-list")
	public List<MovieData> randomMovieList(@RequestParam("quantity") Integer quantity) {
		List<MovieData> movieList = dao.findAll();
		List<MovieData> randMovies = new ArrayList<MovieData>();
		Collections.shuffle(movieList);
		for (int i = 0; i < quantity; i++) {
			randMovies.add(movieList.get(i));
		}

		return randMovies;
	}

	// #6
	@GetMapping("/movies/{id}")
	public MovieData movieByID(@PathVariable("id") Long id) {
		return dao.findById(id).get();
	}
	
	//#7
	@GetMapping("/categories")
	public List<String> categories() {
		List<MovieData> movieList = dao.findAll();
		Map<String, String> map = new HashMap<>();
		for (MovieData movies : movieList) {
			map.put(movies.getCategory(), movies.getTitle());
		}
		List<String> result = new ArrayList<String>(map.keySet());
		return result;
	}

}
