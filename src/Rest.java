import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Rest {
	private static final boolean Verbose = false;

	public static String DoQuery(String str) {
		String res;

		try {
			res = Exec(str);
		} catch (Exception e) {
			System.out.println("REST: " + e.getMessage());
			res = "";
		}

		return res;
	}

	private static String Exec(String str) throws IOException {
		URL url = new URL(str);

		HttpURLConnection conn = (HttpURLConnection)
				url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "*/*");
		conn.setRequestProperty("User-Agent", "curl/7.37.0");

		if (Verbose) {
			int i = 1;

			String header = conn.getHeaderField(0);
			System.out.println(header);
			System.out.println("---------------------");
			while ((header = conn.getHeaderField(i)) != null) {
				String key = conn.getHeaderFieldKey(i);
				System.out.println((key == null ? "" : key + ": ") + header);

				++i;
			}

			System.out.println("---------------------");
		}

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("HTTP error code: " + conn.getResponseCode());
		}

		// I cringe every time I have to instantiate a dozen objects just
		// to read a string line by line
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		StringBuilder buffer = new StringBuilder();
		String output;
		while ((output = br.readLine()) != null) {
			buffer.append(output);
		}

		conn.disconnect();

		return buffer.toString();
	}
}
