import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class Wikiparser {
	private String data;

	private boolean isValid;

	private JsonObject main;
	private JsonObject results;

	private String header;
	private String output;

	public Wikiparser(String data) {
		this.data = data;
		this.output = "";
		this.header = "";
		this.isValid = false;
	}

	public boolean parse() {
		try (JsonReader r = Json.createReader(new StringReader(data))) {
			main = r.readObject();
		} catch (Exception e) {
			System.out.println("Not a valid JSON string!");
			System.out.println(e.getMessage());
			return isValid = false;
		}

		StringBuilder sb = new StringBuilder();

		JsonObject query;
		query = main.getJsonObject("query");
		results = query.getJsonObject("pages");

		for (String k : results.keySet()) {
			JsonObject res = results.getJsonObject(k);
			AppendTopic(sb, res, 2);
		}

		output = sb.toString();
		return isValid = true;
	}

	// Internal, do not call
	private void AppendTopic(StringBuilder sb, JsonObject o, int spaces) {
		if (o.get("title") == null) return;

		// Secret Java operator "-->"
		// Minimum 250 IQ required to understand this
		while (spaces --> 0) {
			sb.append(" ");
		}

		sb.append("- ");
		sb.append(o.getString("title"));
		sb.append("\n");
		sb.append(o.getString("extract"));
		sb.append("\n");
	}

	public boolean IsValid() {
		return isValid;
	}

	public String ToString() {
		return output;
	}

}
