package ngp.data.encryption;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVWriter;


public class NgpCSVWriter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NgpCSVWriter.class);
	
	private Writer writer;
	private CSVWriter csvWriter;
	
	public NgpCSVWriter(AppParams params, String[] headers) {
		final String OUTPUT_FILE = params.getOutputFilePath();
		
		try {
			
			writer = Files.newBufferedWriter(Paths.get(OUTPUT_FILE));
			
			csvWriter = new CSVWriter(writer, 
					CSVWriter.DEFAULT_SEPARATOR,
					CSVWriter.NO_QUOTE_CHARACTER,
					CSVWriter.NO_ESCAPE_CHARACTER,
					CSVWriter.DEFAULT_LINE_END);
					
			
			csvWriter.writeNext(headers);
					
		} catch(Exception e) {
			LOGGER.error("Error writing files:", e);
			System.exit(1);
		}
	}
	
	
	public void csvWrite(String[] records) {
		try {
			
			csvWriter.writeNext(records);
			
		} catch(Exception e) {
			LOGGER.warn("", e);
		}
	
		
	}
	
	public void close() {
		
		if (csvWriter != null) {
			try {
				csvWriter.close();
			} catch (IOException e ) {
				LOGGER.warn("", e);
			}
		}
		
	}

}
