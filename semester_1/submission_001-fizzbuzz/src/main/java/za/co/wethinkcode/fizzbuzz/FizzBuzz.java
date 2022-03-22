package za.co.wethinkcode.fizzbuzz;
import java.util.Arrays;

public class FizzBuzz {
/*
    public static void main(String[] args)
    {
//        System.out.println("enter a number");
//        int number = kb.nextInt();
////        System.out.println(new FizzBuzz().checkNumber(number));

//        System.out.println(new FizzBuzz().countTo(number));

        int [] arr = {10,20};
        System.out.println(Arrays.toString(arr));
    }
    //
*/


    public String checkNumber(int number) {

        boolean divisibleBy3 = number % 3 == 0;
        boolean divisibleBy5 = number % 5 == 0;
        if (divisibleBy3 || divisibleBy5) {
            if (divisibleBy3 && divisibleBy5) {
                return "FizzBuzz";
            } else if (divisibleBy3) {
                return "Fizz";
            } else if (divisibleBy5) {
                return "Buzz";
            }
        }
            return String.valueOf(number);
    }

    public String countTo(int number) {
        String[] array = {};
        for (int i = 1; i <= number; i++) {
            array = Arrays.copyOf(array, array.length + 1);
            array[array.length - 1] = checkNumber(i);
            System.out.print(Arrays.toString(array));
        }
        return Arrays.toString(array);
    }
}