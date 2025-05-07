package AccountService;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
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
     * The file being initialized and a dictionary as to me it makes it very easy accessing people's accounts (using the key)
     */
    File userDetails =  new File("src//AccountService//UserInfo.csv");
    HashMap<String,Accounts> users = new HashMap<String,Accounts>();
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
     * delets the account (who would've thought???)
     */
    public void DeleteAccount(String accountName){
        users.remove(accountName);
    }
    /*
     * gets called when a click occurs and then changes the menu depending on the location
     */
    public void clickOccurred(int x, int y){
        switch (currentScreen) {
            case Screen.OptionMenu:
                if(buttons[0].isClicked(x,y)){
                    currentScreen = Screen.BalanceChecker;
                    /*
                    * deletes previous buttons and creates the new one
                    */
                    buttons = new Buttons[1];
                    buttons[0] = new Buttons(buttonX, buttonGap*9+buttonHeight*4+headerSize, buttonWidth, buttonHeight, textBoxCurviness, buttonText[5]);
                    repaint();
                }else if(buttons[1].isClicked(x,y)){
                    currentScreen = Screen.BalanceChanger;

                    foundUser = false;
                    buttons = new Buttons[3];
                    buttons[0] = new Buttons(buttonX, buttonGap*9+buttonHeight*4+headerSize, buttonWidth, buttonHeight, textBoxCurviness, buttonText[5]);
                    buttons[1] = new Buttons(canvasWidth/16, buttonGap*7+buttonHeight*3+headerSize, buttonWidth*3/4, buttonHeight, textBoxCurviness, buttonText[6]);
                    buttons[2] = new Buttons(canvasWidth*9/16, buttonGap*7+buttonHeight*3+headerSize, buttonWidth*3/4, buttonHeight, textBoxCurviness, buttonText[7]);
                    repaint();

                }
            break;
            case Screen.BalanceChecker:
                if(buttons[0].isClicked(x,y)){
                    currentScreen = Screen.OptionMenu;
                    createMenuButtons();
                    repaint();
                }
            break;
            case Screen.BalanceChanger:
                if(buttons[0].isClicked(x,y)){
                    currentScreen = Screen.OptionMenu;
                    createMenuButtons();
                    repaint();
                    /*
                     * when eiher the deposit or withdraw are hit it calls a function which trnasfers the money and says if it was succseful doing so or not
                     */
                }else if(buttons[1].isClicked(x, y)&&foundUser){
                    succsessfulTransfer(false);
                }else if(buttons[2].isClicked(x, y)&&foundUser){
                    succsessfulTransfer(true);
                }
            break;
            default:
                break;
        }
    }
    /*
     * wrote this to lessen code 
     */
    private void succsessfulTransfer(boolean withdrawing){
        System.out.println("moneying");
        try {
            Double amount = Double.parseDouble(input);
            if(users.get(currentUserInfo[0]).siphonMoney(amount, withdrawing)){
                textToDraw = "Balance: $"+users.get(currentUserInfo[0]).balance;
            }else{
                textToDraw = "Fail";
            }
        } catch (Exception e) {
            textToDraw = "Invalid";
            System.out.println(e);
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
            default:
                break;
        }
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
        switch (currentScreen) {
            case Screen.BalanceChecker:
                /*
                 * drawing the balance number
                 */
                g2d.setFont(new Font("Arial", Font.PLAIN, 80));
                g2d.drawString(textToDraw, (canvasWidth-(int)(new Font("Arial", Font.PLAIN, 80).getStringBounds(textToDraw,frc).getWidth()))/2,canvasHeight*3/4);
            break;
            case Screen.BalanceChanger:
                g2d.setFont(new Font("Arial", Font.PLAIN, 40));
                g2d.drawString(textToDraw, (canvasWidth-(int)(new Font("Arial", Font.PLAIN, 40).getStringBounds(textToDraw,frc).getWidth()))/2,canvasHeight*5/8);
            break;
        }
        if((currentScreen != Screen.OptionMenu)&&(currentScreen != Screen.EndDay)){
            g2d.setColor(textColor);
            g2d.setFont(typedTextFont);
            g2d.drawString(input,textHorizontalPlacement,textVerticalPlacement);
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

        }
    }
}