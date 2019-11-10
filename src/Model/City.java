/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Michael Walah
 * @author Qolbi Fathurrohim
 * @author William Walah
 */
public class City {
    private int x;
    private int y;
    
    public City(int x, int y){
        this.x=x;
        this.y=y;
    }
    
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public void setX(int x){this.x=x;}
    public void setY(int y){this.y=y;}
    
    public double calculateDistance(City o){
        double x = Math.pow(this.x-o.getX(),2);
        double y = Math.pow(this.y-o.getY(),2);
        double res = Math.sqrt(x+y);
        return res;
    }
}
