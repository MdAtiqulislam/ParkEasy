/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkeasy;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Atiq
 */
public class Home extends javax.swing.JPanel {

    /**
     * Creates new form Home
     */
    //String parkedTableName, new_date;
    SimpleDateFormat dateformater, timeformater;
    Date date;
    private static Connection connection = null;

    private String u_name, sensorTableName, parkedTableName;
    private int u_id;
    ArrayList<String> list = new ArrayList();
    private String dateAvail = "n";
    int cashReceive = 0;

    public Home() {
        initComponents();
        dateformater = new SimpleDateFormat("dd/MM/yyyy");
        timeformater = new SimpleDateFormat("HH:mm");
        date = new Date();

    }

    public Home(int id, String username) {
        initComponents();
        this.u_id = id;
        this.u_name = username;
        sensorTableName = u_name + "_sensor_status";
        parkedTableName = u_name + "_parked_status";
        connection = DbConnect.dbconnect();
        this.setVisible(true);
        showDataTable();

    }

    public void Home(String parkedTBname, String name, String userName, String phone, String email, String address, String vehicalNo, String vehicalType, String fee,
            String paymentType, String position, String paymentStatus, int value) {

        //new activeLogTable;
        //initComponents();
        String parkedTableName = parkedTBname, Name = name, UserName = userName, Phone = phone, Email = email, Address = address, VehicalNo = vehicalNo, VehicalType = vehicalType,
                Fee = fee, PaymentType = paymentType, Position = position, PaymentStatus = paymentStatus;
        int Value = value;
        int paid = 0, due = 0;
        if (paymentStatus.equals("Paid")) {
            paid = Integer.parseInt(Fee);
            due = 0;
        } else {
            paid = 0;
            due = Integer.parseInt(Fee);
        }

        if (Value == 1 && !Position.equals("No park is available")) {
            dateformater = new SimpleDateFormat("dd/MM/yyyy");
            timeformater = new SimpleDateFormat("HH:mm");
            date = new Date();

            try {
                String sql_1 = "insert into " + parkedTableName + ""
                        + "(name,user_name,phone,email,address,vehical_type,vehical_no,"
                        + "position,fee,paid,due,payment,payment_status,"
                        + "date,time,entry_time,status)"
                        + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement statement2 = connection.prepareStatement(sql_1);

                statement2.setString(1, Name);
                statement2.setString(2, UserName);
                statement2.setString(3, Phone);
                statement2.setString(4, Email);
                statement2.setString(5, Address);
                statement2.setString(6, VehicalType);
                statement2.setString(7, VehicalNo);
                statement2.setString(8, Position);
                statement2.setString(9, Fee);
                statement2.setInt(10, paid);
                statement2.setInt(11, due);
                statement2.setString(12, PaymentType);
                statement2.setString(13, PaymentStatus);
                statement2.setString(14, dateformater.format(date));
                statement2.setString(15, timeformater.format(date));
                statement2.setString(16, timeformater.format(date) + " " + dateformater.format(date));
                statement2.setInt(17, 1);

                statement2.executeUpdate();
                statement2.close();
                JOptionPane.showMessageDialog(null, "New entry successful");
                //showMessageLabel.setText("");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }

            if (paymentStatus.equals("Paid")) {
                try {
                    String sql = "select * from " + u_name + "_payment_status";
                    PreparedStatement st = connection.prepareStatement(sql);
                    ResultSet rs = st.executeQuery();
                    while (rs.next()) {
                        list.add(rs.getString("date"));
                    }
                } catch (Exception e) {
                }
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equals(dateformater.format(date))) {
                        dateAvail = "y";
                    }
                }

                if (dateAvail.equals("y")) {

                    try {
                        String sql = "select * from " + u_name + "_payment_status where date='" + dateformater.format(date) + "' ";
                        PreparedStatement st = connection.prepareStatement(sql);
                        ResultSet rs = st.executeQuery();
                        if (rs.next()) {

                            cashReceive = Integer.parseInt(Fee) + rs.getInt("cash_received");

                            try {
                                String sql_2 = "update " + u_name + "_payment_status set cash_received = '" + cashReceive + "' where date='" + dateformater.format(date) + "'";
                                PreparedStatement statement = connection.prepareStatement(sql_2);
                                statement.executeUpdate();

                            } catch (Exception e) {
                            }

                        }
                    } catch (Exception e) {
                    }
                    dateAvail = "n";

                } else if (dateAvail.equals("n")) {
                    try {
                        String sql_1 = "insert into " + u_name + "_payment_status"
                                + "(cash_received,due_received,date)"
                                + " values(?,?,?)";
                        PreparedStatement statement2 = connection.prepareStatement(sql_1);

                        statement2.setInt(1, Integer.parseInt(Fee));
                        statement2.setInt(2, 0);
                        statement2.setString(3, dateformater.format(date));
                        statement2.executeUpdate();
                    } catch (Exception e) {
                    }

                }
            }

        } else if (Value == 1 && Position.equals("No park is available")) {
            JOptionPane.showMessageDialog(null, "There is no space to park!");
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
        activeLogLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        activeLogTable = new javax.swing.JTable();
        btnCancel = new javax.swing.JPanel();
        fontCancel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(700, 470));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(700, 470));

        activeLogLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        activeLogLabel.setForeground(new java.awt.Color(0, 102, 0));
        activeLogLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        activeLogLabel.setText("Active Log");

        activeLogTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        activeLogTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        activeLogTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                activeLogTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(activeLogTable);

        btnCancel.setBackground(new java.awt.Color(255, 255, 255));
        btnCancel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.setPreferredSize(new java.awt.Dimension(150, 24));
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelMouseExited(evt);
            }
        });

        fontCancel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fontCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Remove_Property_20px_1.png"))); // NOI18N
        fontCancel.setText("Release");
        fontCancel.setIconTextGap(8);

        javax.swing.GroupLayout btnCancelLayout = new javax.swing.GroupLayout(btnCancel);
        btnCancel.setLayout(btnCancelLayout);
        btnCancelLayout.setHorizontalGroup(
            btnCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnCancelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fontCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnCancelLayout.setVerticalGroup(
            btnCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fontCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addComponent(activeLogLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(activeLogLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void activeLogTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_activeLogTableMouseClicked
        // TODO add your handling code here:
        //MainFraim ob = new MainFraim();
        //ob.nameTF.setText("yes");
        //System.err.println("yes");
        int row = activeLogTable.getSelectedRow();
        String taleClicked = (activeLogTable.getModel().getValueAt(row, 1).toString());
        // System.err.println(taleClicked);
        //activeLogLabel.setText(taleClicked);
        //ob.gettext(taleClicked);

        /*try {
            String sql = "select * from " + parkedTableName + " where vehical_no='" + taleClicked + "' AND status='" + 1 + "'";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                ob.settableData(rs.getString("name"), rs.getString("phone"), rs.getString("email"),
                    rs.getString("address"), rs.getString("vehical_no"), rs.getString("vehical_type"),
                    rs.getString("position"), rs.getString("time"), rs.getInt("fee"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainFraim.class.getName()).log(Level.SEVERE, null, ex);
        }*/

    }//GEN-LAST:event_activeLogTableMouseClicked

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        // TODO add your handling code here:
        SimpleDateFormat time = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        SimpleDateFormat dateformate = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String exit_time = time.format(date);
        String dateLabel = dateformate.format(date);
        setclickedcolor(btnCancel, fontCancel);
        ReleseForm releseForm = new ReleseForm();

        int row = activeLogTable.getSelectedRow();

        if (row >= 0) {
            String tableClicked = (activeLogTable.getModel().getValueAt(row, 1).toString());

            try {
                String sql = "select * from park_owner where user_id='" + u_id + "' ";
                PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    releseForm.titleLabel.setText(rs.getString("park_name"));
                }
            } catch (Exception e) {

            }

            try {
                String sql = "select * from " + parkedTableName + " where vehical_no='"
                        + tableClicked + "' AND status='" + 1 + "'";
                PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery();

                if (rs.next()) {

                    releseForm.nameTF.setText(rs.getString("name"));
                    releseForm.phoneTF.setText(rs.getString("phone"));
                    releseForm.emailTF.setText(rs.getString("email"));
                    releseForm.addressTF.setText(rs.getString("address"));
                    releseForm.vehicalNoTF.setText(rs.getString("vehical_no"));
                    releseForm.vehicalTypeTF.setText(rs.getString("vehical_type"));
                    releseForm.positionTF.setText(rs.getString("position"));
                    releseForm.entryTimeTF.setText(rs.getString("entry_time"));
                    releseForm.exitTimeTF.setText(exit_time);
                    releseForm.feeTF.setText(String.valueOf(rs.getInt("fee")));
                    releseForm.extraFeeTF.setText(String.valueOf(rs.getInt("extra_fee")));

                    releseForm.paidTF.setText(String.valueOf(rs.getInt("paid")));
                    releseForm.dueTF.setText(String.valueOf(rs.getInt("due")));
                    releseForm.paymentStatusTF.setText("Paid");
                    releseForm.dateLabel.setText("Current Date:" + dateLabel);
                    releseForm.toPayTF.setText("0");
                    releseForm.parkedTableName = parkedTableName;

                    int rs_paid = rs.getInt("paid"), rs_due = rs.getInt("due"), rs_fee = rs.getInt("fee"), rs_extra = rs.getInt("extra_fee");

                    releseForm.due = rs_due;
                    releseForm.paid = rs_paid;
                    releseForm.extra = rs_extra;
                    releseForm.fee = rs_fee;
                    int total = rs_extra + rs_fee;
                    releseForm.totalTF.setText(String.valueOf(total));
                    releseForm.u_id = u_id;
                    releseForm.u_name = u_name;
                    releseForm.toPayTF.requestFocus();
                    releseForm.setVisible(true);
                }

            } catch (SQLException ex) {
                Logger.getLogger(MainFraim.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to release car.");
        }

    }//GEN-LAST:event_btnCancelMouseClicked

    private void btnCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseEntered
        // TODO add your handling code here:
        setselectcolor(btnCancel, fontCancel);
    }//GEN-LAST:event_btnCancelMouseEntered

    private void btnCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseExited
        // TODO add your handling code here:
        resetselectcolor(btnCancel, fontCancel);
    }//GEN-LAST:event_btnCancelMouseExited

    public void showDataTable() {
        try {
            String sql_1 = "select name,vehical_no,position,time,date from " + parkedTableName + " where status='" + 1 + "'";
            PreparedStatement st = connection.prepareStatement(sql_1);
            ResultSet rs_1 = st.executeQuery();
            activeLogTable.setModel(DbUtils.resultSetToTableModel(rs_1));

        } catch (SQLException ex) {
            Logger.getLogger(MainFraim.class.getName()).log(Level.SEVERE, null, ex);
        }

        //System.err.println("dd" + parkedTableName);
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
        label.setForeground(Color.BLACK);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel activeLogLabel;
    private javax.swing.JTable activeLogTable;
    private javax.swing.JPanel btnCancel;
    private javax.swing.JLabel fontCancel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
