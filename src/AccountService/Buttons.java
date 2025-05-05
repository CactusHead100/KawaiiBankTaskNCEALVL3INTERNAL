package AccountService;

import java.awt.geom.RoundRectangle2D;
    
public class Buttons {
    /*
     * all parameters for class button
     */
    public int x;
    public int y;
    private int width;
    private int height;
    private int curviness;
    public String text;
    public RoundRectangle2D.Double shape;
    Buttons(int x, int y, int width, int height, int curviness, String text){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.curviness = curviness;
        this.text = text;
        /*
         * so i can easily draw the button sadly can't do this with text so far as i know
         */
        this.shape = new RoundRectangle2D.Double(x,y,width,height,curviness,curviness);
    }
    public boolean isClicked(int x, int y){
        if((x>=this.x)&&(x<=this.x+this.width)&&(y>=this.y)&&(y<=this.y+this.height)){
            return true;
        }else{
            return false;
        }
    }
}
