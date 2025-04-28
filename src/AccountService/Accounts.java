package AccountService;

public class Accounts{
    public enum ACCOUNTS{Everyday, Savings, Current};
    private final ACCOUNTS ACCOUNTTYPE;
    private final String CUSTOMERNAME;
    private final int ACCOUNTNUMBER;
    private final String CUSTOMERADDRESS;

    int balance;
    

    Accounts(String customerName, String customerAddress, int accountNumber, ACCOUNTS accountType, int balance){
        CUSTOMERNAME = customerName;
        ACCOUNTNUMBER = accountNumber;
        CUSTOMERADDRESS = customerAddress;
        ACCOUNTTYPE = accountType;
        this.balance = balance;
        System.out.println(ACCOUNTTYPE);
    }

}