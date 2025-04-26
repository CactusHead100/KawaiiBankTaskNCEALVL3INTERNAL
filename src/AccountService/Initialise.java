package AccountService;
//I create canvas and add the bankTellingService to it but give the bankTellingService focus as otherwise 
//the code won't run as the code in the bankTellingService class is want i want run
public class Initialise {
    private BankTellingService bankTellingService;
    private Canvas canvas;
    public Initialise(){
        bankTellingService = new BankTellingService();
        canvas = new Canvas(bankTellingService);
        bankTellingService.requestFocus();
    }
}
