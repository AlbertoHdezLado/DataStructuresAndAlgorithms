import java.util.HashSet;
import java.util.Set;

public class DisjointSetLinkedList implements DisjointSetDataStructure {

	
    private class Element
    {
        Element representative;
        Element next;
        Element last;
        int length;
        int value;
    }

    private static final int NULL = -1;

    Element arr[];

    public DisjointSetLinkedList(int size)
    {
        arr = new Element[size];
    }

    @Override
    public void makeSet(int x)
    {
        Element e = new Element();
        e.representative = e;
        e.next = null;
        e.length = 1;
        e.last = e;
        e.value = x;
        arr[x] = e;
    }

    @Override
    public int findSet(int x)
    {
        return arr[x].representative.value;
    }

    @Override
    public boolean union(int x, int y)
    {
        Element repX = arr[x].representative;
        Element repY = arr[y].representative;
        
        if (repX.value == repY.value) {
            return false;
        }
        
        if (repY.length > repX.length) {
            Element tmp = repX;
            repX = repY;
            repY = tmp;
        }

        Element lastX = repX.last;
        lastX.next = repY;
        repX.last = repY.last;
        
        while (repY != null) {
            repX.length++;
            repY.representative = repX;
            repY = repY.next;
        }
        
        return true;
    }


    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder("Disjoint sets as linked list:\n");
        Set<Integer> processed = new HashSet<>();
        for (int i = 0; i < arr.length; i++)
        {
            Element e = arr[i].representative;
            if (!processed.contains(e.value))
            {
                processed.add(e.value);
                while (e != null)
                {
                    result.append(e.value);
                    e = e.next;
                    if (e != null)
                    {
                        result.append(", ");
                    }
                }
                if (i < arr.length - 2)
                {
                    result.append("\n");
                }
            }
        }
        return result.toString();
    }

}