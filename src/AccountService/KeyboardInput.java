package AccountService;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener{
    private String input;

    private BankTellingService bankTellingService;
    public KeyboardInput(BankTellingService bankTellingService){
        this.bankTellingService = bankTellingService;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println(e);
        System.out.println(bankTellingService.CharacterEntered(e.getKeyChar(),e.getKeyCode()));
        //bankTellingService.CharacterEntered(e.getKeyChar(),e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
