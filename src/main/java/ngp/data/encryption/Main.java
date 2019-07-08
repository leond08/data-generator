package ngp.data.encryption;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private final AppParams params;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	public static void main(String args[]) {
        Main m = new Main(args);
        m.execute();
        
    }
	
    public Main(String[] args) {
    	this.params = getParameters(args);
	}
   
    public void execute() {
    	JobPoller poller = new JobPoller(params);
    	Thread t = new Thread(poller);
    	t.start();
    }
    
	public AppParams getParameters(String args[]) {
        CommandLineParser parser = new BasicParser();
        HelpFormatter formatter = new HelpFormatter();
        Options options = createOptions();
        CommandLine cmd = null;
        AppParams result = null;
        try {
            cmd = parser.parse(options, args);
            result = new AppParams(cmd);
        } catch (Exception e) {
        	LOGGER.error("Unable to convert", e);
        	
            formatter.printHelp("java -cp . data-encryption [options here]", options);
            
            System.exit(1);
        }

        return result;
    }
    
    public Options createOptions(){
        Options options = new Options();

        Option opt = null;
        
        opt = new Option("input_file_path", "inputFilePath", true, "Input File Path of the csv");
        options.addOption(opt);
        
        opt = new Option("output_file_path", "outputFilePath", true, "Output File Path of the csv");
        options.addOption(opt);
        
        opt = new Option("generate_data", "generateData", true, "Set to true to generate dummy data");
        options.addOption(opt);
        
        opt = new Option("field_names", "fieldNames", true, "Field names of data to generate");
        options.addOption(opt);
        
        opt = new Option("number_rows", "numberRows", true, "Number of rows to generate");
        options.addOption(opt);
        
        opt = new Option("fields_to_encrypt", "fieldsToEncrypt", true, "Fields to encrypt");
        options.addOption(opt);
        
        opt = new Option("encryption_type", "encryptionType", true, "Encryption type to use (Rijandael, MD5 only)");
        options.addOption(opt);
        
        opt = new Option("secure_key", "secureKey", true, "Secure key for Rijandael");
        options.addOption(opt);
        
        opt = new Option("vector_key", "vectorKey", true, "Vector key for Rijandael");
        options.addOption(opt);
        
        opt = new Option("block_size", "blockSize", true, "Block size for Rijandael");
        
        options.addOption(opt);
        
        opt = new Option("thread_count", "threadCount", true, "Number of threads to execute");
        options.addOption(opt);
        
        opt = new Option("use_temp_files", "useTempFiles", true, "Use temp files");
        options.addOption(opt);

        return options;
    }



}
