package cracking.the.coding.interview.recursion.and.dynamic.programming;

public class Problem3 {

static int findIndex(int[] array, int left, int right) {
    if (left > right) return -1;
    int mid = (left + right) / 2;
    if (array[mid] == mid) return mid;
    int i = findIndex(array, Math.min(mid + 1, array[mid]), right);
    if (i >= 0) return i;
    return findIndex(array, left, Math.max(mid - 1, array[mid]));
}

static int findIndex(int[] array) {
    return findIndex(array, 0, array.length - 1);
}

    public static void main(String[] args) {
        int[] array = {4, 4, 4, 4, 4, 4, 4, 4, 4};
        System.out.println(findIndex(array));
    }
}
