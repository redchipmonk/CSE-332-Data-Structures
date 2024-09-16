package p2.sorts;

import java.util.Comparator;
import java.util.Random;
public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        sortHelper(array, comparator, 0, array.length - 1);
    }
    private static <E> void sortHelper(E[] array, Comparator<E> comparator, int minIndex, int maxIndex) {
        if (minIndex < maxIndex) {
            int partitionIndex = partition(array, comparator, minIndex, maxIndex);
            sortHelper(array, comparator, minIndex, partitionIndex - 1);
            sortHelper(array, comparator, partitionIndex + 1, maxIndex);
        }
    }
    private static <E> int partition(E[] array, Comparator<E> comparator, int minIndex, int maxIndex) {
        //median of three random indices
        Random random = new Random();
        int first = minIndex + random.nextInt(maxIndex - minIndex + 1);
        int second = minIndex + random.nextInt(maxIndex - minIndex + 1);
        int third = minIndex + random.nextInt(maxIndex - minIndex + 1);
        int pivotIndex;
        if ((comparator.compare(array[first], array[second]) < 0 && comparator.compare(array[second], array[third]) < 0)
                || (comparator.compare(array[third], array[second]) < 0 &&
                comparator.compare(array[second], array[first]) < 0)) {
            pivotIndex = second;
        } else if ((comparator.compare(array[second], array[first]) < 0 && comparator.compare(array[first], array[third]) < 0)
                || (comparator.compare(array[third], array[first]) < 0 &&
                comparator.compare(array[first], array[second]) < 0)) {
            pivotIndex = first;
        } else {
            pivotIndex = third;
        }
        E pivot = array[pivotIndex];
        array[pivotIndex] = array[maxIndex];
        array[maxIndex] = pivot;
        int i = minIndex - 1;
        for (int j = minIndex; j < maxIndex; j++) {
            if (comparator.compare(array[j], pivot) <= 0) {
                i++;
                E temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }

        }
        E temp = array[i + 1];
        array[i + 1] = array[maxIndex];
        array[maxIndex] = temp;
        return i + 1;
    }
}
