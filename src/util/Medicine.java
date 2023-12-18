package util;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "medicines")
public class Medicine {
    @JacksonXmlProperty(localName = "nameMedicine")
    private String nameMedicine;
    @JacksonXmlProperty(localName = "nameDisease")
    private String nameDisease;
    @JacksonXmlProperty(localName = "price")
    private String price;
    @JacksonXmlProperty(localName = "quantity")
    private String quantity;

    public Medicine() { }

    public Medicine(String nameMedicine, String nameDisease, String price, String quantity) {
        this.nameMedicine = nameMedicine;
        this.nameDisease = nameDisease;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "\n" +
                "\tНазвание лекарства - '" + nameMedicine + '\'' +
                ", Болезнь от которой лечит - '" + nameDisease + '\'' +
                ", Цена лекарства - '" + price + " руб.\'" +
                ", Количество штук на складе - '" + quantity + " шт.\'";
    }

    public static Medicine getMedicineByDisease(String nameDisease, Medicine m) {
        if (m.getNameDisease().equalsIgnoreCase(nameDisease)) {
            return m;
        }
        return null;
    }

    public String getNameMedicine() {
        return nameMedicine;
    }

    public void setNameMedicine(String nameMedicine) {
        this.nameMedicine = nameMedicine;
    }

    public String getNameDisease() {
        return nameDisease;
    }

    public void setNameDisease(String nameDisease) {
        this.nameDisease = nameDisease;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
