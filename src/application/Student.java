package application;
import java.text.DecimalFormat;
/**
 * The Student is a super class for other types of students: International, TriState, NonResident, Resident
 * payTuition() takes in a payment amount and date to pay off the tuition.
 * totalCost holds the value for the tuition due;
 * toString() formats the student to list their attributes.
 *
 * @author Jason Li, John Leng
 */

public class Student {

    /**
     * An instance of profile
     */
    private Profile profile;

    /**
     * Credit hours of students
     */
    private int creditHours;

    /**
     * Total tuition cost
     */
    private double totalCost = 0;

    /**
     * Total payment paid
     */
    private double totalPayment = 0;

    /**
     * Date instantiated for payment
     */
    private Date date = null;



    /**
     * Constructor for Student class
     */
    public Student(){
    }

    /**
     * Constructs a Student.
     *
     * @param name input name
     * @param major input major
     * @param creditHours input credit hours
     */
    public Student (String name, String major, int creditHours){
        this.profile = new Profile(name, major);
        this.creditHours = creditHours;
    }

    /**
     * Abstract class.
     */
    public void tuitionDue() {
    }

    /**
     * Pays the tuition by subtracting the total tuition of student by input payment.
     *
     * @param payment input payment
     * @param date date of payment
     * @return boolean returns true if payment was successful, otherwise returns false
     */
    public boolean payTuition(double payment, Date date){
        if (payment <= 0){
            System.out.println("Invalid amount.");
            return false;
        }
        else{
            if (payment <= this.totalCost){
                this.totalPayment = this.totalPayment + payment;
                this.date = date;
                this.totalCost = this.totalCost - payment;
                return true;
            }
            else{                //payment > cost
                return false;
            }
        }
    }

    /**
     * Returns information in string format.
     * @return returns information in 'name:major:creditHours:tuitionDue:totalPayment:lastPaymentDate:typeOfStudent' format.
     */
    @Override
    public String toString() {  //John Doe:EE:18 credit hours:tuition due:0.00:total payment:0.00:last payment date: --/--/--:resident
        String pattern = "####,###0.00";
        DecimalFormat numberFormat = new DecimalFormat(pattern);
        String dateString = "";
        if(this.date == null){
            dateString = "--/--/--";
        }
        else{
            dateString = date.getDate();
        }
        String string = this.profile.getName() + ":" + this.profile.getMajor() + ":" + this.creditHours
                + " credit hours:" + "tuition due:" + numberFormat.format(this.totalCost) + ":" +
                "total payment:" + numberFormat.format(this.totalPayment) + ":" + "last payment date: "
                + dateString;

        return string;
    }

    /**
     * Checks to see if Object o is equal to Student.
     *
     * @param o input student
     * @return returns true if o is equal, otherwise returns false
     */
    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if(o instanceof Student){
            Student newStudent = (Student) o;
            if(this.profile.equals(newStudent.profile)){
                return true;
            }
        }
        return false;
    }


    /**
     * Gets profile.
     * @return profile of student
     */
    public Profile getProfile() {return this.profile;}

    /**
     * Gets credit hours.
     * @return credit hours of student.
     */
    public int getCreditHours() {return this.creditHours;}

    /**
     * Sets credit hours of student.
     * @param credits input credits.
     */
    public void setCreditHours(int credits) {this.creditHours = credits;}

    /**
     * Gets the total cost of tuition of student.
     * @return total cost as a double.
     */
    public double getTotalCost() {return this.totalCost;}

    /**
     * Sets the total cost of tuition for student.
     * @param totalCost input total cost.
     */
    public void setTotalCost(double totalCost) {this.totalCost = totalCost;}

    /**
     * Gets last payment date of student.
     * @return last payment date.
     */
    public Date getDate() {return this.date;}

    /**
     * Gets total amount of payment student has paid.
     * @return total amount of payment.
     */
    public double getTotalPayment() {return this.totalPayment;}

    /**
     * Sets total payment to input double.
     * @param doub input double
     */
    public void setTotalPayment(double doub) {this.totalPayment = doub;}

}