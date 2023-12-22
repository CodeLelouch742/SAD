package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import forms.MainMenu;
import manager.SellEntityManager;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Sells {
    public static List<Sell> sells = new ArrayList<>();

    public static List<Sell> readSells = new ArrayList<>();

    public static List<Sell> sellsMedicinesTimeInterval = new ArrayList<>();

    public static String [] nameColomnSell = {"Имя лекарства", "Количество проданных", "Дата продажи"};

    public static void getAllSellsMedicinesTimeInterval(String dayStartString, String monthStartString, String yearStartString,
                                                            String dayEndString, String monthEndString, String yearEndString, String nameMedicineTime) {
        int dayStartInt = Integer.parseInt(dayStartString);
        int monthStartInt = Integer.parseInt(monthStartString);
        int yearStartInt = Integer.parseInt(yearStartString);
        LocalDate startLocaleDate = LocalDate.of(yearStartInt, monthStartInt, dayStartInt);

        int dayEndInt = Integer.parseInt(dayEndString);
        int monthEndInt = Integer.parseInt(monthEndString);
        int yearEndInt = Integer.parseInt(yearEndString);
        LocalDate endLocaleDate = LocalDate.of(yearEndInt, monthEndInt, dayEndInt);

        for (Sell s : sells) {
            LocalDate nowLocaleDate = LocalDate.of(Integer.parseInt(s.getYearSell()), Integer.parseInt(s.getMonthSell()), Integer.parseInt(s.getDaySell()));
            if((nowLocaleDate.isAfter(startLocaleDate) && nowLocaleDate.isBefore(endLocaleDate) && nameMedicineTime.equals(s.getNameMedicine()))
                    || (nowLocaleDate.isEqual(startLocaleDate) && nameMedicineTime.equals(s.getNameMedicine())
                    || (nowLocaleDate.isEqual(endLocaleDate)&& nameMedicineTime.equals(s.getNameMedicine())))){
                sellsMedicinesTimeInterval.add(s);
            }
        }
    }

    public static int getMonthlySalesAmount(){
        LocalDate today = getNowDate();
        int sum = 0;
        LocalDate todayMinusMonth = LocalDate.of(today.getYear(), today.getMonthValue() - 1, today.getDayOfMonth());
        for(Sell s : sells){
            LocalDate nowLocaleDate = LocalDate.of(Integer.parseInt(s.getYearSell()), Integer.parseInt(s.getMonthSell()), Integer.parseInt(s.getDaySell()));
            if((nowLocaleDate.isAfter(todayMinusMonth) && nowLocaleDate.isBefore(today)) || (nowLocaleDate.isEqual(todayMinusMonth)) || (nowLocaleDate.isEqual(today))){
                for(Medicine m : Medicines.medicines){
                    if(s.getNameMedicine().equals(m.getNameMedicine())){
                        sum = sum + Integer.parseInt(s.getQuantitySell()) * Integer.parseInt(m.getPrice());
                    }
                }
            }
        }
        return sum;
    }
    public static LocalDate getNowDate(){
        ZoneId z = ZoneId.of( "Europe/Moscow" );
        LocalDate today = LocalDate.now(z);
        return today;
    }

    public static boolean isDateValid(String day, String month, String year)
    {
        try {
            String date = day + "-" + month + "-" + year;
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static void writeXmlSells() {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            String sellXml = xmlMapper.writeValueAsString(new SellContainer(sells));
            String fileName = "sells.xml";
            Files.writeString(
                    Paths.get(fileName),
                    sellXml,
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

    public static void writeInFileSells(){
        try {
            FileWriter writer = new FileWriter("sells.txt");
            for(Sell sell : sells) {
                String nameMedicine = sell.getNameMedicine();
                String price = sell.getQuantitySell();
                String day = sell.getDaySell();
                String month = sell.getMonthSell();
                String year = sell.getYearSell();
                writer.write(nameMedicine + ";" + price + ";" + day + ";" + month + ";" + year + System.getProperty("line.separator"));
            }
            writer.close();
            JOptionPane.showMessageDialog(MainMenu.instance,
                    "<html><h2 align=\"center\">Данные успешно записаны.</h2>");
        }catch (IOException e){
            JOptionPane.showMessageDialog(MainMenu.instance,
                    "<html><h2 align=\"center\">Не верное имя файла.</h2><p align=\"center\"> Введите имя файла снова.</p>");
        }
    }

    public static int readOutFileSells(String fileName){
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                String[] lineData  = line.split(Pattern.quote(";"));
                Sell sell = new Sell();
                sell.setNameMedicine(lineData[0]);
                if(Medicines.checkInt(lineData[1])){
                    sell.setQuantitySell(lineData[1]);
                }else{
                    return 1;
                }
                if(isDateValid(lineData[2], lineData[3], lineData[4])){
                    if(Medicines.checkInt(lineData[2])){
                        sell.setDaySell(lineData[2]);
                    }else{
                        return 1;
                    }
                    if(Medicines.checkInt(lineData[3])){
                        sell.setMonthSell(lineData[3]);
                    }else{
                        return 1;
                    }
                    if(Medicines.checkInt(lineData[4])){
                        sell.setYearSell(lineData[4]);
                    }else{
                        return 1;
                    }
                }
                Sells.readSells.add(sell);
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

    public static void readXmlSells(String fileName) {
        System.out.println(fileName);
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document dom = builder.parse(new File(fileName));
            dom.normalizeDocument();
            NodeList nodeList = dom.getElementsByTagName("sell");
            for(int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                Element element = (Element) node;
                if(isDateValid(element.getElementsByTagName("daySell").item(0).getTextContent(),
                        element.getElementsByTagName("monthSell").item(0).getTextContent(),
                        element.getElementsByTagName("yearSell").item(0).getTextContent())
                        && Medicines.checkInt(element.getElementsByTagName("quantitySell").item(0).getTextContent())){
                    Sell sell = new Sell(element.getElementsByTagName("nameMedicine").item(0).getTextContent(),
                            element.getElementsByTagName("quantitySell").item(0).getTextContent(),
                            element.getElementsByTagName("daySell").item(0).getTextContent(),
                            element.getElementsByTagName("monthSell").item(0).getTextContent(),
                            element.getElementsByTagName("yearSell").item(0).getTextContent());
                    Sells.sells.add(sell);
                    SellEntityManager.insert(sell);
                }
            }
            JOptionPane.showMessageDialog(MainMenu.instance,
                    "<html><h2 align=\"center\">Данные успешно прочитаны.</h2>");
        } catch (IOException e){
            JOptionPane.showMessageDialog(MainMenu.instance,
                    "<html><h2 align=\"center\">Не верное имя файла.</h2><p align=\"center\"> Введите имя файла снова.</p>");
        }catch (ArrayIndexOutOfBoundsException array){
            JOptionPane.showMessageDialog(MainMenu.instance,
                    "<html><h2 align=\"center\">Не верный формат файла.</h2><p align=\"center\"> Введите имя файла снова.</p>");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}









