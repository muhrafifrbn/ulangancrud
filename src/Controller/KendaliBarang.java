/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controller;

import java.sql.SQLException;
import View.BarangJam;
/**
 *
 * @author lenovot470s
 */
public interface KendaliBarang {
    public void Simpan(BarangJam brg) throws SQLException;
    public void Ubah(BarangJam brg) throws SQLException;
    public void Hapus(BarangJam brg) throws SQLException;
    public void Tampil(BarangJam brg) throws SQLException;
    public void KlikTable(BarangJam brg) throws SQLException;
    public void Reset(BarangJam brg) throws SQLException;
    public void AutoKodeKategori(BarangJam brg) throws SQLException;
    public void CariBarang(BarangJam brg) throws SQLException;
}
