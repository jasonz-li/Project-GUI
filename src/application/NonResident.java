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
        if (fullTime == true){ // full-time
            if (this.getCreditHours() > 16){ // over 16 credit fees
                tuition += fee + 966 * (this.getCreditHours() - 16);
            }
            else{ // 12-16 credits
                tuition += fee;
            }
        }
        else if (fullTime == false){// part-time
            tuition = 966 * this.getCreditHours() + 3268 * 0.8;
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
        String pattern = "###,##0.00";
        DecimalFormat numberFormat = new DecimalFormat(pattern);
        Date studentDateCheck = super.getDate(); // gets the student date
        String dateString = "";
        if(studentDateCheck == null){
            dateString = "--/--/--";
        }else{
            dateString = this.getDate().getDate();
        }
        String string = this.getProfile().getName() + ":" + this.getProfile().getMajor() + ":" + this.getCreditHours()
                + " credit hours:" + "tuition due:" + numberFormat.format(this.getTotalCost()) + ":" +
                "total payment:" + numberFormat.format(this.getTotalPayment()) + ":" + "last payment date: "
                + dateString + ":" + "non-resident";
        return string;
    }

}