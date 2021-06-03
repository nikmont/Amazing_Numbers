package numbers;

import numbers.exceptions.ExclusivePropertiesException;
import numbers.exceptions.FirstNumberException;
import numbers.exceptions.SecondNumberException;
import numbers.exceptions.WrongPropertyException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        MagicNumber number;
        List<String> properties = List.of("buzz", "duck", "palindromic", "gapful", "spy", "even", "odd", "square", "sunny", "jumping", "happy", "sad");

        System.out.println("Welcome to Amazing Numbers!\n" +
                "\n" +
                "Supported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be processed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.");

        while (true) {
            System.out.println("\nEnter a request:");
            try {
                String input = scan.nextLine();
                List<String> nums = new ArrayList<>(Arrays.asList(input.split("\\s")));

                if (nums.size() == 1) {

                    long n = Long.parseLong(nums.get(0));

                    if (n == 0) {
                        System.out.println("Goodbye!");
                        break;
                    }

                    if (n < 0) throw new FirstNumberException();

                    number = new MagicNumber(n);
                    number.printAllProps();

                } else if (nums.size() == 2) {
                    if (Long.parseLong(nums.get(1)) < 0) throw new SecondNumberException();

                    List<MagicNumber> unfiltredNums =
                            LongStream.iterate(Long.parseLong(nums.get(0)), e -> e + 1)
                            .mapToObj(MagicNumber::new)
                            .limit(Long.parseLong(nums.get(1)))
                            .collect(Collectors.toList());

                    showProperties(unfiltredNums);
                } else {
                    List<String> unknownProps;
                    List<String> includeProps = new ArrayList<>();
                    List<String> excludeProps = new ArrayList<>();

                    for (int i = 2 ; i < nums.size(); i++) {

                        String property = nums.get(i).toLowerCase();
                        if (property.startsWith("-")) {
                            excludeProps.add(property.substring(1));
                        } else {
                            includeProps.add(property);
                        }
                    }

                    unknownProps = Stream.of(includeProps, excludeProps)
                            .flatMap(Collection::stream)
                            .filter(Predicate.not(properties::contains))
                            .collect(Collectors.toList());

                    if (!unknownProps.isEmpty()) throw new WrongPropertyException(unknownProps.toArray());

                    checkExclusiveProperties(includeProps, excludeProps);

                    List<MagicNumber> filtredNumbers = new ArrayList<>();
                    MagicNumber mn;
                    for (long i = Long.parseLong(nums.get(0)), counter = 0; counter < Long.parseLong(nums.get(1)) ; i++) {
                        mn = new MagicNumber(i);
                            if (includeProps.stream().allMatch(mn.toString()::contains)
                                    && excludeProps.stream().noneMatch(mn.toString()::contains)) {
                                counter++;
                                filtredNumbers.add(mn);
                            }
                    }

                    if (filtredNumbers.isEmpty()) {
                        System.out.println("There are no numbers with these properties.");
                        return;
                    }
                    showProperties(filtredNumbers);
                }
            } catch (NumberFormatException | FirstNumberException ex) {
                System.out.println("The first parameter should be a natural number or zero.");
            } catch (SecondNumberException | WrongPropertyException ex) {
                System.out.println(ex.getMessage());
            } catch (ExclusivePropertiesException ex) {
                System.out.println("The request contains mutually exclusive properties: " + ex.getMessage());
            }
        }
    }

    private static void showProperties(List<MagicNumber> list) {
        for (MagicNumber n : list) {
            System.out.println(n);
        }
    }

    private static void checkExclusiveProperties(List<String> incl, List<String> excl) throws ExclusivePropertiesException {
        List<List<String>> exclusivePairs = List.of(
                List.of("even", "odd"),
                List.of("duck", "spy"),
                List.of("sunny", "square"),
                List.of("sad", "happy")
        );

        //check in opposite pairs
        List<String> commonProps = new ArrayList<>(incl);
        commonProps.retainAll(excl);
        if (!commonProps.isEmpty()) {
            String[] cmnPrp = new String[commonProps.size() * 2];
            for (int i = 0; i < cmnPrp.length; i += 2) {
                cmnPrp[i] = commonProps.get(i);
                cmnPrp[i + 1] = "-" + commonProps.get(i);
            }
            throw new ExclusivePropertiesException(Arrays.toString(cmnPrp));
            }

        //check in inclusive pairs
        for (List<String> exclPair : exclusivePairs) {
            if (incl.containsAll(exclPair)) throw new ExclusivePropertiesException(Arrays.toString(exclPair.toArray()));
        }

        //check in exclusive pairs
        for (List<String> exclPair : exclusivePairs) {
            if (excl.containsAll(exclPair)) throw new ExclusivePropertiesException(Arrays.toString(exclPair.toArray()));
        }
    }
}
