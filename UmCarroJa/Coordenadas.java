
/**
 * Write a description of class Coordenadas here.
 */

import java.io.Serializable;

public class  Coordenadas implements Serializable
{
    private double coordX;
    private double coordY;
    
    /*
     * CONSTRUTORES
     */
    
    //Construtor vazio
    public Coordenadas(){
        this.coordX = 0;
        this.coordY = 0;
    }
    
    //Construtor parametrizado
    public Coordenadas(double x, double y){
        this.coordX = x;
        this.coordY = y;
    }
    
    //Construtor de cópia
    public Coordenadas(Coordenadas c){
        this.coordX = c.getX();
        this.coordY = c.getY();
    }
    
    /*
     * GETTERS
     */
    public double getX() { return this.coordX; }
    public double getY() { return this.coordY; }
    
    /*
     * SETTERS
     */
    public void setX(double x) { this.coordX = x; }
    public void setY(double y) { this.coordY = y;}
    
    /*
     * MÉTODOS
     */   
    public Coordenadas clone(){
        return new Coordenadas(this);
    }
    
    
    public String toString(){
        StringBuilder s = new StringBuilder();        

        s.append("Coordenada em x: "+getX()+"\n");
        s.append(" | ");
        s.append("Coordenada em Y: "+getY()+"\n");
        
        return s.toString();
        
    }

}
