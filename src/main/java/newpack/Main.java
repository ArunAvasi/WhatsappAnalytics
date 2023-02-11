package newpack;/*

                                                                                ----Whatsapp Message Analytics Project----
                                                                                              by Arun Avasi

Overview:
This project analyzes Whatsapp message data, exports it to an Excel sheet, and brings about numerous statistics
External APIs integrated -> Apache POI API, REST API

1. Displays the message with the largest  number of text (done) - Maxmessage
2. Displays the date in which the most messages have been sent and received (done) - mostActiveDate
3. Displays the individual who messaged more frequently - morefreqtexter
4. Displays average word count per message (done) avgWordsPerMessage

 */

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
public class Main {


    public static void main(String[] args) throws IOException {
        SpringApplication.run(Main.class, args);

        File text = new File("C:\\Users\\arund\\OneDrive\\Documents\\WhatsApp Chat with Dad.txt");
        BufferedReader br = new BufferedReader(new FileReader(text));
        String st;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(" Message_Data");
        FileOutputStream file = new FileOutputStream("C:\\Users\\arund\\OneDrive\\Desktop\\messageDatas.xlsx");
        int rownum = 0;
        int maxLength = 0;
        String maxMessage = "";
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<Integer> WordsPerMessage = new ArrayList<>();
        HashMap<String, Integer> messPerPerson = new HashMap<>();

        String personOne  = null;
        int personOnefreq = 0;
        String personTwo = null;
        int persontwofreq=0;

        while ((st = br.readLine()) != null) {
            String date = dateData(st);
            String time = timeData(st);
            String person = user(st);
            String userMessages = message(st);
            XSSFRow row = spreadsheet.createRow(rownum);
            row.createCell(0).setCellValue(date);
            row.createCell(1).setCellValue(time);
            row.createCell(2).setCellValue(person);
            row.createCell(3).setCellValue(userMessages);
            rownum++;
            if (maxLength < userMessages.length()) {
                maxLength = userMessages.length();
                maxMessage = userMessages;
            }
            dates.add(date);
            String[] words = userMessages.split(" ");
            WordsPerMessage.add(words.length);

            Integer num = messPerPerson.get(person);
            if (num == null) {
                messPerPerson.put(person, 0);
            } else {
                messPerPerson.put(person, num + 1);
            }

            if(personOne==null)
            {
                personOne=person;
            }
            if(personTwo==null&& !(person.equals(personOne)))
            {
                personTwo=person;
            }

            if(person.equals(personOne))
            {
                personOnefreq++;
            }
            else if(person.equals(personTwo))
            {
                persontwofreq++;
            }




        }

        String morefreqtexter;
        if(personOnefreq>persontwofreq)
        {
            morefreqtexter = personOne;
        }
        else
        {
            morefreqtexter = personTwo;
        }



        int sum = WordsPerMessage.stream().reduce(0, Integer::sum);
        double avgWordsPerMessage = (double) sum / rownum;
        String mostActiveDate = mostCommonDate(dates);
        workbook.write(file);
        file.close();

    }

    //brings the message data from data sheet
    private static String message(String str) {
        int timere = str.indexOf(":");
        str = str.substring(timere + 1);
        int first = str.indexOf(":");
        return str.substring(first + 2);
    }

    //Brings the date data from data sheet
    public static String dateData(String str) {
        int indexofDate = str.indexOf(",");
        return str.substring(0, indexofDate);
    }

    //Brings the time data from the data sheet
    public static String timeData(String str) {
        int first = str.indexOf(", ") + 2;
        int second = str.indexOf("- ") - 1;
        return str.substring(first, second);
    }

    //Brings the user information from data sheet
    public static String user(String str) {
        int timere = str.indexOf(":");
        str = str.substring(timere + 1);
        int first = str.indexOf("- ") + 2;
        int second = str.indexOf(":");
        return str.substring(first, second);
    }

    public static String mostCommonDate(ArrayList<String> dates) {
        HashMap<String, Integer> dateOccurance = new HashMap<>();
        for (String x : dates) {
            Integer num = dateOccurance.get(x);
            if (num == null) {
                dateOccurance.put(x, 0);
            } else {
                dateOccurance.put(x, num + 1);
            }
        }
        return dateOccurance.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();

    }



}