package AccountService;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import AccountService.BankTellingService.Screen;

public class MouseInput implements MouseListener, MouseMotionListener{

    private BankTellingService bankTellingService;
    public MouseInput(BankTellingService bankTellingService) {
        this. bankTellingService = bankTellingService;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(bankTellingService.currentScreen == Screen.OptionMenu){
            bankTellingService.clickOccurred(e.getX(),e.getY());
            
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
