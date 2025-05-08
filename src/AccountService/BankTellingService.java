package AccountService;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FileWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.lang.StackWalker.Option;

import javax.lang.model.SourceVersion;
import javax.swing.JPanel;

public class BankTellingService extends JPanel{
    private KeyboardInput keyboardInput = new KeyboardInput(this);
    private MouseInput mouseInput = new MouseInput(this);
    /*
     * the keycode value for the enter key
     */
    final int enterKeyVal = 10;
    /*
     * the canvas size in pixels and the background colour i want (should be pretty obvious...)
     */
    final int canvasHeight = 640;
    final int canvasWidth = 640;
    final Color backgroundColor = Color.cyan;
    /*
     * A screen enum containing the different states the program could be in
     */
    public enum Screen{OptionMenu, BalanceChecker, BalanceChanger, AccountCreater, AccountDeleter, EndDay};
    public Screen currentScreen = Screen.OptionMenu;
    private String[] headers = {"Menu","Balance","Transactions","Create Account","Delete Account"};
    /*
     * placement for the text and textbox on the canvas and other related things
     */
    final int textHorizontalPlacement = (canvasWidth/4);
    final int textVerticalPlacement = (canvasHeight/2);
    final int typedTextSize = 25;
    final Font typedTextFont = new Font("Arial", Font.PLAIN, typedTextSize);
    final Color textColor = Color.black;
    final int textBoxX = textHorizontalPlacement-5;
    final int textBoxY = (canvasHeight/2)-typedTextSize;
    final int textBoxWidth = canvasWidth/2+10;
    final int textBoxHeight = typedTextSize+5;
    final int textBoxCurviness = 20;
    /*
     * Creates an array of rectangles
     */
    final int numberOfButtons = 5;
    final int buttonX = canvasWidth/4;
    final int buttonWidth = canvasWidth/2;
    final int buttonHeight = 60;
    final int headerSize = buttonHeight*2;
    final int buttonGap = ((canvasHeight-headerSize)/numberOfButtons - buttonHeight)/2;
    final int buttonTextSize = 35;
    final Font buttonTextFont = new Font("Arial", Font.PLAIN, buttonTextSize);
    final int buttonTextOffeset = 3;
    final String[] buttonText = {"Veiw Balance","Deposit or Withdraw","Create Account","Delete Account","End Day","Return to Menu","Deposit","Withdraw"};
    Buttons buttons[];
    /*
     * used for text expressed right below the text input box and above it
     */
    final int generalFontSize = 40;
    final int generalFontPlacement = canvasHeight*5/8;
    final Font generalFont = new Font("Arial", Font.PLAIN, generalFontSize);
    final int headerFontSize = 80;
    final int headerFontPlacement = canvasHeight*5/32;
    final Font headerFont = new Font("Arial", Font.PLAIN, headerFontSize);
    /*
     * The file being initialized and a dictionary as to me it makes it very easy accessing people's accounts (using the key)
     */
    File userDetails =  new File("src//AccountService//UserInfo.csv");
    HashMap<String,Accounts> users = new HashMap<String,Accounts>();
    final String bankNumber = "08-0104-0";
    /*
     * A balance variable that is set then drawn when in the balance screen 
     */
    private String textToDraw = "";
    /*
     * allows me to know if im grabbing someones account info or putting money into it
     */
    private boolean foundUser = false;
    private String currentUserInfo[];
    /*
     * used to track overall withdrawls and deposits so i can express them when the day ends
     */
    public static Double totalWithdrawls = 0.0;
    public static Double totalDeposits = 0.0;
    /*
     * to track mouse position so we can know if i clicked something correctly
     */
    int mouseX;
    int mouseY;
    /*
     * create a array of characters as it makes it easy to delete the last inputted one and a string so i can do stuff wit hwhat they typed
     */
    char[] typedChars = new char[0];
    String input = "";
    /*
     * allows for keyboard input
     */
    BankTellingService (){
        addKeyListener(keyboardInput);
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        /*
         * Passes infomation from the file to the account creator which creates accounts using this data
         */
        try{
            Scanner infoGetter = new Scanner(userDetails);
            while(infoGetter.hasNextLine()){
                String info = infoGetter.nextLine();
                CreateNewAccount(info);
            }
            infoGetter.close();
        }catch(IOException e){
        }
        createMenuButtons();
    }
    /*
     * creates menu buttons so that we can get back to the menu easily
     */
    public void createMenuButtons(){
        buttons = new Buttons[numberOfButtons];
        buttons[0] = new Buttons(buttonX, buttonGap+headerSize, buttonWidth, buttonHeight, textBoxCurviness, buttonText[0]);
        for(int i = 1; i < numberOfButtons;i++){
            buttons[i] = new Buttons(buttonX, buttonGap*(2*i+1)+buttonHeight*i+headerSize, buttonWidth, buttonHeight, textBoxCurviness, buttonText[i]);
        }
        input = "";
        typedChars = new char[0];
        textToDraw = "";
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
    /*
     * gets called when a click occurs and then changes the menu depending on the location
     */
    public void clickOccurred(int x, int y){
        switch (currentScreen) {
            case Screen.OptionMenu:
                if(buttons[0].isClicked(x,y)){
                    currentScreen = Screen.BalanceChecker;
                    createBackToMenuButton(1);
                }else if(buttons[1].isClicked(x,y)){
                    currentScreen = Screen.BalanceChanger;
                    foundUser = false;
                    createBackToMenuButton(3);
                    buttons[1] = new Buttons(canvasWidth/16, buttonGap*7+buttonHeight*3+headerSize, buttonWidth*3/4, buttonHeight, textBoxCurviness, buttonText[6]);
                    buttons[2] = new Buttons(canvasWidth*9/16, buttonGap*7+buttonHeight*3+headerSize, buttonWidth*3/4, buttonHeight, textBoxCurviness, buttonText[7]);
                    repaint();
                }else if(buttons[2].isClicked(x,y)){
                    currentScreen = Screen.AccountCreater;

                    currentUserInfo = new String[3];
                    createBackToMenuButton(1);
                }else if(buttons[3].isClicked(x,y)){
                    currentScreen = Screen.AccountDeleter;

                    createBackToMenuButton(1);
                }else if(buttons[4].isClicked(x, y)){
                    currentScreen = Screen.EndDay;

                    writeBackToFile();
                    buttons = new Buttons[1];
                    buttons[0] = new Buttons(buttonX, buttonGap*9+buttonHeight*4+headerSize, buttonWidth, buttonHeight, textBoxCurviness, "Exit");
                    repaint();
                }
            break;
            case Screen.BalanceChecker:
                backToMenu(x, y);
            break;
            case Screen.BalanceChanger:
                backToMenu(x, y);
                    /*
                     * when eiher the deposit or withdraw are hit it calls a function which trnasfers the money and says if it was succseful doing so or not
                     */
                if(buttons[1].isClicked(x, y)&&foundUser){
                    succsessfulTransfer(false);
                }else if(buttons[2].isClicked(x, y)&&foundUser){
                    succsessfulTransfer(true);
                }
            break;
            case Screen.AccountCreater:
                backToMenu(x, y);
            break;
            case Screen.AccountDeleter:
                backToMenu(x, y);
            break;
            case Screen.EndDay:
                System.exit(0);
            break;
        }
    }
    /*
     * writes the current data back to file
     */
    private void writeBackToFile(){
        Set<String> setOfUsers = users.keySet();
            try {
                FileWriter fileWriter = new FileWriter(userDetails);
                for(String key : setOfUsers){
                    fileWriter.write(users.get(key).returnInfo()+"\n");
                }
                fileWriter.flush();
                    fileWriter.close();
            } catch (Exception e) {
            }
        }
    /*
     * creates a button that leads back to the menu 
     */
    private void createBackToMenuButton(int numberToCreate){
        buttons = new Buttons[numberToCreate];
        buttons[0] = new Buttons(buttonX, buttonGap*9+buttonHeight*4+headerSize, buttonWidth, buttonHeight, textBoxCurviness, buttonText[5]);
        textToDraw = "Enter Account Name";
        repaint();
    }
    /*
     * checks if they are clicked and if so brings it back to the menu
     */
    private void backToMenu(int x,int y){
        if(buttons[0].isClicked(x,y)){
            currentScreen = Screen.OptionMenu;
            createMenuButtons();
            repaint();
        }
    }
    /*
     * wrote this to lessen code 
     */
    private void succsessfulTransfer(boolean withdrawing){
        try {
            Double amount = Double.parseDouble(input);
            if(users.get(currentUserInfo[0]).siphonMoney(amount, withdrawing)){
                textToDraw = "Balance: $"+users.get(currentUserInfo[0]).balance;
            }else{
                textToDraw = "Fail";
            }
        } catch (Exception e) {
            textToDraw = "Invalid";
        }
        repaint();
    }
    /*
     * takes the text typed and then tries to find key using that string in teh dictionary then attempts to print that users balance
     */
    public void responseToInput(String input){
        switch (currentScreen) {
            case Screen.BalanceChecker:
                try {
                    textToDraw = "$"+Double.toString(users.get(input).balance);
                } catch (Exception e) {
                    textToDraw = "User Not Found";
                }
            break;
            case Screen.BalanceChanger:
                if(foundUser == false){
                    currentUserInfo = new String[1];  
                    if(users.containsKey(input)){
                        currentUserInfo[0] = input;
                        foundUser = true;
                        textToDraw = "Type Amount";
                        }else{
                        textToDraw = "User Not Found";
                    }
                }
            break;
            /*
             * each iteration of being called (after a new string has been entered) it will store this then once all has been entered will create the account
             */
            case Screen.AccountCreater:
                if((currentUserInfo[0] == null)){
                    if(!users.containsKey(input)){
                        currentUserInfo[0] = input;
                        textToDraw = "Enter Adress";
                    }
                }else if(currentUserInfo[1] == null){
                    currentUserInfo[1] = input;
                    textToDraw = "Enter Account Type";
                }else if(currentUserInfo[2] == null){
                    if((input.equals("Savings"))||input.equals("Current")||input.equals("Everyday")){
                        currentUserInfo[2] = input;
                        if(!users.containsKey(currentUserInfo[0])){
                            uniqueNumberCreator();
                    
                            CreateNewAccount(currentUserInfo[0]+","+currentUserInfo[1]+","+uniqueNumberCreator()+","+currentUserInfo[2]+",0");
                            textToDraw = "Success";
                            currentUserInfo = new String[4];
                        }
                    }
                }
            break; 
            case Screen.AccountDeleter:
                if(users.containsKey(input)){
                    users.remove(input);
                    textToDraw = "Removed";
                }else{
                    textToDraw = "Not Found";
                }
            break;         
        }
    }
    /*
     * creates a unique bank number
     */
    private String uniqueNumberCreator(){
        Set<String> setOfUsers = users.keySet();
        Random randNum = new Random();
        String sixDigitNumber = Integer.toString(randNum.nextInt(0,10))+Integer.toString(randNum.nextInt(0,10))+
        Integer.toString(randNum.nextInt(0,10))+Integer.toString(randNum.nextInt(0,10))+
        Integer.toString(randNum.nextInt(0,10))+Integer.toString(randNum.nextInt(0,10));
        
        for(String key : setOfUsers){
            if(!users.get(key).ACCOUNTNUMBER.equals(bankNumber+sixDigitNumber+"00")){
                return bankNumber+sixDigitNumber+"-00";
            }else{
                return uniqueNumberCreator();
            }
        }
        return "";
    }
    /*
     * A method that calls another depending on whether it wants to add or decrease characters in an array then returns the string back to where it was first called.
     */
    public Boolean CharacterEntered(char character, int charVal){
        if(charVal == enterKeyVal){
            responseToInput(input);
            return true;
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
        return false;
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
    /*
     * a paint class that draws everything on the canvas 
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();
        /*
         * clears all previous drawings so that they don't stack infinitly every time i draw (not sure if this could be lag inducing but it helps me mentally asses my code)
         */
        g2d.clearRect(0,0,canvasWidth, canvasHeight);
        /*
         * draws a background (had trouble previously trying to set canvas colour so eceided to instead draw my own background)
         */
        g2d.setColor(backgroundColor);
        g2d.fillRect(0,0,canvasWidth,canvasHeight);
        /*
         * specifies the asthetics of the text then draws it 
         */
        for(int i = 0; i < buttons.length;i++){
            g2d.setColor(Color.green);
            g2d.fill(buttons[i].shape);
            g2d.setColor(textColor);
            g2d.setFont(buttonTextFont);

            g2d.drawString(buttons[i].text,((buttons[i].x+(buttons[i].width/2))-((int)buttonTextFont.getStringBounds(buttons[i].text,frc).getWidth())/2),//gets the width of the string and then uses that to center it on the canvas
            buttons[i].y+buttonTextSize+buttonTextOffeset*2);
        }
        if((currentScreen != Screen.OptionMenu)&&(currentScreen != Screen.EndDay)){
            String headerText="";
            switch (currentScreen) {
                case Screen.BalanceChecker:
                    headerText = headers[1];
                break;
                case Screen.BalanceChanger:
                    headerText = headers[2];
                break;
                case Screen.AccountCreater:
                    headerText = headers[3];
                break;
                case Screen.AccountDeleter:
                    headerText = headers[4];
                break;
            }
            /*
            * draws the text box and text for all pages apart from menu and end screen that's under the input box
            */
            g2d.setColor(textColor);
            g2d.setFont(typedTextFont);
            g2d.drawString(input,textHorizontalPlacement,textVerticalPlacement);
            /* 
             * Draws the Header for each Screen
            */
            g2d.setFont(headerFont);
            g2d.drawString(headerText,(canvasWidth-(int)(headerFont.getStringBounds(headerText,frc).getWidth()))/2,headerFontPlacement);
            /*
            * coveers any text exiting the box allowing for a smooth clean feel
            */
            g2d.setColor(backgroundColor);
            g2d.fillRect(textBoxX+textBoxWidth, textBoxY, textHorizontalPlacement, textBoxHeight);
            /*
            * Drawws the text box to indicate where to type
            */
            g2d.setColor(textColor);
            g2d.drawRoundRect(textBoxX, textBoxY, textBoxWidth, textBoxHeight, textBoxCurviness, textBoxCurviness);
            if(currentScreen != Screen.OptionMenu){
                g2d.setFont(generalFont);
                g2d.drawString(textToDraw, (canvasWidth-(int)(generalFont.getStringBounds(textToDraw,frc).getWidth()))/2,generalFontPlacement);
            }
        }else if(currentScreen == Screen.EndDay){
            /*
             * makes sure that the are no rounding errors
             */
            totalDeposits = Math.round(totalDeposits*100.0)/100.0;
            totalWithdrawls = Math.round(totalWithdrawls*100.0)/100.0;
            /*
             * draws the text showing what happened that day
             */
            g2d.setFont(generalFont);
            g2d.drawString("Total Deposits: ", (canvasWidth-(int)(generalFont.getStringBounds("Total Deposits: ".toString(),frc).getWidth()))/2,canvasHeight/8);
            g2d.drawString("$"+totalDeposits.toString(), (canvasWidth-(int)(generalFont.getStringBounds("$"+totalDeposits.toString().toString(),frc).getWidth()))/2,canvasHeight*2/8);
            g2d.drawString("Total Withdrawls: ", (canvasWidth-(int)(generalFont.getStringBounds("Total Withdrawls: ".toString(),frc).getWidth()))/2,canvasHeight*4/8);
            g2d.drawString("$"+totalWithdrawls.toString(), (canvasWidth-(int)(generalFont.getStringBounds("$"+totalWithdrawls.toString(),frc).getWidth()))/2,canvasHeight*6/8);
        }else{
            g2d.setFont(headerFont);
            g2d.drawString(headers[0], (canvasWidth-(int)(headerFont.getStringBounds(headers[0].toString(),frc).getWidth()))/2, headerFontPlacement);
        }
    }
}