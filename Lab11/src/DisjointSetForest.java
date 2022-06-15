import java.util.HashSet;
import java.util.Set;

public class DisjointSetForest implements DisjointSetDataStructure {
	
        int count;
	
        class Element{
		int rank;
		int parent;
	}

	Element []arr;
	
	public DisjointSetForest(int size) {
		//TODO
                arr = new Element[size];
	}
	
	@Override
	public void makeSet(int item) {
		//TODO
                Element e = new Element();
                e.parent = item;
                e.rank = 0;
                arr[item] = e;
                count++;
	}
        
        private void link(int x, int y)
        {
            Element eX = arr[x];
            Element eY = arr[y];
            if (eX.rank > eY.rank)
            {
                eY.parent = x;
            }
            else
            {
                eX.parent = y;
                if (eX.rank == eY.rank)
                {
                    eY.rank++;
                }
            }
        }
        
	//@Override
	public int findSet(int item) {
		//TODO
            Element e = arr[item];
            if (item != e.parent)
            {
                e.parent = findSet(e.parent);
            }
            return e.parent;
        }       

	@Override
	public boolean union(int x, int y)
        {
            int setX = findSet(x);
            int setY = findSet(y);
            if (setX == setY)
            {
                return false;
            }
            link(setX, setY);
            return true;
        }

	
	@Override
	public String toString() {
		//TODO
            StringBuilder result = new StringBuilder("Disjoint sets as forest:\n");
            for (int i = 0; i < arr.length; i++)
            {
                Element e = arr[i];
                result.append(i).append(" -> ").append(e.parent);
                if (i < arr.length - 1)
                {
                    result.append("\n");
                }
            }
            return result.toString();
	}

	@Override
	public int countSets() {
		// TODO 
            Set<Integer> uniques = new HashSet<>();
            for (int i = 0; i < arr.length; i++)
            {
                uniques.add(arr[i].parent);
            }
            return uniques.size();
	}
}