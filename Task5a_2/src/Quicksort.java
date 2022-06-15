import java.util.Arrays;
import java.util.Random;

public class Quicksort {
    static int partition(int[] arr, int low, int high)
    {
        Random rand= new Random();
        int pivot;
        System.out.println("Size: " + (high-low+1) + " (from " + low + " to " + high + ")");
        if (high-low+1 > 100) {
            int[] randomPivotIndex = new int[3];
            for (int i = 0; i < 3; i++)
                randomPivotIndex[i] = rand.nextInt(high-low)+low;
            System.out.println("Random pivot array: [" + arr[randomPivotIndex[0]] + ", " + arr[randomPivotIndex[1]] + ", " + arr[randomPivotIndex[2]] + "]");
            if (arr[randomPivotIndex[0]] > arr[randomPivotIndex[1]]) swap(randomPivotIndex,0,1);
            if (arr[randomPivotIndex[1]] > arr[randomPivotIndex[2]]) swap(randomPivotIndex,1,2);
            if (arr[randomPivotIndex[0]] > arr[randomPivotIndex[1]]) swap(randomPivotIndex,0,1);
            pivot = randomPivotIndex[1];
        }
        else {
            pivot = rand.nextInt(high-low)+low;
        }

        System.out.println("Random pivot: Index = " + pivot + ", Value = " + arr[pivot]);
        swap(arr, pivot, high);

        int i = (low-1);
        for (int j = low; j < high; j++)
            if (arr[j] < arr[high])
            {
                i++;
                swap(arr, i, j);
            }

        swap(arr, i+1, high);
        return i+1;
    }

    static void quicksort(int[] arr, int low, int high)
    {
        if (low < high)
        {
            System.out.println("Array: " + Arrays.toString(Arrays.copyOfRange(arr, low, high+1)));
            int pivotIndex = partition(arr, low, high);
            System.out.println();
            quicksort(arr, low, pivotIndex-1);
            quicksort(arr, pivotIndex+1, high);
        }
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args)
    {
        Random rand = new Random();
        int[] arr = new int[300];
        for (int i = 0; i<arr.length;i++)
            arr[i] = rand.nextInt(1000); //fill arr[] with random integers between 0-999

        quicksort(arr, 0, arr.length-1);

        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}

