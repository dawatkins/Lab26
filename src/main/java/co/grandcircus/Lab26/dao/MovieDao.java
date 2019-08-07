package co.grandcircus.Lab26.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.grandcircus.Lab26.entity.MovieData;

public interface MovieDao extends JpaRepository<MovieData, Long>{
	
	List<MovieData> findByTitleContainsIgnoreCase(String nameMatch);
	List<MovieData> findByCategoryContainsIgnoreCase(String catMatch);
}
