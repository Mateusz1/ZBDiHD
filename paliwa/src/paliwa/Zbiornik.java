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
public class Zbiornik {

    public Timestamp ts;    //        1.	Data i godzina (stempel czasowy)
    public int locstionId;  //        2.	LocationId – zawsze puste
    public int MeterId;     //        3.	MeterId – zawsze puste
    public int idZbiornika; //        4.	Id zbiornika
    public float wysokoscP; //        5.	Wysokość paliwa – zawsze zero
    public float objetoscP; //        6.	Objętość paliwa
    public float temoP;     //        7.	Temperatura paliwa
    public int wysokoscW;   //        8.	Wysokość wody – zawsze zero
    public int objetoscW;   //        9.	Objętość wody – zawsze zero

    public static Zbiornik konwerujNaZbiornik(String z) throws ParseException {
        Zbiornik nowy = new Zbiornik();
        String[] split = z.split(";");
        for (int i = 0; i < split.length + 1; ++i) {
            switch (i) {
                case 0: //data
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = sdf.parse(split[i]);
                    nowy.ts = new Timestamp(date.getTime());
                }
                break;
                case 1:// locstionId
                {
                    nowy.locstionId = 0;
                }
                break;
                case 2: // MeterId
                {
                    nowy.MeterId = 0;
                }
                break;
                case 3: //idZbiornika
                {
                    nowy.idZbiornika = Integer.parseInt(split[i]);
                }
                break;
                case 4: //Wysokość paliwa
                {
                    nowy.wysokoscP = 0;
                }
                break;
                case 5: //Objętość paliwa
                {
                    nowy.objetoscP = Float.parseFloat(split[i].replace(',', '.'));
                }
                break;
                case 6: //Temperatura paliwa
                {
                    nowy.temoP = Float.parseFloat(split[i].replace(',', '.'));
                }
                break;
                case 7: //wysokość wody
                {
                    nowy.wysokoscW = 0;
                }
                break;
                case 8: {
                    nowy.objetoscW = 0;
                }
                break;
            }
        }
        return nowy;
    }

    public static ArrayList<Zbiornik> konwerujNaZbiornik(ArrayList<String> listaZbiorniki) throws ParseException {
        ArrayList<Zbiornik> zbiorniki = new ArrayList<>();
        for (String z : listaZbiorniki) {
            Zbiornik nowy = konwerujNaZbiornik(z);
            zbiorniki.add(nowy);
        }
        System.out.println("Ilośc wczytanych zbiorników: " + zbiorniki.size());
        return zbiorniki;
    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> l = new ArrayList<>();
        l.add(ts.toString());
        l.add(Integer.toString(locstionId));
        l.add(Integer.toString(MeterId));
        l.add(Integer.toString(idZbiornika));
        l.add(Float.toString(wysokoscP));
        l.add(Float.toString(objetoscP));
        l.add(Float.toString(wysokoscW));
        l.add(Float.toString(objetoscW));
        return l;
    }

    public static void wyslijDoBazy(ArrayList<Zbiornik> zbiorniki, Connection connection) {
        try {
            for (int i = 0; i < zbiorniki.size(); ++i) {

                String date = zbiorniki.get(i).ts.toString(); //20
                String query = "INSERT INTO [dbo].[TankMeasures] "
                        + "([Data i godzina],[LocationId],[MeterId],[Id zbiornika],[Wysokość paliwa],[Objętość paliwa],[Temperatura paliwa],[Wysokość wody],[Objętość wody]) "
                        + "VALUES ("
                        + "'" + date + "'"
                        + ","
                        + zbiorniki.get(i).locstionId + ","
                        + zbiorniki.get(i).MeterId + ","
                        + zbiorniki.get(i).idZbiornika + ","
                        + zbiorniki.get(i).wysokoscP + ","
                        + zbiorniki.get(i).objetoscP + ","
                        + zbiorniki.get(i).temoP + ","
                        + zbiorniki.get(i).wysokoscW + ","
                        + zbiorniki.get(i).objetoscW + ")";
                boolean sendQueryAlt = OknoGłówne.sendQueryAlt(connection, query);
                //Dopóki "result" coś przechwouje.
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Zbiornik\tPobrano :" + zbiorniki.size() + " rekordów.");
    }
}
