package idol.contest.model;

/**
 * Modeling the value of final state store {@literal registration-details-by-age} containing the age group total count and male count.
 */
public class AgeGroupDetails {

    int totalCount;

    int maleCount;

    /**
     * @param totalcount total number of users registered for an age group
     * @param maleCount number of males registered in the age group.
     */
    public AgeGroupDetails(final int totalcount, final int maleCount) {
        super();
        totalCount = totalcount;
        this.maleCount = maleCount;
    }

    /**
     * @return the totalCount
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(final int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return the maleCount
     */
    public int getMaleCount() {
        return maleCount;
    }

    /**
     * @param maleCount the maleCount to set
     */
    public void setMaleCount(final int maleCount) {
        this.maleCount = maleCount;
    }

}
