package AccountService;

public class Accounts{
    final int maxWithdrawl = 5000;
    final int maxOverdraft = 1000;
    public enum ACCOUNTS{Everyday, Savings, Current};
    private final ACCOUNTS ACCOUNTTYPE;
    private final String CUSTOMERNAME;
    public final String ACCOUNTNUMBER;
    private final String CUSTOMERADDRESS;
    String accountTypeString;
    double balance;
    

    Accounts(String customerName, String customerAddress, String accountNumber, ACCOUNTS accountType, double balance){
        CUSTOMERNAME = customerName;
        ACCOUNTNUMBER = accountNumber;
        CUSTOMERADDRESS = customerAddress;
        ACCOUNTTYPE = accountType;
        this.balance = balance;
        System.out.println(ACCOUNTTYPE);
        System.out.println(ACCOUNTNUMBER);
    }
    public boolean siphonMoney(Double amount, boolean withdrawing){
        System.out.println(this.ACCOUNTTYPE);
        if(amount >= 0.01){
            switch (this.ACCOUNTTYPE) {
                case ACCOUNTS.Everyday:
                System.out.println(amount);
                    if((withdrawing)&&(amount<=maxWithdrawl)&&(this.balance-amount>=0)){
                        this.balance = Math.round((this.balance-amount)*100)/100;
                        BankTellingService.totalWithdrawls+=amount;
                    }else if(!withdrawing){
                        this.balance = Math.round((this.balance+amount)*100)/100;
                        BankTellingService.totalDeposits+=amount;
                    }
                    return true;
                case ACCOUNTS.Savings:
                if((withdrawing)&&(amount<=maxWithdrawl)&&(this.balance-amount>=0)){
                    this.balance = Math.round((this.balance-amount)*100)/100;
                    BankTellingService.totalWithdrawls+=amount;
                }else if(!withdrawing){
                    this.balance = Math.round((this.balance+amount)*100)/100;
                    BankTellingService.totalDeposits+=amount;
                }
                return true;
                case ACCOUNTS.Current:
                if((withdrawing)&&(amount<=maxWithdrawl)&&(this.balance-amount>=-maxOverdraft)){
                    this.balance = Math.round((this.balance-amount)*100)/100;
                    BankTellingService.totalWithdrawls+=amount;
                }else if(!withdrawing){
                    this.balance = Math.round((this.balance+amount)*100)/100;
                    BankTellingService.totalDeposits+=amount;
                }
                return true;
            }
        }    
        return false;
    }
    /*
     * corrects any rounding errros
     */
    private void correctROundingError(){
        this.balance = Math.round(this.balance*100.0)/100.0;
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