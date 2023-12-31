/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel.prototype;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDate;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * FileController reads and writes data from files for data storage purposes and
 * for use during run-time
 *
 * @author Jonathan
 */
public class FileController {

    //private static String roomsFile = "rooms.txt";
    /**
     * Reads from a text file and generates an ArrayList of strings of the whole
     * unbroken line from the text file
     *
     * @param fileName name of the text file to be read from
     * @return An ArrayList of room objects
     */
    public static ArrayList<String> readFile(String fileName) {
        String fileData;
        String[] dataArr = null;
        ArrayList<String> list = new ArrayList<String>();

        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);

            while ((fileData = br.readLine()) != null) {
                list.add(fileData);
            }

            br.close();
            fr.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return list;
    }

    /**
     * Reads from a file and generates an ArrayList of Customer objects from the
     * file
     *
     * @param fileName name of the text file to be read from
     * @return an ArrayList of Customer objects
     */
    public static ArrayList readCustomerFile(String fileName) {
        String customerName, customerEmail;
        String fileData;
        String[] dataArr = null;
        ArrayList<Customer> customerList = new ArrayList<Customer>();
        ArrayList<String> reservationIDs = new ArrayList<String>();

        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);

            while ((fileData = br.readLine()) != null) {
                dataArr = fileData.split(",");
                customerName = dataArr[0];
                customerEmail = dataArr[1];

                for (int i = 2; i < dataArr.length; i++) {
                    reservationIDs.add((dataArr[i]));
                }

                //customerList.add(new Customer(customerName, customerEmail, reservationIDs));
            }

            br.close();
            fr.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return customerList;
    }

    /**
     * Reads from a file and generates an ArrayList of Reservation objects from
     * the file
     *
     * @param fileName name of the text file to be read from
     * @return an ArrayList of Reservation objects
     */
    public static ArrayList readReservationFile(String fileName) {
        String fileData, reservationID;
        LocalDate checkIn, checkOut;
        String[] dataArr = null;
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();

        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);

            while ((fileData = br.readLine()) != null) {
                dataArr = fileData.split(",");

                reservationID = dataArr[0];
                checkIn = dateParser(dataArr[1]);
                checkOut = dateParser(dataArr[2]);

                //reservationList.add(new Reservation(reservationID,
                //checkIn,
                //checkOut,
                //new Room()/*integerParser(dataArr[4]))*/,
                //new Customer()
                //));
            }

            br.close();
            fr.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return reservationList;
    }

    /**
     * Method for deleting a line from a text file
     *
     * @param fileName name of the text file to be altered
     * @param idOrNum the reservation id or room number you are looking to have
     * removed from the file
     */
    public static void removeLine(String fileName, int idOrNum) {
        try {
            File temp = new File("temp.txt");
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
            String[] dataArr;
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                dataArr = currentLine.split(",");
                if (integerParser(dataArr[0]) == idOrNum) {
                    currentLine = "";
                }
                bw.write(currentLine + System.getProperty("line.seperator"));
            }
            bw.close();
            br.close();

            File oldFile = new File(fileName);
            temp.renameTo(oldFile);
            oldFile.delete();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Line replacement method for modifying old text file data
     *
     * @param filename name of the text file to be altered
     * @param newLine new data to replace old in text file
     * @param oldLine old data to be replaced in text file
     */
    public static void replaceLine(String filename, String newLine, String oldLine) {
        try {
            File file = new File(filename);
            FileWriter fr = new FileWriter(file);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String oldData = "";
            String newData;
            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                oldData += currentLine + System.lineSeparator();
            }
            System.out.println(oldData);
            newData = oldData.replaceAll(oldLine, newLine);
            fr.write(newData);
            br.close();
            fr.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Method for appending string data to lines in a text file
     *
     * @param fileName The name of the file to have text added to
     * @param data The string data of the text to be added to the file
     * @throws IOException
     */
    public static void appendFile(String fileName, String data) throws IOException {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.append(data);
            fw.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Method for appending data to a specific line in a text file
     * @param fileName Name of the text file
     * @param appendString Data to be added
     * @param targetLine Line to add to
     */
    public static void findAndAdd(String fileName, String appendString, String targetLine) {
        try {
            File file = new File(fileName);
            File tempFile = new File("tempFile.txt");

            BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean found = false;

            while ((currentLine = br.readLine()) != null) {
                System.out.println("Current Line: " + currentLine);
                currentLine = currentLine.trim();
                if (currentLine.equals(targetLine.trim())) {
                    currentLine += appendString;
                    found = true;
                }
                bw.write(currentLine);
                if (br.ready()) {
                    bw.newLine();
                }
            }

            br.close();
            bw.close();

            if (!found) {
                System.out.println("Target line not found in the file.");
                System.out.println("Generated Line: " + targetLine);
                return;
            }

            if (file.exists()) {
                file.delete();
            }
            tempFile.renameTo(file);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Method for converting string data to integer data
     *
     * @param stringData The string to be converted to an integer
     * @return The integer value of the converted string
     */
    public static int integerParser(String stringData) {
        try {
            return Integer.parseInt(stringData);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    /**
     * Method for converting char data to integer data
     * @param charData The char to be converted to an integer
     * @return The integer value of the converted char
     */
    public static int integerParser(char charData) {
        try {
            return Character.getNumericValue(charData);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    /**
     * Method for converting string data to Boolean data
     *
     * @param stringData The string to be converted to an integer
     * @return The Boolean value of the converted string
     */
    public static boolean booleanParser(String stringData) {
        if (stringData != null) {
            return Boolean.parseBoolean(stringData);
        } else {
            return false;
        }
    }

    /**
     * Method for converting string data to data of the LocalDate type
     *
     * @param stringData The string to be converted to the LocalDate type
     * @return The LocalDate equivalent of the converted string
     */
    public static LocalDate dateParser(String stringData) {
        LocalDate localDate;
        if (stringData != null) {
            localDate = LocalDate.parse(stringData);
            return localDate;
        } else {
            return null;
        }
    }
}
