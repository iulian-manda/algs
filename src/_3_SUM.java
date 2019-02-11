public class _3_SUM {
    static int _3_SUM(int[] a) {
        int count = 0;
        int n = a.length;
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1 ; j++) {
                int key = -(a[i] + a[j]);
                count += binarySearch(a, key, j + 1) ? 1 : 0;
            }
        }
        return count;
    }

    static boolean binarySearch(int a[], int key, int lo) {
        int hi = a.length - 1;
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if(key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(_3_SUM(new int[] {-40, -20, -10, 0, 5, 10, 30, 40, 50, 60}));
    }
}
