package AccountService;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class BankTellingService extends JPanel{
    private KeyboardInput keyboardInput = new KeyboardInput(this);

    final int canvasHeight = 640;
    final int canvasWidth = 640;

    int mouseX;
    int mouseY;

    char[] typedChars = new char[0];
    String input = "";

    String keyBoardInput;
    BankTellingService (){
        addKeyListener(keyboardInput);
    }
    public void CreateNewAccount(){

    }
    public String CharacterEntered(char character, int charVal){
        if(charVal == 10){
            for(int i = 0; i < typedChars.length; i++){
                input = input + typedChars[i]; 
                System.out.println(typedChars[i]);
            }
            System.out.println(input);
            return input;
        }else if((charVal == 8)&&(typedChars.length > 0)){
            typedChars = SizeChanger(typedChars,false,character);
        }else{
            typedChars = SizeChanger(typedChars,true,character);
        }
        return "r";
    }
    public char[] SizeChanger(char[] oldString,boolean increase, char newChar){
        if(increase){
            char[] newString = new char[oldString.length+1];
            for(int i = 0; i < oldString.length;i++){
                newString[i] = oldString[i];
            }
            newString[oldString.length] = newChar;
            return newString;
        }else{
            char[] newString = new char[oldString.length-1];
            for(int i = 0; i < oldString.length-1;i++){
                newString[i] = oldString[i];
            }
            return newString;
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.clearRect(0,0,canvasWidth, canvasHeight);
    }
}