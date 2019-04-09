public class DuckDuckGo {

	private static final String Usage = "java -cp .:javax.json-1.0.jar DuckDuckGo <word>";
	private static final String QueryFmt = "https://api.duckduckgo.com/?q=%s&format=json";

	private static String GetQueryFromString(String query) {
		return String.format(QueryFmt, query);
	}

	public static void main(String[] args) {
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
