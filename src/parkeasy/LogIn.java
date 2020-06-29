/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkeasy;

import java.awt.Color;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Atiq
 */
public class LogIn extends javax.swing.JFrame {

    /**
     * Creates new form LogIn
     */
    public static Connection connection = null;
    String username, password;
    
    public LogIn() {
        initComponents();
        this.setLocationRelativeTo(this);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("parking.png")));
        connection = DbConnect.dbconnect();


    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        registerButton = new javax.swing.JPanel();
        fontregister = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        userNameTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        userPaswarField = new javax.swing.JPasswordField();
        recoveryPasswardButton = new javax.swing.JLabel();
        loginButton = new javax.swing.JPanel();
        fontlogin = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        errormessage = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(109, 122, 138));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/parking (8).png"))); // NOI18N
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel5.setFont(new java.awt.Font("Vijaya", 3, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("ParkEasy");

        registerButton.setBackground(new java.awt.Color(255, 255, 255));
        registerButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        registerButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerButtonMouseExited(evt);
            }
        });

        fontregister.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fontregister.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fontregister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Registration_30px.png"))); // NOI18N
        fontregister.setText("Register");
        fontregister.setIconTextGap(10);

        javax.swing.GroupLayout registerButtonLayout = new javax.swing.GroupLayout(registerButton);
        registerButton.setLayout(registerButtonLayout);
        registerButtonLayout.setHorizontalGroup(
            registerButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fontregister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        registerButtonLayout.setVerticalGroup(
            registerButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fontregister, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(registerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 44, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(45, 62, 80));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_User_20px.png"))); // NOI18N
        jLabel1.setText("User Name:");

        userNameTextField.setBackground(new java.awt.Color(109, 122, 138));
        userNameTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        userNameTextField.setForeground(new java.awt.Color(255, 255, 255));
        userNameTextField.setToolTipText("User Name");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(45, 62, 80));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Password_20px.png"))); // NOI18N
        jLabel2.setText("Password:");

        userPaswarField.setBackground(new java.awt.Color(109, 122, 138));
        userPaswarField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        userPaswarField.setForeground(new java.awt.Color(255, 255, 255));
        userPaswarField.setToolTipText("Passward");

        recoveryPasswardButton.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        recoveryPasswardButton.setForeground(new java.awt.Color(255, 60, 42));
        recoveryPasswardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Forgot_Password_20px_2.png"))); // NOI18N
        recoveryPasswardButton.setText("Forgote Password?");
        recoveryPasswardButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        recoveryPasswardButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recoveryPasswardButtonMouseClicked(evt);
            }
        });

        loginButton.setBackground(new java.awt.Color(255, 255, 255));
        loginButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        loginButton.setForeground(new java.awt.Color(51, 51, 51));
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButtonMouseExited(evt);
            }
        });

        fontlogin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fontlogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fontlogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Login_30px.png"))); // NOI18N
        fontlogin.setText("Login");
        fontlogin.setIconTextGap(10);

        javax.swing.GroupLayout loginButtonLayout = new javax.swing.GroupLayout(loginButton);
        loginButton.setLayout(loginButtonLayout);
        loginButtonLayout.setHorizontalGroup(
            loginButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loginButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fontlogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        loginButtonLayout.setVerticalGroup(
            loginButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fontlogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/group.png"))); // NOI18N

        errormessage.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        errormessage.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 40, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(loginButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(errormessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userPaswarField, javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(userNameTextField)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(recoveryPasswardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errormessage, javax.swing.GroupLayout.DEFAULT_SIZE, 15, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(userNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(userPaswarField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(recoveryPasswardButton)
                .addGap(18, 18, 18)
                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(249, 148, 6));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("X");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel9MouseMoved(evt);
            }
        });
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Log In Form");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("-");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addContainerGap())
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jLabel10)
                    .addContainerGap(431, Short.MAX_VALUE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel4.setBackground(new java.awt.Color(249, 148, 6));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:

        System.exit(0);
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel9MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel9MouseMoved

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabel11MouseClicked

    private void registerButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerButtonMouseClicked
        // TODO add your handling code here:

        Registration registration = new Registration();
        setclickedcolor(registerButton, fontregister);
        this.setVisible(false);
        registration.setVisible(true);
    }//GEN-LAST:event_registerButtonMouseClicked

    private void loginButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginButtonMouseClicked
        // TODO add your handling code here:
        setclickedcolor(loginButton, fontlogin);
        
        username = userNameTextField.getText();
        password = userPaswarField.getText();
        
        try {
            
            String sql = "select * from users where user_name=? and password=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userNameTextField.getText());
            statement.setString(2, userPaswarField.getText());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int user_id = rs.getInt("id");
                String s = rs.getString("user_type");
                if (s.equals("park_owner")) {
                    MainFraim ob = new MainFraim(user_id,username);
                    // MainFraim ob = new MainFraim();
                    //Test test=new Test();
                    //ob.sensorTableName(user_id);
                    ob.setVisible(true);
                    this.setVisible(false);
                    // test.setVisible(true);
                } else if (s.equals("customer")) {
                    JOptionPane.showMessageDialog(null, "You have no permission to open this page!");
                    this.setVisible(true);
                }
                
            } else {
                errormessage.setText("Incorrect user name or password");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        

    }//GEN-LAST:event_loginButtonMouseClicked

    private void recoveryPasswardButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recoveryPasswardButtonMouseClicked
        // TODO add your handling code here:
        RecoveryPassward recovery = new RecoveryPassward();
        this.setVisible(false);
        recovery.setVisible(true);
    }//GEN-LAST:event_recoveryPasswardButtonMouseClicked

    private void loginButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginButtonMouseEntered
        // TODO add your handling code here:
        setselectcolor(loginButton, fontlogin);
    }//GEN-LAST:event_loginButtonMouseEntered

    private void loginButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginButtonMouseExited
        // TODO add your handling code here:
        resetselectcolor(loginButton, fontlogin);
    }//GEN-LAST:event_loginButtonMouseExited

    private void registerButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerButtonMouseEntered
        // TODO add your handling code here:
        setselectcolor(registerButton, fontregister);
    }//GEN-LAST:event_registerButtonMouseEntered

    private void registerButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerButtonMouseExited
        // TODO add your handling code here:
        resetselectcolor(registerButton, fontregister);
    }//GEN-LAST:event_registerButtonMouseExited

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
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LogIn().setVisible(true);
                
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel errormessage;
    private javax.swing.JLabel fontlogin;
    private javax.swing.JLabel fontregister;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel loginButton;
    private javax.swing.JLabel recoveryPasswardButton;
    private javax.swing.JPanel registerButton;
    private javax.swing.JTextField userNameTextField;
    private javax.swing.JPasswordField userPaswarField;
    // End of variables declaration//GEN-END:variables
}
