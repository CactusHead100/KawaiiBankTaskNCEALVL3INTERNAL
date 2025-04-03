package AccountService;

public class Accounts{
    public enum ACCOUNTS{Everyday, Savings, Current};
    private final ACCOUNTS ACCOUNTTYPE;
    private final String CUSTOMERNAME;
    private final int ACCOUNTNUMBER;
    private final String CUSTOMERADDRESS;
    

    Accounts(String customerName, int accountNumber, String customerAddress, ACCOUNTS accountType){
        CUSTOMERNAME = customerName;
        ACCOUNTNUMBER = accountNumber;
        CUSTOMERADDRESS = customerAddress;
        ACCOUNTTYPE = accountType;
        System.out.println(ACCOUNTTYPE);
    }

}