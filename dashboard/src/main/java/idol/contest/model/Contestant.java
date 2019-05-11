package idol.contest.model;

/**
 * Model for the topic value sourced from the database table containing the contestant profiles created during registrations.
 */
public class Contestant {

    /**
     * contestant group for age 3 to age 16(included)
     */
    public static final String SENIOR_GROUP = "senior";

    /**
     * contestant group for age 16 to age 25(included)
     */
    public static final String JUNIOR_GROUP = "junior";

    /**
     * contestant group for age 25 to age 45(included)
     */
    public static final String BUDDIES_GROUP = "buddies";

    String name;

    short age;

    String gender;

    /**
     *
     */
    public Contestant() {}

    /**
     * @param name contestan name
     * @param age age of the contestant
     * @param gender gender of the contestant
     */
    public Contestant(final String name, final short age, final String gender) {
        super();
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    public short getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(final short age) {
        this.age = age;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(final String gender) {
        this.gender = gender;
    }

    /**
     * Identify the group of the contestant.
     *
     * @return {@code Contestant.BUDDIES_GROUP}, {@code Contestant.JUNIOR_GROUP} or {@code Contestant.SENIOR_GROUP}
     */
    public String groupByAge() {
        if (age >= 3 && age <= 16) {
            return BUDDIES_GROUP;
        }
        if (age > 16 && age <= 25) {
            return JUNIOR_GROUP;
        }
        return SENIOR_GROUP;
    }

}
