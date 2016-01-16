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
public class Tankowanie {

    public Timestamp ts;         //        1.	Data i godzina (stempel czasowy)
    public int idZbiornika;
    public float objetoscPaliwa;
    public float szybkoscTankowania;

    public static Tankowanie konwertujNaTankowanie(String z) throws ParseException {
        Tankowanie nowy = new Tankowanie();
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
                        nowy.idZbiornika = Integer.parseInt(split[i]);;
                    }
                    break;
                    case 2: // pistoletu
                    {
                        nowy.objetoscPaliwa = Float.parseFloat(split[i].replace(',', '.'));
                    }
                    break;
                    case 3: //zbiornika
                    {
                        nowy.szybkoscTankowania = Float.parseFloat(split[i].replace(',', '.'));
                    }
                    break;
                }
            }
        return nowy;
    }
    
    public static ArrayList<Tankowanie> konwertujNaTankowanie(ArrayList<String> listaTankowanie) throws ParseException {
        ArrayList<Tankowanie> tankowanie = new ArrayList<>();
        for (String z : listaTankowanie) {
            Tankowanie nowy = konwertujNaTankowanie(z);
            tankowanie.add(nowy);
        }
        System.out.println("Ilośc wczytanych tankowanń: " + tankowanie.size());
        return tankowanie;
    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> l = new ArrayList<>();
        l.add(ts.toString());
        l.add(Integer.toString(idZbiornika));
        l.add(Float.toString(objetoscPaliwa));
        l.add(Float.toString(szybkoscTankowania));
        return l;
    }

    public static void wyslijDoBazy(ArrayList<Tankowanie> tankowanies, Connection connection) {
        try {
            for (int i = 0; i < tankowanies.size(); ++i) {

                String date = tankowanies.get(i).ts.toString();
                String query = "INSERT INTO [dbo].[Refuel]\n"
                        + "([Data i godzina]\n"
                        + ",[Id zbiornika]\n"
                        + ",[Objętość paliwa]\n"
                        + ",[Szybkość tankowania])\n"
                        + "VALUES("
                        + "'" + date + "'"
                        + ","
                        + tankowanies.get(i).idZbiornika + ","
                        + tankowanies.get(i).objetoscPaliwa + ","
                        + tankowanies.get(i).szybkoscTankowania + ")";
                boolean sendQueryAlt = OknoGłówne.sendQueryAlt(connection, query);
                //Dopóki "result" coś przechwouje.
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Tankowanie\tPobrano :" + tankowanies.size() + " rekordów.");
    }
}
