package AccountService;

public class Accounts{
    final int maxWithdrawl = 5000;
    final int maxOverdraft = 1000;
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
    public boolean siphonMoney(Double amount, boolean withdrawing){
        System.out.println(this.ACCOUNTTYPE);
        switch (this.ACCOUNTTYPE) {
            case ACCOUNTS.Everyday:
            System.out.println(amount);
                if((withdrawing)&&(amount<=maxWithdrawl)&&(this.balance-amount>=0)){
                    this.balance-=amount;
                    BankTellingService.totalWithdrawls+=amount;
                }else if(!withdrawing){
                    this.balance+=amount;
                    BankTellingService.totalDeposits+=amount;
                }
                return true;
            case ACCOUNTS.Savings:
            if((withdrawing)&&(amount<=maxWithdrawl)&&(this.balance-amount>=0)){
                this.balance-=amount;
                BankTellingService.totalWithdrawls+=amount;
            }else if(!withdrawing){
                this.balance+=amount;
                BankTellingService.totalDeposits+=amount;
            }
            return true;
            case ACCOUNTS.Current:
            if((withdrawing)&&(amount<=maxWithdrawl)&&(this.balance-amount>=-maxOverdraft)){
                this.balance-=amount;
                BankTellingService.totalWithdrawls+=amount;
            }else if(!withdrawing){
                this.balance+=amount;
                BankTellingService.totalDeposits+=amount;
            }
            return true;
        }
        return false;
    }
}