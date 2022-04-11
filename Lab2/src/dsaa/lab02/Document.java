package dsaa.lab02;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Document{
	public String name;
	public OneWayLinkedList<Link> links;
	public Document(String name, Scanner scan) {
			links = new OneWayLinkedList();
			this.name = name;
			load(scan);
		}
	public void load(Scanner scan) {
		String line;
		do {
			line = scan.nextLine().toLowerCase();
			String arr[] = line.split(" ");
			for (String word : arr) {
				if (word.startsWith("link=")) {
					String link = word.substring(5);
					if (correctLink(link)) {
						links.add(new Link(link));
					}
				}
			}
		} while (!line.equalsIgnoreCase("eod"));
	}
	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
	private static boolean correctLink(String link) {
		return Pattern.matches("[a-zA-Z][a-zA-Z0-9_]*", link);
	}
	
	@Override
	public String toString() {
		String s = "Document: " + this.name;
		for (int i = 0; i < links.size(); i++)
			s += "\n" + links.get(i).ref;
		return s;
	}

}
