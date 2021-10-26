package application; /**
 * The TriState class extends from NonResident, and it is a type of student in the roster.
 * Tuition is calculated accordingly based on whether they're from CT or NY and credit hours
 * toString() formats the student to list their attributes.
 *
 * @author Jason Li, John Leng
 */
import java.text.DecimalFormat;

public class TriState extends NonResident {
    public boolean livesInCT;
    public boolean livesInNY;

    /**
     * Constructs a Tri-State area student.
     * @param name input name
     * @param major input major
     * @param credits input credits
     * @param state input state; can only be NY or CT
     */
    public TriState(String name, String major, int credits, String state){
        super(name, major, credits);
        if (state.toUpperCase().equals("NY")){
            this.livesInCT = false;
            this.livesInNY = true;
        }
        else{
            this.livesInCT = true;
            this.livesInNY = false;
        }
    }

    /**
     * Abstract class.
     */
    public void tuitionDue() {
        double tuition = 29737;
        double fee = 3268;
        double NYDiscount = 4000;
        double CTDiscount = 5000;
        if (livesInCT){ // CT discount
            if (this.getCreditHours() < 12){
                tuition = 966 * this.getCreditHours() + 3268 * 0.8;
            }else if (this.getCreditHours() > 16){
                tuition = tuition + fee - CTDiscount + 966 * (this.getCreditHours() - 16);
            }
            else{
                tuition = tuition + fee - CTDiscount;
            }
        }
        else{ // NY discount
            if (this.getCreditHours() < 12){
                tuition = 966 * this.getCreditHours() + 3268 * 0.8;
            }else if (this.getCreditHours() > 16){
                tuition += fee - NYDiscount + 966 * (this.getCreditHours() - 16);
            }
            else{
                tuition += fee - NYDiscount;
            }
        }
        this.setTotalCost(tuition);
    }

    /**
     * Checks to see if obj is equal to a TriState student.
     *
     * @param obj input student
     * @return returns true if obj is equal, otherwise returns false
     */
    @Override
    public boolean equals(Object obj) {
        TriState input = TriState.class.cast(obj);
        if (input.livesInNY == this.livesInNY && input.livesInCT == this.livesInCT && this.getProfile().getName().equals(input.getProfile().getName())
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
    public String toString() {  //John Doe:IT:18 credit hours:tuition due:30,937.00:total payment:0.00:last payment date: --/--/--:non-resident(tri-state):NY
        String pattern = "###,##0.00";
        DecimalFormat numberFormat = new DecimalFormat(pattern);
        Date studentDateCheck = super.getDate(); // gets the student date
        String dateString = "";
        if(studentDateCheck == null){
            dateString = "--/--/--";
        }else{
            dateString = this.getDate().getDate();
        }
        String CTorNY = "NY";
        if (livesInCT) {
            CTorNY = "CT";
        }
        String string = this.getProfile().getName() + ":" + this.getProfile().getMajor() + ":" + this.getCreditHours()
                + " credit hours:" + "tuition due:" + numberFormat.format(this.getTotalCost()) + ":" +
                "total payment:" + numberFormat.format(this.getTotalPayment()) + ":" + "last payment date: "
                + dateString + ":" + "non-resident(tri-state):" + CTorNY;

        return string;
    }

}