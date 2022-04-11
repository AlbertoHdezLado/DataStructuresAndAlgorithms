import java.util.Scanner;
import java.util.regex.*;

public class Document {
	public static void loadDocument(String name, Scanner scan) {
		String line;
		do {
			line = scan.nextLine().toLowerCase();
			String arr[] = line.split(" ");
			for (String word : arr) {
				if (word.startsWith("link=")) {
					String link = word.substring(5);
					if (correctLink(link))
						System.out.println(link);
				}
			}
		} while (!line.equalsIgnoreCase("eod"));
	}

	// accepted only small letters, capitalic letter, digits nad '_' (but not on the
	// begin)
	public static boolean correctLink(String link) {
		return Pattern.matches("[a-zA-Z][a-zA-Z0-9_]*", link);
	}

}