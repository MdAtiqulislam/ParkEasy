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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static jdk.nashorn.internal.objects.NativeError.printStackTrace;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Atiq
 */
public class Report extends javax.swing.JPanel {

    /**
     * Creates new form Report
     */
    private static Connection connection = null;
    int u_id;
    String u_name, parkedTableName;
   // private 

    public Report() {
        initComponents();
    }

    public Report(int id, String userName) {
        this.u_id = id;
        this.u_name = userName;
        parkedTableName = u_name + "_parked_status";
        initComponents();
        connection = DbConnect.dbconnect();
        dailyReportShowDataTable();
        monthlyReportShowDataTable();
        yearlyReportShowDataTable();
        

    }
    
    

    public void dailyReportShowDataTable() {
        int paid_sum = 0;
        int totalDue=0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String currentDate = dateFormat.format(date);


        //Date date_2 = new Date();
        currentDateTF.setText(currentDate);

        try {
            String sql_1 = "select name,vehical_no,entry_time,exit_time,fee,extra_fee,paid,due from "
                    + parkedTableName + " where date='" + currentDate + "'OR status='" + 1 + "'OR "
                    + "exit_time LIKE '%"+currentDate+"'";
            PreparedStatement st = connection.prepareStatement(sql_1);
            ResultSet rs_1 = st.executeQuery();
            dailyReportTable.setModel(DbUtils.resultSetToTableModel(rs_1));

            String sql_2 = "select * from " + u_name + "_payment_status where date='" + currentDate + "'";
            PreparedStatement st_2 = connection.prepareStatement(sql_2);
            ResultSet rs_2 = st_2.executeQuery();
            if (rs_2.next()) {
                paid_sum =rs_2.getInt("cash_received")+rs_2.getInt("due_received");
                //due_sum += rs_2.getInt("due");
            }

            paidTF.setText(String.valueOf(paid_sum));

        } catch (SQLException ex) {
            Logger.getLogger(MainFraim.class.getName()).log(Level.SEVERE, null, ex);
        }
                try {
             String sql = "select * from "
                    + parkedTableName + " ";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                totalDue =totalDue+rs.getInt("due");
                
            }
            dueTF.setText(String.valueOf(totalDue));
            
        } catch (Exception e) {
        }
        totalDue=0;
       // dueView();

    }

    public void monthlyReportShowDataTable() {
        int paid_sum = 0;
        int due_sum = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
        Date date = new Date();
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM/yyyy");
        String searchMonth = dateFormat2.format(date);
        String currentMonth = dateFormat.format(date);
        currentMonthTF.setText(currentMonth);

        try {
            String sql_1 = "select name,vehical_no,entry_time,exit_time,fee,extra_fee,paid,due from "
                    + parkedTableName + " where date LIKE '%" + searchMonth + "' ";
            PreparedStatement st = connection.prepareStatement(sql_1);
            ResultSet rs_1 = st.executeQuery();
            monthlyReportTable.setModel(DbUtils.resultSetToTableModel(rs_1));

            String sql_2 = "select * from " + parkedTableName + "  ";
            PreparedStatement st_2 = connection.prepareStatement(sql_2);
            ResultSet rs_2 = st_2.executeQuery();
            while (rs_2.next()) {
                paid_sum = paid_sum + rs_2.getInt("paid");
                due_sum += rs_2.getInt("due");
            }

            monthPaidTF.setText(String.valueOf(paid_sum));
            dueTF1.setText(String.valueOf(due_sum));
            
        } catch (Exception e) {
        }

        paid_sum=0;
        due_sum=0;
    }

    public void yearlyReportShowDataTable() {
        int paid_sum = 0;
        int due_sum = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        //SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM/yyyy");
        //String searchMonth = dateFormat2.format(date);
        //String searchYear = String.valueOf(yearChooser.getYear());

        String currentYear = dateFormat.format(date);
        currentDateTF2.setText(currentYear);

        try {
            String sql_1 = "select name,vehical_no,entry_time,exit_time,fee,extra_fee,paid,due from "
                    + parkedTableName + " where date LIKE '%" + currentYear + "' ";
            PreparedStatement st = connection.prepareStatement(sql_1);
            ResultSet rs_1 = st.executeQuery();
            yearlyReportShowTable.setModel(DbUtils.resultSetToTableModel(rs_1));

            String sql_2 = "select * from " + parkedTableName + "  ";
            PreparedStatement st_2 = connection.prepareStatement(sql_2);
            ResultSet rs_2 = st_2.executeQuery();
            while (rs_2.next()) {
                paid_sum = paid_sum + rs_2.getInt("paid");
                due_sum += rs_2.getInt("due");
            }

            paidYearTF.setText(String.valueOf(paid_sum));
            dueYearTF.setText(String.valueOf(due_sum));
        } catch (Exception e) {
        }
        paid_sum=0;
        due_sum=0;
    }
    //System.err.println("dd" + parkedTableName);

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        dailyReportPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dailyReportTable = new javax.swing.JTable();
        btnPrint = new javax.swing.JPanel();
        fontPrint = new javax.swing.JLabel();
        currentDateTF = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        dateChoser = new com.toedter.calendar.JDateChooser();
        btnDailyReportSearch = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        dueTF = new javax.swing.JTextField();
        paidTF = new javax.swing.JTextField();
        btnViewHistory = new javax.swing.JPanel();
        fontCancel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnDailyReportSearch2 = new javax.swing.JLabel();
        searchLabel = new javax.swing.JTextField();
        monthlyReportPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        monthlyReportTable = new javax.swing.JTable();
        currentMonthTF = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnMonthSearch = new javax.swing.JLabel();
        monthChooser = new com.toedter.calendar.JMonthChooser();
        yearChooser = new com.toedter.calendar.JYearChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        monthPaidTF = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        dueTF1 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        btnsearchByNumber = new javax.swing.JLabel();
        searchLabelMonth = new javax.swing.JTextField();
        btnViewHistory1 = new javax.swing.JPanel();
        fontCancel1 = new javax.swing.JLabel();
        btnPrint3 = new javax.swing.JPanel();
        fontPrint3 = new javax.swing.JLabel();
        yearlyReportPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        yearlyReportShowTable = new javax.swing.JTable();
        btnPrint2 = new javax.swing.JPanel();
        fontPrint2 = new javax.swing.JLabel();
        currentDateTF2 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnYearlyReportSearch = new javax.swing.JLabel();
        jYearChooser2 = new com.toedter.calendar.JYearChooser();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        paidYearTF = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        dueYearTF = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        btnSearchByNumber = new javax.swing.JLabel();
        searchLabelYear = new javax.swing.JTextField();
        btnViewHistory2 = new javax.swing.JPanel();
        fontCancel2 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(710, 470));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(710, 470));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(710, 470));

        dailyReportPanel.setBackground(new java.awt.Color(255, 255, 255));
        dailyReportPanel.setPreferredSize(new java.awt.Dimension(695, 452));

        dailyReportTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dailyReportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.N", "Name", "Car Number", "Entry Time", "Exit Time", "Payment "
            }
        ));
        jScrollPane1.setViewportView(dailyReportTable);

        btnPrint.setBackground(new java.awt.Color(255, 255, 255));
        btnPrint.setToolTipText("Print");
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrintMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPrintMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPrintMouseExited(evt);
            }
        });

        fontPrint.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fontPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Print_30px_1.png"))); // NOI18N

        javax.swing.GroupLayout btnPrintLayout = new javax.swing.GroupLayout(btnPrint);
        btnPrint.setLayout(btnPrintLayout);
        btnPrintLayout.setHorizontalGroup(
            btnPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPrintLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fontPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnPrintLayout.setVerticalGroup(
            btnPrintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fontPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 29, Short.MAX_VALUE)
        );

        currentDateTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        currentDateTF.setPreferredSize(new java.awt.Dimension(6, 25));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(272, 41));

        dateChoser.setDateFormatString("dd/MM/yyyy");
        dateChoser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnDailyReportSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Search_20px_1.png"))); // NOI18N
        btnDailyReportSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnDailyReportSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDailyReportSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDailyReportSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDailyReportSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(dateChoser, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDailyReportSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(dateChoser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 0));
        jLabel4.setText("Selected Date");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 0));
        jLabel5.setText("Select date to view report");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Total cash resived (Tk):");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Total due (Tk):");

        dueTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        paidTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnViewHistory.setBackground(new java.awt.Color(255, 255, 255));
        btnViewHistory.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnViewHistory.setForeground(new java.awt.Color(255, 255, 255));
        btnViewHistory.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewHistory.setPreferredSize(new java.awt.Dimension(150, 26));
        btnViewHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnViewHistoryMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnViewHistoryMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnViewHistoryMouseExited(evt);
            }
        });

        fontCancel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fontCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Activity_History_20px.png"))); // NOI18N
        fontCancel.setText("View History");
        fontCancel.setIconTextGap(8);

        javax.swing.GroupLayout btnViewHistoryLayout = new javax.swing.GroupLayout(btnViewHistory);
        btnViewHistory.setLayout(btnViewHistoryLayout);
        btnViewHistoryLayout.setHorizontalGroup(
            btnViewHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnViewHistoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fontCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnViewHistoryLayout.setVerticalGroup(
            btnViewHistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fontCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setPreferredSize(new java.awt.Dimension(272, 41));

        btnDailyReportSearch2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Search_20px_1.png"))); // NOI18N
        btnDailyReportSearch2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDailyReportSearch2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDailyReportSearch2MouseClicked(evt);
            }
        });

        searchLabel.setBorder(null);
        searchLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchLabelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(btnDailyReportSearch2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(btnDailyReportSearch2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout dailyReportPanelLayout = new javax.swing.GroupLayout(dailyReportPanel);
        dailyReportPanel.setLayout(dailyReportPanelLayout);
        dailyReportPanelLayout.setHorizontalGroup(
            dailyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dailyReportPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(dailyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(dailyReportPanelLayout.createSequentialGroup()
                        .addGroup(dailyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(currentDateTF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(dailyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnViewHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addGroup(dailyReportPanelLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(paidTF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(dueTF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(2, 2, 2))
        );
        dailyReportPanelLayout.setVerticalGroup(
            dailyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dailyReportPanelLayout.createSequentialGroup()
                .addGroup(dailyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dailyReportPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(dailyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                            .addGroup(dailyReportPanelLayout.createSequentialGroup()
                                .addGroup(dailyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(btnViewHistory, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(dailyReportPanelLayout.createSequentialGroup()
                        .addGroup(dailyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dailyReportPanelLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(currentDateTF, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(dailyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(dueTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paidTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Daily Report", dailyReportPanel);

        monthlyReportPanel.setBackground(new java.awt.Color(255, 255, 255));

        monthlyReportTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        monthlyReportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Total car parked", "Cash payment", "Others payment", "Total cash resived"
            }
        ));
        jScrollPane2.setViewportView(monthlyReportTable);

        currentMonthTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(272, 25));

        btnMonthSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Search_20px_1.png"))); // NOI18N
        btnMonthSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnMonthSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMonthSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMonthSearchMouseClicked(evt);
            }
        });

        monthChooser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        yearChooser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnMonthSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(yearChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(monthChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnMonthSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(yearChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(monthChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 0));
        jLabel6.setText("Selected month");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 0));
        jLabel7.setText("Select month to view report");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Total cash resived (Tk):");

        monthPaidTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Total due (Tk):");

        dueTF1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setPreferredSize(new java.awt.Dimension(272, 41));

        btnsearchByNumber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Search_20px_1.png"))); // NOI18N
        btnsearchByNumber.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsearchByNumber.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnsearchByNumberMouseClicked(evt);
            }
        });

        searchLabelMonth.setBorder(null);
        searchLabelMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchLabelMonthActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(btnsearchByNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchLabelMonth, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchLabelMonth, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(btnsearchByNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        btnViewHistory1.setBackground(new java.awt.Color(255, 255, 255));
        btnViewHistory1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnViewHistory1.setForeground(new java.awt.Color(255, 255, 255));
        btnViewHistory1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewHistory1.setPreferredSize(new java.awt.Dimension(150, 26));
        btnViewHistory1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnViewHistory1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnViewHistory1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnViewHistory1MouseExited(evt);
            }
        });

        fontCancel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fontCancel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Activity_History_20px.png"))); // NOI18N
        fontCancel1.setText("View History");
        fontCancel1.setIconTextGap(8);

        javax.swing.GroupLayout btnViewHistory1Layout = new javax.swing.GroupLayout(btnViewHistory1);
        btnViewHistory1.setLayout(btnViewHistory1Layout);
        btnViewHistory1Layout.setHorizontalGroup(
            btnViewHistory1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnViewHistory1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fontCancel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnViewHistory1Layout.setVerticalGroup(
            btnViewHistory1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fontCancel1, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        btnPrint3.setBackground(new java.awt.Color(255, 255, 255));
        btnPrint3.setToolTipText("Print");
        btnPrint3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrint3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPrint3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPrint3MouseExited(evt);
            }
        });

        fontPrint3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fontPrint3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Print_30px_1.png"))); // NOI18N

        javax.swing.GroupLayout btnPrint3Layout = new javax.swing.GroupLayout(btnPrint3);
        btnPrint3.setLayout(btnPrint3Layout);
        btnPrint3Layout.setHorizontalGroup(
            btnPrint3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPrint3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fontPrint3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2))
        );
        btnPrint3Layout.setVerticalGroup(
            btnPrint3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fontPrint3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout monthlyReportPanelLayout = new javax.swing.GroupLayout(monthlyReportPanel);
        monthlyReportPanel.setLayout(monthlyReportPanelLayout);
        monthlyReportPanelLayout.setHorizontalGroup(
            monthlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(monthlyReportPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(monthlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(monthlyReportPanelLayout.createSequentialGroup()
                        .addGroup(monthlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(monthlyReportPanelLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(monthPaidTF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(dueTF1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(monthlyReportPanelLayout.createSequentialGroup()
                                .addGroup(monthlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(currentMonthTF)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(monthlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(monthlyReportPanelLayout.createSequentialGroup()
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnViewHistory1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPrint3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addGap(2, 2, 2))
        );
        monthlyReportPanelLayout.setVerticalGroup(
            monthlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(monthlyReportPanelLayout.createSequentialGroup()
                .addGroup(monthlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(monthlyReportPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                    .addGroup(monthlyReportPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(monthlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btnViewHistory1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(monthlyReportPanelLayout.createSequentialGroup()
                        .addGroup(monthlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(monthlyReportPanelLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(currentMonthTF, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnPrint3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(monthlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(dueTF1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthPaidTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Monthly Report", monthlyReportPanel);

        yearlyReportPanel.setBackground(new java.awt.Color(255, 255, 255));

        yearlyReportShowTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        yearlyReportShowTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Month", "Total car parked", "Cash payment", "Others payment", "Total cash", "Total from others", "Balance"
            }
        ));
        jScrollPane3.setViewportView(yearlyReportShowTable);

        btnPrint2.setBackground(new java.awt.Color(255, 255, 255));
        btnPrint2.setToolTipText("Print");
        btnPrint2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrint2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPrint2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPrint2MouseExited(evt);
            }
        });

        fontPrint2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fontPrint2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Print_30px_1.png"))); // NOI18N

        javax.swing.GroupLayout btnPrint2Layout = new javax.swing.GroupLayout(btnPrint2);
        btnPrint2.setLayout(btnPrint2Layout);
        btnPrint2Layout.setHorizontalGroup(
            btnPrint2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnPrint2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fontPrint2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnPrint2Layout.setVerticalGroup(
            btnPrint2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fontPrint2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        currentDateTF2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(272, 25));

        btnYearlyReportSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Search_20px_1.png"))); // NOI18N
        btnYearlyReportSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnYearlyReportSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnYearlyReportSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnYearlyReportSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnYearlyReportSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jYearChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnYearlyReportSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jYearChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 0));
        jLabel8.setText("Selected month");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 0));
        jLabel9.setText("Select year to view report");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Total cash resived (Tk):");

        paidYearTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Total due (Tk):");

        dueYearTF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel9.setPreferredSize(new java.awt.Dimension(272, 41));

        btnSearchByNumber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Search_20px_1.png"))); // NOI18N
        btnSearchByNumber.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchByNumber.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchByNumberMouseClicked(evt);
            }
        });

        searchLabelYear.setBorder(null);
        searchLabelYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchLabelYearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(btnSearchByNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchLabelYear, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchLabelYear, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(btnSearchByNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        btnViewHistory2.setBackground(new java.awt.Color(255, 255, 255));
        btnViewHistory2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnViewHistory2.setForeground(new java.awt.Color(255, 255, 255));
        btnViewHistory2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnViewHistory2.setPreferredSize(new java.awt.Dimension(150, 26));
        btnViewHistory2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnViewHistory2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnViewHistory2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnViewHistory2MouseExited(evt);
            }
        });

        fontCancel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fontCancel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Activity_History_20px.png"))); // NOI18N
        fontCancel2.setText("View History");
        fontCancel2.setIconTextGap(8);

        javax.swing.GroupLayout btnViewHistory2Layout = new javax.swing.GroupLayout(btnViewHistory2);
        btnViewHistory2.setLayout(btnViewHistory2Layout);
        btnViewHistory2Layout.setHorizontalGroup(
            btnViewHistory2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnViewHistory2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fontCancel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnViewHistory2Layout.setVerticalGroup(
            btnViewHistory2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fontCancel2, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout yearlyReportPanelLayout = new javax.swing.GroupLayout(yearlyReportPanel);
        yearlyReportPanel.setLayout(yearlyReportPanelLayout);
        yearlyReportPanelLayout.setHorizontalGroup(
            yearlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(yearlyReportPanelLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(yearlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(yearlyReportPanelLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(paidYearTF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(dueYearTF, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, yearlyReportPanelLayout.createSequentialGroup()
                        .addGroup(yearlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(yearlyReportPanelLayout.createSequentialGroup()
                                .addGroup(yearlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(currentDateTF2)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(yearlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(yearlyReportPanelLayout.createSequentialGroup()
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnViewHistory2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                                .addComponent(btnPrint2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3))
                        .addGap(2, 2, 2))))
        );
        yearlyReportPanelLayout.setVerticalGroup(
            yearlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(yearlyReportPanelLayout.createSequentialGroup()
                .addGroup(yearlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(yearlyReportPanelLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(yearlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                            .addGroup(yearlyReportPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(yearlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(btnViewHistory2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(yearlyReportPanelLayout.createSequentialGroup()
                        .addGroup(yearlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(yearlyReportPanelLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(currentDateTF2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnPrint2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(yearlyReportPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(dueYearTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(paidYearTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Yearly Report", yearlyReportPanel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseClicked
        // TODO add your handling code here:
        setclickedcolor(btnPrint, fontPrint);
    }//GEN-LAST:event_btnPrintMouseClicked

    private void btnPrintMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseEntered
        // TODO add your handling code here:
        setselectcolor(btnPrint, fontPrint);
    }//GEN-LAST:event_btnPrintMouseEntered

    private void btnPrintMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseExited
        // TODO add your handling code here:
        resetselectcolor(btnPrint, fontPrint);
    }//GEN-LAST:event_btnPrintMouseExited

    private void btnPrint2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrint2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrint2MouseClicked

    private void btnPrint2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrint2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrint2MouseEntered

    private void btnPrint2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrint2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrint2MouseExited

    private void btnDailyReportSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDailyReportSearchMouseClicked
        // TODO add your handling code here:

        int paid_sum = 0, due_sum = 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateChoser.getDate();

        if (date != null) {
            String currentDate = dateFormat.format(date);

            currentDateTF.setText(currentDate);

            try {
                String sql_1 = "select name,vehical_no,entry_time,exit_time,fee,extra_fee,paid,due from "
                        + parkedTableName + " where date='" + currentDate + "'";
                PreparedStatement st = connection.prepareStatement(sql_1);
                ResultSet rs_1 = st.executeQuery();
                dailyReportTable.setModel(DbUtils.resultSetToTableModel(rs_1));

                String sql_2 = "select * from " + parkedTableName + " where date='" + currentDate + "'";
                PreparedStatement st_2 = connection.prepareStatement(sql_2);
                ResultSet rs_2 = st_2.executeQuery();
                while (rs_2.next()) {
                    paid_sum = paid_sum + rs_2.getInt("paid");
                    due_sum += rs_2.getInt("due");
                }

                paidTF.setText(String.valueOf(paid_sum));
                dueTF.setText(String.valueOf(due_sum));

            } catch (SQLException ex) {
                Logger.getLogger(MainFraim.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select date to view report.");
        }

    }//GEN-LAST:event_btnDailyReportSearchMouseClicked

    private void btnMonthSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMonthSearchMouseClicked
        // TODO add your handling code here:
        String monthInWord = "";
        int paid_sum = 0, due_sum = 0;
        int month = monthChooser.getMonth();
        int year = yearChooser.getYear();

        String searchMonth = "";

        if (month == 0) {
            monthInWord = "January";
            searchMonth = "01/" + String.valueOf(year);
        } else if (month == 1) {
            monthInWord = "Februaty";
            searchMonth = "02/" + String.valueOf(year);
        } else if (month == 2) {
            monthInWord = "March";
            searchMonth = "03/" + String.valueOf(year);
        } else if (month == 3) {
            monthInWord = "Appril";
            searchMonth = "04/" + String.valueOf(year);
        } else if (month == 4) {
            monthInWord = "May";
            searchMonth = "05/" + String.valueOf(year);
        } else if (month == 5) {
            monthInWord = "June";
            searchMonth = "06/" + String.valueOf(year);
        } else if (month == 6) {
            monthInWord = "July";
            searchMonth = "07/" + String.valueOf(year);
        } else if (month == 7) {
            monthInWord = "August";
            searchMonth = "08/" + String.valueOf(year);
        } else if (month == 8) {
            monthInWord = "September";
            searchMonth = "09/" + String.valueOf(year);
        } else if (month == 9) {
            monthInWord = "October";
            searchMonth = "10/" + String.valueOf(year);
        } else if (month == 10) {
            monthInWord = "November";
            searchMonth = "11/" + String.valueOf(year);
        } else if (month == 11) {
            monthInWord = "December";
            searchMonth = "12/" + String.valueOf(year);
        }
        currentMonthTF.setText(monthInWord + " " + String.valueOf(year));

        try {
            String sql_1 = "select name,vehical_no,entry_time,exit_time,fee,extra_fee,paid,due from "
                    + parkedTableName + " where date LIKE '%" + searchMonth + "' ";
            PreparedStatement st = connection.prepareStatement(sql_1);
            ResultSet rs_1 = st.executeQuery();
            monthlyReportTable.setModel(DbUtils.resultSetToTableModel(rs_1));

            String sql_2 = "select * from " + parkedTableName + " where date LIKE '%" + searchMonth + "' ";
            PreparedStatement st_2 = connection.prepareStatement(sql_2);
            ResultSet rs_2 = st_2.executeQuery();
            while (rs_2.next()) {
                paid_sum = paid_sum + rs_2.getInt("paid");
                due_sum += rs_2.getInt("due");
            }

            monthPaidTF.setText(String.valueOf(paid_sum));
            dueTF1.setText(String.valueOf(due_sum));
        } catch (Exception e) {
        }

    }//GEN-LAST:event_btnMonthSearchMouseClicked

    private void btnYearlyReportSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnYearlyReportSearchMouseClicked
        // TODO add your handling code here:
        int paid_sum = 0;
        int due_sum = 0;
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        //Date date = new Date();
        //SimpleDateFormat dateFormat2 = new SimpleDateFormat("MM/yyyy");
        //String searchMonth = dateFormat2.format(date);
        //String searchYear = String.valueOf(yearChooser.getYear());

        String searchedTear = String.valueOf(yearChooser.getYear());
        currentDateTF2.setText(searchedTear);

        try {
            String sql_1 = "select name,vehical_no,entry_time,exit_time,fee,extra_fee,paid,due from "
                    + parkedTableName + " where date LIKE '%" + searchedTear + "' ";
            PreparedStatement st = connection.prepareStatement(sql_1);
            ResultSet rs_1 = st.executeQuery();
            yearlyReportShowTable.setModel(DbUtils.resultSetToTableModel(rs_1));

            String sql_2 = "select * from " + parkedTableName + " where date LIKE '%" + searchedTear + "' ";
            PreparedStatement st_2 = connection.prepareStatement(sql_2);
            ResultSet rs_2 = st_2.executeQuery();
            while (rs_2.next()) {
                paid_sum = paid_sum + rs_2.getInt("paid");
                due_sum += rs_2.getInt("due");
            }

            paidYearTF.setText(String.valueOf(paid_sum));
            dueYearTF.setText(String.valueOf(due_sum));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btnYearlyReportSearchMouseClicked

    private void btnViewHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewHistoryMouseClicked
        // TODO add your handling code here:
        SimpleDateFormat time = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        SimpleDateFormat dateformate = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String exit_time = time.format(date);
        String dateLabel = dateformate.format(date);
        setclickedcolor(btnViewHistory, fontCancel);
        ReleaseHistory realeseHistory = new ReleaseHistory();

        int row = dailyReportTable.getSelectedRow();

        if (row >= 0) {
            String carNo = (dailyReportTable.getModel().getValueAt(row, 1).toString());
            String entryTime = (dailyReportTable.getModel().getValueAt(row, 2).toString());

            try {
                String sql = "select * from park_owner where user_id='" + u_id + "' ";
                PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    realeseHistory.titleLabel.setText(rs.getString("park_name"));
                }
            } catch (Exception e) {
                printStackTrace(e);

            }

            try {
                String sql = "select * from " + parkedTableName + " where vehical_no='" + carNo + "' AND entry_time='" + entryTime + "'";
                PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery();

                if (rs.next()) {

                    realeseHistory.nameTF.setText(rs.getString("name"));
                    realeseHistory.phoneTF.setText(rs.getString("phone"));
                    realeseHistory.emailTF.setText(rs.getString("email"));
                    realeseHistory.addressTF.setText(rs.getString("address"));
                    realeseHistory.vehicalNoTF.setText(rs.getString("vehical_no"));
                    realeseHistory.vehicalTypeTF.setText(rs.getString("vehical_type"));
                    realeseHistory.positionTF.setText(rs.getString("position"));
                    realeseHistory.entryTimeTF.setText(rs.getString("entry_time"));
                    realeseHistory.exitTimeTF.setText(exit_time);
                    realeseHistory.feeTF.setText(String.valueOf(rs.getInt("fee")));
                    realeseHistory.extraFeeTF.setText(String.valueOf(rs.getInt("extra_fee")));

                    realeseHistory.paidTF.setText(String.valueOf(rs.getInt("paid")));
                    realeseHistory.dueTF.setText(String.valueOf(rs.getInt("due")));
                    realeseHistory.paymentStatusTF.setText("Paid");
                    realeseHistory.dateLabel.setText("Current Date:" + dateLabel);
                    realeseHistory.toPayTF.setText("0");
                    realeseHistory.parkedTableName = parkedTableName;

                    int rs_paid = rs.getInt("paid"), rs_due = rs.getInt("due"), rs_fee = rs.getInt("fee"), rs_extra = rs.getInt("extra_fee");

                    realeseHistory.due = rs_due;
                    realeseHistory.paid = rs_paid;
                    realeseHistory.extra = rs_extra;
                    realeseHistory.fee = rs_fee;
                    int total = rs_extra + rs_fee;
                    realeseHistory.totalTF.setText(String.valueOf(total));
                    realeseHistory.u_id = u_id;
                    realeseHistory.u_name = u_name;

                    realeseHistory.setVisible(true);
                }

            } catch (SQLException ex) {
                Logger.getLogger(MainFraim.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to View History.");
        }

    }//GEN-LAST:event_btnViewHistoryMouseClicked

    private void btnViewHistoryMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewHistoryMouseEntered
        // TODO add your handling code here:
        setselectcolor(btnViewHistory, fontCancel);
    }//GEN-LAST:event_btnViewHistoryMouseEntered

    private void btnViewHistoryMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewHistoryMouseExited
        // TODO add your handling code here:
        resetselectcolor(btnViewHistory, fontCancel);
    }//GEN-LAST:event_btnViewHistoryMouseExited

    private void btnDailyReportSearch2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDailyReportSearch2MouseClicked
        // TODO add your handling code here:
        String key = searchLabel.getText();

        if (!key.isEmpty()) {

            try {
                String sql_1 = "select vehical_no from "
                        + parkedTableName + " where vehical_no='" + key + "'";
                PreparedStatement st = connection.prepareStatement(sql_1);
                ResultSet rs_1 = st.executeQuery();

                if (rs_1.next()) {

                    try {
                        String sql_2 = "select name,vehical_no,entry_time,exit_time,fee,extra_fee,paid,due from "
                                + parkedTableName + " where vehical_no='" + key + "'";
                        PreparedStatement st_2 = connection.prepareStatement(sql_2);
                        ResultSet rs_2 = st_2.executeQuery();

                        dailyReportTable.setModel(DbUtils.resultSetToTableModel(rs_2));
                    } catch (SQLException ex) {
                        Logger.getLogger(MainFraim.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Car number not found.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainFraim.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        else
JOptionPane.showMessageDialog(null, "Please enter car number.");
    }//GEN-LAST:event_btnDailyReportSearch2MouseClicked

    private void searchLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchLabelActionPerformed
        // TODO add your handling code here:
        searchLabel.setText("");
    }//GEN-LAST:event_searchLabelActionPerformed

    private void btnsearchByNumberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsearchByNumberMouseClicked
       String key = searchLabelMonth.getText();

        if (!key.isEmpty()) {

            try {
                String sql_1 = "select vehical_no from "
                        + parkedTableName + " where vehical_no='" + key + "'";
                PreparedStatement st = connection.prepareStatement(sql_1);
                ResultSet rs_1 = st.executeQuery();

                if (rs_1.next()) {

                    try {
                        String sql_2 = "select name,vehical_no,entry_time,exit_time,fee,extra_fee,paid,due from "
                                + parkedTableName + " where vehical_no='" + key + "'";
                        PreparedStatement st_2 = connection.prepareStatement(sql_2);
                        ResultSet rs_2 = st_2.executeQuery();

                        monthlyReportTable.setModel(DbUtils.resultSetToTableModel(rs_2));
                    } catch (SQLException ex) {
                        Logger.getLogger(MainFraim.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Car number not found.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainFraim.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        else
JOptionPane.showMessageDialog(null, "Please enter car number.");
    }//GEN-LAST:event_btnsearchByNumberMouseClicked

    private void searchLabelMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchLabelMonthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchLabelMonthActionPerformed

    private void btnViewHistory1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewHistory1MouseClicked
        // TODO add your handling code here:
        SimpleDateFormat time = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        SimpleDateFormat dateformate = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String exit_time = time.format(date);
        String dateLabel = dateformate.format(date);
        setclickedcolor(btnViewHistory1, fontCancel1);
        ReleaseHistory realeseHistory = new ReleaseHistory();

        int row = monthlyReportTable.getSelectedRow();

        if (row >= 0) {
            String carNo = (monthlyReportTable.getModel().getValueAt(row, 1).toString());
            String entryTime = (monthlyReportTable.getModel().getValueAt(row, 2).toString());

            try {
                String sql = "select * from park_owner where user_id='" + u_id + "' ";
                PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    realeseHistory.titleLabel.setText(rs.getString("park_name"));
                }
            } catch (Exception e) {
                printStackTrace(e);

            }

            try {
                String sql = "select * from " + parkedTableName + " where vehical_no='" + carNo + "' AND entry_time='" + entryTime + "'";
                PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery();

                if (rs.next()) {

                    realeseHistory.nameTF.setText(rs.getString("name"));
                    realeseHistory.phoneTF.setText(rs.getString("phone"));
                    realeseHistory.emailTF.setText(rs.getString("email"));
                    realeseHistory.addressTF.setText(rs.getString("address"));
                    realeseHistory.vehicalNoTF.setText(rs.getString("vehical_no"));
                    realeseHistory.vehicalTypeTF.setText(rs.getString("vehical_type"));
                    realeseHistory.positionTF.setText(rs.getString("position"));
                    realeseHistory.entryTimeTF.setText(rs.getString("entry_time"));
                    realeseHistory.exitTimeTF.setText(exit_time);
                    realeseHistory.feeTF.setText(String.valueOf(rs.getInt("fee")));
                    realeseHistory.extraFeeTF.setText(String.valueOf(rs.getInt("extra_fee")));

                    realeseHistory.paidTF.setText(String.valueOf(rs.getInt("paid")));
                    realeseHistory.dueTF.setText(String.valueOf(rs.getInt("due")));
                    realeseHistory.paymentStatusTF.setText("Paid");
                    realeseHistory.dateLabel.setText("Current Date:" + dateLabel);
                    realeseHistory.toPayTF.setText("0");
                    realeseHistory.parkedTableName = parkedTableName;

                    int rs_paid = rs.getInt("paid"), rs_due = rs.getInt("due"), rs_fee = rs.getInt("fee"), rs_extra = rs.getInt("extra_fee");

                    realeseHistory.due = rs_due;
                    realeseHistory.paid = rs_paid;
                    realeseHistory.extra = rs_extra;
                    realeseHistory.fee = rs_fee;
                    int total = rs_extra + rs_fee;
                    realeseHistory.totalTF.setText(String.valueOf(total));
                    realeseHistory.u_id = u_id;
                    realeseHistory.u_name = u_name;

                    realeseHistory.setVisible(true);
                }

            } catch (SQLException ex) {
                Logger.getLogger(MainFraim.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to View History.");
        }
    }//GEN-LAST:event_btnViewHistory1MouseClicked

    private void btnViewHistory1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewHistory1MouseEntered
        // TODO add your handling code here:
        setselectcolor(btnViewHistory1, fontCancel1);
    }//GEN-LAST:event_btnViewHistory1MouseEntered

    private void btnViewHistory1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewHistory1MouseExited
        // TODO add your handling code here:
        resetselectcolor(btnViewHistory1, fontCancel1);
    }//GEN-LAST:event_btnViewHistory1MouseExited

    private void btnSearchByNumberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchByNumberMouseClicked
        // TODO add your handling code here:
        String key = searchLabelYear.getText();

        if (!key.isEmpty()) {

            try {
                String sql_1 = "select vehical_no from "
                        + parkedTableName + " where vehical_no='" + key + "'";
                PreparedStatement st = connection.prepareStatement(sql_1);
                ResultSet rs_1 = st.executeQuery();

                if (rs_1.next()) {

                    try {
                        String sql_2 = "select name,vehical_no,entry_time,exit_time,fee,extra_fee,paid,due from "
                                + parkedTableName + " where vehical_no='" + key + "'";
                        PreparedStatement st_2 = connection.prepareStatement(sql_2);
                        ResultSet rs_2 = st_2.executeQuery();

                        yearlyReportShowTable.setModel(DbUtils.resultSetToTableModel(rs_2));
                    } catch (SQLException ex) {
                        Logger.getLogger(MainFraim.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Car number not found.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(MainFraim.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        else
JOptionPane.showMessageDialog(null, "Please enter car number.");
    }//GEN-LAST:event_btnSearchByNumberMouseClicked

    private void searchLabelYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchLabelYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchLabelYearActionPerformed

    private void btnViewHistory2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewHistory2MouseClicked
        // TODO add your handling code here:
           SimpleDateFormat time = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        SimpleDateFormat dateformate = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String exit_time = time.format(date);
        String dateLabel = dateformate.format(date);
        setclickedcolor(btnViewHistory2, fontCancel2);
        ReleaseHistory realeseHistory = new ReleaseHistory();

        int row = yearlyReportShowTable.getSelectedRow();

        if (row >= 0) {
            String carNo = (yearlyReportShowTable.getModel().getValueAt(row, 1).toString());
            String entryTime = (yearlyReportShowTable.getModel().getValueAt(row, 2).toString());

            try {
                String sql = "select * from park_owner where user_id='" + u_id + "' ";
                PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    realeseHistory.titleLabel.setText(rs.getString("park_name"));
                }
            } catch (Exception e) {
                printStackTrace(e);

            }

            try {
                String sql = "select * from " + parkedTableName + " where vehical_no='" + carNo + "' AND entry_time='" + entryTime + "'";
                PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery();

                if (rs.next()) {

                    realeseHistory.nameTF.setText(rs.getString("name"));
                    realeseHistory.phoneTF.setText(rs.getString("phone"));
                    realeseHistory.emailTF.setText(rs.getString("email"));
                    realeseHistory.addressTF.setText(rs.getString("address"));
                    realeseHistory.vehicalNoTF.setText(rs.getString("vehical_no"));
                    realeseHistory.vehicalTypeTF.setText(rs.getString("vehical_type"));
                    realeseHistory.positionTF.setText(rs.getString("position"));
                    realeseHistory.entryTimeTF.setText(rs.getString("entry_time"));
                    realeseHistory.exitTimeTF.setText(exit_time);
                    realeseHistory.feeTF.setText(String.valueOf(rs.getInt("fee")));
                    realeseHistory.extraFeeTF.setText(String.valueOf(rs.getInt("extra_fee")));

                    realeseHistory.paidTF.setText(String.valueOf(rs.getInt("paid")));
                    realeseHistory.dueTF.setText(String.valueOf(rs.getInt("due")));
                    realeseHistory.paymentStatusTF.setText("Paid");
                    realeseHistory.dateLabel.setText("Current Date:" + dateLabel);
                    realeseHistory.toPayTF.setText("0");
                    realeseHistory.parkedTableName = parkedTableName;

                    int rs_paid = rs.getInt("paid"), rs_due = rs.getInt("due"), rs_fee = rs.getInt("fee"), rs_extra = rs.getInt("extra_fee");

                    realeseHistory.due = rs_due;
                    realeseHistory.paid = rs_paid;
                    realeseHistory.extra = rs_extra;
                    realeseHistory.fee = rs_fee;
                    int total = rs_extra + rs_fee;
                    realeseHistory.totalTF.setText(String.valueOf(total));
                    realeseHistory.u_id = u_id;
                    realeseHistory.u_name = u_name;

                    realeseHistory.setVisible(true);
                }

            } catch (SQLException ex) {
                Logger.getLogger(MainFraim.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to View History.");
        }
       
    }//GEN-LAST:event_btnViewHistory2MouseClicked

    private void btnViewHistory2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewHistory2MouseEntered
        // TODO add your handling code here:
        setselectcolor(btnViewHistory2, fontCancel2);
    }//GEN-LAST:event_btnViewHistory2MouseEntered

    private void btnViewHistory2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewHistory2MouseExited
        // TODO add your handling code here:
        resetselectcolor(btnViewHistory2, fontCancel2);
    }//GEN-LAST:event_btnViewHistory2MouseExited

    private void btnPrint3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrint3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrint3MouseClicked

    private void btnPrint3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrint3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrint3MouseEntered

    private void btnPrint3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrint3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrint3MouseExited

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
    private javax.swing.JLabel btnDailyReportSearch;
    private javax.swing.JLabel btnDailyReportSearch1;
    private javax.swing.JLabel btnDailyReportSearch2;
    private javax.swing.JLabel btnDailyReportSearch3;
    private javax.swing.JLabel btnMonthSearch;
    private javax.swing.JPanel btnPrint;
    private javax.swing.JPanel btnPrint2;
    private javax.swing.JPanel btnPrint3;
    private javax.swing.JLabel btnSearchByNumber;
    private javax.swing.JPanel btnViewHistory;
    private javax.swing.JPanel btnViewHistory1;
    private javax.swing.JPanel btnViewHistory2;
    private javax.swing.JLabel btnYearlyReportSearch;
    private javax.swing.JLabel btnsearchByNumber;
    private javax.swing.JTextField currentDateTF;
    private javax.swing.JTextField currentDateTF2;
    private javax.swing.JTextField currentMonthTF;
    private javax.swing.JPanel dailyReportPanel;
    private javax.swing.JTable dailyReportTable;
    private com.toedter.calendar.JDateChooser dateChoser;
    private com.toedter.calendar.JDateChooser dateChoser1;
    private javax.swing.JTextField dueTF;
    private javax.swing.JTextField dueTF1;
    private javax.swing.JTextField dueYearTF;
    private javax.swing.JLabel fontCancel;
    private javax.swing.JLabel fontCancel1;
    private javax.swing.JLabel fontCancel2;
    private javax.swing.JLabel fontPrint;
    private javax.swing.JLabel fontPrint2;
    private javax.swing.JLabel fontPrint3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JYearChooser jYearChooser2;
    private com.toedter.calendar.JMonthChooser monthChooser;
    private javax.swing.JTextField monthPaidTF;
    private javax.swing.JPanel monthlyReportPanel;
    private javax.swing.JTable monthlyReportTable;
    private javax.swing.JTextField paidTF;
    private javax.swing.JTextField paidYearTF;
    private javax.swing.JTextField searchLabel;
    private javax.swing.JTextField searchLabel1;
    private javax.swing.JTextField searchLabelMonth;
    private javax.swing.JTextField searchLabelYear;
    private com.toedter.calendar.JYearChooser yearChooser;
    private javax.swing.JPanel yearlyReportPanel;
    private javax.swing.JTable yearlyReportShowTable;
    // End of variables declaration//GEN-END:variables
}
