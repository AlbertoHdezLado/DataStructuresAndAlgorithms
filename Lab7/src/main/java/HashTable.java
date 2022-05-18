import java.util.LinkedList;
import java.util.*;

public class HashTable{
	//LinkedList arr[]; // use pure array
        LinkedList<IWithName> arr[]; // use pure array
	private final static int defaultInitSize = 8;
	private final static double defaultMaxLoadFactor = 0.7;
	private int size;	
	private final double maxLoadFactor;
        private int threshold;
        
	public HashTable() {
		this(defaultInitSize);
	}                 
        
        public HashTable(int initCapacity) {
            this(initCapacity, defaultMaxLoadFactor);
        }


	
    @SuppressWarnings("unchecked")
    public HashTable(int initCapacity, double maxLF)
    {
        if (initCapacity < 0)
        {
            throw new IllegalArgumentException("Illegal Capacity: " + initCapacity);
        }
        if (maxLF <= 0 || Double.isNaN(maxLF))
        {
            throw new IllegalArgumentException("Illegal Load: " + maxLF);
        }

        if (initCapacity == 0)
        {
            initCapacity = 1;
        }
        this.maxLoadFactor = maxLF;
        arr = new LinkedList[initCapacity];
        threshold = (int) (initCapacity * maxLoadFactor);
    }

    public boolean add(Object elem)
    {
        if (!(elem instanceof IWithName)) {
            return false;
        }
        
        IWithName value = (IWithName) elem;

        if (size >= threshold) {
            doubleArray();
        }

        int index = (value.hashCode() & 0x7FFFFFFF) % arr.length;
        
        if (arr[index] == null) {
            arr[index] = new LinkedList<>();
        }
        
        if (arr[index].contains(value)) {
            return false;
        }
        
        arr[index].add(value);
        size++;
        
        return true;
    }

    @SuppressWarnings("unchecked")
    private void doubleArray() //3
    {
        int oldCap = arr.length;
        int newCap = oldCap * 2;

        LinkedList<IWithName>[] oldArr = arr;
        arr = new LinkedList[newCap];

        threshold = (int) (newCap * maxLoadFactor);

        for (int i = 0; i < oldCap; i++)
        {
            List<IWithName> old = oldArr[i];
            if (old == null)
            {
                continue;
            }
            for (IWithName e : old)
            {
                int index = (e.hashCode() & 0x7FFFFFFF) % newCap;
                if (arr[index] == null)
                {
                    arr[index] = new LinkedList<>();
                }
                arr[index].add(e);
            }
        }
    }


    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < arr.length; i++)
        {
            result.append(i).append(':');
            LinkedList<IWithName> list = arr[i];
            if (list != null)
            {
                Iterator<IWithName> it = list.iterator();
                while (it.hasNext())
                {
                    IWithName elem = it.next();
                    result.append(' ').append(elem.getName());
                    if (it.hasNext())
                    {
                        result.append(',');
                    }

                }
            }
            result.append('\n');
        }
        return result.toString();
    }

    public Object get(Object toFind)
    {
        if (toFind == null)
        {
            return null;
        }
        int index = (toFind.hashCode() & 0x7FFFFFFF) % arr.length;
        LinkedList<IWithName> list = arr[index];
        if (list == null)
        {
            return null;
        }
        for (IWithName e : list)
        {
            if (toFind.equals(e))
            {
                return e;
            }
        }
        return null;
    }

}