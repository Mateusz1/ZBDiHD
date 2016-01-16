/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paliwa;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author luna
 */
public class TableModel extends AbstractTableModel {

    ArrayList<ArrayList<String>> kolumny = new ArrayList<ArrayList<String>>();
    String[] nazwakol;

    public void setNazwakol(String[] nazwakol) {
        this.nazwakol = nazwakol;
    }

    public void setKolumny(ArrayList<ArrayList<String>> kolumny) {
        this.kolumny = kolumny;
    }

    @Override
    public String getColumnName(int index) {
        if (nazwakol.length > 0) {
            return nazwakol[index];
        }
        return "";
    }

    @Override
    public int getRowCount() {
        return kolumny.size();
    }

    @Override
    public int getColumnCount() {
        if (kolumny.size() > 0) {
            return kolumny.get(0).size();
        }
        return 0;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        kolumny.get(rowIndex).set(columnIndex, (String) aValue);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return kolumny.get(rowIndex).get(columnIndex);
    }
}
