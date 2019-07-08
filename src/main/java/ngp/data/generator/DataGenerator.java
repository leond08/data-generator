package ngp.data.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javafaker.Faker;


public class DataGenerator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataGenerator.class);
	
	private Faker faker;


	public boolean generateBoolean() {
		
		
		return false;
		
	}
	
	public String generateString() {
		
		faker = new Faker();
		
		String text = faker.name().lastName();
		
		LOGGER.info(text);
		
		
		return text;
	}
	
	public int generateInt() {
		
		return 0;
		
	}

}
