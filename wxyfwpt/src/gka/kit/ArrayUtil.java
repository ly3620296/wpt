package gka.kit;


public class ArrayUtil {

    public static boolean contains(String[] array, String target) {
        boolean contains = false;
        for (String str : array) {
            if (target.equals(str)) {
                contains = true;
                break;
            }
        }
        return contains;
    }
}
