/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Controller.KendaliBarang;
import View.BarangJam;
import java.sql.SQLException;
import Koneksi.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lenovot470s
 */
public class ModelBarang implements KendaliBarang{
    String jk;
    List<BarangJam> list;
    String [] header;
    @Override
    public void Simpan(BarangJam brg) throws SQLException {
      Integer harga = Integer.valueOf(brg.txtHarga.getText());
         Integer stok = Integer.valueOf(brg.txtStock.getText());
         
        if (brg.rbLakiLaki.isSelected()) {
            jk = "Laki-laki";
        } else {
            jk = "Perempuan";
        }
          try {
            Connection con = koneksi.getcon();
            String sql = "INSERT INTO barangjam VALUES (?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, brg.txtKodeJam.getText());
            pst.setString(2, brg.txtMerkJam.getText());
            pst.setString(3, jk);
            pst.setInt(4, harga);
            pst.setInt(5, stok);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil di simpan");
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal di simpan");
            System.err.println("Error "+ e);
        } finally{
            Tampil(brg);
            Reset(brg);
            brg.setLebarKolom();
        }  
    }

    @Override
    public void Ubah(BarangJam brg) throws SQLException {
        Integer harga = Integer.valueOf(brg.txtHarga.getText());
         Integer stok = Integer.valueOf(brg.txtStock.getText());
         
        if (brg.rbLakiLaki.isSelected()) {
            jk = "Laki-laki";
        } else {
            jk = "Perempuan";
        }
        try {
            Connection con =koneksi.getcon();
            String sql = "UPDATE barangjam SET merek_jam=?, jenis_jam=?, harga=?, stok=? WHERE kode_jam=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(5, brg.txtKodeJam.getText());
            pst.setString(1, brg.txtMerkJam.getText());
            pst.setString(2,  jk);
            pst.setInt(3, harga);
            pst.setInt(4, stok);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Diubah");
            System.out.println(e);
            } finally{
            Tampil(brg);
            Reset(brg);
            brg.setLebarKolom();
        }
    }

    @Override
    public void Hapus(BarangJam brg) throws SQLException {
         try {
            Connection con = koneksi.getcon();
            String sql = "DELETE FROM barangjam WHERE kode_jam=?";
            PreparedStatement prepare = con.prepareStatement(sql);
            prepare.setString(1,brg.txtKodeJam.getText());
            prepare.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            prepare.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data Gagal dihapus");
            System.out.println(e);
        } finally{
           Tampil(brg);
            Reset(brg);
            brg.setLebarKolom();
        }
    }

    @Override
    public void Tampil(BarangJam brg) throws SQLException {
        brg.tblmodel.getDataVector().removeAllElements();
        brg.tblmodel.fireTableDataChanged();
        
        try {
            Connection con = koneksi.getcon();
            Statement stt = con.createStatement();
            // Query menampilkan semua data pada tabel siswa
            // dengan urutan NIS dari kecil ke besar
            String sql = "SELECT * FROM barangjam ORDER BY kode_jam ASC";
            ResultSet res = stt.executeQuery(sql);
            int no = 1;
            while (res.next()) {
                Object[] ob = new Object[8];
                ob[0] = no++;
                ob[1] = res.getString(1);
                ob[2] = res.getString(2);
                ob[3] = res.getString(3);
                ob[4] = res.getString(4);
                ob[5] = res.getString(5);
                brg.tblmodel.addRow(ob);
            }        
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void KlikTable(BarangJam brg) throws SQLException {
        try {
            int pilih = brg.tabelBarang.getSelectedRow();
            if (pilih == -1) {
               return;
            }
            brg.txtKodeJam.setText(brg.tblmodel.getValueAt(pilih, 1).toString());
            brg.txtMerkJam.setText(brg.tblmodel.getValueAt(pilih, 2).toString());
            jk = String.valueOf(brg.tblmodel.getValueAt(pilih, 3));
            brg.txtHarga.setText(brg.tblmodel.getValueAt(pilih, 4).toString());
            brg.txtStock.setText(brg.tblmodel.getValueAt(pilih, 5).toString());
        } catch (Exception e) {
            System.out.println(e);
        }
          // memberi nilai jk pada radio button
        if (brg.rbLakiLaki.getText().equals(jk)) {
           brg.rbLakiLaki.setSelected(true);
        } else {
            brg.rbPerempuan.setSelected(true);
        }
    }

    @Override
    public void Reset(BarangJam brg) throws SQLException {
         brg.txtMerkJam.setText("");
        brg.rbLakiLaki.setSelected(true);
        brg.txtHarga.setText("");
        brg.txtStock.setText("");
        brg.txtCariJam.setText("");
    }

    @Override
    public void AutoKodeKategori(BarangJam brg) throws SQLException {
         PreparedStatement statement = null;
        Connection con = koneksi.getcon();
       int nomor_berikutnya = 0;
       String urutan = "";
        try {
            
            String COUNTER = "SELECT ifnull(max(convert(right(kode_jam,5), signed integer)),0) as kode,"
            + "ifnull(length(max(convert(right(kode_jam,5), signed integer))),0) as panjang FROM barangjam";
            
            statement = con.prepareStatement(COUNTER);
            ResultSet rs2 = statement.executeQuery();
            if(rs2.next()){
                nomor_berikutnya = rs2.getInt("kode") + 1;
               if (rs2.getInt("kode") != 0) {
                            if (rs2.getInt("panjang") == 1) {
                                urutan = "KTGJM" + "0000" + nomor_berikutnya;
                            } else if (rs2.getInt("panjang") == 2) {
                               urutan = "KTGJM" + "000" + nomor_berikutnya;
                            }else if (rs2.getInt("panjang") == 3) {
                               urutan = "KTGJM" + "00" + nomor_berikutnya;
                            }else if (rs2.getInt("panjang") == 4) {
                               urutan = "KTGJM" + "0" + nomor_berikutnya;
                            }else if (rs2.getInt("panjang") == 5) {
                               urutan = "KTGJM"+ nomor_berikutnya;
                            }
                   brg.txtKodeJam.setText(urutan);
                }
               else {
                   urutan = "KTGJM"+ "00001";
                  brg.txtKodeJam.setText(urutan);
               }
            } else {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void CariBarang(BarangJam brg) throws SQLException {
          brg.tblmodel.getDataVector().removeAllElements();
        brg.tblmodel.fireTableDataChanged();

        try {
            if (brg.txtCariJam.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong", "Pesan", JOptionPane.ERROR_MESSAGE);
            } else {
                Connection con = koneksi.getcon();
                Statement stt = con.createStatement();
                String sql = "select * from barangjam where merek_jam LIKE '%"+ brg.txtCariJam.getText() +"%' OR jenis_jam LIKE '%"+ brg.txtCariJam.getText() +"%' OR harga LIKE '%"+ brg.txtCariJam.getText() +"%' OR stok LIKE '%"+ brg.txtCariJam.getText() +"%' ";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet res = pst.executeQuery(sql);
                int no = 1;
                while (res.next()) {
                Object[] ob = new Object[8];
                ob[0] = no++;
                ob[1] = res.getString(1);
                ob[2] = res.getString(2);
                ob[3] = res.getString(3);
                ob[4] = res.getString(4);
                ob[5] = res.getString(5);
                    brg.tblmodel.addRow(ob);

                 Reset(brg);   
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
