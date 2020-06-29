package parkeasy;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static jdk.nashorn.internal.objects.NativeError.printStackTrace;

/**
 *
 * @author Atiq
 */
public class MainFraim extends javax.swing.JFrame {

    private static Connection connection = null;
    /**
     * Creates new form MainFraim
     */

    SimpleDateFormat dateformater, timeformater;
    Date date;
    GridBagLayout layout = new GridBagLayout();
    Home home;
    NewEntry newentry;
    ViewProfile viewProfile;
    Report report;
    Notification notification;
    Requests requests;
    AddUser addUser;
    Features features;
    Registration registration;
    LogIn login;
    //NewEntryFromBooking newEntryFromBooking;
    SensorStatusUpdater sensorDataUpdater;
    SetLocation setLocation;
    SetPassword setPassword;

    String garage_name, new_date, new_sensordata, old_sensordata, old_date, sensorTableName,
            s = "n", parked_username;
    int id, fee, extra, due, paid;
    int newp[], oldp[];
    int homebtn = 0, newentrybtn = 0, relesebtn = 0, notificationbtn = 0,
            reportbtn = 0, tutorialbtn = 0, aboutusbtn = 0, featurebtn = 0;
    private String u_name;
    private int u_id;
    private String atiq;
    private String parkedTableName;
    String park_name = "";

    //private String key;
    public MainFraim() {

        initComponents();

    }
    int count = 0;
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        private int count2;
        @Override
        public void run() {

            try {
                String sql_1 = "select * from " + sensorTableName + " where date='" + new_date + "'";
                PreparedStatement st = connection.prepareStatement(sql_1);
                ResultSet rs_1 = st.executeQuery();

                // blockAtable.setModel(DbUtils.resultSetToTableModel(rs_1));
                //blockBtable.setModel(DbUtils.resultSetToTableModel(rs_1));
                if (rs_1.next()) {
                    p1TF.setText(rs_1.getString("p1"));
                    p2TF.setText(rs_1.getString("p2"));
                    p3TF.setText(rs_1.getString("p3"));
                    p4TF.setText(rs_1.getString("p4"));
                    p5TF.setText(rs_1.getString("p5"));
                    p6TF.setText(rs_1.getString("p6"));
                    p7TF.setText(rs_1.getString("p7"));
                    p8TF.setText(rs_1.getString("p8"));
                    p9TF.setText(rs_1.getString("p9"));
                    p10TF.setText(rs_1.getString("p10"));
                    p11TF.setText(rs_1.getString("p11"));
                    p12TF.setText(rs_1.getString("p12"));
                    p13TF.setText(rs_1.getString("p13"));
                    p14TF.setText(rs_1.getString("p14"));

                }

            } catch (SQLException ex) {
                Logger.getLogger(MainFraim.class.getName()).log(Level.SEVERE, null, ex);
            }
//testing            

            count = 0;
            count2 = 0;

            try {
                String sql_1 = "select park_name from park_owner where user_id='" + u_id + "'";
                PreparedStatement st = connection.prepareStatement(sql_1);
                ResultSet rs_1 = st.executeQuery();
                if (rs_1.next()) {
                    park_name = rs_1.getString("park_name");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                String sql_1 = "select notification_type from book_notification"
                        + " where notification_type='New' AND park_name='" + park_name + "'";

                PreparedStatement st = connection.prepareStatement(sql_1);
                ResultSet rs_1 = st.executeQuery();
                while (rs_1.next()) {
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (count == 0) {
                notificationfont.setText("");
            } else {
                notificationfont.setText(String.valueOf(count));

            }
            
             try {
                String sql_1 = "select activity from response_to_urgent_park"
                        + " where activity='allow' AND park_name='" + park_name + "'";

                PreparedStatement st = connection.prepareStatement(sql_1);
                ResultSet rs_1 = st.executeQuery();
                while (rs_1.next()) {
                    count2++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (count2 == 0) {
                font.setText("");
            } else {
                font.setText(String.valueOf(count2));

            }

            approved();
            countDownTime();
            confirmparked();
            deny();
            countParking();

        }
    };

    public void showDataTable() {
        timer.scheduleAtFixedRate(task, 1000, 1000);

        //System.err.println("dd" + parkedTableName);
    }

    public void countParking()
    {
        int i=14;
        try {
             String sql_1 = "select status from " + parkedTableName + " where date='" + new_date + "'";
                    PreparedStatement st = connection.prepareStatement(sql_1);
                    ResultSet rs_1 = st.executeQuery();
                    while(rs_1.next())
                    {
                        if(rs_1.getInt("status")==1)
                        {
                            i--;
                        }
                    }
                    
                    String sql_2 = "select approval_status from book_notification where date='" + new_date + "'";
                    PreparedStatement st1 = connection.prepareStatement(sql_2);
                    ResultSet rs_2 = st1.executeQuery();
                    while(rs_2.next())
                    {
                        if(rs_2.getString("approval_status").equals("booking_confirm"))
                        {
                            i--;
                        }
                    }
                    
                   String sql_3 = "update "+sensorTableName+" set available ='" + i + "'"
                                    + " where date='"+new_date+"' ";
                            PreparedStatement statement = connection.prepareStatement(sql_3);
                            statement.executeUpdate(); 

        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }
    
    
    public void confirmparked() {
        int c = 0;
        String p = "", vehicle_no = "";
        String position = "";
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();

        try {
            String sql = "select * from book_notification where date='" + df.format(d) + "'AND park_name='" + park_name + "'"
                    + "AND approval_status='parked'";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                position = rs.getString("position");

                try {
                    String sql3 = "select * from " + u_name + "_sensor_status where date='" + df.format(d) + "'";
                    PreparedStatement st2 = connection.prepareStatement(sql3);
                    ResultSet rs2 = st2.executeQuery();
                    if (rs2.next()) {
                        c = rs2.getInt("" + position + "");
                        try {
                            String sql2 = "update book_notification set sensor_status ='" + c + "'"
                                    + " where approval_status='parked' AND park_name='" + park_name + "'AND "
                                    + "position='" + position + "'";
                            PreparedStatement statement = connection.prepareStatement(sql2);
                            statement.executeUpdate();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String sql = "select * from book_notification where date='" + df.format(d) + "'AND park_name='" + park_name + "'"
                    + "AND approval_status='confirm' AND status='1'";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                p = rs.getString("position");
                vehicle_no = rs.getString("vehicle_no");
                try {

                    String sql3 = "select * from " + u_name + "_sensor_status where date='" + df.format(d) + "'";
                    PreparedStatement st2 = connection.prepareStatement(sql3);
                    ResultSet rs2 = st2.executeQuery();
                    if (rs2.next()) {

                        if (rs2.getInt("" + p + "") == 1) {
                            try {
                                String sql2 = "update book_notification set approval_status ='parked',status='0' "
                                        + " where approval_status='confirm' AND park_name='" + park_name + "'AND "
                                        + "vehicle_no='" + vehicle_no + "'";
                                PreparedStatement statement = connection.prepareStatement(sql2);
                                statement.executeUpdate();

                                statement.close();
                            } catch (Exception e) {
                            }
                        }

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //test
    public void approved() {
        double compTime = 0;
        String time = "";
        SimpleDateFormat daeformatFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String currentTime = daeformatFormat.format(date);
        String hour = String.valueOf(currentTime.charAt(0)) + String.valueOf(currentTime.charAt(1));
        String min = String.valueOf(currentTime.charAt(3)) + String.valueOf(currentTime.charAt(4));
        String sec = String.valueOf(currentTime.charAt(6)) + String.valueOf(currentTime.charAt(7));
        double currentTimeinSec = Long.parseLong(hour) * 60 * 60 + (Long.parseLong(min) * 60) + (Long.parseLong(sec));

        ArrayList<String> list = new ArrayList();

        try {
            String sql_1 = "select * from book_notification"
                    + " where approval_status= 'pending' AND park_name='" + park_name + "'";

            PreparedStatement st = connection.prepareStatement(sql_1);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("time"));
            }
        } catch (Exception e) {
        }

        for (int i = 0; i < list.size(); i++) {
            time = list.get(i);
            String hour2 = String.valueOf(time.charAt(0)) + String.valueOf(time.charAt(1));
            String min2 = String.valueOf(time.charAt(3)) + String.valueOf(time.charAt(4));
            String sec2 = String.valueOf(time.charAt(6)) + String.valueOf(time.charAt(7));
            compTime = Long.parseLong(hour2) * 60 * 60 + (Long.parseLong(min2) * 60) + (Long.parseLong(sec2));
            double different = ((currentTimeinSec - compTime) / 60);
            if (different >= 1) {
                try {
                    String sql = "update book_notification set approval_status ='approved' "
                            + " where time='" + time + "' AND park_name='" + park_name + "'";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.executeUpdate();

                    statement.close();
                } catch (Exception e) {
                }
            }

        }

    }

    public void countDownTime() {
        SimpleDateFormat daeformatFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String currentTime = daeformatFormat.format(date);
        String hour = String.valueOf(currentTime.charAt(0)) + String.valueOf(currentTime.charAt(1));
        String min = String.valueOf(currentTime.charAt(3)) + String.valueOf(currentTime.charAt(4));
        String sec = String.valueOf(currentTime.charAt(6)) + String.valueOf(currentTime.charAt(7));
        double currentTimeinSec = Long.parseLong(hour) * 60 * 60 + (Long.parseLong(min) * 60) + (Long.parseLong(sec));
        ArrayList<String> list = new ArrayList();

        try {
            String sql_1 = "select * from book_notification"
                    + " where (approval_status= 'approved' OR approval_status= 'booking_confirm')AND park_name='" + park_name + "'";

            PreparedStatement st = connection.prepareStatement(sql_1);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("time"));
            }
        } catch (Exception e) {
        }

        for (int i = 0; i < list.size(); i++) {

            String time = list.get(i);
            String hour2 = String.valueOf(time.charAt(0)) + String.valueOf(time.charAt(1));
            String min2 = String.valueOf(time.charAt(3)) + String.valueOf(time.charAt(4));
            String sec2 = String.valueOf(time.charAt(6)) + String.valueOf(time.charAt(7));
            double compTime = Long.parseLong(hour2) * 60 * 60 + (Long.parseLong(min2) * 60) + (Long.parseLong(sec2));

            if (currentTimeinSec >= compTime + 660) {
                try {
                    String sql = "update book_notification set approval_status ='not_approved',"
                            + "notification_type="
                            + "'',status='0' "
                            + " where time='" + time + "' AND park_name='" + park_name + "'";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.executeUpdate();

                    statement.close();
                } catch (Exception e) {
                }
            }
        }

    }

    
    
    public void deny() {
        SimpleDateFormat daeformatFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String currentTime = daeformatFormat.format(date);
        String hour = String.valueOf(currentTime.charAt(0)) + String.valueOf(currentTime.charAt(1));
        String min = String.valueOf(currentTime.charAt(3)) + String.valueOf(currentTime.charAt(4));
        String sec = String.valueOf(currentTime.charAt(6)) + String.valueOf(currentTime.charAt(7));
        double currentTimeinSec = Long.parseLong(hour) * 60 * 60 + (Long.parseLong(min) * 60) + (Long.parseLong(sec));
        ArrayList<String> list2 = new ArrayList();

        try {
            String sql_1 = "select time from response_to_urgent_park"
                    + " where activity= 'allow'AND park_name='" + park_name + "'";

            PreparedStatement st = connection.prepareStatement(sql_1);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list2.add(rs.getString("time"));
            }
        } catch (Exception e) {
        }

        for (int i = 0; i < list2.size(); i++) {

            String time = list2.get(i);
            String hour2 = String.valueOf(time.charAt(0)) + String.valueOf(time.charAt(1));
            String min2 = String.valueOf(time.charAt(3)) + String.valueOf(time.charAt(4));
            String sec2 = String.valueOf(time.charAt(6)) + String.valueOf(time.charAt(7));
            double compTime = Long.parseLong(hour2) * 60 * 60 + (Long.parseLong(min2) * 60) + (Long.parseLong(sec2));

            if (currentTimeinSec >= compTime + 660) {
                try {
                    String sql = "update response_to_urgent_park set activity ='deny'"                         
                            + " where time='" + time + "' AND park_name='" + park_name + "'";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.executeUpdate();

                    statement.close();
                } catch (Exception e) {
                }
            }
        }

    }
    
    //end test
    public MainFraim(int id, String user_name) {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("parking.png")));
        old_date = "empty";
        newp = new int[14];
        oldp = new int[14];

        this.u_id = id;
        this.u_name = user_name;
        this.pack();
        sensorTableName = u_name + "_sensor_status";
        parkedTableName = u_name + "_parked_status";
        initComponents();

        this.setLocationRelativeTo(this);
        connection = DbConnect.dbconnect();
        title();
        currentDateTime();

        // newEntryFromBooking= new NewEntryFromBooking();
        sensorDataUpdater = new SensorStatusUpdater(u_name);
        home = new Home(u_id, u_name);
        newentry = new NewEntry(u_id, u_name);
        viewProfile = new ViewProfile();
        report = new Report(u_id, u_name);
        notification = new Notification(u_id, u_name);
        requests = new Requests(u_id, u_name);
        addUser = new AddUser();
        features = new Features();

        showDataTable();

        containerFrame.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        containerFrame.add(home, c);
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 0;
        containerFrame.add(newentry, c1);
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 0;
        containerFrame.add(viewProfile, c2);

        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 0;
        c3.gridy = 0;
        containerFrame.add(notification, c3);
        GridBagConstraints c4 = new GridBagConstraints();
        c4.gridx = 0;
        c4.gridy = 0;
        containerFrame.add(requests, c4);
        GridBagConstraints c5 = new GridBagConstraints();
        c5.gridx = 0;
        c5.gridy = 0;
        containerFrame.add(addUser, c5);
        GridBagConstraints c6 = new GridBagConstraints();
        c6.gridx = 0;
        c6.gridy = 0;
        containerFrame.add(features, c6);
        GridBagConstraints c7 = new GridBagConstraints();
        c7.gridx = 0;
        c7.gridy = 0;
        containerFrame.add(report, c7);
        sensorDataUpdater.setVisible(true);
        // sensorDataUpdater.dispose();
        home.setVisible(true);
        newentry.setVisible(false);
        viewProfile.setVisible(false);
        report.setVisible(false);
        notification.setVisible(false);
        requests.setVisible(false);
        addUser.setVisible(false);
        features.setVisible(false);

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
        titleLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        sensordataTF = new javax.swing.JTextField();
        timeLael = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        homeButton = new javax.swing.JPanel();
        homeFont = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        newEntryButton = new javax.swing.JPanel();
        newentryfont = new javax.swing.JLabel();
        viewprofileButton = new javax.swing.JPanel();
        fontViweprofile = new javax.swing.JLabel();
        notificationButton = new javax.swing.JPanel();
        notificationfont = new javax.swing.JLabel();
        notificationLabel = new javax.swing.JLabel();
        featureButton = new javax.swing.JPanel();
        featurefont = new javax.swing.JLabel();
        tutorialButton = new javax.swing.JPanel();
        font = new javax.swing.JLabel();
        tutorialfont = new javax.swing.JLabel();
        reportButton = new javax.swing.JPanel();
        reportfont = new javax.swing.JLabel();
        addUserButton = new javax.swing.JPanel();
        addUserFont = new javax.swing.JLabel();
        containerFrame = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        p1TF = new javax.swing.JTextField();
        p2TF = new javax.swing.JTextField();
        p3TF = new javax.swing.JTextField();
        p4TF = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jPanel9 = new javax.swing.JPanel();
        p5TF = new javax.swing.JTextField();
        p6TF = new javax.swing.JTextField();
        p7TF = new javax.swing.JTextField();
        p8TF = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        p9TF = new javax.swing.JTextField();
        p10TF = new javax.swing.JTextField();
        p11TF = new javax.swing.JTextField();
        p12TF = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        p13TF = new javax.swing.JTextField();
        p14TF = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        nameTF = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        phoneTF = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        emailTF = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        vehicalNoTF = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        vehicalTypeTF = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        positionTF = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        entryTimeTF = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        feeTF = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        searchTF = new javax.swing.JTextField();
        clearButton = new javax.swing.JLabel();
        searchButton = new javax.swing.JLabel();
        btnRelese = new javax.swing.JPanel();
        fontRelese = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JPanel();
        fontupdate = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        addressTF = new javax.swing.JTextArea();
        jLabel20 = new javax.swing.JLabel();
        exitTimeTF = new javax.swing.JTextField();
        extraFeeTF = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        totalTF = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        paymentstatusCombo = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemNewPrimumCustomer = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItemNewEntry = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItemLogOut = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        timeMenu = new javax.swing.JMenu();
        dateMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(45, 62, 80));
        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 760));
        jPanel1.setRequestFocusEnabled(false);

        jPanel2.setBackground(new java.awt.Color(249, 148, 6));

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Demo station");

        jButton1.setText("send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timeLael, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(184, 184, 184)
                .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sensordataTF, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(50, 50, 50))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(timeLael, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(titleLabel)
                .addComponent(jButton1)
                .addComponent(sensordataTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addGap(0, 45, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(288, 646));

        homeButton.setBackground(new java.awt.Color(255, 255, 255));
        homeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        homeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeButtonMouseExited(evt);
            }
        });

        homeFont.setBackground(new java.awt.Color(255, 250, 246));
        homeFont.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        homeFont.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Home_30px.png"))); // NOI18N
        homeFont.setText("Home");
        homeFont.setIconTextGap(25);

        javax.swing.GroupLayout homeButtonLayout = new javax.swing.GroupLayout(homeButton);
        homeButton.setLayout(homeButtonLayout);
        homeButtonLayout.setHorizontalGroup(
            homeButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homeButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(homeFont, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        homeButtonLayout.setVerticalGroup(
            homeButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(homeFont, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/parking (8).png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Vijaya", 3, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("ParkEasy");

        newEntryButton.setBackground(new java.awt.Color(255, 255, 255));
        newEntryButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        newEntryButton.setPreferredSize(new java.awt.Dimension(110, 40));
        newEntryButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newEntryButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                newEntryButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                newEntryButtonMouseExited(evt);
            }
        });

        newentryfont.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        newentryfont.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Add_New_30px.png"))); // NOI18N
        newentryfont.setText("New Entry");
        newentryfont.setIconTextGap(25);

        javax.swing.GroupLayout newEntryButtonLayout = new javax.swing.GroupLayout(newEntryButton);
        newEntryButton.setLayout(newEntryButtonLayout);
        newEntryButtonLayout.setHorizontalGroup(
            newEntryButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, newEntryButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newentryfont, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        newEntryButtonLayout.setVerticalGroup(
            newEntryButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(newentryfont, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        viewprofileButton.setBackground(new java.awt.Color(255, 255, 255));
        viewprofileButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        viewprofileButton.setPreferredSize(new java.awt.Dimension(110, 40));
        viewprofileButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewprofileButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewprofileButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                viewprofileButtonMouseExited(evt);
            }
        });

        fontViweprofile.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fontViweprofile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Profile_30px_1.png"))); // NOI18N
        fontViweprofile.setText("View  Profile");
        fontViweprofile.setIconTextGap(25);

        javax.swing.GroupLayout viewprofileButtonLayout = new javax.swing.GroupLayout(viewprofileButton);
        viewprofileButton.setLayout(viewprofileButtonLayout);
        viewprofileButtonLayout.setHorizontalGroup(
            viewprofileButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewprofileButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fontViweprofile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        viewprofileButtonLayout.setVerticalGroup(
            viewprofileButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fontViweprofile, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        notificationButton.setBackground(new java.awt.Color(255, 255, 255));
        notificationButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        notificationButton.setPreferredSize(new java.awt.Dimension(147, 40));
        notificationButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                notificationButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                notificationButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                notificationButtonMouseExited(evt);
            }
        });

        notificationfont.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        notificationfont.setForeground(new java.awt.Color(204, 0, 0));
        notificationfont.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Bell_30px.png"))); // NOI18N
        notificationfont.setIconTextGap(0);
        notificationfont.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        notificationLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        notificationLabel.setText("Notifications");

        javax.swing.GroupLayout notificationButtonLayout = new javax.swing.GroupLayout(notificationButton);
        notificationButton.setLayout(notificationButtonLayout);
        notificationButtonLayout.setHorizontalGroup(
            notificationButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificationButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(notificationfont, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(notificationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        notificationButtonLayout.setVerticalGroup(
            notificationButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(notificationfont, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(notificationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        featureButton.setBackground(new java.awt.Color(255, 255, 255));
        featureButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        featureButton.setPreferredSize(new java.awt.Dimension(122, 40));
        featureButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                featureButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                featureButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                featureButtonMouseExited(evt);
            }
        });

        featurefont.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        featurefont.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Features_List_30px.png"))); // NOI18N
        featurefont.setText("Features");
        featurefont.setIconTextGap(25);

        javax.swing.GroupLayout featureButtonLayout = new javax.swing.GroupLayout(featureButton);
        featureButton.setLayout(featureButtonLayout);
        featureButtonLayout.setHorizontalGroup(
            featureButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(featureButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(featurefont, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        featureButtonLayout.setVerticalGroup(
            featureButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(featurefont, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        tutorialButton.setBackground(new java.awt.Color(255, 255, 255));
        tutorialButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tutorialButton.setPreferredSize(new java.awt.Dimension(122, 40));
        tutorialButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tutorialButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tutorialButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tutorialButtonMouseExited(evt);
            }
        });

        font.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        font.setForeground(new java.awt.Color(204, 0, 0));
        font.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Mailbox_30px_1.png"))); // NOI18N
        font.setIconTextGap(0);
        font.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        tutorialfont.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tutorialfont.setText("Requests");

        javax.swing.GroupLayout tutorialButtonLayout = new javax.swing.GroupLayout(tutorialButton);
        tutorialButton.setLayout(tutorialButtonLayout);
        tutorialButtonLayout.setHorizontalGroup(
            tutorialButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tutorialButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(font, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tutorialfont, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        tutorialButtonLayout.setVerticalGroup(
            tutorialButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(font, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(tutorialfont, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        reportButton.setBackground(new java.awt.Color(255, 255, 255));
        reportButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        reportButton.setPreferredSize(new java.awt.Dimension(112, 40));
        reportButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                reportButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                reportButtonMouseExited(evt);
            }
        });

        reportfont.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        reportfont.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Report_Card_30px.png"))); // NOI18N
        reportfont.setText("Report");
        reportfont.setIconTextGap(25);

        javax.swing.GroupLayout reportButtonLayout = new javax.swing.GroupLayout(reportButton);
        reportButton.setLayout(reportButtonLayout);
        reportButtonLayout.setHorizontalGroup(
            reportButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(reportfont, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        reportButtonLayout.setVerticalGroup(
            reportButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(reportfont, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        addUserButton.setBackground(new java.awt.Color(255, 255, 255));
        addUserButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addUserButton.setPreferredSize(new java.awt.Dimension(110, 40));
        addUserButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addUserButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addUserButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addUserButtonMouseExited(evt);
            }
        });

        addUserFont.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addUserFont.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/add-user.png"))); // NOI18N
        addUserFont.setText("Add User");
        addUserFont.setIconTextGap(25);

        javax.swing.GroupLayout addUserButtonLayout = new javax.swing.GroupLayout(addUserButton);
        addUserButton.setLayout(addUserButtonLayout);
        addUserButtonLayout.setHorizontalGroup(
            addUserButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addUserButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addUserFont, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        addUserButtonLayout.setVerticalGroup(
            addUserButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addUserFont, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(homeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(newEntryButton, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
            .addComponent(addUserButton, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
            .addComponent(viewprofileButton, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
            .addComponent(notificationButton, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
            .addComponent(reportButton, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
            .addComponent(tutorialButton, javax.swing.GroupLayout.PREFERRED_SIZE, 254, Short.MAX_VALUE)
            .addComponent(featureButton, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel2)
                .addGap(9, 9, 9)
                .addComponent(homeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(newEntryButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(addUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(viewprofileButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(notificationButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(reportButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(tutorialButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(featureButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        containerFrame.setBackground(new java.awt.Color(255, 255, 255));
        containerFrame.setPreferredSize(new java.awt.Dimension(730, 478));

        javax.swing.GroupLayout containerFrameLayout = new javax.swing.GroupLayout(containerFrame);
        containerFrame.setLayout(containerFrameLayout);
        containerFrameLayout.setHorizontalGroup(
            containerFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        containerFrameLayout.setVerticalGroup(
            containerFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(730, 166));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Sensor Status");

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true), "Block A Sensor Status", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 102, 0))); // NOI18N

        p1TF.setEditable(false);
        p1TF.setBackground(new java.awt.Color(255, 255, 255));
        p1TF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p1TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        p2TF.setEditable(false);
        p2TF.setBackground(new java.awt.Color(255, 255, 255));
        p2TF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p2TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        p3TF.setEditable(false);
        p3TF.setBackground(new java.awt.Color(255, 255, 255));
        p3TF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p3TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        p4TF.setEditable(false);
        p4TF.setBackground(new java.awt.Color(255, 255, 255));
        p4TF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p4TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Park 1");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Park 2");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Park 3");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Park 4");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(p1TF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(p2TF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel10)
                    .addComponent(p3TF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel11)
                    .addComponent(p4TF)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p1TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p2TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p3TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p4TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true), "Block B Sensor Status", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 102, 0))); // NOI18N

        p5TF.setEditable(false);
        p5TF.setBackground(new java.awt.Color(255, 255, 255));
        p5TF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p5TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        p6TF.setEditable(false);
        p6TF.setBackground(new java.awt.Color(255, 255, 255));
        p6TF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p6TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        p7TF.setEditable(false);
        p7TF.setBackground(new java.awt.Color(255, 255, 255));
        p7TF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p7TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        p8TF.setEditable(false);
        p8TF.setBackground(new java.awt.Color(255, 255, 255));
        p8TF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p8TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Park 1");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel26.setText("Park 2");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel27.setText("Park 3");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel28.setText("Park 4");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel12)
                    .addComponent(p5TF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel26)
                    .addComponent(p6TF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel27)
                    .addComponent(p7TF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel28)
                    .addComponent(p8TF)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p5TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p6TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p7TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p8TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true), "Block C Sensor Status", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 102, 0))); // NOI18N

        p9TF.setEditable(false);
        p9TF.setBackground(new java.awt.Color(255, 255, 255));
        p9TF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p9TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        p10TF.setEditable(false);
        p10TF.setBackground(new java.awt.Color(255, 255, 255));
        p10TF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p10TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        p11TF.setEditable(false);
        p11TF.setBackground(new java.awt.Color(255, 255, 255));
        p11TF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p11TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        p12TF.setEditable(false);
        p12TF.setBackground(new java.awt.Color(255, 255, 255));
        p12TF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p12TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setText("Park 1");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setText("Park 2");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel31.setText("Park 3");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel32.setText("Park 4");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel29)
                    .addComponent(p9TF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel30)
                    .addComponent(p10TF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel31)
                    .addComponent(p11TF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel32)
                    .addComponent(p12TF)))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p9TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p10TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p11TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p12TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true), "Block D Sensor Status", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(0, 102, 0))); // NOI18N

        p13TF.setEditable(false);
        p13TF.setBackground(new java.awt.Color(255, 255, 255));
        p13TF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p13TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        p14TF.setEditable(false);
        p14TF.setBackground(new java.awt.Color(255, 255, 255));
        p14TF.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        p14TF.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setText("Park 1");

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setText("Park 2");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(p13TF)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel33)))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(p14TF))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel34)
                        .addGap(37, 37, 37))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p13TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p14TF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(56, 56, 56)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(59, 59, 59)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(47, 47, 47)
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(340, 494));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(45, 62, 80));
        jLabel13.setText("Name:");

        nameTF.setBackground(new java.awt.Color(109, 122, 138));
        nameTF.setForeground(new java.awt.Color(255, 255, 255));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(45, 62, 80));
        jLabel22.setText("Phone No:");

        phoneTF.setEditable(false);
        phoneTF.setBackground(new java.awt.Color(109, 122, 138));
        phoneTF.setForeground(new java.awt.Color(255, 255, 255));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(45, 62, 80));
        jLabel21.setText("Email:");

        emailTF.setEditable(false);
        emailTF.setBackground(new java.awt.Color(109, 122, 138));
        emailTF.setForeground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(45, 62, 80));
        jLabel15.setText("Address:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(45, 62, 80));
        jLabel14.setText("Vehical No:");

        vehicalNoTF.setEditable(false);
        vehicalNoTF.setBackground(new java.awt.Color(109, 122, 138));
        vehicalNoTF.setForeground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(45, 62, 80));
        jLabel16.setText("Vehical Type:");

        vehicalTypeTF.setEditable(false);
        vehicalTypeTF.setBackground(new java.awt.Color(109, 122, 138));
        vehicalTypeTF.setForeground(new java.awt.Color(255, 255, 255));
        vehicalTypeTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vehicalTypeTFActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(45, 62, 80));
        jLabel17.setText("Position:");

        positionTF.setEditable(false);
        positionTF.setBackground(new java.awt.Color(109, 122, 138));
        positionTF.setForeground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(45, 62, 80));
        jLabel18.setText("Entry Time:");

        entryTimeTF.setEditable(false);
        entryTimeTF.setBackground(new java.awt.Color(109, 122, 138));
        entryTimeTF.setForeground(new java.awt.Color(255, 255, 255));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(45, 62, 80));
        jLabel19.setText("Fee:");

        feeTF.setEditable(false);
        feeTF.setBackground(new java.awt.Color(109, 122, 138));
        feeTF.setForeground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        searchTF.setToolTipText("Search by car number/ position");
        searchTF.setBorder(null);

        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Delete_20px_1.png"))); // NOI18N
        clearButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearButtonMouseClicked(evt);
            }
        });

        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Search_20px_1.png"))); // NOI18N
        searchButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchButton)
                .addGap(10, 10, 10)
                .addComponent(searchTF, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearButton)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(clearButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
            .addComponent(searchTF, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(searchButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnRelese.setBackground(new java.awt.Color(255, 255, 255));
        btnRelese.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnRelese.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRelese.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReleseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnReleseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnReleseMouseExited(evt);
            }
        });

        fontRelese.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fontRelese.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fontRelese.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Remove_Property_20px_1.png"))); // NOI18N
        fontRelese.setText("Release");
        fontRelese.setIconTextGap(8);

        javax.swing.GroupLayout btnReleseLayout = new javax.swing.GroupLayout(btnRelese);
        btnRelese.setLayout(btnReleseLayout);
        btnReleseLayout.setHorizontalGroup(
            btnReleseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnReleseLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fontRelese, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnReleseLayout.setVerticalGroup(
            btnReleseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fontRelese, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        btnUpdate.setBackground(new java.awt.Color(255, 255, 255));
        btnUpdate.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnUpdateMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnUpdateMouseExited(evt);
            }
        });

        fontupdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fontupdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fontupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Edit_Property_20px.png"))); // NOI18N
        fontupdate.setText("Update ");
        fontupdate.setIconTextGap(8);

        javax.swing.GroupLayout btnUpdateLayout = new javax.swing.GroupLayout(btnUpdate);
        btnUpdate.setLayout(btnUpdateLayout);
        btnUpdateLayout.setHorizontalGroup(
            btnUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnUpdateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fontupdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnUpdateLayout.setVerticalGroup(
            btnUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fontupdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        addressTF.setBackground(new java.awt.Color(109, 122, 138));
        addressTF.setColumns(20);
        addressTF.setForeground(new java.awt.Color(255, 255, 255));
        addressTF.setLineWrap(true);
        addressTF.setRows(5);
        jScrollPane1.setViewportView(addressTF);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(45, 62, 80));
        jLabel20.setText("Exit Time:");

        exitTimeTF.setEditable(false);
        exitTimeTF.setBackground(new java.awt.Color(109, 122, 138));
        exitTimeTF.setForeground(new java.awt.Color(255, 255, 255));

        extraFeeTF.setEditable(false);
        extraFeeTF.setBackground(new java.awt.Color(109, 122, 138));
        extraFeeTF.setForeground(new java.awt.Color(255, 255, 255));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(45, 62, 80));
        jLabel23.setText("Extra:");

        totalTF.setEditable(false);
        totalTF.setBackground(new java.awt.Color(109, 122, 138));
        totalTF.setForeground(new java.awt.Color(255, 255, 255));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(45, 62, 80));
        jLabel24.setText("Total:");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(45, 62, 80));
        jLabel25.setText("Payment status: ");

        paymentstatusCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "Paid", "Due" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel19))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel16))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel14)))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel17))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel18))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel20))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel23))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel24)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(totalTF)
                            .addComponent(extraFeeTF)
                            .addComponent(feeTF)
                            .addComponent(exitTimeTF)
                            .addComponent(entryTimeTF)
                            .addComponent(positionTF)
                            .addComponent(vehicalTypeTF)
                            .addComponent(vehicalNoTF)
                            .addComponent(jScrollPane1)
                            .addComponent(emailTF)
                            .addComponent(phoneTF)
                            .addComponent(nameTF)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(paymentstatusCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRelese, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(35, 35, 35))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(nameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(emailTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(vehicalNoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(vehicalTypeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(positionTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(entryTimeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(exitTimeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(feeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(extraFeeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(totalTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(paymentstatusCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRelese, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 845, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(256, 256, 256)
                .addComponent(containerFrame, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(containerFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jMenu1.setText("File");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });
        jMenu1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenu1KeyPressed(evt);
            }
        });

        jMenuItemNewPrimumCustomer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemNewPrimumCustomer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/add-user (3).png"))); // NOI18N
        jMenuItemNewPrimumCustomer.setText("Add User");
        jMenuItemNewPrimumCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewPrimumCustomerActionPerformed(evt);
            }
        });
        jMenuItemNewPrimumCustomer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenuItemNewPrimumCustomerKeyPressed(evt);
            }
        });
        jMenu1.add(jMenuItemNewPrimumCustomer);
        jMenu1.add(jSeparator4);

        jMenuItemNewEntry.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemNewEntry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Add_New_24px.png"))); // NOI18N
        jMenuItemNewEntry.setText("New Entry");
        jMenuItemNewEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNewEntryActionPerformed(evt);
            }
        });
        jMenuItemNewEntry.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenuItemNewEntryKeyPressed(evt);
            }
        });
        jMenu1.add(jMenuItemNewEntry);
        jMenu1.add(jSeparator2);

        jMenuItemLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Sign_Out_24px.png"))); // NOI18N
        jMenuItemLogOut.setText("Log out");
        jMenuItemLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLogOutActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemLogOut);
        jMenu1.add(jSeparator3);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Close_Window_24px.png"))); // NOI18N
        jMenuItem2.setText("Exi");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);
        jMenu1.add(jSeparator1);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Tools");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_USB_Connector_24px_1.png"))); // NOI18N
        jMenuItem1.setText("Select Sensor-Control Device");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Marker_24px_1.png"))); // NOI18N
        jMenuItem7.setText("Set Location");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Password_20px.png"))); // NOI18N
        jMenuItem12.setText("Set Gate-Cotroll Password");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem12);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Edit");

        jMenuItem3.setText("Edit own profile");
        jMenu2.add(jMenuItem3);
        jMenu2.add(jSeparator6);

        jMenuItem11.setText("Edit customer profile");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem11);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Help");

        jMenuItem8.setText("Help contents");
        jMenu4.add(jMenuItem8);
        jMenu4.add(jSeparator9);

        jMenuItem9.setText("Online help");
        jMenu4.add(jMenuItem9);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("About           ");

        jMenuItem10.setText("About us");
        jMenu5.add(jMenuItem10);

        jMenuBar1.add(jMenu5);

        timeMenu.setText("Time     ");
        jMenuBar1.add(timeMenu);

        dateMenu.setText("Date");
        jMenuBar1.add(dateMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 845, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 709, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void title() {

        try {

            String sql_5 = "select * from park_owner where user_id='" + u_id + "'";
            PreparedStatement st = connection.prepareStatement(sql_5);
            ResultSet rs_5 = st.executeQuery();

            if (rs_5.next()) {

                garage_name = rs_5.getString("park_name");
                st.close();
                rs_5.close();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

        titleLabel.setText(garage_name);

    }


    private void homeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeButtonMouseClicked

        homebtn = 1;
        newentrybtn = 0;
        relesebtn = 0;
        notificationbtn = 0;
        reportbtn = 0;
        tutorialbtn = 0;
        aboutusbtn = 0;
        featurebtn = 0;
        setclickedcolor(homeButton, homeFont);
        resetselectcolor(newEntryButton, newentryfont);
        resetselectcolor(viewprofileButton, fontViweprofile);
        resetselectcolor(notificationButton, notificationLabel);
        resetselectcolor(reportButton, reportfont);
        resetselectcolor(tutorialButton, tutorialfont);
        resetselectcolor(addUserButton, addUserFont);
        resetselectcolor(featureButton, featurefont);

        home.showDataTable();
        home.setVisible(true);
        newentry.setVisible(false);
        viewProfile.setVisible(false);
        report.setVisible(false);
        notification.setVisible(false);
        requests.setVisible(false);
        addUser.setVisible(false);
        features.setVisible(false);
        //home=new Home();


    }//GEN-LAST:event_homeButtonMouseClicked

    private void homeButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeButtonMouseEntered
        // TODO add your handling code here:
        if (homebtn != 1) {
            setselectcolor(homeButton, homeFont);
        }
    }//GEN-LAST:event_homeButtonMouseEntered

    private void homeButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeButtonMouseExited
        // TODO add your handling code here:
        if (homebtn != 1) {
            resetselectcolor(homeButton, homeFont);
        }
    }//GEN-LAST:event_homeButtonMouseExited

    private void newEntryButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newEntryButtonMouseClicked
        // TODO add your handling code here:

        homebtn = 0;
        newentrybtn = 1;
        relesebtn = 0;
        notificationbtn = 0;
        reportbtn = 0;
        tutorialbtn = 0;
        aboutusbtn = 0;
        featurebtn = 0;
        setclickedcolor(newEntryButton, newentryfont);
        resetselectcolor(homeButton, homeFont);
        resetselectcolor(viewprofileButton, fontViweprofile);
        resetselectcolor(notificationButton, notificationLabel);
        resetselectcolor(reportButton, reportfont);
        resetselectcolor(tutorialButton, tutorialfont);
        resetselectcolor(addUserButton, addUserFont);
        resetselectcolor(featureButton, featurefont);
        newentry.setfeeAndPosition();
        home.setVisible(false);
        newentry.setVisible(true);
        viewProfile.setVisible(false);
        report.setVisible(false);
        notification.setVisible(false);
        requests.setVisible(false);
        addUser.setVisible(false);
        features.setVisible(false);
    }//GEN-LAST:event_newEntryButtonMouseClicked

    private void newEntryButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newEntryButtonMouseEntered
        // TODO add your handling code here:
        if (newentrybtn != 1) {
            setselectcolor(newEntryButton, newentryfont);
        }
    }//GEN-LAST:event_newEntryButtonMouseEntered

    private void newEntryButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newEntryButtonMouseExited
        // TODO add your handling code here:
        if (newentrybtn != 1) {
            resetselectcolor(newEntryButton, newentryfont);
        }
    }//GEN-LAST:event_newEntryButtonMouseExited

    private void viewprofileButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewprofileButtonMouseClicked
        // TODO add your handling code here:
        homebtn = 0;
        newentrybtn = 0;
        relesebtn = 1;
        notificationbtn = 0;
        reportbtn = 0;
        tutorialbtn = 0;
        aboutusbtn = 0;
        featurebtn = 0;
        setclickedcolor(viewprofileButton, fontViweprofile);
        resetselectcolor(newEntryButton, newentryfont);
        resetselectcolor(homeButton, homeFont);
        resetselectcolor(notificationButton, notificationLabel);
        resetselectcolor(reportButton, reportfont);
        resetselectcolor(tutorialButton, tutorialfont);
        resetselectcolor(addUserButton, addUserFont);
        resetselectcolor(featureButton, featurefont);

        home.setVisible(false);
        newentry.setVisible(false);
        viewProfile.setVisible(true);
        report.setVisible(false);
        notification.setVisible(false);
        requests.setVisible(false);
        addUser.setVisible(false);
        features.setVisible(false);
    }//GEN-LAST:event_viewprofileButtonMouseClicked

    private void notificationButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificationButtonMouseClicked
        // TODO add your handling code here:
        homebtn = 0;
        newentrybtn = 0;
        relesebtn = 0;
        notificationbtn = 1;
        reportbtn = 0;
        tutorialbtn = 0;
        aboutusbtn = 0;
        featurebtn = 0;
        setclickedcolor(notificationButton, notificationLabel);
        resetselectcolor(newEntryButton, newentryfont);
        resetselectcolor(homeButton, homeFont);
        resetselectcolor(viewprofileButton, fontViweprofile);
        resetselectcolor(reportButton, reportfont);
        resetselectcolor(tutorialButton, tutorialfont);
        resetselectcolor(addUserButton, addUserFont);
        resetselectcolor(featureButton, featurefont);

        notification.showBookingNotification();
        notification.showDetailTextArea.setText("");
        home.setVisible(false);
        newentry.setVisible(false);
        viewProfile.setVisible(false);
        report.setVisible(false);
        notification.setVisible(true);
        requests.setVisible(false);
        addUser.setVisible(false);
        features.setVisible(false);


    }//GEN-LAST:event_notificationButtonMouseClicked

    private void featureButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_featureButtonMouseClicked
        // TODO add your handling code here:
        homebtn = 0;
        newentrybtn = 0;
        relesebtn = 0;
        notificationbtn = 0;
        reportbtn = 0;
        tutorialbtn = 0;
        aboutusbtn = 0;
        featurebtn = 1;
        setclickedcolor(featureButton, featurefont);
        resetselectcolor(newEntryButton, newentryfont);
        resetselectcolor(homeButton, homeFont);
        resetselectcolor(notificationButton, notificationLabel);
        resetselectcolor(reportButton, reportfont);
        resetselectcolor(tutorialButton, tutorialfont);
        resetselectcolor(addUserButton, addUserFont);
        resetselectcolor(viewprofileButton, fontViweprofile);

        home.setVisible(false);
        newentry.setVisible(false);
        viewProfile.setVisible(false);
        report.setVisible(false);
        notification.setVisible(false);
        requests.setVisible(false);
        addUser.setVisible(false);
        features.setVisible(true);

    }//GEN-LAST:event_featureButtonMouseClicked

    private void addUserButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addUserButtonMouseClicked
        // TODO add your handling code here:
        homebtn = 0;
        newentrybtn = 0;
        relesebtn = 0;
        notificationbtn = 0;
        reportbtn = 0;
        tutorialbtn = 0;
        aboutusbtn = 1;
        featurebtn = 0;
        setclickedcolor(addUserButton, addUserFont);
        resetselectcolor(newEntryButton, newentryfont);
        resetselectcolor(homeButton, homeFont);
        resetselectcolor(notificationButton, notificationLabel);
        resetselectcolor(reportButton, reportfont);
        resetselectcolor(tutorialButton, tutorialfont);
        resetselectcolor(featureButton, featurefont);
        resetselectcolor(viewprofileButton, fontViweprofile);

        home.setVisible(false);
        newentry.setVisible(false);
        viewProfile.setVisible(false);
        report.setVisible(false);
        notification.setVisible(false);
        requests.setVisible(false);
        addUser.setVisible(true);
        features.setVisible(false);
    }//GEN-LAST:event_addUserButtonMouseClicked

    private void tutorialButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tutorialButtonMouseClicked
        // TODO add your handling code here:
        homebtn = 0;
        newentrybtn = 0;
        relesebtn = 0;
        notificationbtn = 0;
        reportbtn = 0;
        tutorialbtn = 1;
        aboutusbtn = 0;
        featurebtn = 0;
        setclickedcolor(tutorialButton, tutorialfont);
        resetselectcolor(newEntryButton, newentryfont);
        resetselectcolor(homeButton, homeFont);
        resetselectcolor(notificationButton, notificationLabel);
        resetselectcolor(reportButton, reportfont);
        resetselectcolor(featureButton, featurefont);
        resetselectcolor(addUserButton, addUserFont);
        resetselectcolor(viewprofileButton, fontViweprofile);

        requests.showRequestNotification();
        requests.showDetailTextArea.setText("");
        home.setVisible(false);
        newentry.setVisible(false);
        viewProfile.setVisible(false);
        report.setVisible(false);
        notification.setVisible(false);
        requests.setVisible(true);
        addUser.setVisible(false);
        features.setVisible(false);
    }//GEN-LAST:event_tutorialButtonMouseClicked

    private void reportButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportButtonMouseClicked
        // TODO add your handling code here:
        homebtn = 0;
        newentrybtn = 0;
        relesebtn = 0;
        notificationbtn = 0;
        reportbtn = 1;
        tutorialbtn = 0;
        aboutusbtn = 0;
        featurebtn = 0;
        setclickedcolor(reportButton, reportfont);
        resetselectcolor(newEntryButton, newentryfont);
        resetselectcolor(homeButton, homeFont);
        resetselectcolor(notificationButton, notificationLabel);
        resetselectcolor(featureButton, featurefont);
        resetselectcolor(tutorialButton, tutorialfont);
        resetselectcolor(addUserButton, addUserFont);
        resetselectcolor(viewprofileButton, fontViweprofile);
        home.setVisible(false);
        newentry.setVisible(false);
        viewProfile.setVisible(false);
        report.dailyReportShowDataTable();
        report.monthlyReportShowDataTable();
        report.yearlyReportShowDataTable();
        report.setVisible(true);
        notification.setVisible(false);
        requests.setVisible(false);
        addUser.setVisible(false);
        features.setVisible(false);
    }//GEN-LAST:event_reportButtonMouseClicked

    private void viewprofileButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewprofileButtonMouseEntered
        // TODO add your handling code here:
        if (relesebtn != 1) {
            setselectcolor(viewprofileButton, fontViweprofile);
        }
    }//GEN-LAST:event_viewprofileButtonMouseEntered

    private void viewprofileButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewprofileButtonMouseExited
        // TODO add your handling code here:
        if (relesebtn != 1) {
            resetselectcolor(viewprofileButton, fontViweprofile);
        }
    }//GEN-LAST:event_viewprofileButtonMouseExited

    private void notificationButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificationButtonMouseEntered
        // TODO add your handling code here:
        if (notificationbtn != 1) {
            setselectcolor(notificationButton, notificationLabel);
        }
    }//GEN-LAST:event_notificationButtonMouseEntered

    private void notificationButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificationButtonMouseExited
        // TODO add your handling code here:
        if (notificationbtn != 1) {
            resetselectcolor(notificationButton, notificationLabel);
        }
    }//GEN-LAST:event_notificationButtonMouseExited

    private void featureButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_featureButtonMouseEntered
        // TODO add your handling code here:
        if (featurebtn != 1) {
            setselectcolor(featureButton, featurefont);
        }
    }//GEN-LAST:event_featureButtonMouseEntered

    private void featureButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_featureButtonMouseExited
        // TODO add your handling code here:
        if (featurebtn != 1) {
            resetselectcolor(featureButton, featurefont);
        }
    }//GEN-LAST:event_featureButtonMouseExited

    private void addUserButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addUserButtonMouseEntered
        // TODO add your handling code here:
        if (aboutusbtn != 1) {
            setselectcolor(addUserButton, addUserFont);
        }
    }//GEN-LAST:event_addUserButtonMouseEntered

    private void addUserButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addUserButtonMouseExited
        // TODO add your handling code here:
        if (aboutusbtn != 1) {
            resetselectcolor(addUserButton, addUserFont);
        }
    }//GEN-LAST:event_addUserButtonMouseExited

    private void tutorialButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tutorialButtonMouseEntered
        // TODO add your handling code here:
        if (tutorialbtn != 1) {
            setselectcolor(tutorialButton, tutorialfont);
        }
    }//GEN-LAST:event_tutorialButtonMouseEntered

    private void tutorialButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tutorialButtonMouseExited
        // TODO add your handling code here:
        if (tutorialbtn != 1) {
            resetselectcolor(tutorialButton, tutorialfont);
        }
    }//GEN-LAST:event_tutorialButtonMouseExited

    private void reportButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportButtonMouseEntered
        // TODO add your handling code here:
        if (reportbtn != 1) {
            setselectcolor(reportButton, reportfont);
        }
    }//GEN-LAST:event_reportButtonMouseEntered

    private void reportButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportButtonMouseExited
        // TODO add your handling code here:
        if (reportbtn != 1) {
            resetselectcolor(reportButton, reportfont);
        }
    }//GEN-LAST:event_reportButtonMouseExited

    private void vehicalTypeTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vehicalTypeTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vehicalTypeTFActionPerformed

    private void searchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchButtonMouseClicked
        // TODO add your handling code here:
        dateformater = new SimpleDateFormat("dd/MM/yyyy");
        timeformater = new SimpleDateFormat("HH:mm");
        date = new Date();
        String time = timeformater.format(date);
        String cDate = dateformater.format(date);
        int rate, total;
        // extra,due,paid;
        String key = searchTF.getText();

        if (key.equals("p1") || key.equals("1") || key.equals("park1") || key.equals("bAp1")) {
            key = "Block-A Park-1";
        } else if (key.equals("p2") || key.equals("2") || key.equals("park2") || key.equals("bAp2")) {
            key = "Block-A Park-2";
        } else if (key.equals("p3") || key.equals("3") || key.equals("park3") || key.equals("bAp3")) {
            key = "Block-A Park-3";
        } else if (key.equals("p4") || key.equals("4") || key.equals("park4") || key.equals("bAp4")) {
            key = "Block-A Park-4";
        } else if (key.equals("p5") || key.equals("5") || key.equals("park5") || key.equals("bBp1")) {
            key = "Block-B Park-1";
        } else if (key.equals("p6") || key.equals("6") || key.equals("park6") || key.equals("bBp2")) {
            key = "Block-B Park-2";
        } else if (key.equals("p7") || key.equals("7") || key.equals("park7") || key.equals("bBp3")) {
            key = "Block-B Park-3";
        } else if (key.equals("p8") || key.equals("8") || key.equals("park8") || key.equals("bBp4")) {
            key = "Block-B Park-4";
        } else if (key.equals("p9") || key.equals("9") || key.equals("park9") || key.equals("bCp1")) {
            key = "Block-C Park-1";
        } else if (key.equals("p10") || key.equals("10") || key.equals("park10") || key.equals("bCp2")) {
            key = "Block-C Park-2";
        } else if (key.equals("p11") || key.equals("11") || key.equals("park11") || key.equals("bCp3")) {
            key = "Block-C Park-2";
        } else if (key.equals("p12") || key.equals("12") || key.equals("park12") || key.equals("bCp4")) {
            key = "Block-C Park-4";
        } else if (key.equals("p13") || key.equals("13") || key.equals("park13") || key.equals("bDp1")) {
            key = "Block-D Park-1";
        } else if (key.equals("p14") || key.equals("14") || key.equals("park14") || key.equals("bDp2")) {
            key = "Block-D Park-2";
        } else {
            key = key;
        }

        try {

            String sql = "select * from " + parkedTableName + " where vehical_no='" + key + "' OR position ='" + key + "'AND status='" + 1 + "'";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                nameTF.setText(rs.getString("name"));
                phoneTF.setText(rs.getString("phone"));
                emailTF.setText(rs.getString("email"));
                addressTF.setText(rs.getString("address"));
                vehicalNoTF.setText(rs.getString("vehical_no"));
                vehicalTypeTF.setText(rs.getString("vehical_type"));
                positionTF.setText(rs.getString("position"));
                entryTimeTF.setText(rs.getString("time") + "    " + rs.getString("date"));
                feeTF.setText(String.valueOf(rs.getInt("fee")));

                //paymentStatusTF.setText(rs.getString("payment_status"));
                exitTimeTF.setText(time + "    " + cDate);
                extraFeeTF.setText(String.valueOf(rs.getInt("extra_fee")));

                rate = rs.getInt("fee");
                fee = rs.getInt("fee");
                extra = rs.getInt("extra_fee");
                due = rs.getInt("due");
                paid = rs.getInt("paid");
                totalTF.setText(String.valueOf(rate + extra));
                nameTF.setEditable(true);
                phoneTF.setEditable(true);
                emailTF.setEditable(true);
                addressTF.setEditable(true);
                vehicalNoTF.setEditable(true);
                vehicalTypeTF.setEditable(true);
                positionTF.setEditable(true);
                extraFeeTF.setEditable(true);
                paymentstatusCombo.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Search result is not found!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainFraim.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_searchButtonMouseClicked

    private void clearButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearButtonMouseClicked
        // TODO add your handling code here:
        searchTF.setText("");
        nameTF.setText("");
        addressTF.setText("");
        phoneTF.setText("");
        emailTF.setText("");
        vehicalNoTF.setText("");
        vehicalTypeTF.setText("");
        positionTF.setText("");
        entryTimeTF.setText("");
        exitTimeTF.setText("");
        feeTF.setText("");
        extraFeeTF.setText("");
        totalTF.setText("");
        paymentstatusCombo.setSelectedIndex(0);

    }//GEN-LAST:event_clearButtonMouseClicked

    private void btnReleseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReleseMouseClicked
        // TODO add your handling code here:

        ReleseForm releseForm = new ReleseForm();
        setclickedcolor(btnRelese, fontRelese);

        dateformater = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        //timeformater = new SimpleDateFormat("HH:mm");
        date = new Date();
        //String cDate = dateformater.format(date);
        String exitTime = dateformater.format(date);

        String paymentStatus = paymentstatusCombo.getSelectedItem().toString();
        String extraFee = extraFeeTF.getText();

        String vehicalNo = vehicalNoTF.getText();

        if (nameTF.getText().isEmpty() || phoneTF.getText().isEmpty() || emailTF.getText().isEmpty()
                || addressTF.getText().isEmpty() || vehicalNoTF.getText().isEmpty() || vehicalTypeTF.getText().isEmpty()
                || positionTF.getText().isEmpty() || entryTimeTF.getText().isEmpty() || exitTimeTF.getText().isEmpty()
                || feeTF.getText().isEmpty() || extraFeeTF.getText().isEmpty() || totalTF.getText().isEmpty()
                || paymentstatusCombo.getSelectedItem().equals("Select one")) {
            JOptionPane.showMessageDialog(null, "Some fields are required!");
        } else {
            releseForm.titleLabel.setText(titleLabel.getText());
            releseForm.nameTF.setText(nameTF.getText());
            releseForm.phoneTF.setText(phoneTF.getText());
            releseForm.emailTF.setText(emailTF.getText());
            releseForm.addressTF.setText(addressTF.getText());
            releseForm.vehicalNoTF.setText(vehicalNoTF.getText());
            releseForm.vehicalTypeTF.setText(vehicalTypeTF.getText());
            releseForm.positionTF.setText(positionTF.getText());
            releseForm.entryTimeTF.setText(entryTimeTF.getText());
            releseForm.exitTimeTF.setText(exitTime);
            releseForm.feeTF.setText(feeTF.getText());
            releseForm.extraFeeTF.setText(extraFeeTF.getText());
            releseForm.totalTF.setText(totalTF.getText());
            releseForm.paidTF.setText(String.valueOf(paid));
            releseForm.dueTF.setText(String.valueOf(due));
            releseForm.paymentStatusTF.setText(paymentstatusCombo.getSelectedItem().toString());
            releseForm.dateLabel.setText(dateMenu.getText());
            releseForm.toPayTF.setText("0");
            releseForm.parkedTableName = parkedTableName;
            releseForm.due = due;
            releseForm.paid = paid;
            releseForm.extra = extra;
            releseForm.fee = fee;
            releseForm.u_id = u_id;
            releseForm.u_name = u_name;
            releseForm.toPayTF.requestFocus();
            releseForm.setVisible(true);

            searchTF.setText("");
            nameTF.setText("");
            addressTF.setText("");
            phoneTF.setText("");
            emailTF.setText("");
            vehicalNoTF.setText("");
            vehicalTypeTF.setText("");
            positionTF.setText("");
            entryTimeTF.setText("");
            exitTimeTF.setText("");
            feeTF.setText("");
            extraFeeTF.setText("");
            totalTF.setText("");
            paymentstatusCombo.setSelectedIndex(0);

        }


    }//GEN-LAST:event_btnReleseMouseClicked

    private void btnReleseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReleseMouseEntered
        // TODO add your handling code here:

        setselectcolor(btnRelese, fontRelese);
    }//GEN-LAST:event_btnReleseMouseEntered

    private void btnReleseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReleseMouseExited
        // TODO add your handling code here:

        resetselectcolor(btnRelese, fontRelese);
    }//GEN-LAST:event_btnReleseMouseExited


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
//sensordataTF.setText("name="+sensorTableName

        ArrayList<String> list = new ArrayList();
        int i;
        new_sensordata = sensordataTF.getText();
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

                SensorStatus();
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
                        SensorStatus();
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
                    SensorStatus();
                }
            }

            old_date = new_date;
        }


    }//GEN-LAST:event_jButton1ActionPerformed


    private void btnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseClicked
        // TODO add your handling code here:
        setclickedcolor(btnUpdate, fontupdate);
        SimpleDateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        ArrayList<String> list = new ArrayList();
        String dateAvail = "n";
        String key = searchTF.getText();
        int cashReceive;

        if (key.equals("p1") || key.equals("1") || key.equals("park1") || key.equals("bAp1")) {
            key = "Block-A Park-1";
        } else if (key.equals("p2") || key.equals("2") || key.equals("park2") || key.equals("bAp2")) {
            key = "Block-A Park-2";
        } else if (key.equals("p3") || key.equals("3") || key.equals("park3") || key.equals("bAp3")) {
            key = "Block-A Park-3";
        } else if (key.equals("p4") || key.equals("4") || key.equals("park4") || key.equals("bAp4")) {
            key = "Block-A Park-4";
        } else if (key.equals("p5") || key.equals("5") || key.equals("park5") || key.equals("bBp1")) {
            key = "Block-B Park-1";
        } else if (key.equals("p6") || key.equals("6") || key.equals("park6") || key.equals("bBp2")) {
            key = "Block-B Park-2";
        } else if (key.equals("p7") || key.equals("7") || key.equals("park7") || key.equals("bBp3")) {
            key = "Block-B Park-3";
        } else if (key.equals("p8") || key.equals("8") || key.equals("park8") || key.equals("bBp4")) {
            key = "Block-B Park-4";
        } else if (key.equals("p9") || key.equals("9") || key.equals("park9") || key.equals("bCp1")) {
            key = "Block-C Park-1";
        } else if (key.equals("p10") || key.equals("10") || key.equals("park10") || key.equals("bCp2")) {
            key = "Block-C Park-2";
        } else if (key.equals("p11") || key.equals("11") || key.equals("park11") || key.equals("bCp3")) {
            key = "Block-C Park-2";
        } else if (key.equals("p12") || key.equals("12") || key.equals("park12") || key.equals("bCp4")) {
            key = "Block-C Park-4";
        } else if (key.equals("p13") || key.equals("13") || key.equals("park13") || key.equals("bDp1")) {
            key = "Block-D Park-1";
        } else if (key.equals("p14") || key.equals("14") || key.equals("park14") || key.equals("bDp2")) {
            key = "Block-D Park-2";
        } else {
            key = key;
        }

        if (nameTF.getText().isEmpty() || phoneTF.getText().isEmpty() || emailTF.getText().isEmpty()
                || addressTF.getText().isEmpty() || vehicalNoTF.getText().isEmpty() || vehicalTypeTF.getText().isEmpty()
                || positionTF.getText().isEmpty() || entryTimeTF.getText().isEmpty() || exitTimeTF.getText().isEmpty()
                || feeTF.getText().isEmpty() || extraFeeTF.getText().isEmpty() || totalTF.getText().isEmpty()
                || paymentstatusCombo.getSelectedItem().equals("Select one")) {
            JOptionPane.showMessageDialog(null, "Some fields are required!");
        } else {

            extra = extra + Integer.parseInt(extraFeeTF.getText());

            if (paymentstatusCombo.getSelectedItem().equals("Paid")) {
                paid = paid + Integer.parseInt(extraFeeTF.getText());

            } else {
                due = due + Integer.parseInt(extraFeeTF.getText());
            }

            try {

                String sql = "update " + parkedTableName + " set name = '" + nameTF.getText() + "' "
                        + ",phone='" + phoneTF.getText() + "',email='" + emailTF.getText() + "'"
                        + ",vehical_no='" + vehicalNoTF.getText() + "',vehical_type='" + vehicalTypeTF.getText() + "'"
                        + ",position='" + positionTF.getText() + "',extra_fee='" + extra + "'"
                        + ",paid='" + paid + "',due='" + due + "'"
                        + ",address='" + addressTF.getText() + "',payment_status='" + paymentstatusCombo.getSelectedItem().toString() + ""
                        + "' where vehical_no='" + key + "'OR position ='" + key + "'AND status='" + 1 + "' ";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                // statement.close();
                //DefaultTableModel model = (DefaultTableModel) blockBtable.getModel();
                //model.setRowCount(0);
                JOptionPane.showMessageDialog(null, "Information is updated.");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }

            if (paymentstatusCombo.getSelectedItem().equals("Paid")) {
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
                    if (list.get(i).equals(datef.format(date))) {
                        dateAvail = "y";
                    }
                }

                if (dateAvail.equals("y")) {
                    try {
                        String sql = "select * from " + u_name + "_payment_status where date='" + datef.format(date) + "' ";
                        PreparedStatement st = connection.prepareStatement(sql);
                        ResultSet rs = st.executeQuery();
                        if (rs.next()) {

                            cashReceive = Integer.parseInt(extraFeeTF.getText()) + rs.getInt("cash_received");

                            try {
                                String sql_2 = "update " + u_name + "_payment_status set cash_received = '" + cashReceive + "' where date='" + datef.format(date) + "'";
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

                        statement2.setInt(1, Integer.parseInt(extraFeeTF.getText()));
                        statement2.setInt(2, 0);
                        statement2.setString(3, datef.format(date));
                        statement2.executeUpdate();
                    } catch (Exception e) {
                    }

                }

            }
            searchTF.setText("");
            nameTF.setText("");
            addressTF.setText("");
            phoneTF.setText("");
            emailTF.setText("");
            vehicalNoTF.setText("");
            vehicalTypeTF.setText("");
            positionTF.setText("");
            entryTimeTF.setText("");
            exitTimeTF.setText("");
            feeTF.setText("");
            extraFeeTF.setText("");
            totalTF.setText("");
            paymentstatusCombo.setSelectedIndex(0);
            nameTF.setEditable(false);
            phoneTF.setEditable(false);
            emailTF.setEditable(false);
            addressTF.setEditable(false);
            vehicalNoTF.setEditable(false);
            vehicalTypeTF.setEditable(false);
            positionTF.setEditable(false);
            extraFeeTF.setEditable(false);
            paymentstatusCombo.setEnabled(false);

            //home.setVisible(true);
            home.showDataTable();
        }


    }//GEN-LAST:event_btnUpdateMouseClicked

    private void btnUpdateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseEntered
        // TODO add your handling code here:
        setselectcolor(btnUpdate, fontupdate);
    }//GEN-LAST:event_btnUpdateMouseEntered

    private void btnUpdateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateMouseExited
        // TODO add your handling code here:
        resetselectcolor(btnUpdate, fontupdate);
    }//GEN-LAST:event_btnUpdateMouseExited

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
        newentry.setfeeAndPosition();
        home.setVisible(false);
        newentry.setVisible(true);
        viewProfile.setVisible(false);
        report.setVisible(false);
        notification.setVisible(false);
        requests.setVisible(false);
        addUser.setVisible(false);
        features.setVisible(false);
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItemLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLogOutActionPerformed
        // TODO add your handling code here:
        LogIn login = new LogIn();
        login.setVisible(true);

        this.dispose();

    }//GEN-LAST:event_jMenuItemLogOutActionPerformed

    private void jMenuItemNewPrimumCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNewPrimumCustomerActionPerformed
        // TODO add your handling code here:

        home.setVisible(false);
        newentry.setVisible(false);
        viewProfile.setVisible(false);
        report.setVisible(false);
        notification.setVisible(false);
        requests.setVisible(false);
        addUser.setVisible(true);
        features.setVisible(false);
    }//GEN-LAST:event_jMenuItemNewPrimumCustomerActionPerformed

    private void jMenuItemNewEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNewEntryActionPerformed
        // TODO add your handling code here:
        newentry.setfeeAndPosition();
        home.setVisible(false);
        newentry.setVisible(true);
        viewProfile.setVisible(false);
        report.setVisible(false);
        notification.setVisible(false);
        requests.setVisible(false);
        addUser.setVisible(false);
        features.setVisible(false);
    }//GEN-LAST:event_jMenuItemNewEntryActionPerformed

    private void jMenu1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenu1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1KeyPressed

    private void jMenuItemNewPrimumCustomerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenuItemNewPrimumCustomerKeyPressed
        // TODO add your handling code here:

        home.setVisible(false);
        newentry.setVisible(false);
        viewProfile.setVisible(false);
        report.setVisible(false);
        notification.setVisible(false);
        requests.setVisible(false);
        addUser.setVisible(true);
        features.setVisible(false);
    }//GEN-LAST:event_jMenuItemNewPrimumCustomerKeyPressed

    private void jMenuItemNewEntryKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenuItemNewEntryKeyPressed
        // TODO add your handling code here:
        newentry.setfeeAndPosition();
        home.setVisible(false);
        newentry.setVisible(true);
        viewProfile.setVisible(false);
        report.setVisible(false);
        notification.setVisible(false);
        requests.setVisible(false);
        addUser.setVisible(false);
        features.setVisible(false);
    }//GEN-LAST:event_jMenuItemNewEntryKeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        sensorDataUpdater = new SensorStatusUpdater(u_name);
        sensorDataUpdater.setVisible(true);


    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        setLocation = new SetLocation(u_id, u_name);
        setLocation.setVisible(true);

    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        setPassword = new SetPassword(u_id, u_name);
        setPassword.setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        home.setVisible(false);
        newentry.setVisible(false);
        viewProfile.setVisible(false);
        report.setVisible(false);
        notification.setVisible(false);
        requests.setVisible(false);
        addUser.setVisible(true);
        features.setVisible(false);
    }//GEN-LAST:event_jMenuItem11ActionPerformed
    public void SensorStatus() {

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
            java.util.logging.Logger.getLogger(MainFraim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFraim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFraim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFraim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFraim().setVisible(true);
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
        label.setForeground(Color.BLACK);
    }

    public void currentDateTime() {
        SimpleDateFormat dateformater = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeformater = new SimpleDateFormat("hh:mm:ss:aa");

        Date date = new Date();
        // System.out.println(dateformater.format(date));
        new_date = dateformater.format(date);
        String time = timeformater.format(date);

        //System.out.println("sen" + currentDate);
        dateMenu.setText("Current Date: " + new_date);
        dateMenu.setForeground(Color.red);
        timeMenu.setText("Starting Time: " + time);
        timeMenu.setForeground(Color.blue);

    }

    /* public void settableData(String name,String phone,String email,String address,String vehicalNo,
            String vehicalType,String position,String entryTime,int fee)
    {
        initComponents();
         SimpleDateFormat timeformater = new SimpleDateFormat("HH:mm:ss");

        Date date = new Date();
        String time = timeformater.format(date);
        
        nameTF.setText(name+"va");
        phoneTF.setText(phone);
        emailTF.setText(email);
        addressTF.setText(address);
        vehicalNoTF.setText(vehicalNo);
        vehicalTypeTF.setText(vehicalType);
        positionTF.setText(position);
        entryTimeTF.setText(entryTime);
        feeTF.setText(String.valueOf(fee));
        extraFeeTF.setText(String.valueOf(0));
        exitTimeTF.setText(time);
        nameTF.setText("aaaa");
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addUserButton;
    private javax.swing.JLabel addUserFont;
    public javax.swing.JTextArea addressTF;
    private javax.swing.JPanel btnRelese;
    private javax.swing.JPanel btnUpdate;
    private javax.swing.JLabel clearButton;
    private javax.swing.JPanel containerFrame;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JMenu dateMenu;
    public javax.swing.JTextField emailTF;
    public javax.swing.JTextField entryTimeTF;
    public javax.swing.JTextField exitTimeTF;
    public javax.swing.JTextField extraFeeTF;
    private javax.swing.JPanel featureButton;
    private javax.swing.JLabel featurefont;
    public javax.swing.JTextField feeTF;
    private javax.swing.JLabel font;
    private javax.swing.JLabel fontRelese;
    private javax.swing.JLabel fontViweprofile;
    private javax.swing.JLabel fontupdate;
    private javax.swing.JPanel homeButton;
    private javax.swing.JLabel homeFont;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem jMenuItemLogOut;
    private javax.swing.JMenuItem jMenuItemNewEntry;
    private javax.swing.JMenuItem jMenuItemNewPrimumCustomer;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    public javax.swing.JTextField nameTF;
    private javax.swing.JPanel newEntryButton;
    private javax.swing.JLabel newentryfont;
    private javax.swing.JPanel notificationButton;
    private javax.swing.JLabel notificationLabel;
    private javax.swing.JLabel notificationfont;
    private javax.swing.JTextField p10TF;
    private javax.swing.JTextField p11TF;
    private javax.swing.JTextField p12TF;
    private javax.swing.JTextField p13TF;
    private javax.swing.JTextField p14TF;
    private javax.swing.JTextField p1TF;
    private javax.swing.JTextField p2TF;
    private javax.swing.JTextField p3TF;
    private javax.swing.JTextField p4TF;
    private javax.swing.JTextField p5TF;
    private javax.swing.JTextField p6TF;
    private javax.swing.JTextField p7TF;
    private javax.swing.JTextField p8TF;
    private javax.swing.JTextField p9TF;
    public javax.swing.JComboBox<String> paymentstatusCombo;
    public javax.swing.JTextField phoneTF;
    public javax.swing.JTextField positionTF;
    private javax.swing.JPanel reportButton;
    private javax.swing.JLabel reportfont;
    private javax.swing.JLabel searchButton;
    private javax.swing.JTextField searchTF;
    private javax.swing.JTextField sensordataTF;
    private javax.swing.JLabel timeLael;
    private javax.swing.JMenu timeMenu;
    private javax.swing.JLabel titleLabel;
    public javax.swing.JTextField totalTF;
    private javax.swing.JPanel tutorialButton;
    private javax.swing.JLabel tutorialfont;
    public javax.swing.JTextField vehicalNoTF;
    public javax.swing.JTextField vehicalTypeTF;
    private javax.swing.JPanel viewprofileButton;
    // End of variables declaration//GEN-END:variables
}
