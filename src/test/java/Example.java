import java.util.List;
import java.util.Arrays;

public class Example {
    public static void exampleFunction(Integer... args) {
        for (Integer arg : args) {
            System.out.println(arg);
        }
    }

    public static void main(String[] args) {
        List<Integer> myList = Arrays.asList(1, 2, 3, 4);
        exampleFunction(myList.toArray(Integer[]::new));
    }
}
