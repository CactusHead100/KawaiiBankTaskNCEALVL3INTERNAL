package AccountService;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JPanel;

import AccountService.Accounts.ACCOUNTS;

public class BankTellingService extends JPanel{
    private KeyboardInput keyboardInput = new KeyboardInput(this);

    final int enterKeyVal = 10;

    final int canvasHeight = 640;
    final int canvasWidth = 640;

    File userDetails =  new File("src//AccountService//UserInfo.csv");
    ArrayList<Accounts> users = new ArrayList<Accounts>();

    Boolean invalidText = false;

    int mouseX;
    int mouseY;

    char[] typedChars = new char[0];
    String input = "";

    String keyBoardInput;
    BankTellingService (){
        addKeyListener(keyboardInput);
        try{
            Scanner infoGetter = new Scanner(userDetails);
            while(infoGetter.hasNextLine()){
                String info = infoGetter.nextLine();
                CreateNewAccount(info);
                System.out.println(info);
            }
            infoGetter.close();
        }catch(IOException e){
            System.out.println("fail");
        }
    }
    public void CreateNewAccount(String customerInfo){
        Accounts.ACCOUNTS accountType = null;
        String[] individualInfo = customerInfo.split(",");
        switch (individualInfo[3]) {
            case "Savings":
                accountType  = Accounts.ACCOUNTS.Savings;
            break;
            case "Everyday":
                accountType = Accounts.ACCOUNTS.Everyday;
            break;
            case "Current":
                accountType = Accounts.ACCOUNTS.Current;
            break;
        }
        Double balance = Double.parseDouble(individualInfo[4]);
        users.add(new Accounts(individualInfo[0],individualInfo[1],individualInfo[2],accountType,balance));
    }
    /*
     *  
     */
    public String CharacterEntered(char character, int charVal){
        if(charVal == enterKeyVal){
            return input;
        }else if((charVal == 8)&&(typedChars.length > 0)){
            typedChars = SizeChanger(typedChars,false,character);
        }else{
            typedChars = SizeChanger(typedChars,true,character);
        }
        input ="";
        for(int i = 0; i < typedChars.length; i++){
            input = input + typedChars[i]; 
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