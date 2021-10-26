package application; /**
 * Tuition Manager takes commands from the user to perform actions on students in the Roster.
 * Some methods perform the following tasks adding, removing, paying tuition, printing, and calculating tuition.
 * checkRosterDuplicate() checks if a student is already in the roster.
 * checkMajor() ensures the inputted major is a proper major
 * handleRSFT() handles commands R, S, F, and T.
 *
 * @author Jason Li, John Leng
 */

import java.util.Scanner;
import java.util.StringTokenizer;

public class TuitionManager {
    public void run(){
        System.out.println("Tuition Manager starts running.");
        Scanner scanner = new Scanner(System.in);
        Roster roster = new Roster();
        while (true){
            String cmdInput = scanner.nextLine();
            if (cmdInput.equals("") != true) {
                StringTokenizer command = new StringTokenizer(cmdInput, ",");
                String input = command.nextToken();
                if (input.equals("Q")) {
                    System.out.println("Tuition Manager terminated.");
                    break;
                } else if (input.equals("P") || input.equals("PN") || input.equals("PT")) {
                    handlePrint(input, roster);
                } else if (input.equals("AR") || input.equals("AN") || input.equals("AT") || input.equals("AI")) {
                    handleAdd(input, command, roster);

                } else if (input.equals("R") || input.equals("S") || input.equals("F") || input.equals("T")) {
                    handleRSFT(input, command, roster);
                }
                else if (input.equals("C")){
                    calculate(roster);
                }
                else{
                    System.out.println("Command '" + input + "' not supported!");
                }
            }
            else{
                // do nothing if no input
            }
        }
        scanner.close();
    }

    /**
     * Handles the print command.
     * @param input print command; can be 'P', 'PN', or 'PT'.
     * @param roster roster of students
     */
    private void handlePrint(String input, Roster roster){
        if(roster.getSize() == 0){
            System.out.println("Student roster is empty!");
        }else {
            if (input.equals("P")) {
                roster.print();
            } else if (input.equals("PN")) {
                roster.printByName();
            } else if (input.equals("PT")) {
                roster.printByPaymentDate();
            }
        }
    }

    /**
     * Handles the add student function.
     * @param input add command; can be 'AR', 'AT', 'AI', or 'AN'.
     * @param command command line inputs
     * @param roster roster of students
     */
    private void handleAdd(String input, StringTokenizer command, Roster roster){
        if(command.hasMoreTokens()){
            String name = command.nextToken();
            if(command.hasMoreTokens()){
                String major = command.nextToken();
                String majorUpperCase = major.toUpperCase();
                if(command.hasMoreTokens()){
                    try{
                        int creditHours = Integer.parseInt(command.nextToken());
                        if(checkMajor(majorUpperCase) == false){System.out.println("'" + major + "'" + " is not a valid major."); return;}
                        if (creditHours < 0){System.out.println("Credit hours cannot be negative.");return;}
                        if (creditHours < 3){System.out.println("Minimum credit hours is 3.");return;}
                        if (creditHours > 24){System.out.println("Credit hours exceed the maximum 24.");return;}

                        if(input.equals("AR")){
                            Resident resident = new Resident(name, major, creditHours);
                            if(!checkRosterDuplicate(resident, roster)){
                                roster.add(resident);
                                System.out.println("Student added.");
                            }
                        }else if(input.equals("AN")){
                            NonResident nonresident = new NonResident(name, major, creditHours);
                            if(!checkRosterDuplicate(nonresident, roster)) {
                                roster.add(nonresident);
                                System.out.println("Student added.");
                            }
                        }else if(input.equals("AT")){
                            if(command.hasMoreTokens()){
                            }else{System.out.println("Missing data in command line.");return;}
                            String state = command.nextToken();
                            if(!state.toUpperCase().equals("NY") && !state.toUpperCase().equals("CT")){
                                System.out.println("Not part of the tri-state area.");
                            }else{
                                TriState tristate = new TriState(name, major, creditHours, state);
                                if(!checkRosterDuplicate(tristate, roster)) {
                                    roster.add(tristate);
                                    System.out.println("Student added.");
                                }
                            }
                        }else if(input.equals("AI")){
                            if(command.hasMoreTokens()){
                            }else{System.out.println("Missing data in command line.");return;}
                            boolean studyAbroad = Boolean.parseBoolean(command.nextToken());
                            International international = new International(name, major, creditHours, studyAbroad);
                            if(!checkRosterDuplicate(international, roster)) {
                                if (creditHours < 12){
                                    System.out.println("International students must enroll at least 12 credits.");
                                }else{
                                    roster.add(international);
                                    System.out.println("Student added.");
                                }
                            }
                        }
                    } catch(NumberFormatException e){
                        System.out.println("Invalid credit hours.");
                        return;
                    }
                }else{
                    System.out.println("Credit hours missing.");return;
                }
            }else{
                System.out.println("Missing data in command line.");return;
            }
        }else{
            System.out.println("Missing data in command line.");return;
        }
    }

    /**
     * Checks to see if input major is a valid major.
     * @param major input major
     * @return true if major is valid and false if not valid.
     */
    private boolean checkMajor(String major) {
        if (major.equals("CS") || major.equals("IT")  ||
                major.equals("BA") || major.equals("EE") || major.equals("ME")) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Handles the remove, set abroad status, set financial aid, and pay tuition commands.
     * @param input remove, set abroad status, set financial aid, or pay tuition command.
     * @param command command line inputs
     * @param roster roster of students
     */
    private void handleRSFT(String input, StringTokenizer command, Roster roster){
        if (!command.hasMoreTokens()){
            System.out.println("Missing data in command line.");
        }
        else {
            String name = command.nextToken();
            String major = command.nextToken().toUpperCase();
            boolean moreThan12 = false;
            if (input.equals("R")) {
                Student target = roster.findStudent(name, major);
                if (target != null) {
                    roster.remove(target);
                    System.out.println("Student removed from the roster.");
                } else {
                    System.out.println("Student is not in the roster.");
                }
            } else if (input.equals("S")) { // set abroad status me
                if (!command.hasMoreTokens()) {
                    System.out.println("Missing data in command line.");
                }
                else {
                    if (roster.findStudent(name, major) != null) {
                        if (roster.findStudent(name, major).getClass().toString().equals(International.class.toString())){
                            boolean bool = Boolean.parseBoolean(command.nextToken());
                            International.class.cast(roster.findStudent(name, major)).studyingAbroad = bool;
                            if (International.class.cast(roster.findStudent(name, major)).getCreditHours() >= 12
                                    && International.class.cast(roster.findStudent(name, major)).studyingAbroad) {
                                roster.findStudent(name, major).setCreditHours(12);
                                roster.findStudent(name, major).setTotalPayment(0.00);
                                moreThan12 = true;
                                if (roster.findStudent(name, major).getDate() == null){
                                    roster.findStudent(name, major).tuitionDue();
                                    System.out.println("Tuition updated.");
                                }
                                else{
                                    roster.findStudent(name, major).getDate().setDateCleared(true);
                                    if (moreThan12){
                                        roster.findStudent(name, major).tuitionDue();
                                        roster.findStudent(name, major).setTotalPayment(0.00);
                                    }
                                    else{
                                        roster.findStudent(name, major).tuitionDue();
                                    }
                                    System.out.println("Tuition updated.");
                                }
                            }
                            else{
                                roster.findStudent(name, major).getDate().setDateCleared(true);
                                roster.findStudent(name, major).tuitionDue();
                                System.out.println("Tuition updated.");
                            }
                        }
                        else {
                            System.out.println("Couldn't find the international student.");
                        }

                    }
                    else {
                        System.out.println("Couldn't find the international student.");
                    }
                }

            }

            else if (input.equals("F")) { // set financial aid
                handleF(name, major, command, roster);
            }

            else if (input.equals("T")) { // pay tuition
                if (!command.hasMoreTokens()) {
                    System.out.println("Payment amount missing.");
                }
                else{
                    double payment = Double.parseDouble(command.nextToken());
                    if (payment <= 0){
                        System.out.println("Invalid amount.");
                    }
                    else{
                        Date date = new Date(command.nextToken());
                        if (!date.isValid()){
                            System.out.println("Payment date invalid.");
                        }
                        else if (payment > roster.findStudent(name, major).getTotalCost()){
                            System.out.println("Amount is greater than amount due.");
                        }
                        else{
                            roster.findStudent(name, major).payTuition(payment, date);
                            System.out.println("Payment applied.");
                        }
                    }

                }
            }
        }
    }

    /**
     * Initializes the tuition for new students in the roster.
     * @param roster roster of students
     */
    private void calculate(Roster roster){
        for (int i = 0; i < roster.getSize(); i++){
            if (roster.getRoster()[i].getDate() == null){
                roster.getRoster()[i].tuitionDue();
            }
        }
        System.out.println("Calculation completed.");
    }

    /**
     * Helper for the set financial aid method
     * @param name student name
     * @param major student major
     * @param command command line inputs
     * @param roster roster of students
     */
    public void handleF(String name, String major, StringTokenizer command, Roster roster) {
        if(command.hasMoreTokens()) {
            Double financialAidInput = Double.parseDouble(command.nextToken());
            Student student = roster.findStudent(name, major);
            if(student == null){                                // student not in roster
                System.out.println("Student not in the roster.");
            }else{                                // in the roster
                if(student.getCreditHours() >= 12) {
                    if (student instanceof Resident) {
                        Resident newRes = (Resident) student;
                        if (newRes.getFinancialAidPaid() == false) { //if it's not paid, pay and set true
                            if (financialAidInput <= 10000 && financialAidInput >= 0) {
                                newRes.setFinancialAid(financialAidInput);
                                newRes.setFinancialAidPaid(true);
                                System.out.println("Tuition updated.");
                            } else {
                                System.out.println("Invalid amount.");
                            }
                        } else {
                            System.out.println("Awarded once already.");
                        }
                    } else {
                        System.out.println("Not a resident student.");
                    }
                }
                else{
                    System.out.println("Parttime student doesn't qualify for the award.");
                }
            }
        }else{
            System.out.println("Missing the amount.");
        }
    }

    /**
     * Checks to see if student is already in the roster.
     * @param student student name
     * @param roster roster
     * @return true if student is already in the roster, otherwise returns false.
     */
    private boolean checkRosterDuplicate(Student student, Roster roster){
        for (int i = 0; i < roster.getSize(); i++){
            if(roster.getRoster()[i].getProfile().getName().equals(student.getProfile().getName())
                    && roster.getRoster()[i].getProfile().getMajor().equals(student.getProfile().getMajor())){
                System.out.println("Student is already in the roster.");
                return true;
            }
        }
        return false;
    }

}