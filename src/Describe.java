// https://studres.cs.st-andrews.ac.uk/CS1003/Practicals/W09/
// https://studres.cs.st-andrews.ac.uk/CS1003/Practicals/W09/W09.pdf

public class Describe {
	private static final String Usage = "java -cp .:javax.json-1.0.jar Describe file.json";

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println(Usage);
		} else {
			String filePath = args[0];

			Parser p = new Parser(filePath);
			p.parse();

			if (p.IsValid()) System.out.print(p.ToString());
		}
	}
}
