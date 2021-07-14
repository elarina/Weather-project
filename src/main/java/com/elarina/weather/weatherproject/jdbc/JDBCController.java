package com.elarina.weather.weatherproject.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.elarina.weather.weatherproject.model.Temperature;
import com.elarina.weather.weatherproject.model.Town;


public class JDBCController {
	
	JdbcTemplate jdbcTemplate;
	
	public JDBCController(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private static final Logger log = LoggerFactory.getLogger(JDBCController.class);
	
	
	public List<Town> queryTowns() {
		List<Town> towns = new ArrayList<Town>();
		
		jdbcTemplate.query(
		        "SELECT name, code FROM towns ORDER BY name", new Object[] {},
		        (rs, rowNum) -> new Town(rs.getString("name"), rs.getInt("code"))
		    ).forEach(town -> towns.add(town));
		
		return towns;
	}
	
	public List<Temperature> queryAllTemperatures() {
		List<Temperature> temperatures = new ArrayList<Temperature>();
		
		jdbcTemplate.query(
		        "SELECT date, value, town_code FROM temperatures  "
		        + "as tm LEFT JOIN towns as tw ON tm.town_code=tw.code "
		        + "ORDER BY date DESC", new Object[] {},
		        (rs, rowNum) -> new Temperature(rs.getInt("value"), rs.getDate("date"), rs.getInt("town_code"))
		    ).forEach(temperature -> temperatures.add(temperature));
		
		return temperatures;
	}
}
