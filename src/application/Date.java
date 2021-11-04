package application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * The Date class holds year, month, and day, and it checks if the date is valid and also compares two dates.
 * Method isValid() checks if the date is valid according to the assignments parameters.
 * Method compareTo() returns 1 if the date is valid, and returns 0 otherwise.
 *
 * @author Jason Li, John Leng
 */

public class Date implements Comparable<Date> {

    /**
     * The date's year
     */
    private int year;

    /**
     * The date's month
     */
    private int month;

    /**
     * The date's day
     */
    private int day;

    /**
     * A boolean check if the date is cleared
     */
    private boolean dateCleared = false;


    /**
     * Takes the string "mm/dd/yyyy" and creates a Date object.
     *
     * @param date input date
     */
    public Date(String date) {
        StringTokenizer inputDate = new StringTokenizer(date);
        this.month = Integer.parseInt(inputDate.nextToken("/"));
        this.day = Integer.parseInt(inputDate.nextToken("/"));
        this.year = Integer.parseInt(inputDate.nextToken("/"));
    }

    /**
     * Creates an object with today's date.
     */
    public Date() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        String todayDate = dateFormat.format(cal.getTime());
        StringTokenizer today = new StringTokenizer(todayDate);
        this.month = Integer.parseInt(today.nextToken("/"));
        this.day = Integer.parseInt(today.nextToken("/"));
        this.year = Integer.parseInt(today.nextToken("/"));
    }


    /**
     * Checks if the date is valid by checking month, year, day, and leap years
     * @return returns true if its valid and false if it's not
     */
    public boolean isValid() {
        final int TWENTY_TWENTY_ONE = 2021, TWENTY_EIGHT_DAYS = 28, TWENTY_NINE_DAYS = 29, THIRTY_DAYS = 30, THIRTY_ONE_DAYS = 31;
        final int JAN = 1, FEB = 2, MAR = 3;
        final int APR = 4;
        final int MAY = 5;
        final int JUN = 6;
        final int JUL = 7;
        final int AUG = 8;
        final int SEP = 9;
        final int OCT = 10;
        final int NOV = 11;
        final int DEC = 12;
        boolean LEAP_YEAR = false;

        //Determines if the year is a leap year
        if(this.year % 4 == 0){
            if(this.year % 100 == 0){
                if(this.year % 400 == 0){
                    LEAP_YEAR = true;
                }
                else{
                    LEAP_YEAR = false;
                }

            }
            else{
                LEAP_YEAR = true;
            }
        }
        else{
            LEAP_YEAR = false;
        }

        //Checks for before 2021
        if(this.year < TWENTY_TWENTY_ONE) return false;
        //checks if the date is greater than today
        Date today = new Date();
        if (this.compareTo(today) == 1){
            return false;
        }
        //Checks February normally and on leap years
        if((this.day <= TWENTY_EIGHT_DAYS && this.month == FEB)
                || (LEAP_YEAR == true && this.day <= TWENTY_NINE_DAYS && this.month == FEB)) return true;

        //Checks months with 30 days
        if(this.day <= THIRTY_DAYS && (this.month == APR || this.month == JUN || this.month == SEP || this.month == NOV) )
            return true;
        //Checks months with 31 days
        if(this.day <= THIRTY_ONE_DAYS && (this.month == JAN || this.month == MAR || this.month == MAY
                || this.month == JUL || this.month == AUG || this.month == OCT || this.month == DEC))
            return true;
        return false;
    }

    /**
     * If the inputted date is older than the date the function is called on, returns 1, else returns 0.
     * @param date input date
     * @return 1 if the inputed date is older, 0 if it's not older
     */
    @Override
    public int compareTo(Date date) {
        if (date.dateCleared){
            return 0;
        }
        else{
            if(this.year > date.year){
                return 1;
            }
            if(this.year == date.year && this.month > date.month){
                return 1;
            }
            if(this.year == date.year && this.month == date.month && this.day >= date.day){
                return 1;
            }
        }
        return 0;
    }

    /**
     * Gets date.
     *
     * @return date in mm/dd/yyyy format and returns --/--/-- if the date has been cleared for
     * international students
     */
    public String getDate() {
        if (dateCleared){
            return "--/--/--";
        }
        else{
            return month + "/" + day + "/" + year;
        }
    }

    /**
     * Checks to see if date has been cleared because of International students.
     * @return true if the date has been cleared, otherwise false.
     */
    public boolean getDateCleared(){
        return this.dateCleared;
    }

    /**
     * Sets setDateCleared to input boolean.
     * @param bool input boolean
     */
    public void setDateCleared(boolean bool){
        this.dateCleared = bool;
    }

}