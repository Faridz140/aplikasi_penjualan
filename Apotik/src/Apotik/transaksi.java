

package Apotik;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Apotik.koneksi.koneksi;




public class transaksi extends javax.swing.JFrame {
    private DefaultTableModel model;

    /** Creates new form  */
    public transaksi() {
        initComponents();
  

        jTextFieldstok.hide();
        jTextFieldid.hide();
        jTextFieldwaktujual.hide();

        model = new DefaultTableModel();
        jTable1.setModel(model);
        model.addColumn("Id Obat");
        model.addColumn("Nama Obat");
        model.addColumn("Harga");
        model.addColumn("jumlah");
        model.addColumn("Sub Total");
        model.addColumn("Waktu Jual");

        //fungsi untuk menyembunyikan kolom ID dan Waktu Jual
        jTable1.getColumnModel().getColumn(5).setMinWidth(0);
        jTable1.getColumnModel().getColumn(5).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(5).setWidth(0);

        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setWidth(0);

        Date date = new Date();
        jftanggaljual.setDate(date);
    }
  

   public void cari_id(){
        try {
            java.sql.Connection kon = koneksi.koneksiDb();
        String sql = "select * from obat where idobat='"+this.jTextField2.getText()+"'";
            java.sql.Statement st = koneksi.koneksiDb().createStatement();
        ResultSet rs = st.executeQuery(sql);

        while(rs.next()){
        this.jTextField4.setText(rs.getString("namaobat"));
        this.jTextField5.setText(rs.getString("hargajual"));
        this.jTextFieldstok.setText(rs.getString("stok"));
        }
        rs.close(); st.close();}
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
}



    public void HitungKembali() {
        int d, e, f;
        d = Integer.parseInt(jftotalbayar.getText());
        e = Integer.parseInt(jfbayar.getText());
        f = e-d;
        jfkembali.setText(""+f);
    }


    public void subtotal() {
        int a, b, c;
        a = Integer.parseInt(jTextField5.getText());
        b = Integer.parseInt(jfjumlahpembeilan.getText());
        c = a*b;
        jTextField7.setText(""+c);
    }



   public void TambahDetail(){
   Date HariSekarang = new Date( );
   SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");

   String nofaktur =this.tfidpembayaran.getText();
   String idobat =this.jTextField2.getText();
   String harga =this.jTextField5.getText();
   String quantity =this.jfjumlahpembeilan.getText();
   String subtotal =this.jTextField7.getText();
   String tanggaljual = ft.format(HariSekarang);


   try{
            java.sql.Connection kon =koneksi.koneksiDb();
     String sql="Insert into detailpenjualan (nofaktur,idobat,hargajual,quantity,subtotal,tanggaljual) values (?,?,?,?,?,?)";
     PreparedStatement p=(PreparedStatement)kon.prepareStatement(sql);
     p.setString(1,nofaktur);
     p.setString(2,idobat);
     p.setString(3,harga);
     p.setString(4,quantity);
     p.setString(5,subtotal);
     p.setString(6,tanggaljual);
     p.executeUpdate();
     p.close();
   }catch(SQLException e){
   System.out.println(e);
   }finally{
   //loadData();
       //JOptionPane.showMessageDialog(this,"Data Telah Tersimpan");
  }
 }




   public final void loadData(){
   model.getDataVector().removeAllElements();
   model.fireTableDataChanged();
   try{
            java.sql.Connection kon = koneksi.koneksiDb();
            java.sql.Statement s= kon.createStatement();
     String sql="Select * from detailpenjualan, obat WHERE detailpenjualan.idobat = obat.idobat AND detailpenjualan.nofaktur='"+this.tfidpembayaran.getText()+"'";
     ResultSet r=s.executeQuery(sql);
     while(r.next()){
       Object[]o=new Object[6];
       o[0]=r.getString("idobat");
       o[1]=r.getString("namaobat");
       o[2]=r.getString("hargajual");
       o[3]=r.getString("quantity");
       o[4]=r.getString("subtotal");
       o[5]=r.getString("stok");
       model.addRow(o);
     }
     r.close();
     s.close();
     //ShowData();
   }catch(SQLException e){
     System.out.println("Terjadi Kesalahan");
   }


   int total = 0;
   for (int i =0; i< jTable1.getRowCount(); i++){
       int amount = Integer.parseInt((String)jTable1.getValueAt(i, 4));
       total += amount;
   }
   jftotalbayar.setText(""+total);
    }



   public void UpdateStock(){
    int x, y, z;
    x = Integer.parseInt(jTextFieldstok.getText());
    y = Integer.parseInt(jfjumlahpembeilan.getText());
    z = x-y;

    String idbarang=this.jTextField2.getText();
     try{
            java.sql.Connection kon =koneksi.koneksiDb();
       String sql ="UPDATE obat set stok=? WHERE idobat=?";
       PreparedStatement p=(PreparedStatement)kon.prepareStatement(sql);
           p.setInt(1,z);
           p.setString(2,idbarang);//yang kode atau id letakkan di nomor terakhir
           p.executeUpdate();
           p.close();
     }catch(SQLException e){
       System.out.println("Terjadi Kesalahan");
     }finally{
       //JOptionPane.showMessageDialog(this,"Stock barang telah di update Diubah");
     }
}

   public void Cari_Kode(){
   int i=jTable1.getSelectedRow();
   if(i==-1)
   { return; }
   String ID =(String)model.getValueAt(i, 0);
   jTextField2.setText(ID);
   }


   public void ShowData(){
   try {
        java.sql.Connection kon = koneksi.koneksiDb();
        String sql="Select * from detailpenjualan, obat WHERE detailpenjualan.idobat = obat.idobat AND detailpenjualan.idobat='"+this.jTextField2.getText()+"'";
        java.sql.Statement st = koneksi.koneksiDb().createStatement();
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
        this.jfjumlahpembeilan.setText(rs.getString("quantity"));
        this.jTextField4.setText(rs.getString("namaobat"));
        this.jTextField5.setText(rs.getString("hargajual"));
        this.jTextField7.setText(rs.getString("subtotal"));
        this.jTextFieldwaktujual.setText(rs.getString("tanggaljual"));
        }
        rs.close(); st.close();}
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
 }


   public void ShowSisa(){
   try {
        java.sql.Connection kon = koneksi.koneksiDb();
        String sql="Select * from obat WHERE idobat ='"+this.jTextField2.getText()+"'";
        java.sql.Statement st = koneksi.koneksiDb().createStatement();
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
        this.jTextFieldstok.setText(rs.getString("stok"));
        }
        rs.close(); st.close();}
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
 }



   public void Batal(){
    int x, y, z;
    x = Integer.parseInt(jTextFieldstok.getText());
    y = Integer.parseInt(jfjumlahpembeilan.getText());
    z = x+y;

    String idobat =this.jTextField2.getText();
     try{
            java.sql.Connection kon = koneksi.koneksiDb();
       String sql ="UPDATE obat set stok=? WHERE idobat=?";
       PreparedStatement p =(PreparedStatement)kon.prepareStatement(sql);
           p.setInt(1,z);
           p.setString(2,idobat);//yang kode atau id letakkan di nomor terakhir
           p.executeUpdate();
           p.close();
     }catch(SQLException e){
       System.out.println("Terjadi Kesalahan");
     }finally{
       //JOptionPane.showMessageDialog(this,"Stock barang telah di update Diubah");
     }



     //Proses mengahapus data dari tabel detail
     try {
        java.sql.Connection kon = koneksi.koneksiDb();
        String sql="DELETE From detailpenjualan WHERE nofaktur='"+this.tfidpembayaran.getText()+"' AND  tanggaljual ='"+this.jTextFieldwaktujual.getText()+"'";
        PreparedStatement p=(PreparedStatement)kon.prepareStatement(sql);
        p.executeUpdate();
        p.close();
    }catch(SQLException e){
        System.out.println("Terjadi Kesalahan");
    }finally{
        loadData();
        JOptionPane.showMessageDialog(this,"keranjang telah kosong...");
    }
   }




   public void Selesai(){
   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
   Date tanggal = new Date();
   tanggal = jftanggaljual.getDate();
   String tanggaljual= dateFormat.format(tanggal);
   
   PreparedStatement ps;
   String sql="insert into penjualan value('"+tfidpembayaran.getText()+"','"+tanggaljual+"','"+jTextField3.getText()+"','"+jfjumlahpembeilan.getText()+
           "','"+jftotalbayar.getText()+"','"+jfbayar.getText()+"','"+jfkembali.getText()+"')";
   
   try{
        ps =(PreparedStatement) koneksi.koneksiDb().prepareStatement(sql);
     
        ps.executeUpdate();
        ps.close();
   }catch(SQLException e){
        System.out.println(e);
   }finally{
   //loadData();
       JOptionPane.showMessageDialog(this,"Data Telah Tersimpan");
  }
  
  loadData();
 }



   public  void bersihkan(){
        jTextField2.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
//        jfjumlahpembeilan.setText("");
        jTextField7.setText("");
        jfbayar.setText("");
        jfkembali.setText("");

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        tfidpembayaran = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jfjumlahpembeilan = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jfbayar = new javax.swing.JTextField();
        jfkembali = new javax.swing.JTextField();
        jbsimpan = new javax.swing.JButton();
        jbhapus = new javax.swing.JButton();
        jftotalbayar = new javax.swing.JTextField();
        jbtambahkeranjang = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextFieldstok = new javax.swing.JTextField();
        jTextFieldid = new javax.swing.JTextField();
        jTextFieldwaktujual = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jftanggaljual = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(44, 62, 80));
        jPanel2.setEnabled(false);

        tfidpembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfidpembayaranActionPerformed(evt);
            }
        });

        jTextField4.setEditable(false);

        jTextField5.setEditable(false);

        jfjumlahpembeilan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jfjumlahpembeilanKeyReleased(evt);
            }
        });

        jTextField7.setEditable(false);
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Bayar");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Kembali");

        jfbayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jfbayarKeyReleased(evt);
            }
        });

        jfkembali.setEditable(false);

        jbsimpan.setBackground(new java.awt.Color(51, 255, 0));
        jbsimpan.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jbsimpan.setText("Simpan");
        jbsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbsimpanActionPerformed(evt);
            }
        });

        jbhapus.setBackground(new java.awt.Color(255, 0, 0));
        jbhapus.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jbhapus.setText("Kosongkan keranjang");
        jbhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbhapusActionPerformed(evt);
            }
        });

        jftotalbayar.setEditable(false);
        jftotalbayar.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jftotalbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jftotalbayarActionPerformed(evt);
            }
        });

        jbtambahkeranjang.setBackground(new java.awt.Color(51, 51, 255));
        jbtambahkeranjang.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jbtambahkeranjang.setText("Tambah Ke Keranjang");
        jbtambahkeranjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtambahkeranjangActionPerformed(evt);
            }
        });

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jButton4.setText("Lihat");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Transaksi");

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("No Faktur");

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Tanggal");

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Nama");

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Total Harga ");

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Id Obat");

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Harga");

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Nama Obat");

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Total");

        jLabel28.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Sub Total");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel22))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel26)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel25)
                                        .addGap(8, 8, 8)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addComponent(jLabel27)
                                        .addGap(25, 25, 25)
                                        .addComponent(jLabel28))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jfjumlahpembeilan, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jbtambahkeranjang)))
                                .addContainerGap(16, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jftotalbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel21))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tfidpembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel18)
                                            .addComponent(jButton4)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jftanggaljual, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel11))
                                        .addGap(47, 47, 47)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jfkembali)
                                            .addComponent(jfbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jbsimpan)))
                                .addGap(95, 95, 95)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldwaktujual)
                                    .addComponent(jTextFieldid)
                                    .addComponent(jTextFieldstok, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jbhapus)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addGap(118, 118, 118))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(46, Short.MAX_VALUE)
                        .addComponent(jLabel23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfidpembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel19))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel21)
                                    .addComponent(jftanggaljual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel22)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jftotalbayar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jfjumlahpembeilan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtambahkeranjang))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbhapus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldstok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(jfbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(12, 12, 12)
                                .addComponent(jbsimpan))
                            .addComponent(jfkembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jTextFieldid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldwaktujual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(248, 148, 6));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(181, 192, 196));
        jLabel14.setText("jLabel7");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(181, 192, 196));
        jLabel15.setText("  jLabel8");

        jLabel20.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Apotik Bersama");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(42, 42, 42))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15)))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(593, 583));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cari_id();
        }
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jfbayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jfbayarKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        HitungKembali();
        }
    }//GEN-LAST:event_jfbayarKeyReleased

    private void jfjumlahpembeilanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jfjumlahpembeilanKeyReleased
        // TODO add your handling code here:
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        subtotal();
        }
    }//GEN-LAST:event_jfjumlahpembeilanKeyReleased

    private void jbtambahkeranjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtambahkeranjangActionPerformed
        // TODO add your handling code here:
        TambahDetail();
        UpdateStock();
        loadData();
        bersihkan();
    }//GEN-LAST:event_jbtambahkeranjangActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        Cari_Kode();
        ShowData();
        ShowSisa();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jbhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbhapusActionPerformed
        // TODO add your handling code here:
        Batal();
        bersihkan();
    }//GEN-LAST:event_jbhapusActionPerformed

    private void jbsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbsimpanActionPerformed
        // TODO add your handling code here:
        Selesai();
        bersihkan();

        int i = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Mencetak Nota ?");
        if(i==0){
        String msg = tfidpembayaran.getText();
        new cetakfaktur(msg).setVisible(true);
        this.dispose();
        }else{
            new Logout().show();
            this.dispose();
        }
    }//GEN-LAST:event_jbsimpanActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        new lihatobat().show();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void jftotalbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jftotalbayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jftotalbayarActionPerformed

    private void tfidpembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfidpembayaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfidpembayaranActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
    }//GEN-LAST:event_formWindowActivated

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksi().setVisible(true);
            }
        });
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextFieldid;
    private javax.swing.JTextField jTextFieldstok;
    private javax.swing.JTextField jTextFieldwaktujual;
    private javax.swing.JButton jbhapus;
    private javax.swing.JButton jbsimpan;
    private javax.swing.JButton jbtambahkeranjang;
    private javax.swing.JTextField jfbayar;
    private javax.swing.JTextField jfjumlahpembeilan;
    private javax.swing.JTextField jfkembali;
    private com.toedter.calendar.JDateChooser jftanggaljual;
    private javax.swing.JTextField jftotalbayar;
    private javax.swing.JTextField tfidpembayaran;
    // End of variables declaration//GEN-END:variables

    private void auto_key() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
