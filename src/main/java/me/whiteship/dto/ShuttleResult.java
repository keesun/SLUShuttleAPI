package me.whiteship.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Keesun Baik
 */
@Data
public class ShuttleResult {

    private List<ShuttleDTOs.ForShuttleResult> shuttles;

}
