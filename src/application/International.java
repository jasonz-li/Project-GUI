package application;
/**
 * The International class extends from NonResident, and it is a type of student in the roster.
 * Tuition is calculated accordingly based on studying abroad, and credit hours
 * toString() formats the student to list their attributes.
 *
 * @author Jason Li, John Leng
 */

public class International extends NonResident {
    public boolean studyingAbroad;

    /**
     * Constructs an international student.
     * @param name input name
     * @param major input major
     * @param credits input credits
     * @param studyingAbroad input boolean to see if International Student is studying abroad or not.
     */
    public International(String name, String major, int credits, boolean studyingAbroad){
        super(name, major, credits);
        this.studyingAbroad = studyingAbroad;
    }

    /**
     * Abstract class.
     */
    public void tuitionDue() {
        double tuition = 29737;
        double fee = 3268;
        double extraFee = 2650;
        if (studyingAbroad){ // student is studying abroad
            tuition = fee + 2650;
        }
        else{ // full-time tuition regular
            if (this.getCreditHours() > 16){
                tuition = tuition + fee + extraFee + 966 * (this.getCreditHours() - 16);
            }
            else{
                tuition = tuition + fee + extraFee;
            }
        }
        this.setTotalCost(tuition);
    }

    /**
     * Checks to see if Object o is equal to NonResident.
     *
     * @param obj input student
     * @return returns true if obj is equal, otherwise returns false
     */
    @Override
    public boolean equals(Object obj) {
        International input = International.class.cast(obj);
        if (input.studyingAbroad == this.studyingAbroad
                && this.getProfile().getName().equals(input.getProfile().getName())
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
    public String toString() {  //Joshua Patel:CS:12 credit hours:tuition due:5,918.00:total payment:0.00:last payment date: --/--/--:non-resident:international:study abroad
        String pattern = "###,##0.00";
        DecimalFormat numberFormat = new DecimalFormat(pattern);
        Date studentDateCheck = super.getDate(); // gets the student date
        String dateString = "";
        if(studentDateCheck == null){
            dateString = "--/--/--";
        }else{
            dateString = this.getDate().getDate();
        }
        String str = "";
        String studyAbroad = "";
        if (this.studyingAbroad == true) {
            studyAbroad = "study abroad";
            str = this.getProfile().getName() + ":" + this.getProfile().getMajor() + ":" + this.getCreditHours()
                    + " credit hours:" + "tuition due:" + numberFormat.format(this.getTotalCost()) + ":" +
                    "total payment:" + numberFormat.format(this.getTotalPayment()) + ":" + "last payment date: "
                    + dateString + ":" + "non-resident:international:" + studyAbroad;
        }
        else{
            str = this.getProfile().getName() + ":" + this.getProfile().getMajor() + ":" + this.getCreditHours()
                    + " credit hours:" + "tuition due:" + numberFormat.format(this.getTotalCost()) + ":" +
                    "total payment:" + numberFormat.format(this.getTotalPayment()) + ":" + "last payment date: "
                    + dateString + ":" + "non-resident:international";
        }
        return str;
    }

}