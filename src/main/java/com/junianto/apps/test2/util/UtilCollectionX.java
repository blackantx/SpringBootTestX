package com.junianto.apps.test2.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.castor.core.util.Base64Decoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;


@Component("UtilCollectionX")
public class UtilCollectionX {
    private final static Logger log = LoggerFactory.getLogger(UtilCollectionX.class);
    
    private DocumentBuilderFactory factory;
	private DocumentBuilder docBuilder;
	private Document doc;

	private static UtilCollectionX instance = null;
	private String[] day = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
	private String[] hari = { "Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu" };
	private HashMap<String, String> dayHari = new HashMap<String, String>();

	private String STR_LOG_PATH = "c:/logs/";
	private static Gson gson = new Gson();
	
	public UtilCollectionX() {
		// init day to hari
		for (int i = 0; i <= 6; i++)
			dayHari.put(day[i], hari[i]);
	}

	public static UtilCollectionX getInstance() {
		if (null == instance)
			instance = new UtilCollectionX();
		return instance;
	}

	public String replaceDayToHari(String day) {
		return dayHari.get(day);
	}

	public String getLocalDir(String param) {
		return ""; // Konstanta.OUT_FILE_PATH+(new
					// SimpleDateFormat("MM").format(new Date()));
	}

	public static String createLocalDir(String param) {
		// create local folder if not exists
		String str = param; // getLocalDir(param);
		File dir = new File(str);
		dir.mkdirs();

		return str;
	}

	public String padRight(String strSource, String strPad, int n) {
		while (strSource.length() < n) {
			strSource += strPad;
		}
		return strSource;
	}

	public String padLeft(String strSource, String strPad, int n) {
		while (strSource.length() < n) {
			strSource = strPad + strSource;
		}
		return strSource;
	}

	public static String getClientIP() {
		InetAddress IP = null;
		try {
			IP = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		// System.out.println("IP of my system is := "+IP.getHostAddress());

		return IP.getHostAddress();
	}

	@SuppressWarnings({ "finally", "unused" })
	public synchronized String getSequence() {
		// --- LOCAL VARIABLE
		NodeList xmlList;
		Node xmlNode;
		Transformer t;
		Result result;
		Source source;
		long xmlData0;
		long xmlData;
		String fileName;

		// --- ALGORITHM
		// initialization
		xmlData = 1;
		fileName = "conf/sequence.xml"; // "C:/QIBM/UserData/FrontEndConfigs/PNetAdapter/sequence.xml";

		try {
			factory = DocumentBuilderFactory.newInstance();
			docBuilder = factory.newDocumentBuilder();
			doc = docBuilder.newDocument();

			doc = this.docBuilder.parse(new File(fileName));
			xmlList = this.doc.getElementsByTagName("sequence");
			xmlNode = xmlList.item(0).getChildNodes().item(0);
			xmlData = Integer.parseInt(xmlNode.getNodeValue().toString());
			xmlData0 = xmlData;
			// return 1 for sequence number when last sequence is 999999
			xmlData = (xmlData == 999999) ? 1 : (++xmlData);
			// update sequence number on XML file
			xmlNode.setNodeValue(String.valueOf(xmlData));

			t = TransformerFactory.newInstance().newTransformer();
			result = new StreamResult(new File(fileName));
			source = new DOMSource(this.doc);
			t.transform(source, result);
			// log.info("#> getAuditNumber : "+xmlData+"/"+xmlData0+" on "+
			// fileName.toString());
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return String.valueOf((xmlData));
		}
	}

	public String getDateMMDD() {
		return new SimpleDateFormat("MMdd").format(new Date());
	}

	public String getDateHHMMSS() {
		return new SimpleDateFormat("hhmmss").format(new Date());
	}

	public String getDateHHMMSS2() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

	public String getDateMMDDhhmmss() {
		return new SimpleDateFormat("MMddhhmmss").format(new Date());
	}

	public String getDateDDMMYYYYHHmmss() {
		return new SimpleDateFormat("ddMMYYYYHHmmss").format(new Date());
	}

	public static String getDateYYYYMMDDHHmmssxxx() {
		return new SimpleDateFormat("YYYYMMddHHmmssSSS").format(new Date());
	}

	public String getDateDDMMYYYYHHmmss2() {
		return new SimpleDateFormat("dd-MM-YYYY HH:mm:ss").format(new Date());
	}

	public static String getDateYYYYMMDDHHmmss() {
		return new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
	}
	
	public static String getToDayYYYYMMDD() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	public String getToDayDDMMYY() {
		return new SimpleDateFormat("ddMMyy").format(new Date());
	}

	public String getToDayYYYYMMDD2() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	public String getCurrentMonth(String month) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());

		return new SimpleDateFormat(month).format(cal.getTime());
	}

	public String getNextMonth(String month) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		// Add next month
		cal.add(Calendar.MONTH, 1);

		return new SimpleDateFormat(month).format(cal.getTime());
	}

	public String getToDayDD() {
		return new SimpleDateFormat("dd").format(new Date());
	}

	public String getToDayD() {
		return new SimpleDateFormat("d").format(new Date());
	}

	public String getToDayYYYY() {
		return new SimpleDateFormat("yyyy").format(new Date());
	}

	@SuppressWarnings("static-access")
	public String getDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		return String.valueOf(cal.getActualMaximum(cal.DAY_OF_MONTH));
	}

	public String getLastDateOfMonthDD() {
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		return new SimpleDateFormat("dd").format(calendar.getTime());
	}

	public void info(String slog) {
		log.info("#> " + slog);
	}

	public void error(String slog) {
		log.error("#> " + slog);
	}

	public String getDateTime() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + " ";
	}

	public static String getMD5(String phrase) {
		String str = "";
		Charset utf8Charset = Charset.forName("UTF-8");

		byte[] bytesOfPhrase = phrase.getBytes(utf8Charset);
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfPhrase);

			str = new String(thedigest, utf8Charset);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return str;
	}

	public void saveLogFile(String fileName, String logtxt) {
		try {
			String filename = STR_LOG_PATH+getToDayYYYYMMDD2()+"."+fileName;
			FileWriter fw = new FileWriter(filename, true); // the true will Append the new data
			fw.write(getDateDDMMYYYYHHmmss2()+" "+logtxt+"\n");// appends the string to the file
			fw.close();
//			System.out.println("#saveLogFile: "+fileName);
		} catch (IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	
	public static void writeFile(String fileName, String rowTxt) {
//		log.debug(">writeFile: "+fileName+" #"+rowTxt);
		try {
			
			FileWriter fw = new FileWriter(fileName, true); // the true will Append the new data
			fw.write(rowTxt+"\n");// appends the string to the file
			fw.close();
		} catch (Exception e) {
			log.error(ConstantaX.EXCEPTION, e);
		}
	}
	
	private Date yesterday() {
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, -1);
	    return cal.getTime();
	}

	private Date nextDay() {
	    final Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, +1);
	    return cal.getTime();
	}

	public String getYesterdayDateString(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(yesterday());
	}
	
	public String getNextdayDateString(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(nextDay());
	}

	public static String getEncrypted(String str){
		byte[] authBytes = str.getBytes(StandardCharsets.UTF_8);
		return Base64.getEncoder().encodeToString(authBytes);

	}

	public static String getDecrypted(String str){
		byte[] dec = Base64.getDecoder().decode(str);
		return new String(dec);

	}
	
	public static String getQueryFromResource(String queryPath) {
		StringBuilder sql = new StringBuilder();
		Resource sqlResource = new ClassPathResource(queryPath); //"sql/log_message_new.sql"
		try (BufferedReader br = new BufferedReader(new InputStreamReader(sqlResource.getInputStream()))) {
			String s = StringUtils.EMPTY;
			while ((s = br.readLine()) != null) {
				sql.append(s);
				sql.append('\n');
			}
		} catch (Exception e) {
			log.error(ConstantaX.EXCEPTION, e);
		}
		return sql.toString();
	}	
	
	public static String getException(Exception e) {
	    StackTraceElement[] stack = e.getStackTrace();
	    String exception = "";
	    for (StackTraceElement s : stack) {
	        exception = exception + s.toString() + "\n\t\t";
	    }
		return ConstantaX.EXCEPTION + e.getMessage()+":"+exception;
	}
	
	public static LocalDateTime getCreateTime(File file) throws IOException {
        Path path = Paths.get(file.getPath());
        BasicFileAttributeView basicfile = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
        BasicFileAttributes attr = basicfile.readAttributes();
        long date = attr.creationTime().toMillis();
        Instant instant = Instant.ofEpochMilli(date);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
	
	public static String isNull(Object obj) {
		return null==obj?"":obj.toString();
	}
	
	
	public static void copyFileFromResource(String fileNameResource, String fileNameDest) {
		log.debug("#>copyFileFromResource..");
		ClassPathResource classPathResource = new ClassPathResource(fileNameResource);
		InputStream inputStream = null;;

		try {
			inputStream = classPathResource.getInputStream();
			File somethingFile = new File(fileNameDest); 
			
		    Files.copy(inputStream, somethingFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
		    IOUtils.closeQuietly(inputStream);
		}		
	}
	
	public static String cleanNumber(String str) {
		String res = str==null||str.isEmpty()||str.trim().equals("-")?"0":str; 
		res = res.replaceAll("[^0-9.]", "").replaceAll("-", "");
//		log.debug("cleanNumber:"+str+" > "+res);
		
		return res;
	}
	
	public static Long getTimeMilis() {
		return System.currentTimeMillis();
	}
	
	
	public static String objectToJson(Object obj) {
		return gson.toJson(obj);
	}
	
	@SuppressWarnings("unused")
	public static String createFileBase64(String filePath, String fileExt, String binaryString) {
		filePath = filePath + "." + fileExt;
		try {
			Base64Decoder decoder = new Base64Decoder();
			byte[] bytes = Base64Decoder.decode(binaryString);

			DataOutputStream os = new DataOutputStream(new FileOutputStream(filePath));
			os.write(bytes);
			os.close();
		} catch (Exception localException) {
		}
		String log = "createFileBase64 constructed : " + filePath;

		return log;
	}
	
}
