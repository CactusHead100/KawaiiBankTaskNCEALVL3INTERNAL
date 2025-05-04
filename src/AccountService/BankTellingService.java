package AccountService;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.io.IOException;

import javax.swing.JPanel;

public class BankTellingService extends JPanel{
    private KeyboardInput keyboardInput = new KeyboardInput(this);

    final int enterKeyVal = 10;

    final int canvasHeight = 640;
    final int canvasWidth = 640;
    final Color backgroundColor = Color.white;
    
    final int textHorizontalPlacement = (canvasWidth/4);
    final int textVerticalPlacement = (canvasHeight/2);
    final int typedTextSize = 25;
    final Color textColor = Color.black;
    final int textBoxX = textHorizontalPlacement-5;
    final int textBoxY = (canvasHeight/2)-typedTextSize;
    final int textBoxWidth = canvasWidth/2+10;
    final int textBoxHeight = typedTextSize+5;
    final int textBoxCurviness = 20;

    File userDetails =  new File("src//AccountService//UserInfo.csv");
    HashMap<String,Accounts> users = new HashMap<String,Accounts>();

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
            }
            infoGetter.close();
        }catch(IOException e){
        }
    }
    /*
     * takes a string (a line read prior from the csv file) and splits it down into the variables we want then creates an object containg them all
     */
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
        users.put(individualInfo[0],new Accounts(individualInfo[0],individualInfo[1],individualInfo[2],accountType,balance));
    }
    public void DeleteAccount(String accountName){
        System.out.println("Current Users: "+users);
        users.remove(accountName);
        System.out.println("Current Users: "+users);
    }
    /*
     * A method that calls another depending on whether it wants to add or decrease characters in an array then returns the string back to where it was first called.
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
    /*
     * creates a new array either one bigger or smaller then the old one and copies the old information over and either adds the next character or deletes one
     */
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

        /*
         * specifies the asthetics of the text then draws it 
         */
        g2d.setColor(textColor);
        g2d.setFont(new Font("Arial", Font.PLAIN, typedTextSize));
        g2d.drawString(input,textHorizontalPlacement,textVerticalPlacement);
        /*
         * coveers any text exiting the box allowing for a smooth clean feel
         */
        g2d.setColor(backgroundColor);
        g2d.fillRect(textBoxX+textBoxWidth, textBoxY, textHorizontalPlacement, textBoxHeight);
        /*
         * Drawws the text box to indicate where to type
         */
        g2d.setColor(Color.black);
        g2d.drawRoundRect(textBoxX, textBoxY, textBoxWidth, textBoxHeight, textBoxCurviness, textBoxCurviness);

        if(invalidText){
            g2d.setColor(Color.red);
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            g2d.drawString("INVALID",canvasWidth/2-50,canvasHeight/2+50);
        }
    }
}