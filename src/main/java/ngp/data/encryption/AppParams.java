package ngp.data.encryption;

import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppParams {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppParams.class);
	private String inputFilePath;
	private String outputFilePath;
	private String fieldsToEncrypt;
	private String encryptionType;
	private String secureKey;
	private String vectorKey;
	private String fieldNames;
	private int numberOfRows;
	private boolean generateData;
	private int blockSize;
    private final int threadCount;
    private final boolean useTempFiles;
    
    AppParams(CommandLine cmd) {
    	
        Properties p = new Properties();

        this.inputFilePath = cmd.getOptionValue("input_file_path");
        this.outputFilePath = cmd.getOptionValue("output_file_path");
        this.fieldsToEncrypt = cmd.getOptionValue("fields_to_encrypt");
        this.fieldNames = cmd.getOptionValue("field_names");
        

        int temp = 10;
        int tempBlockSize = 256;
        boolean tempGenerateData = false;
        String tempEncryptionType = "md5";
        String tempSecureKey = "";
        String tempVectorKey = "";
        String tempFieldsToEncrypt = "";
        int tempNumberRows = 0;
        boolean tempBool = false;
        
        
        try {
            temp = new Integer(StringUtils.defaultIfEmpty(p.getProperty("thread_count"), cmd.getOptionValue("thread_count")));
        } catch (NumberFormatException nfe) { }
        
        if (temp < 1) {
            temp = 10;
        }
        
        try {
            tempBool = new Boolean(cmd.getOptionValue("use_temp_files"));
        } catch (Exception e) { }
        
        try {
        	
        	if (cmd.getOptionValue("generate_data") != "") {
        		tempGenerateData = Boolean.parseBoolean(cmd.getOptionValue("generate_data"));
        		
        		if (tempGenerateData) {
        			if (cmd.getOptionValue("number_rows") != "") {
        				tempBlockSize = new Integer(StringUtils.defaultIfEmpty(p.getProperty("number_rows"), cmd.getOptionValue("number_rows")));
        			}
        		}
        	}
        	
        	if (cmd.getOptionValue("fields_to_encrypt") != "") {
        		tempFieldsToEncrypt = cmd.getOptionValue("fields_to_encrypt");
        	}
        	else {
        	    LOGGER.info("Setting fields to encrypt to all");
        	}
        	
        	if(cmd.getOptionValue("encryption_type").equals("md5")) {
        		tempEncryptionType = "md5";
        	}
        	else if(cmd.getOptionValue("encryption_type").equals("aes")) {
        		
        		tempEncryptionType = "aes";
        		
        		if (cmd.getOptionValue("secure_key") != "") {
        			tempSecureKey = cmd.getOptionValue("secure_key");
        		}
        		
        		if (cmd.getOptionValue("vector_key") != "") {
        			tempVectorKey = cmd.getOptionValue("vector_key");
        		}
        		
        		if (cmd.getOptionValue("block_size") != "" ) {
        			
        			tempBlockSize = new Integer(StringUtils.defaultIfEmpty(p.getProperty("block_size"), cmd.getOptionValue("block_size")));
        		}
        		else {
        			
        			tempBlockSize = 256;
        		}
        		
        	}
        	else {
        		this.encryptionType = "md5";
        	}
        }catch(Exception e) { }
        
        this.threadCount = temp; 
        this.encryptionType = tempEncryptionType;
        this.secureKey = tempSecureKey;
        this.vectorKey = tempVectorKey;
        this.blockSize = tempBlockSize;
        this.fieldsToEncrypt = tempFieldsToEncrypt;
        this.useTempFiles = tempBool;
        this.generateData = tempGenerateData;
        this.numberOfRows = new Integer(StringUtils.defaultIfEmpty(p.getProperty("number_rows"), cmd.getOptionValue("number_rows")));
    }
    
    public String getInputFilePath() {
    	return inputFilePath;
    }
    
    public String getOutputFilePath() {
    	return outputFilePath;
    }
    
    public int getThreadCount() {
    	return threadCount;
    }
    
    public String getFieldsToEncrypt() {
    	return fieldsToEncrypt;
    }
    
    public String getEncryptionType() {
    	return encryptionType;
    }
    
    public String getSecureKey() {
    	return secureKey;
    }
    
    public String getVectorKey() {
    	return vectorKey;
    }
    
    public int getBlockSize() {
    	return blockSize;
    }
    
    public boolean getGenerateData() {
    	return generateData;
    }
    
    public String getFieldNames() {
    	return fieldNames;
    }
    
    public int getNumberOfRows() {
    	return numberOfRows;
    }


}
