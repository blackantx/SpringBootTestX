package com.junianto.apps.test2.rest.xtest.resource;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.junianto.apps.test2.module.admin.service.MessageLoggerServiceMod;
import com.junianto.apps.test2.rest.xtest.dto.BaseRequest;
import com.junianto.apps.test2.rest.xtest.dto.PersonelDTO;
import com.junianto.apps.test2.rest.xtest.dto.XTestRequestDTO;
import com.junianto.apps.test2.rest.xtest.dto.response.Response;
import com.junianto.apps.test2.rest.xtest.service.XTestService;
import com.junianto.apps.test2.util.ConstantaX;
import com.junianto.apps.test2.util.UtilCollectionX;




@SuppressWarnings("unused")
@RestController
@RequestMapping(XTestConstants.API_ROOT)
public class XTestResource {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private MessageLoggerServiceMod msgLogger;
    @Autowired
    private XTestService xtestSvc;

    public XTestResource() {}		
    
    @PostConstruct
    public void initApplication() {
    	msgLogger = new MessageLoggerServiceMod();
    }
    
    public XTestResource(MessageLoggerServiceMod msgLogger) {
    	this.msgLogger=msgLogger;
    }

	@RequestMapping(value = XTestConstants.API_PERSONEL_SUBMIT, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> submitPersonel(@RequestBody String payload, HttpServletRequest request) {
    	log.debug("#REST INCOMING submitPersonel.. "+payload);
		return execRequest(payload, request,XTestConstants.API_PERSONEL_SUBMIT);
	}	
	
	private ResponseEntity<String> execRequest(@RequestBody String payload, HttpServletRequest request, String reqType) {
    	log.debug("#execRequest.. ");
    	String svcName = reqType.replace("/", "");
 
    	Response resp = new Response();		
    	Gson gson = new Gson();

    	String reffNumber = "";
    	String clientIpAddress = request.getHeader("X-FORWARDED-FOR");
        String localIpAddress = UtilCollectionX.getClientIP();
		if (clientIpAddress == null) clientIpAddress = request.getRemoteAddr(); 

		
		try {
			XTestRequestDTO dto = new ObjectMapper().readValue(payload, XTestRequestDTO.class);
			PersonelDTO personel = new PersonelDTO();
			personel.setName(dto.getName());
			personel.setPhone(dto.getPhone());
			
			reffNumber = dto.getReffNumber();
	        String response = "{}";

			//add msg log for incoming from channel
			msgLogger.insertLogMessage(dto.getReffNumber(), ConstantaX.MSG_IN, svcName, payload, clientIpAddress, "","0","0");

	        
			//submit insert into db
			int result = xtestSvc.updatePersonelList(personel);

			resp.setReffNumber(reffNumber);
			resp.setResponseCode(ConstantaX.RC_00);
			resp.setResponseDesc(ConstantaX.SUCCESS);
			resp.setResponseData(gson.toJson(resp));
	
			//add msg log for incoming from endpoint host
			msgLogger.insertLogMessage(dto.getReffNumber(), ConstantaX.MSG_RESP, svcName, response, localIpAddress, resp.getResponseCode(),"0","0");

			log.info("#execRequest responseData: "+resp.getResponseData());

			return new ResponseEntity<String>(resp.getResponseData(), HttpStatus.OK);
       }catch (Exception e) {
        	e.printStackTrace();
			log.error(UtilCollectionX.getException(e));
			resp.setReffNumber(reffNumber);
			resp.setResponseCode(ConstantaX.RC_XX);
			resp.setResponseDesc("Exception. Cek data: "+e.getMessage());
			resp.setResponseData(gson.toJson(resp));
			
			//add msg log for incoming from channel
			msgLogger.insertLogMessage(reffNumber, ConstantaX.MSG_OUT, svcName, resp.getResponseData(), localIpAddress, resp.getResponseCode(),"0","0");

			return new ResponseEntity<String>(resp.getResponseData(), HttpStatus.OK);
       }
		
    }
    
    
	@RequestMapping(value = XTestConstants.API_PERSONEL_LIST, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<String> getPersonalList(@RequestBody String payload, HttpServletRequest request) {
    	log.debug("#getPersonalList.. ");
    	String svcName = XTestConstants.API_PERSONEL_LIST.replace("/", "");
 
    	Response resp = new Response();		
    	Gson gson = new Gson();

    	String reffNumber = "";
    	String clientIpAddress = request.getHeader("X-FORWARDED-FOR");
        String localIpAddress = UtilCollectionX.getClientIP();
		if (clientIpAddress == null) clientIpAddress = request.getRemoteAddr(); 

		
		try {
			BaseRequest dto = new ObjectMapper().readValue(payload, BaseRequest.class);
			
			reffNumber = dto.getReffNumber();
	        String response = "{}";

			//add msg log for incoming from channel
			msgLogger.insertLogMessage(dto.getReffNumber(), ConstantaX.MSG_IN, svcName, payload, clientIpAddress, "","0","0");

	        
			//submit insert into db
			List<PersonelDTO> result = xtestSvc.getListPersonel();

			resp.setReffNumber(reffNumber);
			resp.setResponseCode(ConstantaX.RC_00);
			resp.setResponseDesc(ConstantaX.SUCCESS);
			resp.setResponseObject(result);
			resp.setResponseData(gson.toJson(resp));
	
			//add msg log for incoming from endpoint host
			msgLogger.insertLogMessage(dto.getReffNumber(), ConstantaX.MSG_RESP, svcName, resp.getResponseData(), localIpAddress, resp.getResponseCode(),"0","0");

			log.info("#getPersonalList responseData: "+resp.getResponseData());

			return new ResponseEntity<String>(resp.getResponseData(), HttpStatus.OK);
       }catch (Exception e) {
        	e.printStackTrace();
			log.error(UtilCollectionX.getException(e));
			resp.setReffNumber(reffNumber);
			resp.setResponseCode(ConstantaX.RC_XX);
			resp.setResponseDesc("Exception. Cek data: "+e.getMessage());
			resp.setResponseData(gson.toJson(resp));
			
			//add msg log for incoming from channel
			msgLogger.insertLogMessage(reffNumber, ConstantaX.MSG_OUT, svcName, resp.getResponseData(), localIpAddress, resp.getResponseCode(),"0","0");

			return new ResponseEntity<String>(resp.getResponseData(), HttpStatus.OK);
       }
		
    }
    
  
    
    
    
}
