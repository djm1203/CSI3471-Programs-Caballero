import java.util.Date;

/*
 * Person
 *
 * This class holds the attributes we need to store;
 * name, startDate, endDate, weight, vitaminC. It contains
 * a person object and getters for the info as well.
 */
public class Person {
    private String name;
    private Date startDate;
    private Date endDate;
    private double weight;
    private double vitaminC;

    public Person(String name, Date startDate, Date endDate, double weight, double vitaminC) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.weight = weight;
        this.vitaminC = vitaminC;
    }

    public String getName() {return name;}
    public Date getStartDate() {return startDate;}
    public Date getEndDate() {return endDate;}
    public double getWeight() {return weight;}
    public double getVitaminC() {return vitaminC;}
}
