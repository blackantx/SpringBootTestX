package com.junianto.apps.test2.module.admin.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;


@SuppressWarnings("unused")
@Component
public class BaseServiceMod {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public static NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
	public static JdbcTemplate jdbcTemplate = null;

	@Autowired
	private DataSource dataSource;
	
	public BaseServiceMod() {}
	
	
    @PostConstruct
    public void initApplication() {
    	namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    	jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		BaseServiceMod.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		BaseServiceMod.jdbcTemplate = jdbcTemplate;
	}
	
}
