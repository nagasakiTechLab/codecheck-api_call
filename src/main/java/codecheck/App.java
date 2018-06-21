package codecheck;

import java.io.IOException;
import java.net.URLEncoder;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;

public class App {

	private static HttpRequestFactory fac = (new NetHttpTransport()).createRequestFactory();

	public static void main(String[] args) {

		if (args.length == 0) {
			System.out.println("引数が必要");
			return;
		}

		HttpResponse res = null;
		try {
			String arg = URLEncoder.encode(args[0], "UTF-8");
			String url = "http://challenge-server.code-check.io/api/hash?q=" + arg;

			HttpRequest req = fac.buildGetRequest(new GenericUrl(url));
			req.setParser(new JacksonFactory().createJsonObjectParser());

			res = req.execute();
			if (res.getStatusCode() == 200) {
				JsonObject json = res.parseAs(JsonObject.class);
				System.out.println(json.hash);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (res != null) {
					res.disconnect();
				}
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}

	public static class JsonObject {
		@Key
		public String q;

		@Key
		public String hash;
	}
}
