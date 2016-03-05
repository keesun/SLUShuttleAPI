package me.whiteship.repository;

import me.whiteship.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Keesun Baik
 */
public interface StationRepository extends JpaRepository<Station, Integer> {
}
