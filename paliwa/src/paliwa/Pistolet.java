/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paliwa;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author luna
 */
public class Pistolet {

    public Timestamp ts;         //        1.	Data i godzina (stempel czasowy)
    public int locstionId;       //        2.	LocationId – zawsze puste
    public int idPistoletu;      //        3.	Id pistoletu
    public int idZbiornika;      //        4.	Id zbiornika
    public double literCounter;  //        5.	LiterCounter – bieżący licznik transakcji
    public double totalCounter;  //        6.	TotalCounter – całkowity licznik pistoletu
    public int status;       //        7.	Status: 1 – pistolet odłożony, 0 – pistolet podniesiony (trwa tankowanie)

    public static Pistolet konwertujNaPistolet(String z) throws ParseException {
        Pistolet nowy = new Pistolet();
        String[] split = z.split(";");
            for (int i = 0; i < split.length; ++i) {
                switch (i) {
                    case 0: //1.	Data i godzina (stempel czasowy
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = sdf.parse(split[i]);
                        nowy.ts = new Timestamp(date.getTime());
                    }
                    break;
                    case 1:// 2.	LocationId 
                    {
                        nowy.locstionId = 0;
                    }
                    break;
                    case 2: // pistoletu
                    {
                        nowy.idPistoletu = Integer.parseInt(split[i]);
                    }
                    break;
                    case 3: //zbiornika
                    {
                        nowy.idZbiornika = Integer.parseInt(split[i]);
                    }
                    break;
                    case 4: //5.	LiterCounter 
                    {
                        nowy.literCounter = Double.parseDouble(split[i].replace(',', '.'));
                    }
                    break;
                    case 5: //6.	TotalCounter 
                    {
                        nowy.totalCounter = Double.parseDouble(split[i].replace(',', '.'));
                    }
                    break;
                    case 6: //7.	Status
                    {
                        nowy.status = Integer.parseInt(split[i]);
                    }
                    break;
                }
            }
            return nowy;
    }
    
    
    public static ArrayList<Pistolet> konwertujNaPistolet(ArrayList<String> listaPistolety) throws ParseException {
        ArrayList<Pistolet> pistolety = new ArrayList<>();
        for (String z : listaPistolety) {
            Pistolet nowy = konwertujNaPistolet(z);
            pistolety.add(nowy);
        }
        System.out.println("Ilośc wczytanych pistoletów: " + pistolety.size());
        return pistolety;
    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> l = new ArrayList<>();
        l.add(ts.toString());
        l.add(Integer.toString(locstionId));
        l.add(Integer.toString(idPistoletu));
        l.add(Integer.toString(idZbiornika));
        l.add(Double.toString(literCounter));
        l.add(Double.toString(totalCounter));
        l.add(Integer.toString(status));
        return l;
    }

    public static void wyslijDoBazy(ArrayList<Pistolet> pistolety, Connection connection) {
        try {
            for (int i = 0; i < pistolety.size(); ++i) {

                String date = pistolety.get(i).ts.toString();
                String query = "INSERT INTO [dbo].[NozzleMeasures]"
                        + "([Data i godzina]"
                        + ",[LocationId]"
                        + ",[Id pistoletu]"
                        + ",[Id zbiornika]"
                        + ",[LiterCounter]"
                        + ",[TotalCounter]"
                        + ",[Status]) VALUES("
                        + "'" + date + "'"
                        + ","
                        + pistolety.get(i).locstionId + ","
                        + pistolety.get(i).idPistoletu + ","
                        + pistolety.get(i).idZbiornika + ","
                        + pistolety.get(i).literCounter + ","
                        + pistolety.get(i).totalCounter + ","
                        + pistolety.get(i).status + ")";
                boolean sendQueryAlt = OknoGłówne.sendQueryAlt(connection, query);
                //Dopóki "result" coś przechwouje.
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Pistolet\tPobrano :" + pistolety.size() + " rekordów.");
    }
}
