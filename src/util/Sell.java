package util;

public class Sell {
    private String nameMedicine;
    private String quantitySell;
    private String daySell;
    private String monthSell;
    private String yearSell;

    public Sell() { }

    public Sell(String nameMedicine, String quantitySell, String daySell, String monthSell, String yearSell) {
        this.nameMedicine = nameMedicine;
        this.quantitySell = quantitySell;
        this.daySell = daySell;
        this.monthSell = monthSell;
        this.yearSell = yearSell;
    }

    @Override
    public String toString() {
        return "\n" +
                "\tНазвание лекарства - '" + nameMedicine + '\'' +
                ", Количество проданных - '" + quantitySell + '\'' +
                ", Дата продажи - '" + daySell + "-" + monthSell + "-" + yearSell + "\'";
    }

    public String getNameMedicine() {
        return nameMedicine;
    }

    public void setNameMedicine(String nameMedicine) {
        this.nameMedicine = nameMedicine;
    }

    public String getQuantitySell() {
        return quantitySell;
    }

    public void setQuantitySell(String quantitySell) {
        this.quantitySell = quantitySell;
    }

    public String getDaySell() {
        return daySell;
    }

    public void setDaySell(String daySell) {
        this.daySell = daySell;
    }

    public String getMonthSell() {
        return monthSell;
    }

    public void setMonthSell(String monthSell) {
        this.monthSell = monthSell;
    }

    public String getYearSell() {
        return yearSell;
    }

    public void setYearSell(String yearSell) {
        this.yearSell = yearSell;
    }

}