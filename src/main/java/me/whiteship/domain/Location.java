package me.whiteship.domain;

import lombok.Data;

import javax.persistence.Embeddable;

/**
 * @author whiteship
 */
@Data
@Embeddable
public class Location {

    private double latitude;

    private double longitude;

}
