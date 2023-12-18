package util;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;
import java.util.Objects;

@JacksonXmlRootElement(localName = "sells")
public class SellContainer {
    @JacksonXmlProperty(localName = "sell")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Sell> sellsContainer;

    public SellContainer(List<Sell> sellsContainer) {
        this.sellsContainer = sellsContainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SellContainer that = (SellContainer) o;

        return Objects.equals(sellsContainer, that.sellsContainer);
    }

    @Override
    public int hashCode() {
        return sellsContainer != null ? sellsContainer.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SellContainer{" +
                "sellsContainer=" + sellsContainer +
                '}';
    }

    public List<Sell> getSellsContainer() {
        return sellsContainer;
    }

    public void setSellsContainer(List<Sell> sellsContainer) {
        this.sellsContainer = sellsContainer;
    }
}
