package newpack;/*

                                                                                ----Whatsapp Message Analytics Project----
                                                                                              by Arun Avasi

Overview:
A Statistical data analysis tool for WhatsApp message data. Employed Apache POI API to extract, process and export the data to an Excel sheet.
The project then analyzed the data to generate various statistics and metrics such as frequency of message exchanges, average message length,
and peak messaging hours, which were displayed on the front-end using Spring Boot and Thymeleaf. The completed project is packaged (Maven) into
 an AWS S3 bucket and deployed on the cloud (AWS Elastic Beanstalk) with appropriate IAM roles, load balancers, security groups, and EC2 instances.
 */

import com.amazonaws.regions.Regions;
import newpack.controller.ExController;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;



import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
public class Main {
    public static String mostActiveDate;
    public static String morefreqtexter;

    public static String maxMessage;
    public static String avgwords;



    public static void main(String[] args) throws IOException {
        AWSCredentials credentials = new BasicAWSCredentials("AKIA3BSILOEAO74OGPWJ", "3vwxWsoQu3qlLpiXf+IW607KiUHyGFWICjq1NQhP");
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
        S3Object s3object = s3Client.getObject(new GetObjectRequest("whatsapptxt", "WhatsApp Chat with Dad.txt"));



        BufferedReader br = new BufferedReader(new InputStreamReader(s3object.getObjectContent()));
        String st;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(" Message_Data");
        FileOutputStream file = new FileOutputStream("C:\\Users\\arund\\OneDrive\\Desktop\\messageDatas.xlsx");
        int rownum = 0;
        int maxLength = 0;

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
            System.out.println(person);
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
        mostActiveDate = mostCommonDate(dates);
        workbook.write(file);
        file.close();
        avgwords = String.valueOf(avgWordsPerMessage);


        SpringApplication.run(Main.class, args);







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
        return str.substring(1, indexofDate);
    }

    //Brings the time data from the data sheet
    public static String timeData(String str) {
        int first = str.indexOf(", ") + 2;
        int second = str.indexOf("] ") - 4;
        return str.substring(first, second);
    }

    //Brings the user information from data sheet
    public static String user(String str) {

        str = str.substring(str.indexOf("] ")+1);

        str=str.substring(0,str.indexOf(":"));

        return str;


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