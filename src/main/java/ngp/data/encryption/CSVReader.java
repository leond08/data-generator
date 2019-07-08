package ngp.data.encryption;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ngp.data.encryption.impl.InputFileReader;


public class CSVReader implements InputFileReader {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CSVReader.class);
	private BufferedReader reader = null;
	private CSVParser parser;
	private Iterator<CSVRecord> iterator;
	private String[] headers;
	private String[] fields;
	private String encryptionType;
	private static final String ARRAY_DELIM = ",";
	private NgpEncrypt ngpEncrypt;
	
	public CSVReader(AppParams params) {
		
		ngpEncrypt = new NgpEncrypt(params);
		
		this.fields = params.getFieldsToEncrypt().split(ARRAY_DELIM);
		
		this.encryptionType = params.getEncryptionType();
		
		try {
			
			reader = Files.newBufferedReader(Paths.get(params.getInputFilePath()));
			
			parser = new CSVParser(reader, CSVFormat.EXCEL
	                .withEscape('\\')
	                .withQuoteMode(QuoteMode.NONE)
	                .withQuote('"')
	                .withIgnoreEmptyLines()
					.withIgnoreHeaderCase());
			
			iterator = parser.iterator();
			this.headers = getHeaders();

		    } catch (IOException e) {
		        LOGGER.error("Error reading file:", e);
		        System.exit(1);
		    }
	}
	
	
	private String[] getHeaders() {
		
        CSVRecord record = iterator.next();
        Iterator<String> i = record.iterator();
        List<String> header = new ArrayList<String>();
        while (i.hasNext()) {
            String columnName = i.next();
            if (null != columnName && !"".equals(columnName)) {
                header.add(columnName);
            }
        }
        return header.toArray(new String[header.size()]);

	}

	public void close() throws IOException {
    	if (reader != null) {
    		try {
    			reader.close();
    		} catch (IOException e) {
    			LOGGER.warn("", e);
    		}
    	}
    	
    	if (parser != null) {
    		try {
    			reader.close();
    		} catch (IOException e) {
    			LOGGER.warn("", e);
    		}
    	}
		
	}


	public List<String> nextLine() {
		throw new RuntimeException("Not supported");
	}


	public synchronized Map<String, String> nextRecord() {
		
		CSVRecord record = iterator.next();
        Iterator<String> columnIter = record.iterator();
        Map<String, String> retval = new LinkedHashMap<String, String>();
        // loops based on the header
        // effectively ignores excess columns
        for (int i = 0; i < this.headers.length; i++) {
            if (columnIter.hasNext()) {
                String value = columnIter.next();
                //Encrypt the value
                for (String encryptedFields: fields) {
                    if (this.headers[i].equals(encryptedFields)) {
                    	if (!value.isEmpty()) {
                    			if (encryptionType.equals("aes")) {
                    				value = ngpEncrypt.aes(value);
                    			}
                    			else {
                    				value = ngpEncrypt.md5(value);
                    			}
                    	}
                    	
                    }

                }

               retval.put(this.headers[i], null!=value?value:"");
            }
        }
        
        LOGGER.info(retval.toString());
        
        return retval;

	}


	public boolean hasNext() {
		return iterator.hasNext();
	}


	public List<String> getHeaderAsList() {
		return Arrays.asList(this.headers);
	}

}
