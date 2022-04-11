import java.util.Iterator;
import java.util.Scanner;

public class Document {

    public String name;
    //

    //
    public TwoWayUnorderedListWithHeadAndTail<Link> link; //para hacer la lista

    public Document(String name, Scanner scan) {
        this.name = name;
        link = new TwoWayUnorderedListWithHeadAndTail<Link>();
        load(scan);

    }
    public void load(Scanner scan) {
        //TODO

        String nxtLine = scan.nextLine();

        while (!nxtLine.equals("eod")) {

            String[] separateStr = nxtLine.split(" ");

            for (int i = 0; i < separateStr.length; i++) {

                if (correctLink(separateStr[i])) {
                    String[] splitStr = separateStr[i].split("=");
                    Link links = new Link(splitStr[1].toLowerCase());
                    link.add(links);
                }
            }

            nxtLine = scan.nextLine();
        }


    }
    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    public static boolean correctLink(String link) {
        //TODO
        boolean correct = false;

        if (link.toLowerCase().contains("link=") && link.length() > 5) {
            char firstChar = link.charAt(5);
            if ((firstChar >= 'a' && firstChar <= 'z') || (firstChar >= 'A' && firstChar <= 'Z'))
                correct = true;
        }
        return correct;
    }

    @Override
    public String toString() {
        // TODO
        String str = "Document: " + this.name;

        for (int i = 0; i < link.size(); i++) {
            str = str + "\n";
            str = str + link.get(i).ref;
        }

        return str;
    }

    public String toStringReverse() {
        String retStr = "Document: " + name;
        return retStr + link.toStringReverse();
    }
}
