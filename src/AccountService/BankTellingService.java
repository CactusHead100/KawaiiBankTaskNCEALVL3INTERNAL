package AccountService;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class BankTellingService extends JPanel{
    private KeyboardInput keyboardInput = new KeyboardInput(this);

    final int canvasHeight = 640;
    final int canvasWidth = 640;

    

    Boolean invalidText = false;

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
        System.out.println("sent: "+input);
        if(charVal == 10){
            return input;
        }else if((charVal == 8)&&(typedChars.length > 0)){
            typedChars = SizeChanger(typedChars,false,character);
        }else{
            typedChars = SizeChanger(typedChars,true,character);
        }
        input ="";
        for(int i = 0; i < typedChars.length; i++){
            input = input + typedChars[i]; 
            System.out.println(input);
        }
        repaint();
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

        System.out.println("recieved: "+input);
        g2d.setColor(Color.black);
        g2d.setFont(new Font("Arial", Font.PLAIN, 25));
        g2d.drawString(input,canvasWidth/2,canvasHeight/2);

        if(invalidText){
            g2d.setColor(Color.red);
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            g2d.drawString("INVALID",canvasWidth/2,canvasHeight/2+50);
        }
    }
}