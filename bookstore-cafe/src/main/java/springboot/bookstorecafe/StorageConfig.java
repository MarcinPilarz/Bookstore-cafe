package springboot.bookstorecafe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Configuration
public class StorageConfig {

	 @Bean
//	 public Storage storage() {
//	        try {
//	            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
//	            return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            throw new RuntimeException("Failed to initialize Google Cloud Storage");
//	        }
//	    }
	 
	    public Storage storage() {
	    
	        return StorageOptions.getDefaultInstance().getService();
	    }
}
