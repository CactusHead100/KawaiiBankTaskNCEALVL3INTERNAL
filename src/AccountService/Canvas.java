package AccountService;
import java.awt.Color;
import javax.swing.*;

public class Canvas extends JFrame {
    final int canvasWidth = 652;
    final int canvasHeight = 675;

    Canvas(BankTellingService bankTellingService){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(canvasWidth,canvasHeight);
        this.add(bankTellingService);
        this.setResizable(false);
        this.setVisible(true);
    }
}
