package numbers;

import java.util.StringJoiner;

public class MagicNumber {

    private final long number;
    static boolean divisibleBy;
    static boolean endsBy;
    private final boolean isBuzz;
    private final boolean isDuck;
    private final boolean isPalindromic;
    private final boolean isGapful;
    private final boolean isSpy;
    private final boolean isEven;
    private final boolean isOdd;
    private final boolean isSquare;
    private final boolean isSunny;
    private final boolean isJumping;
    private final boolean isHappy;
    private final boolean isSad;

    public long getNumber() {
        return number;
    }

    public MagicNumber(long number) {
        this.number = number;

        isBuzz = checkBuzz();
        isDuck = checkDuck();
        isPalindromic = checkPalindromic();
        isGapful = checkGapful();
        isSpy = checkSpy();
        isEven = !checkOdd();
        isOdd = checkOdd();
        isSquare = checkSquare(number);
        isSunny = checkSunny();
        isJumping = checkJumping();
        isHappy = checkHappy();
        isSad = !checkHappy();
    }

    public boolean checkDuck() {
        return number > 0 && Long.toString(number).contains("0");
    }

    public boolean checkBuzz() {
        divisibleBy = isDivisibleBy7(number);
        endsBy = Long.toString(number).endsWith("7");

        return divisibleBy || endsBy;
    }

    private boolean isDivisibleBy7(long num) {
        long lastDigit = num % 10;
        long remainingNum = num / 10;

        return (remainingNum - lastDigit * 2) % 7 == 0;
    }

    public boolean checkOdd() {
        return number % 2 != 0;
    }

    public boolean checkPalindromic() {
        StringBuilder sb = new StringBuilder(Long.toString(number)).reverse();

        return sb.toString().equals(Long.toString(number));
    }

    public boolean checkGapful() {
        String digit = Long.toString(number);

        if (digit.length() < 3) return false;

        long divisor = (long) Math.pow(10, digit.length() - 1);
        long first_digit = number / divisor;
        String firstAndLast = "" + first_digit + number % 10;

        return number % Long.parseLong(firstAndLast) == 0;
    }

    public boolean checkSpy() {
        return (sumDigits(number) == productDigits(number));
    }

    private long sumDigits(long num) {
        long sum = 0;

        while (num > 0) {
            sum = sum + num % 10;
            num = num / 10;
        }
        return sum;
    }

    private long productDigits(long num) {
        long product = 1;

        while (num != 0) {
            product = product * (num % 10);
            num = num / 10;
        }
        return product;
    }

    public boolean checkSunny() {
        return checkSquare(number + 1);
    }

    public boolean checkSquare(long num) {
        double sr = Math.sqrt(num);

        return((sr - Math.floor(sr)) == 0);
    }

    public boolean checkJumping() {
        String numberString = number + "";
        for (int i = 0; i < numberString.length() - 1; i++) {
            if (Math.abs(numberString.charAt(i) - numberString.charAt(i + 1)) != 1) return false;
        }

        return true;
    }

    public boolean checkHappy() {
        long result = number;

        while(result != 1 && result != 4){
            result = isHappyNumber(result);
        }

        return result == 1;
    }

    private long isHappyNumber(long num){
        long rem, sum = 0;

        while(num > 0) {
            rem = num%10;
            sum = sum + (rem*rem);
            num = num/10;
        }
        return sum;
    }

    public void printAllProps() {
        System.out.println("Properties of " + number);
        System.out.println("even: " + isEven);
        System.out.println("odd: " + isOdd);
        System.out.println("buzz: " + isBuzz);
        System.out.println("duck: " + isDuck);
        System.out.println("palindromic: " + isPalindromic);
        System.out.println("gapful: " + isGapful);
        System.out.println("spy: " + isSpy);
        System.out.println("square: " + isSquare);
        System.out.println("sunny: " + isSunny);
        System.out.println("jumping: " + isJumping);
        System.out.println("happy: " + isHappy);
        System.out.println("sad: " + isSad);
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ");

        if (isBuzz) sj.add("buzz");
        if (isDuck) sj.add("duck");
        if (isPalindromic) sj.add("palindromic");
        if (isGapful) sj.add("gapful");
        if (isSpy) sj.add("spy");
        if (isEven) sj.add("even");
        if (isOdd) sj.add("odd");
        if (isSquare) sj.add("square");
        if (isSunny) sj.add("sunny");
        if (isJumping) sj.add("jumping");
        if (isHappy) sj.add("happy");
        if (isSad) sj.add("sad");

        return number + " is " + sj;
    }

}
