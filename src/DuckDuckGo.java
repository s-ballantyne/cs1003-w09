import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DuckDuckGo {

	private static final String Usage = "java -cp .:javax.json-1.0.jar DuckDuckGo <word>";
	private static final String QueryFmt = "https://api.duckduckgo.com/?q=%s&format=json";

	private static String GetQueryFromString(String query) throws UnsupportedEncodingException {
		return String.format(QueryFmt, URLEncoder.encode(query, "UTF-8"));
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		if (args.length != 1) {
			System.out.println(Usage);
		} else {
			String result = Rest.DoQuery(GetQueryFromString(args[0]));

			Parser parser = new Parser(result, true);
			parser.parse();

			if (parser.IsValid()) System.out.print(parser.ToString());
		}
	}

}
