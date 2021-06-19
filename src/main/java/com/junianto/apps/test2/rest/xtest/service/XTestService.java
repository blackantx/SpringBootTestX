package com.junianto.apps.test2.rest.xtest.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import com.junianto.apps.test2.module.admin.service.BaseServiceMod;
import com.junianto.apps.test2.rest.xtest.dto.PersonelDTO;
import com.junianto.apps.test2.util.UtilCollectionX;

@SuppressWarnings("unused")
@Configuration
@Component("XTestService")
public class XTestService extends BaseServiceMod {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String PERSONEL_LIST_QUERY = "select * from x_personal";
    private final String PERSONEL_LIST_INSERT = "insert x_personal(name,phone) "
    		+ "values(:name, :phone) ";
    private final String PERSONEL_LIST_UPDATE = "update x_personal set name=:name, phone=:phone, "
    		+ "id=:id ";

    
	public XTestService() {}
	
	public List<PersonelDTO> getListPersonel() {
    	log.debug("#>getListPersonel ");
    	List<PersonelDTO> list = new ArrayList<PersonelDTO>();
    	
		Integer result = 0;
		try {
	        List<Map<String, Object>> rows = jdbcTemplate.queryForList(PERSONEL_LIST_QUERY);
			HashMap<String, String> map = new HashMap<String, String>();
	        for (Map<String, Object> row : rows) {
	        	PersonelDTO dto = new PersonelDTO();
	        	
	        	dto.setId(row.get("id").toString());
	        	dto.setName(row.get("name").toString());
	        	dto.setPhone(row.get("phone").toString());
	        	
	        	list.add(dto);
	        }

		} catch (Exception e) {
			log.error("getListPersonel exception: "+UtilCollectionX.getException(e));
		}

		return list;
	}


	public Integer updatePersonelList(PersonelDTO dto) throws Exception {
    	log.debug("#>updatePersonelList : ");

		Integer result = 0;
		try {
			if(dto.getId().equals("")) {
				namedParameterJdbcTemplate.update(PERSONEL_LIST_INSERT,
						new MapSqlParameterSource()
						.addValue("name", dto.getName(), Types.VARCHAR)
						.addValue("phone", dto.getPhone(), Types.VARCHAR)
		
					);
				
			}else {
				namedParameterJdbcTemplate.update(PERSONEL_LIST_UPDATE,
						new MapSqlParameterSource()
						.addValue("id", dto.getId(), Types.INTEGER)
						.addValue("name", dto.getName(), Types.VARCHAR)
						.addValue("phone", dto.getPhone(), Types.VARCHAR)
					);
			}

		} catch (Exception e) {
			log.info("updateChannelList : "+result);
			log.error(UtilCollectionX.getException(e));
			throw new Exception(e);
		}

		return result;
	}
	
//--------------	
	private String isNull(Object s){
		return (null==s||s.equals("null"))?"":s.toString();
	}
	
}
