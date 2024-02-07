import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CSVParser {
    /*
    * load(String)
    *   parameters:
    *       --String: fileName
    *
    * This function reads in a file path and opens the file. It then creates
    * a List of people that it reads in the needed info into the People class
    * attributes. If the line does not have the intended columns it is skipped
    */
    private static List<Person> load(String path) throws FileNotFoundException {
        List<Person> people = new ArrayList<>();

        try(Scanner scanner = new Scanner(new File(path))) {
            if(scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                line = line.replaceAll("\"", "");
                String[] info = line.split(",");
                if(info.length == 8){
                    String name = info[0];
                    double weight = Double.parseDouble(info[2]);
                    Date startDate = new SimpleDateFormat("MM/dd/yy HH:mm").parse(info[5]);
                    Date endDate = new SimpleDateFormat("MM/dd/yy HH:mm").parse(info[6]);

                    String[] consumptionColumn = info[7].split("/");
                    double vitaminC = 0;
                    if(consumptionColumn.length >= 2){
                        vitaminC = Double.parseDouble(consumptionColumn[1]);
                    }

                    people.add(new Person(name, startDate, endDate, weight, vitaminC));
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return people;
    }
    /*
     * main(String[] args)
     *   parameters:
     *       --String[]: cmd line arguments
     *
     * The main creates a list for each needed attribute that is printed
     * to store multiple people if they have a tie in the highest trait. Using
     * the load() function it works through each one comparing their duration,
     * weight, and vitamin C to get the highest. Finally, the number of people is
     * counted and the average is taken and printed.
     */
    public static void main(String[] args) throws IOException {
        String csvPath = args[0];
        List<Person> people = load(csvPath);

        List<String> longestWorkoutList = new ArrayList<>();
        List<String> highestWeightList = new ArrayList<>();
        List<String> highestVitaminCList = new ArrayList<>();
        long longestWorkout = 0;
        double highestWeight = 0;
        double highestVitaminC = 0;
        double avgWeight = 0;
        int count = 0;

        for(Person p : people) {
            String name = p.getName();
            long duration = p.getEndDate().getTime() - p.getStartDate().getTime();
            double weight = p.getWeight();
            double vitaminC = p.getVitaminC();
            avgWeight += weight;
            count++;

            if(duration == longestWorkout) {
                longestWorkoutList.add(name);
            }else if(duration > longestWorkout) {
                longestWorkout = duration;
                longestWorkoutList.clear();
                longestWorkoutList.add(name);
            }
            if(weight == highestWeight) {
                highestWeightList.add(name);
            }else if(weight > highestWeight) {
                highestWeight = weight;
                highestWeightList.clear();
                highestWeightList.add(name);
            }
            if(vitaminC == highestVitaminC) {
                highestVitaminCList.add(name);
            }else if(vitaminC > highestVitaminC) {
                highestVitaminC = vitaminC;
                highestVitaminCList.clear();
                highestVitaminCList.add(name);
            }
        }
        avgWeight /= count;

        System.out.println("People w/ longest workout: " + longestWorkoutList);
        System.out.println("People w/ highest weight: " + highestWeightList);
        System.out.println("People w/ highest Vitamin C: " + highestVitaminCList);
        System.out.println("Number of people: " + count);
        System.out.println("Average Weight: " + avgWeight);
    }
}