package util;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;
import java.util.Objects;

@JacksonXmlRootElement(localName = "medicines")
public class MedicineContainer {
    @JacksonXmlProperty(localName = "medicine")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Medicine> medicinesContainer;

    public MedicineContainer(List<Medicine> medicinesContainer) {
        this.medicinesContainer = medicinesContainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicineContainer that = (MedicineContainer) o;

        return Objects.equals(medicinesContainer, that.medicinesContainer);
    }

    @Override
    public int hashCode() {
        return medicinesContainer != null ? medicinesContainer.hashCode() : 0;
    }

    public List<Medicine> getMedicinesContainer() {
        return medicinesContainer;
    }

    public void setMedicinesContainer(List<Medicine> medicinesContainer) {
        this.medicinesContainer = medicinesContainer;
    }

    @Override
    public String toString() {
        return "MedicineContainer{" +
                "medicinesContainer=" + medicinesContainer +
                '}';
    }
}
