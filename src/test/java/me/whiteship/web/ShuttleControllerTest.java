package me.whiteship.web;

import me.whiteship.Application;
import me.whiteship.domain.Station;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Keesun Baik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
public class ShuttleControllerTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ModelMapper modelMapper;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void di() {
        assertNotNull(wac);
    }

    @Test
    public void testFind_OK() throws Exception {
        mockMvc.perform(get("/from/trb/to/blackfoot"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.departingStation.name", is(Station.TRB.getName())))
                .andExpect(jsonPath("$.arrivingStation.name", is(Station.BLACKFOOT.getName())));

        mockMvc.perform(get("/from/Day1North/to/blackfoot"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.departingStation.name", is(Station.DAY_1_NORTH.getName())))
                .andExpect(jsonPath("$.arrivingStation.name", is(Station.BLACKFOOT.getName())));

        mockMvc.perform(get("/from/d1n/to/blackfoot"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.departingStation.name", is(Station.DAY_1_NORTH.getName())))
                .andExpect(jsonPath("$.arrivingStation.name", is(Station.BLACKFOOT.getName())));
    }

    @Test
    public void testFind_BAD_REQUEST() throws Exception {
        mockMvc.perform(get("/from/1/to/2"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Not found '1' station. Please check if the name of station is correct."));
    }

    @Test
    public void testShuttle() throws Exception {
        mockMvc.perform(get("/shuttle/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testShuttleNotFoundException() throws Exception {
        mockMvc.perform(get("/shuttle/sdf"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}