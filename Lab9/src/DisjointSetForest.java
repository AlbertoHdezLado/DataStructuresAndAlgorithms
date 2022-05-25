public class DisjointSetForest implements DisjointSetDataStructure {

    private class Element {
        int rank;
        int parent;
    }

    Element[] arr;

    public DisjointSetForest(int n) {
        arr = new Element[n];
    }

    @Override
    public void makeSet(int item) {
        Element e = new Element();
        e.parent = item;
        e.rank = 0;
        arr[item] = e;
    }

    @Override
    public int findSet(int item) {
        Element e = arr[item];
        if (item != e.parent) {
            e.parent = findSet(e.parent);
        }
        return e.parent;
    }

    @Override
    public boolean union(int x, int y) {
        int setX = findSet(x);
        int setY = findSet(y);
        if (setX == setY) {
            return false;
        }
        Element eX = arr[setX];
        Element eY = arr[setY];
        if (eX.rank > eY.rank) {
            eY.parent = setX;
        } else {
            eX.parent = setY;
            if (eX.rank == eY.rank) {
                eY.rank++;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder result = new StringBuilder("Disjoint sets as forest:\n");
        for (int i = 0; i < arr.length; i++) {
            Element e = arr[i];
            result.append(i).append(" -> ").append(e.parent);
            if (i < arr.length - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }
}