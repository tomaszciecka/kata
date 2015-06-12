package kata.dto;

public class LimitDto {

    private Integer grantLimit;

    private Integer signLimit;

    public LimitDto(Integer grantLimit, Integer signLimit) {
        super();
        this.grantLimit = grantLimit;
        this.signLimit = signLimit;
    }

    public Integer getGrantLimit() {
        return grantLimit;
    }

    public Integer getSignLimit() {
        return signLimit;
    }

}
