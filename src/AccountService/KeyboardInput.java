package AccountService;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import AccountService.BankTellingService.Screen;

public class KeyboardInput implements KeyListener{
    private Boolean nextChar;
    /*
     * all the keycodes and exemptions used to show what can be typed but im sure the bankteller knows what they are doing so shouldn't be totally neccessar
     */
    private int keyCodeA = 65;
    private int keyCodeZ = 90;
    private int keyCode0 = 48;
    private int keycode9 = 57;
    private int keyCodeExceptionBackspace = 8;
    private int keyCodeExceptionEnter = 10;
    private int keyCodeExceptionSpace = 32;
    private int keyCodeExceptionPeriod = 46;

    private BankTellingService bankTellingService;
    public KeyboardInput(BankTellingService bankTellingService){
        this.bankTellingService = bankTellingService;
    }

    @Override
    public  void keyTyped(KeyEvent e) {

    }
    /*
     * when a key is typed checks if it is an applicable key then calls a function in banktelling service class and gives it the key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if((bankTellingService.currentScreen != Screen.OptionMenu)&&(bankTellingService.currentScreen != Screen.EndDay)){
            if(correctInput(e.getKeyCode())){
                nextChar = bankTellingService.CharacterEntered(e.getKeyChar(),e.getKeyCode());
            }else{
                bankTellingService.repaint();
            }
            if(nextChar){
                bankTellingService.input = "";
                bankTellingService.typedChars = new char[0];
                bankTellingService.repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    /*
     * checks if the input is applicable or just random nonsense
     */
    private boolean correctInput(int charKeyCode){
            if((charKeyCode == keyCodeExceptionSpace)||(charKeyCode == keyCodeExceptionPeriod)||(charKeyCode == keyCodeExceptionEnter)||((charKeyCode >= keyCodeA)&&(charKeyCode <= keyCodeZ))||((charKeyCode >= keyCode0)&&(charKeyCode <= keycode9))||((charKeyCode == keyCodeExceptionBackspace)&&(bankTellingService.typedChars.length != 0))){
                return true;
            }
            return false;
    }
}
