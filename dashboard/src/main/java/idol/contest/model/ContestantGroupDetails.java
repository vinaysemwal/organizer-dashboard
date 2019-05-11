package idol.contest.model;

/**
 * Model to be streamed to the clients.
 */
public class ContestantGroupDetails {

    private String group;

    private Integer totalCount;

    private Integer maleCount;

    /**
     * @param group contestant group. Can be {@value 'buddies'}, {@value 'young'} or {@value 'senior'}
     * @param totalCount total number of contestants registered in the group
     * @param maleCount number of males registered in the group
     */
    public ContestantGroupDetails(final String group, final Integer totalCount, final Integer maleCount) {
        super();
        this.group = group;
        this.totalCount = totalCount;
        this.maleCount = maleCount;
    }

    /**
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(final String group) {
        this.group = group;
    }

    /**
     * @return the totalCount
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(final Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return the maleCount
     */
    public Integer getMaleCount() {
        return maleCount;
    }

    /**
     * @param maleCount the maleCount to set
     */
    public void setMaleCount(final Integer maleCount) {
        this.maleCount = maleCount;
    }

}
