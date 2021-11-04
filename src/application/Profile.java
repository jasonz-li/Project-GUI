package application;

/**
 * Profile is the identifier for a unique student: it has their name and major.
 * equals() compares two instances of Profile.
 * toString() changes the profile to a string format.
 *
 * @author Jason Li, John Leng
 */

public class Profile {
    /**
     * Student name
     */
    private String name;

    /**
     * Student major
     */
    private Major major; //5 majors and 2-characters each: CS, IT, BA, EE, ME

    /**
     * Constructs a profile.
     * @param name student name
     * @param major student's major
     */
    public Profile(String name, String major){
        this.name = name;
        String majorUpperCase = major.toUpperCase();
        this.major = Major.valueOf(majorUpperCase);
    }

    /**
     * Checks if input object's name and major is equal to current profile name and major.
     * @param o input profile
     * @return true if equal to current profile information, otherwise returns false
     */
    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if(o instanceof Profile){
            Profile newProf = (Profile) o;
            if(this.getName().equals(newProf.getName()) && this.getMajor().equals(newProf.getMajor())){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns name and major in string format.
     * @return returns information in 'name, major' format.
     */
    @Override
    public String toString(){
        return this.name + ", " + this.major.toString();
    }


    /**
     * Gets the name of the student in the profile.
     * @return name of student in profile.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets the major of the student in the profile.
     * @return major of student in profile.
     */
    public String getMajor(){
        return this.major.toString();
    }
}