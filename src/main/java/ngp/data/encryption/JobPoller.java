package ngp.data.encryption;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ngp.data.encryption.impl.InputFileReader;
import ngp.data.generator.DataGenerator;

public class JobPoller implements Runnable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JobPoller.class);
	
	private final AppParams params;
	
	private NgpCSVWriter writer;
	
	private InputFileReader reader;
	
	private DataGenerator dataGenerator;
	

	public JobPoller(AppParams params) {
		this.params = params;
	}
	
	public void run() {
		if (this.params.getGenerateData()) {

			try {
				
				if (this.params.getOutputFilePath() == "") {
					LOGGER.error("Missing output file path. Provide by using -output_file_path <filename>");
					System.exit(1);
				}
				
				generateData(this.params);
				
			} catch(Exception e) {
				LOGGER.error("Error encountered while generating data:", e);
			}
		
		}
		else {
			
			LOGGER.info("encrypting files...");
			
			try {
				
				if (this.params.getInputFilePath() == "") {
					LOGGER.error("Missing input file path. Provide by using -input_file_path <filename>");
					System.exit(1);
				}
				
				if (this.params.getOutputFilePath() == "") {
					LOGGER.error("Missing output file path. Provide by using -output_file_path <filename>");
					System.exit(1);
				}
					
				// read and write csv
				processFile(this.params);	
				
			} catch (Exception e) {
				LOGGER.error("Error encountered while processing file:", e);
				//break;
				
			}
		}
		
	}
	
	private InputFileReader getFileReader(AppParams params) {
		
		if (this.params.getInputFilePath() != null) {
			return new CSVReader(params);
		}
		else {
			return null;
		}

	}
	
	public void processFile(AppParams params) throws IOException {
		
		try {
			
			reader = getFileReader(params);
			
			String[] headers = reader.getHeaderAsList().toArray(new String[0]);
			
			writer = new NgpCSVWriter(params, headers);
			
			if (reader.hasNext()) {
				while (reader.hasNext()) {
					
					Map<String, String> records = reader.nextRecord();
					
					if (records == null) {
						continue;
					}
					
					String[] record = records.values().toArray(new String[0]);
					
					
					writer.csvWrite(record);
					
				}
			}

			
		} catch (Exception e) {
			LOGGER.error("Error processing file:", e);
		} finally {
			reader.close();
			writer.close();
		}
	}
	
	public void generateData(AppParams params) throws IOException {
		
		try {
			
			LOGGER.info("generating dummy data...");
			
			String[] headers = params.getFieldNames().split(",");
			int rows = params.getNumberOfRows();
			
			if (rows == 0) {
				LOGGER.error("Please provide number higher than 0.");
				System.exit(1);
			}
		
			LOGGER.info("OUTPUT FILE: " + params.getOutputFilePath());
			LOGGER.info("Number of Rows: " + rows);
			
			writer = new NgpCSVWriter(params, headers);
				
			dataGenerator = new DataGenerator();
			
			for (int i = 0; i < rows; i++) {
				
					Map<String, String> retval = new LinkedHashMap<String, String>();
					
					for (int j = 0; j < headers.length; j++) {
						
						retval.put(headers[j], dataGenerator.generateString());
					}
					
					String[] record = retval.values().toArray(new String[0]);
					
					writer.csvWrite(record);
					
					LOGGER.info("Status: " + i);
			
			}
			
			
		} catch (Exception e) {
			LOGGER.error("Error generating data:", e);
		} finally {
			writer.close();
		}
	}

}