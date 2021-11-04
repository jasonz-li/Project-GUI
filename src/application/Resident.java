package application;

/**
 * The Resident class extends from Student, and it is a type of student in the roster.
 * Tuition is calculated accordingly based credit hours and financial aid is offered.
 * setFinancialAid() sets the financial aid and only operates once.
 * toString() formats the student to list their attributes.
 *
 * @author Jason Li, John Leng
 */



public class Resident extends Student {
    /**
     * financial aid for residents
     */
    private double financialAid = 0;

    /**
     * Boolean to check if financial aid has been paid
     */
    private boolean financialAidPaid = false;



    /**
     * Constructor a Resident.
     * @param name input name
     * @param major input major
     * @param creditHours input credit hours
     */
    public Resident (String name, String major, int creditHours){
        super(name, major, creditHours);
    }

    /**
     * Abstract class.
     */
    public void tuitionDue() {
        double tuition = 12536;
        double partTimeCreditRate = 404;
        double universityFee = 3268;
        int creditsOver16 = 0;
        if(super.getCreditHours() < 12 && super.getCreditHours() >= 3) {     //part-time
            this.setTotalCost(super.getCreditHours() * partTimeCreditRate + (universityFee * 0.8));
        }else if(super.getCreditHours() >= 12 && super.getCreditHours() <= 24 ){              //full time
            if(super.getCreditHours() > 16){
                creditsOver16 = super.getCreditHours() - 16;
            }
            this.setTotalCost(tuition + creditsOver16 * partTimeCreditRate + universityFee);
        }else{
            //improper hours
        }
    }

    /**
     * Sets the financial aid of the student.
     * @param aid input aid amount,
     * @return true if aid was successfully given and returns false if student's aid limit has been capped.
     */
    public boolean setFinancialAid(double aid) {
        if (aid <= 10000 && aid >= 0) {
            this.financialAid = aid;
            if(super.getTotalCost() < financialAid){
                setTotalCost(0);
            }else{
                setTotalCost(super.getTotalCost() - financialAid);
                setTotalPayment(super.getTotalPayment() + financialAid);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns information in string format.
     * @return returns information in 'name:major:creditHours:tuitionDue:totalPayment:lastPaymentDate:typeOfStudent' format.
     */
    @Override
    public String toString() {  //John Doe:EE:18 credit hours:tuition due:0.00:total payment:0.00:last payment date: --/--/--:resident
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
                + dateString + ":" + "resident";
        return string;
    }


    /**
     * Gets financial aid paid to check if it has been offered already or not.
     * @return true if financial aid was offered already and false if it was not offered.
     */
    public boolean getFinancialAidPaid() {return this.financialAidPaid;}

    /**
     *  Sets financial aid paid.
     * @param bool input boolean.
     */
    public void setFinancialAidPaid(boolean bool) {this.financialAidPaid = bool;}


}