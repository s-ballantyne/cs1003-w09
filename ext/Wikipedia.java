import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Wikipedia {
	private static final String Usage = "java -cp .:javax.json-1.0.jar Wikipedia <query>";
	private static final String Query = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro&explaintext&redirects=1&titles=";

	public static String GetQueryFromString(String s) throws UnsupportedEncodingException {
		return Query + URLEncoder.encode(s, "UTF-8");
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		if (args.length != 1) {
			System.out.println(Usage);
		} else {
			String result = Rest.DoQuery(GetQueryFromString(args[0]));

			Wikiparser parser = new Wikiparser(result);
			parser.parse();

			if (parser.IsValid()) System.out.print(parser.ToString());
		}
	}

}