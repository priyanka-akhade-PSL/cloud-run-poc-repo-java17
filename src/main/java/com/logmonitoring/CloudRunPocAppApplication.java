package com.logmonitoring;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.net.ssl.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.cloud.storage.*;

@SpringBootApplication
public class CloudRunPocAppApplication implements CommandLineRunner{
	String fileName = "samplefile.json";
    String bucketName = "test-bucket-search-conversation";

	public static void main(String[] args) {
		SpringApplication.run(CloudRunPocAppApplication.class, args);
	}
	private void uploadFileToBucket() throws IOException {
//		logger.info("Uploading file to GCS Bucket...");
        Storage storage = StorageOptions.getDefaultInstance().getService();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        Blob blob = storage.create(blobInfo, Files.readAllBytes(Paths.get("samplefile.json")));
        System.out.println(blob);
//		logger.info("File has been uploaded!");
    }
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		createTrustManager();
		uploadFileToBucket();
	}
	
	private void createTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HostnameVerifier allHostsValid = new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
