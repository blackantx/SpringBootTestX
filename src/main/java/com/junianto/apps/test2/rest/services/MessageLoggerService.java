package com.junianto.apps.test2.rest.services;

import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import com.junianto.apps.test2.module.admin.service.BaseServiceMod;
import com.junianto.apps.test2.util.UtilCollectionX;



@SuppressWarnings("unused")
@Component("MessageLoggerService")
public class MessageLoggerService extends BaseServiceMod {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String insertLogMessage = "INSERT INTO log_message(reff_no, msg_type, msg_service, response_code, msg_data, ipaddress,channel_id,endpoint_id) "
    		+ "VALUES (:reff_no, :msg_type, :msg_service, :response_code, :msg_data, :ipaddress,:channel_id,:endpoint_id)";
    

	public MessageLoggerService() {}

	public Integer insertLogMessage(String reff_no, String msg_type, String msg_service, String msg_data, String ipaddress, String response_code,
			String channel_id,
			String endpoint_id ) {
    	log.debug("#>insertLogMessage : "+reff_no+"/"+msg_type+"/"+msg_service+"/"+msg_data.length()+"/"+ipaddress+"/"+response_code);

		Integer result = null;
		
		try {
			
			namedParameterJdbcTemplate.update(
					insertLogMessage,
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
			log.info("insertLogMessage : "+result+" sql:"+insertLogMessage);
			log.error(UtilCollectionX.getException(e));
		}

		
		return result;
	}

}
