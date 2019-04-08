import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Parser {
	private String path;

	private boolean isValid;

	private JsonObject main;
	private JsonArray related;

	private String header;
	private String output;

	public Parser(/*@NotNull*/ String path) {
		this.path = path;
		this.isValid = false;
	}

	public boolean parse() {
		try (JsonReader r = Json.createReader(new FileReader(path))) {
			main = r.readObject();
		} catch (FileNotFoundException e) {
			System.out.printf("File not found: %s\n", path);
			return isValid = false;
		} catch (Exception e) {
			System.out.println("Not a valid JSON string!");
			return isValid = false;
		}

		StringBuilder sb = new StringBuilder();
		header = main.get("Heading").toString();
		sb.append(header);
		sb.append(" can refer to:\n");

		related = main.getJsonArray("RelatedTopics");

		for (int i = 0; i < related.size(); ++i) {
			JsonObject o = related.getJsonObject(i);
			JsonArray subtopics;

			if (o.containsKey("Result")) {
				AppendTopic(sb, o, 2);
			} else if (o.containsKey("Topics")
					&& (subtopics = o.getJsonArray("Topics")).size() > 0) {

				sb.append("  * Category: ");
				sb.append(o.get("Name"));
				sb.append("\n");

				for (int j = 0; j < subtopics.size(); ++j) {
					AppendTopic(sb, subtopics.getJsonObject(j), 4);
				}
			}
		}

		output = sb.toString();
		return isValid = true;
	}

	// Internal, do not call
	private void AppendTopic(StringBuilder sb, /*@NotNull*/ JsonObject o, int spaces) {
		if (o.get("Text") == null) return;

		// Secret Java operator "-->"
		// Minimum 250 IQ required to understand this
		while (spaces --> 0) {
			sb.append(" ");
		}

		sb.append("- ");
		sb.append(o.get("Text").toString());
		sb.append("\n");
	}

	public boolean IsValid() {
		return isValid;
	}

	public String ToString() {
		return output;
	}
}
