package me.whiteship.repository;

import me.whiteship.domain.Shuttle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Keesun Baik
 */
public interface ShuttleRepository extends JpaRepository<Shuttle, Integer> {
}
