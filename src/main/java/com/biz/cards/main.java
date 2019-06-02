package com.biz.cards;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {

    public static void main(String[] args) {
        ocrBizCardGenerater("bizCard0.jpg");
        ocrBizCardGenerater("bizCard1.jpg");
        ocrBizCardGenerater("bizCard2.jpg");
        ocrBizCardGenerater("bizCard3.jpg");

        for (String card : args)
            ocrBizCardGenerater(card);
    }

    /**
     * Parses the sting form of the biz card to break it into Phone num, email, and name by using regex and returns it as a ContactInfo
     *
     * @param Document String form of the biz card
     * @return A ContactInfo that holds all the details of the card
     */
    public static ContactInfo businessCardParser(String Document) {
        final String EMAILREGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        final String PHONEREGEX = "(?:(?:\\+?([1-9]|[0-9][0-9]|[0-9][0-9][0-9])\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([0-9][1-9]|[0-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?";
//      Using regex to find emails and phone numbers===========
        Matcher emailMatcher = Pattern.compile(EMAILREGEX).matcher(Document);
        Matcher phoneMatcher = Pattern.compile(PHONEREGEX).matcher(Document);
        String name = "";
        String email = "";
        String phone = "";
        if (emailMatcher.find()) email = emailMatcher.group(0);
        if (phoneMatcher.find()) phone = phoneMatcher.group(0);
//          Looks through every line to check for a line that contains the last three letters of the email before the @ except for
//          the last one so for CalebMorton@somewhatAdequateDeveloper.com it would grab "rto" and search for that to find the name.
        for (String line : Document.split("\n"))
//          This just checks if line is the name we are looking for
//          the second check validates that it is a valid email and it is not just a blank or really short thing found by ocr. The third check looks at the email and tries to find it based on the start of an email.
            if (!line.contains("@") && email.split("@")[0].length() > 4 && line.toLowerCase().contains(email.split("@")[0].toLowerCase().substring(email.split("@")[0].length() - 4, email.split("@")[0].length() - 1)) && !line.equals(email))
                name = line;
//          Fallback to check for three letter last names when the last name is too
//          short and the top one fails.It will be blank if it did not find the name
        if (name.equals(""))
            for (String line : Document.split("\n"))
                if (!line.contains("@") && email.split("@")[0].length() > 4 && line.toLowerCase().contains(email.split("@")[0].toLowerCase().substring(email.split("@")[0].length() - 4, email.split("@")[0].length() - 1)) && !line.equals(email))
                    name = line;
//      Idiot proofing measure if somehow the email is not found by regex
        if (email.equals(""))
            for (String line : Document.split("\n"))
                if (line.contains("@"))
                    email = line;
//            System.out.println(phoneMatcher);
        System.out.println();

        if (!phone.equals("") && !email.equals("") && !name.equals("")) {
            System.out.println("It worked");
            return new ContactInfo(email, phone, name);
        }
        else if (phone.equals("") && email.equals("") && name.equals("")) {
            System.out.println("Found Nothing\n");
            return null;
        }else{
            if (!phone.equals("")) System.out.println("Found phone: " + phone);
            if (!email.equals("")) System.out.println("Found email: " + email);
            if (!name.equals("")) System.out.println("Found name: " + name);
        }

//        }
        return null;

    }


    /**
     * This grabs as much as it can from the image. Tesseract, the ocr framework that I like and have experience with, does not work really well with side by side elements
     * So you will see that a name on the left of other text on a business card will often not be found or other details like that will be left out.
     * I programmed it such that it will tell you what it did figure out if it finds something, but it does not always find something.
     *
     * @param fileName Name of the picture of the biz card that will be passed to the func
     * @return ContactInfo object created by businessCardParser. All this does is get the text than it uses the other func to parse it
     */
    public static ContactInfo ocrBizCardGenerater(String fileName) {
        try {
//          Tesseract adds .txt to the end of the filename
            Runtime.getRuntime().exec("tesseract " + fileName + " values").waitFor();
            try {
                try (Scanner scnr = new Scanner(new File("values.txt"))) {
                    StringBuilder sb = new StringBuilder();
                    while (scnr.hasNextLine())
                        sb.append(scnr.nextLine() + "\n");
//                  Deletes file so that if there is some kind of error the next time it does not just read the old file again
//                    new File("values.txt").delete();
                    if(sb.toString().equals("")) throw new FileNotFoundException("That picture does not exist");
                    return businessCardParser(sb.toString());
                }
            } catch (IOException e) {
                System.out.println("That image does not exist\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("That file does not exist");
            return null;
        } catch (IOException e) {
            System.out.println("Tesseract error. :(");
            return null;
        } catch (InterruptedException e) {
            System.out.println("Tesseract error :(");
            return null;
        }
        return null;
    }
}
