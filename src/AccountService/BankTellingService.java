package AccountService;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class BankTellingService extends JPanel{
    BankTellingService (){
        new Accounts("Koen", 1, "La dee Doo daa", Accounts.ACCOUNTS.Current, 0);
    }
    public void CreateNewAccount(){

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.CYAN);
        g2d.fillRect(0,0,100,100);
    }
}