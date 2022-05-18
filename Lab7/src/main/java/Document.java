import java.util.ListIterator;
import java.util.Scanner;
import java.util.*;

class Document implements IWithName
{
    public String name;
    public TwoWayCycledOrderedListWithSentinel<Link> link;

    private static final int[] HASHCODE_SEQUENCE = {7, 11, 13, 17, 19};

    public Document(String name)
    {
        this.name = name.toLowerCase();
    }

    public Document(String name, Scanner scan)
    {
        this.name = name.toLowerCase();
        link = new TwoWayCycledOrderedListWithSentinel<Link>();
        load(scan);
    }

    public void load(Scanner scan)
    {
        String marker = "link=";
        String endMarker = "eod";
        String line = scan.nextLine().toLowerCase();
        while (!line.equalsIgnoreCase(endMarker))
        {
            String arr[] = line.split(" ");
            for (String word : arr)
            {
                if (word.startsWith(marker))
                {
                    String linkStr = word.substring(marker.length());
                    Link l;
                    if ((l = createLink(linkStr)) != null)
                    {
                        link.add(l);
                    }
                }

            }
            line = scan.nextLine().toLowerCase();
        }

    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)


    public static boolean isCorrectId(String id)
    {
        id = id.toLowerCase();
        if (id.length() == 0)
        {
            return false;
        }
        if (id.charAt(0) < 'a' || id.charAt(0) > 'z')
        {
            return false;
        }
        for (int i = 1; i < id.length(); i++)
        {
            if (!(id.charAt(i) >= 'a' && id.charAt(i) <= 'z' || id.charAt(i) >= '0' && id.charAt(i) <= '9' || id.charAt(i) == '_'))
            {
                return false;
            }
        }
        return true;
    }

    private static Link createIdAndNumber(String id, int n)
    {
        if (!isCorrectId(id))
        {
            return null;
        }
        return new Link(id.toLowerCase(), n);
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    public static Link createLink(String link)
    {
        if (link.length() == 0)
        {
            return null;
        }
        int openBracket = link.indexOf('(');
        int closeBracket = link.indexOf(')');
        if (openBracket > 0 && closeBracket > openBracket && closeBracket == link.length() - 1)
        {
            String strNumber = link.substring(openBracket + 1, closeBracket);
            try
            {
                int number = Integer.parseInt(strNumber);
                if (number < 1)
                {
                    return null;
                }
                return createIdAndNumber(link.substring(0, openBracket), number);
            }
            catch (NumberFormatException ex)
            {
                return null;
            }
        }
        return createIdAndNumber(link, 1);
    }

    @Override
    public String toString()
    {
        String retStr = "Document: " + name;
        int counter = 0;
        for (Link linkElem : link)
        {
            if (counter % 10 == 0)
            {
                retStr += "\n";
            }
            else
            {
                retStr += " ";
            }
            retStr += linkElem.toString();
            counter++;
        }
        return retStr;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Document document = (Document) o;

        return name != null ? name.equals(document.name) : document.name == null;
    }

    @Override
    public int hashCode()
    {
        if (name.length() == 0) {
            return 0;
        }
        
        int result = name.charAt(0);
        
        for (int i = 1; i < name.length(); i++) {
            int seqNum = HASHCODE_SEQUENCE[(i - 1) % HASHCODE_SEQUENCE.length];
            result = result * seqNum + name.charAt(i);
        }
        
        return result;
    }

    public String toStringReverse()
    {
        String retStr = "Document: " + name;
        int counter = 0;
        ListIterator<Link> iter = link.listIterator();
        while (iter.hasNext())
        {
            iter.next();
        }
        while (iter.hasPrevious())
        {
            if (counter % 10 == 0)
            {
                retStr += "\n";
            }
            else
            {
                retStr += " ";
            }
            retStr += iter.previous().toString();
            counter++;
        }
        return retStr;
    }

    @Override
    public String getName()
    {
        return name;
    }


}

