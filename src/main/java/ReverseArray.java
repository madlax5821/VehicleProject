import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReverseArray {
    public static void main(String[] args) {
        Test t = new Test();

    }

    public static void reverseString(){
        Scanner s = new Scanner(System.in);
        System.out.println("Print any words you wanted to reverse");
        String string = s.nextLine();
        String[] strings = string.split("\\ ");
        String[] strs = new String[strings.length];
        for(int i=0;i<strings.length;i++){
            strs[i]=strings[strings.length-1-i];
            System.out.print(strs[i]+" ");
        }

    }
}

class Test{
    private ArrayList<Integer> list;

    public Test(){
        list = getList();
    }

    private ArrayList<Integer> getList(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        return list;
    }
}
