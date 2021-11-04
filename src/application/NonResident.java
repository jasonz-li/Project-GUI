package application;

/**
 * The NonResident class extends from Student, and it is a type of student in the roster.
 * Tuition is calculated accordingly based on whether a student is full time, and credit hours
 * toString() formats the student to list their attributes.
 *
 * @author Jason Li, John Leng
 */

public class NonResident extends Student {
    /**
     * Boolean for full-time or not
     */
    public boolean fullTime;


    /**
     * Constructs a NonResident
     * @param name input name
     * @param major input major
     * @param credits input credits
     */
    public NonResident(String name, String major, int credits){
        super(name, major, credits);
        this.fullTime = credits >= 12;
    }


    /**
     * Abstract class.
     */
    public void tuitionDue() {
        double tuition = 29737;
        double fee = 3268;
        double parttimeFee = 0.8;
        double nonresRate = 966;
        if (fullTime == true){ // full-time
            if (this.getCreditHours() > 16){ // over 16 credit fees
                tuition += fee + nonresRate * (this.getCreditHours() - 16);
            }
            else{ // 12-16 credits
                tuition += fee;
            }
        }
        else if (fullTime == false){// part-time
            tuition = nonresRate * this.getCreditHours() + fee * parttimeFee;
        }
        this.setTotalCost(tuition);
    }

    /**
     * Checks to see if obj is equal to NonResident.
     *
     * @param obj input student
     * @return returns true if obj is equal, otherwise returns false
     */
    @Override
    public boolean equals(Object obj) {
        super.toString();
        NonResident input = NonResident.class.cast(obj);
        if (input.fullTime == this.fullTime && this.getProfile().getName().equals(input.getProfile().getName())
                && this.getProfile().getMajor().equals(input.getProfile().getMajor())){
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * Returns information in string format.
     * @return returns information in 'name:major:creditHours:tuitionDue:totalPayment:lastPaymentDate:typeOfStudent' format.
     */
    @Override
    public String toString() {
        return super.toString() + ":nonresident";
    }

}