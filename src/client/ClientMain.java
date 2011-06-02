package client;

/**
 *
 * @author Nathan
 */
public class ClientMain {
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new LoginGUI().setVisible(true);
                ClientGUI client = new ClientGUI("test");
                client.init();
                client.setVisible(true);
            }
        });
    }
}
