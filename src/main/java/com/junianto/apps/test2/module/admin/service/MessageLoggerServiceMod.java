package com.junianto.apps.test2.module.admin.service;

import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import com.junianto.apps.test2.util.UtilCollectionX;


@SuppressWarnings("unused")
@Configuration
@Component("MessageLoggerServiceMod")
public class MessageLoggerServiceMod extends BaseServiceMod {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    private final String insertLogMessageQuery = "INSERT INTO log_message(reff_no, msg_type, msg_service, response_code, msg_data, ipaddress,channel_id,endpoint_id) VALUES(:reff_no, :msg_type, :msg_service, :response_code, :msg_data, :ipaddress,:channel_id,:endpoint_id)";
    private final String insertLogMessageQuery2 = "INSERT INTO log_message(created_by,reff_no, msg_type, msg_service, response_code, msg_data, ipaddress,channel_id,endpoint_id) "
    		+ "VALUES(:created_by, :reff_no, :msg_type, :msg_service, :response_code, :msg_data, :ipaddress,:channel_id,:endpoint_id)";
    
	public MessageLoggerServiceMod() {
	}

	public Integer insertLogMessage(String reff_no, String msg_type, String msg_service, String msg_data, String ipaddress, String response_code,
			String channel_id,
			String endpoint_id ) {
//    	log.debug("#>insertLogMessage : "+reff_no+"/"+msg_type+"/"+msg_service+"/"+msg_data.length()+"/"+ipaddress+"/"+response_code);

		Integer result = 0;
		
		try {
			
			namedParameterJdbcTemplate.update(insertLogMessageQuery,
					new MapSqlParameterSource()
						.addValue("reff_no", reff_no, Types.VARCHAR)
						.addValue("msg_type", msg_type, Types.VARCHAR)
						.addValue("msg_service", msg_service.replace("/", ""), Types.VARCHAR)
						.addValue("response_code", response_code, Types.VARCHAR)
						.addValue("msg_data", msg_data, Types.LONGVARCHAR)
						.addValue("ipaddress", ipaddress, Types.VARCHAR)
						.addValue("channel_id", Integer.valueOf(channel_id), Types.INTEGER)
						.addValue("endpoint_id", Integer.valueOf(endpoint_id), Types.INTEGER)
					);

		} catch (Exception e) {
			log.info("insertLogMessage : "+result+" sql:"+insertLogMessageQuery);
			log.error(UtilCollectionX.getException(e));
		}
		
		return result;
	}

	public Integer insertLogMessageCreateBy(String reff_no, String msg_type, String msg_service, String msg_data, 
			String ipaddress, String response_code,String createBy) {
//    	log.debug("#>insertLogMessage : "+reff_no+"/"+msg_type+"/"+msg_service+"/"+msg_data.length()+"/"+ipaddress+"/"+response_code);

		Integer result = 0;
		try {
			namedParameterJdbcTemplate.update(insertLogMessageQuery2,
					new MapSqlParameterSource()
						.addValue("reff_no", reff_no, Types.VARCHAR)
						.addValue("msg_type", msg_type, Types.VARCHAR)
						.addValue("msg_service", msg_service, Types.VARCHAR)
						.addValue("response_code", response_code, Types.VARCHAR)
						.addValue("msg_data", msg_data, Types.LONGVARCHAR)
						.addValue("ipaddress", ipaddress, Types.VARCHAR)
						.addValue("channel_id", Integer.valueOf("0"), Types.INTEGER)
						.addValue("endpoint_id", Integer.valueOf("0"), Types.INTEGER)
						.addValue("created_by", createBy, Types.VARCHAR)
					);

		} catch (Exception e) {
//			log.info("insertLogMessageCreateBy exception : "+result+" sql:"+SubrogasiConstanta.insertLogMessageQuery2);
			log.error("insertLogMessageCreateBy exception : "+UtilCollectionX.getException(e));
		}

		
		return result;
	}
	
	
}
