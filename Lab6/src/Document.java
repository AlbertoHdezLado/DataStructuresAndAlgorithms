import java.util.ListIterator;
import java.util.Scanner;
import java.util.*;

public class Document {
    public String name;
    public TwoWayCycledOrderedListWithSentinel<Link> link;

    public Document(String name, Scanner scan) {
        this.name = name.toLowerCase();
        link = new TwoWayCycledOrderedListWithSentinel<Link>();
        load(scan);
    }

    public void load(Scanner scan) {
        String marker = "link=";
        String endMarker = "eod";
        String line = scan.nextLine().toLowerCase();
        while (!line.equalsIgnoreCase(endMarker)) {
            String arr[] = line.split(" ");
            for (String word : arr) {
                if (word.startsWith(marker)) {
                    String linkStr = word.substring(marker.length());
                    Link l;
                    if ((l = createLink(linkStr)) != null)
                        link.add(l);
                }

            }
            line = scan.nextLine().toLowerCase();
        }
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)


    public static boolean isCorrectId(String id) {
        id = id.toLowerCase();
        if (id.length() == 0) return false;
        if (id.charAt(0) < 'a' || id.charAt(0) > 'z')
            return false;
        for (int i = 1; i < id.length(); i++) {
            if (!(id.charAt(i) >= 'a' && id.charAt(i) <= 'z'
                    || id.charAt(i) >= '0' && id.charAt(i) <= '9'
                    || id.charAt(i) == '_'))
                return false;
        }
        return true;
    }

    private static Link createIdAndNumber(String id, int n) {
        if (!isCorrectId(id)) return null;
        return new Link(id.toLowerCase(), n);
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    public static Link createLink(String link) {
        if (link.length() == 0) return null;
        int openBracket = link.indexOf('(');
        int closeBracket = link.indexOf(')');
        if (openBracket > 0 && closeBracket > openBracket && closeBracket == link.length() - 1) {
            String strNumber = link.substring(openBracket + 1, closeBracket);
            try {
                int number = Integer.parseInt(strNumber);
                if (number < 1)
                    return null;
                return createIdAndNumber(link.substring(0, openBracket), number);
            } catch (NumberFormatException ex) {
                return null;
            }
        }

        return createIdAndNumber(link, 1);
    }

    @Override
    public String toString() {
        String retStr = "Document: " + name;
        int counter = 0;
        for (Link linkElem : link) {
            if (counter % 10 == 0)
                retStr += "\n";
            else
                retStr += " ";
            retStr += linkElem.toString();
            counter++;
        }
        return retStr;
    }

    public String toStringReverse() {
        String retStr = "Document: " + name;
        int counter = 0;
        ListIterator<Link> iter = link.listIterator();
        while (iter.hasNext())
            iter.next();
        while (iter.hasPrevious()) {
            if (counter % 10 == 0)
                retStr += "\n";
            else
                retStr += " ";
            retStr += iter.previous().toString();
            counter++;
        }
        return retStr;
    }

    public int[] getWeights() {
        int[] arr = new int[link.size()];
        int i = 0;
        for (Link linkElem : link) {
            arr[i++] = linkElem.weight;
        }
        return arr;
    }

    public static void showArray(int[] arr) {
        if (arr.length > 0) {
            System.out.print(arr[0]);
            for (int i = 1; i < arr.length; i++)
                System.out.print(" " + arr[i]);
        }
        System.out.println();
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    void bubbleSort(int[] arr) {
        showArray(arr);
        for (int begin = 0; begin < arr.length - 1; begin++) {
            for (int j = arr.length - 1; j > begin; j--)
                if (arr[j - 1] > arr[j])
                    swap(arr, j - 1, j);
            showArray(arr);
        }
    }

    public void insertSort(int[] arr) {
        showArray(arr);
        for (int pos = arr.length - 2; pos >= 0; pos--) {
            int value = arr[pos];
            int i = pos + 1;
            while (i < arr.length && value > arr[i]) {
                arr[i - 1] = arr[i];
                i++;
            }
            arr[i - 1] = value;
            showArray(arr);
        }

    }

    public void selectSort(int[] arr) {
        showArray(arr);
        for (int border = arr.length; border > 1; border--) {
            int maxPos = 0;
            for (int pos = 0; pos < border; pos++)
                if (arr[maxPos] < arr[pos])
                    maxPos = pos;
            swap(arr, border - 1, maxPos);
            showArray(arr);
        }
    }

    // Merge two sorted subarrays
    public static void merge(int[] A, int[] temp, int left, int mid, int right)
    {
        int k = left, i = left, j = mid + 1;

        while (i <= mid && j <= right)
        {
            if (A[i] < A[j]) {
                temp[k++] = A[i++];
            }
            else {
                temp[k++] = A[j++];
            }
        }

        // copy remaining elements
        while (i <= mid) {
            temp[k++] = A[i++];
        }

        for (i = left; i <= right; i++) {
            A[i] = temp[i];
        }
    }

    // Iteratively sort subarray `A[low…high]` using a temporary array
    public static void iterativeMergeSort(int[] A)
    {
        showArray(A);
        int low = 0;
        int high = A.length - 1;

        // sort array `A[]` using a temporary array `temp`
        int[] temp = Arrays.copyOf(A, A.length);

        // divide the array into blocks of size `m`
        for (int m = 1; m <= high - low; m = 2*m)
        {

            for (int i = low; i < high; i += 2*m)
            {
                int left = i;
                int mid = i + m - 1;
                int right = Integer.min(i + 2*m - 1, high);

                merge(A, temp, left, mid, right);
            }
            showArray(A);
        }

    }

    public static void CountingSortByExp (int[] arr, int exp) {
        int[] output = new int[arr.length];

        // Largest element
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max)
                max = arr[i];
        }
        int[] count = new int[max + 1];

        // Fill count array (0)
        for (int i = 0; i < max; ++i) {
            count[i] = 0;
        }

        // Count numbers
        for (int j = 0; j < arr.length; j++) {
            count[(arr[j] / exp) % 10]++;
        }

        // Cumulative sum
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Order the output
        for (int i = arr.length - 1; i >= 0; i--) {
            int counter = count[(arr[i] / exp) % 10]--;
            output[counter - 1] = arr[i];
        }

        System.arraycopy(output, 0, arr, 0, arr.length);
        showArray(output);
    }

    public void radixSort(int[] arr) {
        showArray(arr);
        for (int exp = 1; exp < 1000; exp *= 10) {
            CountingSortByExp(arr, exp);
        }
    }
} 