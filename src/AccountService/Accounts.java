package AccountService;

public class Accounts{
    public enum ACCOUNTS{Everyday, Savings, Current};
    private final ACCOUNTS ACCOUNTTYPE;
    private final String CUSTOMERNAME;
    private final String ACCOUNTNUMBER;
    private final String CUSTOMERADDRESS;

    double balance;
    

    Accounts(String customerName, String customerAddress, String accountNumber, ACCOUNTS accountType, double balance){
        CUSTOMERNAME = customerName;
        ACCOUNTNUMBER = accountNumber;
        CUSTOMERADDRESS = customerAddress;
        ACCOUNTTYPE = accountType;
        this.balance = balance;
        System.out.println(ACCOUNTTYPE);
    }

}