package AccountService;

public class Initialise {
    private BankTellingService bankTellingService;
    private Canvas canvas;
    public Initialise(){
        bankTellingService = new BankTellingService();
        canvas = new Canvas(bankTellingService);
        bankTellingService.requestFocus();
    }
}
