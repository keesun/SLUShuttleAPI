package me.whiteship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author whiteship
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Building {

    /**
     * TRB, BLACKFOOT("SEA33"), ARIZONA("SEA29"), DAY_1_NORTH("SEA20"), BUS_TRAIN_TUNNEL, HOUSTON("SEA49"),
     * ROXANNE("SEA37"), UTAH("SEA32"), VARZEA("SEA30"), BRAZIL, BIGFOOT, NESSI, DELIGHT,
     * APOLLO, COLMAN_DOCK, CORAL, DAWSON("SEA27"), DOPPLER, MAYDAY("SEA51"), ALEXANDRIA("SEA18"),
     * KUMO, LEONA_ST_4TH, OTTER, PRIME("SEA36"), CONVENTION, WAC_6TH_UNION, PIKE_7TH, RIVET,
     * STACKHOUSE, GALAXY, JOHN_BROAD, LENORA_ST_4TH, STEWART_3RD, DENNY_HWY99;
     */

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private String sea;

    @Embedded
    private Location location;

}
