package parkeasy;

import com.fazecast.jSerialComm.*;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static jdk.nashorn.internal.objects.NativeError.printStackTrace;

public class SensorStatusUpdater extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    private static Connection connection = null;
    /**
     * Creates new form MainFraim
     */

    SimpleDateFormat dateformater, timeformater;
    Date date;
    GridBagLayout layout = new GridBagLayout();

    String garage_name, new_date, new_sensordata, old_sensordata, old_date, sensorTableName,
            s = "n", parked_username;
    int id, fee, extra, due, paid;
    int newp[], oldp[];
    int homebtn = 0, newentrybtn = 0, relesebtn = 0, notificationbtn = 0,
            reportbtn = 0, tutorialbtn = 0, aboutusbtn = 0, featurebtn = 0;
    private String u_name;
    //private int u_id;
    //private String atiq;
    //private String parkedTableName;

    SerialPort[] ports;
    SerialPort serialPort;
    String value = "";

    public SensorStatusUpdater() {
        initComponents();
    }

    public SensorStatusUpdater(String username) {
        u_name = username;
        dateformater = new SimpleDateFormat("dd/MM/yyyy");
        //timeformater = new SimpleDateFormat("HH:mm");
        date = new Date();
        new_date = dateformater.format(date);
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("parking.png")));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        old_date = "empty";
        newp = new int[14];
        oldp = new int[14];
        sensorTableName = u_name + "_sensor_status";
        connection = DbConnect.dbconnect();

        SerialPort[] ports = SerialPort.getCommPorts();
        System.out.println("Select a port:");
        int i = 1;
        for (SerialPort port : ports) {
            comboCOMPORT.addItem(port.getSystemPortName());
        }

    }

    void sensordata() {

        if (serialPort.openPort()) {
            System.out.println("Port opened successfully.");
        } else {
            System.out.println("Unable to open the port.");
            return;
        }
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

        Scanner data = new Scanner(serialPort.getInputStream());

        while (data.hasNextLine()) {

            value = data.nextLine();
            
            System.err.println("nodemcu dta:"+value);
            if(value.length()==14){

            ArrayList<String> list = new ArrayList();
            int i;
            new_sensordata = value;
//

            // System.out.println(new_sensordata);
            // System.out.println(old_sensordata);
            if (!new_sensordata.equals(old_sensordata)) {
                for (i = 0; i < 14; i++) {
                    newp[i] = new_sensordata.charAt(i) - 48;
                }
                old_sensordata = new_sensordata;
            } else {
                old_sensordata = new_sensordata;
            }

            if (!old_date.equals(new_date)) {
                try {

                    String sql_1 = "select * from " + sensorTableName + " ";
                    PreparedStatement st = connection.prepareStatement(sql_1);
                    ResultSet rs_1 = st.executeQuery();

                    while (rs_1.next()) {

                        list.add(rs_1.getString("date"));

                    }

                    st.close();
                    rs_1.close();

                } catch (Exception e) {
                    printStackTrace(e);
                }
                for (i = 0; i < list.size(); i++) {
                    if (list.get(i).equals(new_date)) {
                        s = "y";
                    }
                }

                if (s.equals("n")) {
                    System.err.println("no date is found!");

                    try {
                        String sql_2 = "insert into " + sensorTableName + ""
                                + "(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,date)"
                                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                        PreparedStatement statement = connection.prepareStatement(sql_2);

                        statement.setInt(1, newp[0]);
                        statement.setInt(2, newp[1]);
                        statement.setInt(3, newp[2]);
                        statement.setInt(4, newp[3]);
                        statement.setInt(5, newp[4]);
                        statement.setInt(6, newp[5]);
                        statement.setInt(7, newp[6]);
                        statement.setInt(8, newp[7]);
                        statement.setInt(9, newp[8]);
                        statement.setInt(10, newp[9]);
                        statement.setInt(11, newp[10]);
                        statement.setInt(12, newp[11]);
                        statement.setInt(13, newp[12]);
                        statement.setInt(14, newp[13]);
                        statement.setString(15, new_date);

                        statement.executeUpdate();

                        //DefaultTableModel model = (DefaultTableModel) blockBtable.getModel();
                        //model.setRowCount(0);
                        System.out.println("Insrt sucessfull");
                        //JOptionPane.showMessageDialog(null, "Registration sucessfull");

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }

                    //SensorStatus();
                    old_date = new_date;

                } else if (s.equals("y")) {

                    try {

                        String sql_1 = "select * from " + sensorTableName + " where date='" + new_date + "'";
                        PreparedStatement st = connection.prepareStatement(sql_1);
                        ResultSet rs_1 = st.executeQuery();

                        if (rs_1.next()) {

                            oldp[0] = rs_1.getInt("p1");
                            oldp[1] = rs_1.getInt("p2");
                            oldp[2] = rs_1.getInt("p3");
                            oldp[3] = rs_1.getInt("p4");
                            oldp[4] = rs_1.getInt("p5");
                            oldp[5] = rs_1.getInt("p6");
                            oldp[6] = rs_1.getInt("p7");
                            oldp[7] = rs_1.getInt("p8");
                            oldp[8] = rs_1.getInt("p9");
                            oldp[9] = rs_1.getInt("p10");
                            oldp[10] = rs_1.getInt("p11");
                            oldp[11] = rs_1.getInt("p12");
                            oldp[12] = rs_1.getInt("p13");
                            oldp[13] = rs_1.getInt("p14");

                        }
                    } catch (Exception e) {

                    }

                    for (i = 0; i < 14; i++) {
                        System.out.println(oldp[i]);
                    }
                    for (i = 1; i <= 14; i++) {

                        if (oldp[i - 1] != newp[i - 1]) {
                            try {

                                String sql6 = "update " + sensorTableName + " set p" + i + " = '" + newp[i - 1]
                                        + "' where p" + i + "='" + oldp[i - 1] + "'";
                                PreparedStatement statement = connection.prepareStatement(sql6);
                                statement.executeUpdate();
                                statement.close();
                                System.out.println("updated");
                                //DefaultTableModel model = (DefaultTableModel) blockBtable.getModel();
                                //model.setRowCount(0);

                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(this, e);
                            }

                        }
                    }
                    s = "n";
                    old_date = new_date;

                }

            } else {
                try {

                    String sql_1 = "select * from " + sensorTableName + " where date='" + new_date + "'";
                    PreparedStatement st = connection.prepareStatement(sql_1);
                    ResultSet rs_1 = st.executeQuery();

                    if (rs_1.next()) {

                        oldp[0] = rs_1.getInt("p1");
                        oldp[1] = rs_1.getInt("p2");
                        oldp[2] = rs_1.getInt("p3");
                        oldp[3] = rs_1.getInt("p4");
                        oldp[4] = rs_1.getInt("p5");
                        oldp[5] = rs_1.getInt("p6");
                        oldp[6] = rs_1.getInt("p7");
                        oldp[7] = rs_1.getInt("p8");
                        oldp[8] = rs_1.getInt("p9");
                        oldp[9] = rs_1.getInt("p10");
                        oldp[10] = rs_1.getInt("p11");
                        oldp[11] = rs_1.getInt("p12");
                        oldp[12] = rs_1.getInt("p13");
                        oldp[13] = rs_1.getInt("p14");

                    }
                } catch (Exception e) {

                }
                for (i = 1; i <= 14; i++) {

                    if (oldp[i - 1] != newp[i - 1]) {
                        try {

                            String sql6 = "update " + sensorTableName + " set p" + i + " = '" + newp[i - 1]
                                    + "' where p" + i + "='" + oldp[i - 1] + "'";
                            PreparedStatement statement = connection.prepareStatement(sql6);
                            statement.executeUpdate();
                            statement.close();
                            System.out.println("updated");

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, e);
                        }

                    }
                }

                old_date = new_date;
            }

            System.out.println(value);
            //sv.j.setText(String.valueOf(value));
        }
        System.out.println("Done.");
    }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        comboCOMPORT = new javax.swing.JComboBox<>();
        btnSave = new javax.swing.JPanel();
        fontSave = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(249, 148, 6));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(249, 148, 6));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 153), 2, true), "Select Com Port", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        btnSave.setBackground(new java.awt.Color(255, 255, 255));
        btnSave.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.setPreferredSize(new java.awt.Dimension(150, 24));
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSaveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSaveMouseExited(evt);
            }
        });

        fontSave.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fontSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Checked_30px.png"))); // NOI18N
        fontSave.setText("Confirm");
        fontSave.setIconTextGap(8);

        javax.swing.GroupLayout btnSaveLayout = new javax.swing.GroupLayout(btnSave);
        btnSave.setLayout(btnSaveLayout);
        btnSaveLayout.setHorizontalGroup(
            btnSaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSaveLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fontSave, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnSaveLayout.setVerticalGroup(
            btnSaveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fontSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/if_com_plug01_47497.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboCOMPORT, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(comboCOMPORT))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/parking (10).png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ParkEasy");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked
        // TODO add your handling code here:
        setclickedcolor(btnSave, fontSave);
        serialPort = SerialPort.getCommPort(comboCOMPORT.getSelectedItem().toString());
        showDataTable();
        this.dispose();


    }//GEN-LAST:event_btnSaveMouseClicked

    private void btnSaveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseEntered

        // TODO add your handling code here:
        setselectcolor(btnSave, fontSave);
    }//GEN-LAST:event_btnSaveMouseEntered

    private void btnSaveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseExited
        // TODO add your handling code here:
        resetselectcolor(btnSave, fontSave);
    }//GEN-LAST:event_btnSaveMouseExited

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            sensordata();
        }
    };

    public void showDataTable() {
        timer.scheduleAtFixedRate(task, 5000, 5000);

        //System.err.println("dd" + parkedTableName);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SensorStatusUpdater.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SensorStatusUpdater.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SensorStatusUpdater.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SensorStatusUpdater.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SensorStatusUpdater().setVisible(true);
            }
        });
    }

    void setselectcolor(JPanel panel, JLabel label) {
        panel.setBackground(new Color(255, 250, 250));
        label.setForeground(new Color(31, 58, 147));
    }

    void setclickedcolor(JPanel panel, JLabel label) {
        panel.setBackground(new Color(31, 58, 147));
        label.setForeground(Color.white);
    }

    void resetselectcolor(JPanel panel, JLabel label) {
        panel.setBackground(Color.WHITE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnSave;
    private javax.swing.JComboBox<String> comboCOMPORT;
    private javax.swing.JLabel fontSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    // End of variables declaration//GEN-END:variables
}
