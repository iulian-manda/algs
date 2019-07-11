package cracking.the.coding.interview.moderate;

public class Problem1 {

    static void swap(Numbers n) {
        n.a = n.a - n.b;
        n.b = n.a + n.b;
        n.a = n.b - n.a;
    }

    public static void main(String[] args) {
        Numbers n = new Numbers();
        n.a = 5671312;
        n.b = 412321;
        swap(n);
        System.out.println(n.a + " " + n.b);
    }

    static class Numbers {
        int a, b;
    }
}
