package AccountService;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener{
    private String input;

    private int keyCodeA = 65;
    private int keyCodeZ = 90;
    private int keyCode0 = 48;
    private int keycode9 = 57;
    private int keyCodeExceptionBackspace = 8;
    private int keyCodeExceptionEnter = 10;
    private int keyCodeExceptionSpace = 32;

    private BankTellingService bankTellingService;
    public KeyboardInput(BankTellingService bankTellingService){
        this.bankTellingService = bankTellingService;
    }

    @Override
    public  void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(correctInput(e.getKeyCode())){
            input = bankTellingService.CharacterEntered(e.getKeyChar(),e.getKeyCode());
            bankTellingService.invalidText = false;
        }else{
            bankTellingService.invalidText = true;
            bankTellingService.repaint();
        }
        if(input != "r"){
            bankTellingService.input = "";
            bankTellingService.typedChars = new char[0];
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    private boolean correctInput(int charKeyCode){
        if((charKeyCode == keyCodeExceptionSpace)||(charKeyCode == keyCodeExceptionEnter)||((charKeyCode >= keyCodeA)&&(charKeyCode <= keyCodeZ))||((charKeyCode >= keyCode0)&&(charKeyCode <= keycode9))||((charKeyCode == keyCodeExceptionBackspace)&&(bankTellingService.typedChars.length != 0))){
            return true;
        }
        System.out.println("false");
        return false;
    }

}
