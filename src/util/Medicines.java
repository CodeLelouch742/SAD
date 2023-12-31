package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import forms.MainMenu;
import manager.MedicineEntityManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Medicines {

    public static List<Medicine> medicines = new ArrayList<>();

    public static List<Medicine> readMedicines = new ArrayList<>();

    public static List<Medicine> listMedicinesByDisease = new ArrayList<>();
    public static List<String> uniqueMedicines = new ArrayList<>();

    public static String[] nameColomnMedicine = {"Имя лекарства", "Болезнь", "Цена", "Колличетсво штук на складе"};

    public static Medicine getMedicineByName(String nameMedicine) {
        for (Medicine m : medicines) {
            if (m.getNameMedicine().equalsIgnoreCase(nameMedicine)) {
                return m;
            }
        }
        return null;
    }

    public static Medicine getAllMedicineByDisease(String nameDisease) {
        for (Medicine m : medicines) {
            if (m.getMedicineByDisease(nameDisease, m) != null) {
                Medicines.listMedicinesByDisease.add(m);
            }
        }
        return null;
    }

    public static void setMedicineByName(Medicine medicine, String nameMedicine) {
        for (int i = 0; i < medicines.size(); i++) {
            Medicine m = medicines.get(i);
            if (m.getNameMedicine().equalsIgnoreCase(nameMedicine)) {
                medicines.set(i, medicine);
                try {
                    MedicineEntityManager.update(medicine, nameMedicine);
                } catch (SQLException e) {

                }
                return;
            }
        }
    }

    public static boolean removeByNameMedicine(String nameMedicine) {
        for (Medicine u : medicines) {
            if (u.getNameMedicine().equalsIgnoreCase(nameMedicine)) {
                for (String removeUniqueMedicine : uniqueMedicines) {
                    if (removeUniqueMedicine.equalsIgnoreCase(nameMedicine)) {
                        uniqueMedicines.remove(removeUniqueMedicine);
                        break;
                    }
                }
                try {
                    MedicineEntityManager.delete(u);
                } catch (SQLException e) {

                }
                medicines.remove(u);
                return true;
            }
        }
        return false;
    }

    public static void removeArrayMedicines() {
        for (int i = medicines.size() - 1; i >= 0; i--) {
            medicines.remove(i);
        }
        uniqueMedicines.clear();
    }

    public static void removeArrayMedicinesByDisease() {
        for (int i = listMedicinesByDisease.size() - 1; i >= 0; i--) {
            listMedicinesByDisease.remove(i);
        }
    }

    public static boolean checkNameMedicineList(String nameMedicine) {
        for (String checkNameMedicine : uniqueMedicines) {
            if (nameMedicine.equalsIgnoreCase(checkNameMedicine)) {
                return true;
            }
        }
        return false;
    }


    public static void writeXmlMedicines() {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            String medicineXml = xmlMapper.writeValueAsString(new MedicineContainer(medicines));
            String fileName = "medicines.xml";
            Files.writeString(
                    Paths.get(fileName),
                    medicineXml,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
            JOptionPane.showMessageDialog(MainMenu.instance,
                    "<html><h2 align=\"center\">Данные успешно записаны.</h2>");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e1)
        {

        }
    }

    public static void readXmlMedicines(String fileName) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document dom = builder.parse(new File(fileName));
            dom.normalizeDocument();
            NodeList nodelist = dom.getElementsByTagName("medicine");
            for(int i = 0; i< nodelist.getLength(); i++){
                Node node = nodelist.item(i);
                Element element = (Element) node;
                if(!(checkNameMedicineList(element.getElementsByTagName("nameMedicine").item(0).getTextContent()))){
                    if(checkInt(element.getElementsByTagName("price").item(0).getTextContent())
                            && checkInt(element.getElementsByTagName("quantity").item(0).getTextContent())){
                        Medicine medicine = new Medicine(element.getElementsByTagName("nameMedicine").item(0).getTextContent(),
                                element.getElementsByTagName("nameDisease").item(0).getTextContent(),
                                element.getElementsByTagName("price").item(0).getTextContent(),
                                element.getElementsByTagName("quantity").item(0).getTextContent());
                        Medicines.medicines.add(medicine);
                        MedicineEntityManager.insert(medicine);
                        Medicines.uniqueMedicines.add(element.getElementsByTagName("nameMedicine").item(0).getTextContent());
                    }else{
                        JOptionPane.showMessageDialog(MainMenu.instance,
                                "<html><h2 align=\"center\">Встречена ошибка данных файла</h2><p align=\"center\"> Проверьте правильность данных в файле</p>");
                    }
                }else{
                    JOptionPane.showMessageDialog(MainMenu.instance,
                            "<html><h2 align=\"center\">Лекарство" + element.getElementsByTagName("nameMedicine").item(0).getTextContent()
                                    + "уже есть в базе.</h2><p align=\"center\"> Пожалуйста внесите информацию о нём вручную через систему.</p>");
                }

            }
            JOptionPane.showMessageDialog(MainMenu.instance,
                    "<html><h2 align=\"center\">Данные успешно прочитаны.</h2>");
        }catch (IOException e){
            JOptionPane.showMessageDialog(MainMenu.instance,
                    "<html><h2 align=\"center\">Не верное имя файла.</h2><p align=\"center\"> Введите имя файла снова.</p>");
        }catch (ArrayIndexOutOfBoundsException array){
            JOptionPane.showMessageDialog(MainMenu.instance,
                    "<html><h2 align=\"center\">Не верный формат файла.</h2><p align=\"center\"> Введите имя файла снова.</p>");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void writeInFileMedicines() {
        try {
            FileWriter writer = new FileWriter("medicines.txt");
            for (Medicine medicine : medicines) {
                String nameMedicine = medicine.getNameMedicine();
                String nameDisease = medicine.getNameDisease();
                String price = medicine.getPrice();
                String quantity = medicine.getQuantity();
                writer.write(nameMedicine + ";" + nameDisease + ";" + price + ";" + quantity + System.getProperty("line.separator"));
            }
            writer.close();
            JOptionPane.showMessageDialog(MainMenu.instance,
                    "<html><h2 align=\"center\">Данные успешно записаны.</h2>");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(MainMenu.instance,
                    "<html><h2 align=\"center\">Не верное имя файла.</h2><p align=\"center\"> Введите имя файла снова.</p>");
        }
    }

    public static int readOutFileMedicines(String fileName) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                String[] lineData = line.split(Pattern.quote(";"));
                if(!(checkNameMedicineList(lineData[0]))){
                    Medicine medicine = new Medicine();
                    medicine.setNameMedicine(lineData[0]);
                    medicine.setNameDisease(lineData[1]);
                    if (checkInt(lineData[2])) {
                        medicine.setPrice(lineData[2]);
                    } else {
                        return 1;
                    }
                    if (checkInt(lineData[3])) {
                        medicine.setQuantity(lineData[3]);
                    } else {
                        return 1;
                    }
                    medicine.setPrice(lineData[2]);
                    medicine.setQuantity(lineData[3]);
                    Medicines.readMedicines.add(medicine);
                }else{
                    JOptionPane.showMessageDialog(MainMenu.instance,
                            "<html><h2 align=\"center\">Лекарство" + lineData[0] + "уже есть в базе.</h2><p align=\"center\"> Пожалуйста внесите информацию о нём вручную через систему.</p>");
                }
            }
            return 0;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(MainMenu.instance,
                    "<html><h2 align=\"center\">Не верный путь до файла.</h2><p align=\"center\"> Введите путь до файла снова.</p>");
            throw new RuntimeException(e);
        }catch (ArrayIndexOutOfBoundsException array){
            JOptionPane.showMessageDialog(MainMenu.instance,
                    "<html><h2 align=\"center\">Не верный формат файла.</h2><p align=\"center\"> Введите имя файла снова.</p>");
            throw new RuntimeException(array);
        }
    }

    public static boolean checkInt(String string) {
        try {
            int checkInt = Integer.parseInt(string);
            return checkInt >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}









