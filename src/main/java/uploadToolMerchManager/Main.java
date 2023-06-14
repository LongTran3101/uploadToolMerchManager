package uploadToolMerchManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.CharSet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;

import com.google.gson.Gson;



public class Main {
	public static void main(String[] args) {
		try {
			File file=new File("C:\\Users\\longtn\\Downloads\\ao rank 100\\ao rank 100\\done\\Worlds best husband and dad gift for fathers day TShirt131.png");
			byte[] fileContent = FileUtils.readFileToByteArray(file);
			String encodedString = Base64.getEncoder().encodeToString(fileContent);
			Image img=new Image();
			img.setName(file.getName());
			img.setUsernam("longtn");
			img.setBaseFile(encodedString);
			Gson gson = new Gson();
			String json = gson.toJson(img);
			String a=callAPIPost("http://localhost:8080/uploadMultifileExcel",json);
			System.out.println(a);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
}

public static String callAPIPost(String completeUrl, String body) {
    try {
        int CONNECTION_TIMEOUT_MS = 60000;
        int LATENT_CONNECTION_TIMEOUT_MS = 6000000;

        CloseableHttpClient client = HttpClientBuilder.create().build();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(Timeout.ofMilliseconds(CONNECTION_TIMEOUT_MS))
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(CONNECTION_TIMEOUT_MS)).build();
        HttpPost httppost = new HttpPost(completeUrl);
        try {
            httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
            httppost.setHeader("Authorization", "Bearer ");
            httppost.setConfig(config);
            StringEntity stringEntity = new StringEntity(body);
            httppost.getRequestUri();
            httppost.setEntity(stringEntity);
        } catch (Exception e) {

        }
        CloseableHttpResponse response = client.execute(httppost);
        if (response.getCode() >= 300) {
            throw new IOException("False");
        }
        HttpEntity entity = response.getEntity();

        // StringWriter writer = new StringWriter();
        // IOUtils.copy(entity.getContent(), writer);
        return EntityUtils.toString(entity, "UTF-8");
    } catch (Exception e) {
    }

    return null;
}
}

