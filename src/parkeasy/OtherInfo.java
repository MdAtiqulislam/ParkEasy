/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkeasy;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Atiq
 */
public class OtherInfo extends javax.swing.JPanel {

    /**
     * Creates new form OtherInfo
     */

    String garagecapacity,workinghour,rate,securityquestion,answer;
    public OtherInfo() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        garagecapacityTF = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        workinghourTF = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        rateTF = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        answerTF = new javax.swing.JTextField();
        securityquestionComboBox = new javax.swing.JComboBox<>();
        chosePhotoButton = new javax.swing.JPanel();
        fontChosePhoto = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 204));
        jLabel15.setText("Other Informations:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(45, 62, 80));
        jLabel19.setText("Garage Capacity:");

        garagecapacityTF.setBackground(new java.awt.Color(109, 122, 138));
        garagecapacityTF.setForeground(new java.awt.Color(255, 255, 255));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(45, 62, 80));
        jLabel20.setText("Working Hour:");

        workinghourTF.setBackground(new java.awt.Color(109, 122, 138));
        workinghourTF.setForeground(new java.awt.Color(255, 255, 255));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(45, 62, 80));
        jLabel21.setText("Rate:");

        rateTF.setBackground(new java.awt.Color(109, 122, 138));
        rateTF.setForeground(new java.awt.Color(255, 255, 255));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(45, 62, 80));
        jLabel22.setText("Security Question:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(45, 62, 80));
        jLabel23.setText("Answer:");

        answerTF.setBackground(new java.awt.Color(109, 122, 138));
        answerTF.setForeground(new java.awt.Color(255, 255, 255));

        securityquestionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one question", "What is your favorite car?", "What is your favorite car color?", " " }));

        chosePhotoButton.setBackground(new java.awt.Color(255, 255, 255));
        chosePhotoButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        chosePhotoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chosePhotoButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                chosePhotoButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                chosePhotoButtonMouseExited(evt);
            }
        });

        fontChosePhoto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fontChosePhoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fontChosePhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parkeasy/icons/icons8_Google_Images_30px_1.png"))); // NOI18N
        fontChosePhoto.setText("Chose Photo");
        fontChosePhoto.setIconTextGap(8);

        javax.swing.GroupLayout chosePhotoButtonLayout = new javax.swing.GroupLayout(chosePhotoButton);
        chosePhotoButton.setLayout(chosePhotoButtonLayout);
        chosePhotoButtonLayout.setHorizontalGroup(
            chosePhotoButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chosePhotoButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fontChosePhoto, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addContainerGap())
        );
        chosePhotoButtonLayout.setVerticalGroup(
            chosePhotoButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fontChosePhoto, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21)
                            .addComponent(jLabel20)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(workinghourTF)
                            .addComponent(rateTF, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(securityquestionComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, 298, Short.MAX_VALUE)
                            .addComponent(answerTF, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(garagecapacityTF)
                            .addComponent(chosePhotoButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(garagecapacityTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(workinghourTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(rateTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(securityquestionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(answerTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chosePhotoButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void chosePhotoButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chosePhotoButtonMouseClicked
        // TODO add your handling code here:
        setclickedcolor(chosePhotoButton, fontChosePhoto);
    }//GEN-LAST:event_chosePhotoButtonMouseClicked

    private void chosePhotoButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chosePhotoButtonMouseEntered
        // TODO add your handling code here:
        setselectcolor(chosePhotoButton, fontChosePhoto);
    }//GEN-LAST:event_chosePhotoButtonMouseEntered

    private void chosePhotoButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chosePhotoButtonMouseExited
        // TODO add your handling code here:
        resetselectcolor(chosePhotoButton, fontChosePhoto);
    }//GEN-LAST:event_chosePhotoButtonMouseExited

    
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
    
    String garagecapacity(){
        garagecapacity=garagecapacityTF.getText();
        return garagecapacity;
    }
        String workinghour(){
        workinghour=workinghourTF.getText();
        return workinghour;
    }
            String rate(){
        rate=rateTF.getText();
        return rate;
    }
                String securiquestion(){
        securityquestion=(String) securityquestionComboBox.getSelectedItem();
        return securityquestion;
    }
                    String answer(){
        answer=answerTF.getText();
        return answer;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField answerTF;
    private javax.swing.JPanel chosePhotoButton;
    private javax.swing.JLabel fontChosePhoto;
    private javax.swing.JTextField garagecapacityTF;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JTextField rateTF;
    private javax.swing.JComboBox<String> securityquestionComboBox;
    private javax.swing.JTextField workinghourTF;
    // End of variables declaration//GEN-END:variables
}
