/*
 * LoginGUI.java
 *
 * Created on Apr 3, 2011, 2:48:07 AM
 */

package client;

import java.io.*;
import java.net.*;
import javax.swing.*;

/**
 *
 * @author Nathan
 */
public class LoginGUI extends JFrame {

    /** Creates new form LoginGUI */
    public LoginGUI() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usernameLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        usernameInput = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        passwordInput = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CodeBuddy Alpha");
        setResizable(false);

        usernameLabel.setText("Username:");

        passwordLabel.setText("Password:");

        usernameInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                usernameInputKeyPressed(evt);
            }
        });

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        passwordInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordInputKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("CoderBuddy");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(usernameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(usernameInput, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(passwordLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(loginButton)
                                    .addComponent(passwordInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(44, 44, 44))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {passwordLabel, usernameLabel});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {passwordInput, usernameInput});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(usernameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(loginButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        checkCredentials();
    }//GEN-LAST:event_loginButtonActionPerformed

    private void passwordInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordInputKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            checkCredentials();
        }
    }//GEN-LAST:event_passwordInputKeyPressed

    private void usernameInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameInputKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            passwordInput.grabFocus();
        }
    }//GEN-LAST:event_usernameInputKeyPressed

    /**
     * Check whether the current username/password combination is valid.
     */
    private void checkCredentials() {
        final String user = usernameInput.getText();
        final String pass = passwordInput.getText();
        //boolean valid = false;
        boolean valid = true;

        if (user.length() >= 3 && user.length() <= 16 &&
                user.matches("[A-Za-z0-9_.]+")) {
            // TODO initiate server communication
        	String srvrAddr = "minthe.ugcs.caltech.edu";
        	Socket srvrSoc = null;
        	PrintWriter srvrOut = null;
        	BufferedReader srvrIn = null;

        if (user.length() >= 3 && user.length() <= 16 &&
                user.matches("[A-Za-z0-9_.]+") && pass.length() >= 3) {        	
        	try {
        		ConnectionManager.openConnection();
        	} catch (Exception e) {
        		JOptionPane.showMessageDialog(null,
                        "Error: Could not establish connection with server.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	
        	ConnectionManager.sendString(user + ":" + pass);
        	try {
        		String srvrResponse = ConnectionManager.readLine();
        		if (srvrResponse.equals("accept"))
        			valid = true;
        	} catch (Exception e) {
        		JOptionPane.showMessageDialog(null,
                        "Error: Communication with server interrupted.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
        		return;
        	}        	
        	try {
        		srvrSoc.close();
        	} catch (IOException e) {
        		
        	}
        }

        
        if (valid) {
        	// TODO this is dirty--it means the login window disappears and
            // the chat program starts, but in reality the login window is just
            // hiding, serving as the parent of the chat window.
            setVisible(false);
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    ClientGUI client = new ClientGUI(user);
                    client.init();
                    client.setVisible(true);                    
                }
            });
        } else {
            usernameInput.setText("");
            passwordInput.setText("");
            JOptionPane.showMessageDialog(null,
                                          "Error: Invalid login information.",
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordInput;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JTextField usernameInput;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables

}
