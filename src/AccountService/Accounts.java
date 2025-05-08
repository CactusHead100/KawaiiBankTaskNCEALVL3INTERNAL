package AccountService;
/*
 * all the variables used in accounts 
 */
public class Accounts{
    final int maxWithdrawl = 5000;
    final int maxOverdraft = 1000;
    final Double twoDPRounder = 100.0;
    public enum ACCOUNTS{Everyday, Savings, Current};
    private final ACCOUNTS ACCOUNTTYPE;
    private final String CUSTOMERNAME;
    public final String ACCOUNTNUMBER;
    private final String CUSTOMERADDRESS;
    String accountTypeString;
    double balance;
    
    /*
     * sets variables so that each account is unique
     */
    Accounts(String customerName, String customerAddress, String accountNumber, ACCOUNTS accountType, double balance){
        CUSTOMERNAME = customerName;
        ACCOUNTNUMBER = accountNumber;
        CUSTOMERADDRESS = customerAddress;
        ACCOUNTTYPE = accountType;
        this.balance = balance;
    }
    /*
     * checks what type of account they have and depending on the rules for the account either allows or not allows them to make the transaction
     */
    public boolean siphonMoney(Double amount, boolean withdrawing){
        if(amount >= 0.01){
            switch (this.ACCOUNTTYPE) {
                case ACCOUNTS.Everyday:
                    if((withdrawing)&&(amount<=maxWithdrawl)&&(this.balance-amount>=0)){
                        /*
                         * rounds as java sometimes has rounding errors
                         */
                        this.balance = Math.round((this.balance-amount)*twoDPRounder)/twoDPRounder;
                        BankTellingService.totalWithdrawls+=amount;
                    }else if(!withdrawing){
                        this.balance = Math.round((this.balance+amount)*twoDPRounder)/twoDPRounder;
                        BankTellingService.totalDeposits+=amount;
                    }
                    return true;
                case ACCOUNTS.Savings:
                if((withdrawing)&&(amount<=maxWithdrawl)&&(this.balance-amount>=0)){
                    this.balance = Math.round((this.balance-amount)*twoDPRounder)/twoDPRounder;
                    BankTellingService.totalWithdrawls+=amount;
                }else if(!withdrawing){
                    this.balance = Math.round((this.balance+amount)*twoDPRounder)/twoDPRounder;
                    BankTellingService.totalDeposits+=amount;
                }
                return true;
                case ACCOUNTS.Current:
                if((withdrawing)&&(amount<=maxWithdrawl)&&(this.balance-amount>=-maxOverdraft)){
                    this.balance = Math.round((this.balance-amount)*twoDPRounder)/twoDPRounder;
                    BankTellingService.totalWithdrawls+=amount;
                }else if(!withdrawing){
                    this.balance = Math.round((this.balance+amount)*twoDPRounder)/twoDPRounder;
                    BankTellingService.totalDeposits+=amount;
                }
                return true;
            }
        }    
        return false;
    }
    public String returnInfo(){
        switch (this.ACCOUNTTYPE) {
            case ACCOUNTS.Current:
                accountTypeString = "Current";
            break;
            case ACCOUNTS.Savings:
                accountTypeString = "Savings";
            break;
            case ACCOUNTS.Everyday:
                accountTypeString = "Everyday";
            break;
        }
        return (CUSTOMERNAME+","+CUSTOMERADDRESS+","+ACCOUNTNUMBER+","+accountTypeString+","+Double.toString(balance));
    }
}