package me.whiteship.repository;

import me.whiteship.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Keesun Baik
 */
public interface BuildingRepository extends JpaRepository<Building, Integer> {
}
