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
        System.out.println("KeyCode: "+e.getKeyCode()+"KeyChar: "+e.getKeyChar());
        if((e.getKeyCode() != 17)&&(e.getKeyCode() != 16)&&(e.getKeyCode() != 20)&&(e.getKeyCode() != 127)&&(e.getKeyCode() != 18)&&(e.getKeyCode() != 524)&&(e.getKeyCode() != 0)){
            input = bankTellingService.CharacterEntered(e.getKeyChar(),e.getKeyCode());
        }
        if(input != "r"){
            System.out.println(input);
            bankTellingService.input = "";
            bankTellingService.typedChars = new char[0];
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
