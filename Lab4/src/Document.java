import java.util.Scanner;

public class Document{
    public String name;
    public TwoWayCycledOrderedListWithSentinel<Link> link;
    public Document(String name, Scanner scan) {
        this.name=name.toLowerCase();
        link=new TwoWayCycledOrderedListWithSentinel<Link>();
        load(scan);
    }

    public void load(Scanner scan) {
        String marker="link=";
        String endMarker="eod";
        String line=scan.nextLine().toLowerCase();
        while(!line.equalsIgnoreCase(endMarker)) {
            String arr[]=line.split(" ");
            for(String word:arr) {
                if(word.startsWith(marker))
                {
                    String linkStr=word.substring(marker.length());
                    Link l;
                    if((l=createLink(linkStr))!=null)
                        link.add(l);
                }

            }
            line=scan.nextLine().toLowerCase();
        }
    }

    public static boolean isCorrectId(String id) {

        id=id.toLowerCase();
        if(id.length()==0) return false;
        if( id.charAt(0)<'a' ||id.charAt(0)>'z')
            return false;
        for(int i=1;i<id.length();i++) {

            if( !(id.charAt(i)>='a' && id.charAt(i)<='z'
                    || id.charAt(i)>='0' && id.charAt(i)<='9'
                    || id.charAt(i)=='_'))
                return false;
        }

        return true;
    }

    private static Link idAndNumber(String id, int n) {
        if(!isCorrectId(id)) return null;
        return new Link(id.toLowerCase(),n);
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    static Link createLink(String link) {
        if(link.length()==0) return null;
        int parentOp = link.indexOf('(');
        int parentCl = link.indexOf(')');
        if(parentOp>0 && parentCl>parentOp && parentCl == link.length()-1) {
            String strNumber = link.substring(parentOp+1, parentCl);
            try {
                int num = Integer.parseInt(strNumber);
                if(num < 1)
                    return null;
                return idAndNumber(link.substring(0, parentOp),num);
            }
            catch(NumberFormatException ex) {
                return null;
            }
        }

        return idAndNumber(link,1);
    }

    @Override
    public String toString() {

        String retStr="Document: "+name;
        int count = 0;
        for(Link linkElem:link) {
            if(count%10==0)
                retStr+="\n";
            else
                retStr+=" ";

            retStr+=linkElem.toString();
            count++;
        }
        return retStr;
    }

    public String toStringReverse() {
        String retStr="Document: " + name + link.toStringReverse();
        return retStr;

    }
}