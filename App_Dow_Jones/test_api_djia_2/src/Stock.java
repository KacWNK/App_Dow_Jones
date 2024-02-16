public class Stock {

    public enum Type{
        Technology, Financial, ConsumerGoods, HealthCare, Industrial,
    }
    private final String fullName;
    private final String abbreviation;
    private boolean displayed;
    private final Type enterpriseType;

    public Stock(String fullName, String abbreviation, boolean displayed, Type enterpriseType) {
        this.fullName = fullName;
        this.abbreviation = abbreviation;
        this.displayed = displayed;
        this.enterpriseType = enterpriseType;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public Type getEnterpriseType() {
        return enterpriseType;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }
}
