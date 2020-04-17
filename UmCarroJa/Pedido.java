import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.io.Serializable;

public class Pedido implements Serializable
{
    private Coordenadas loc;//onde o carro esta estacionado
    private String matricula;
    private Coordenadas locDest;//onde o carro se irá dirigir
    private GregorianCalendar data;
    private int estado; // 0-Não Visto. 1-Visto. 2-Aceite. 3-Recusado.
    private int nif;//cliente
    private String combustivel;
    private String preferencia;
    
    
    /**
     * COnstrutor para objetos da classe Pedido
     */
    public Pedido(){
        this.loc = new Coordenadas();
        this.matricula = "sem matricula";
        this.locDest = new Coordenadas();
        this.data = new GregorianCalendar();
        this.estado = 0;
        this.nif = 0;
        this.combustivel = "sem combustivel";
        this.preferencia = "";
    }
    
    public Pedido(double x, double y, String v, double xD, double yD, GregorianCalendar data, int e, int n, String c,String preferencia){
        this.loc = new Coordenadas(x,y);
        this.matricula = v;
        this.locDest = new Coordenadas(xD,yD);
        this.data = data;
        this.estado = e;
        this.nif = n;
        this.combustivel = c;
        this.preferencia = preferencia;
    }
    
    public Pedido(Pedido p){
        this.loc = p.loc.clone();
        this.matricula = p.getMatricula();
        this.locDest = p.locDest.clone();
        this.data = p.getData();
        this.estado = p.getEstado();
        this.nif = p.getNif();
        this.combustivel = p.getCombustivel();
        this.preferencia =p.getPreferencia();
    }
    
    public double getX(){
        return this.loc.getX();
    }
    
    public double getY(){
        return this.loc.getY();
    }
    
    public String getMatricula(){
        return this.matricula;
    }
    
    public double getXDest(){
        return this.locDest.getX();
    }
    
    public double getYDest(){
        return this.locDest.getY();
    }
    
    public GregorianCalendar getData(){
        return this.data;
    }
    
    public int getEstado(){
        return this.estado;
    }
    
    public int getNif(){
        return this.nif;
    }
    
    public String getCombustivel(){
        return this.combustivel;
    }
    
    public String getPreferencia(){
        return this.preferencia;
    }
    
    public Coordenadas getLoc(){
        return this.loc;
    }
    
    public Coordenadas getLocDest(){
        return this.locDest;
    }
    
    public void setX(double x){
        this.loc.setX(x);
    }
    
    /**public void setY(double y){
        this.loc.setY(y);
    }*/
    
    public void setLoc(double x, double y){
        this.loc = new Coordenadas(x,y);
    }
    
    public void setLocCoor(Coordenadas coor){
        this.loc = coor;
    }
    
    public void setLocDest(double x, double y){
        this.locDest = new Coordenadas(x,y);
    }
    
    public void setLocDestCoor(Coordenadas coor){
        this.locDest = coor;
    }
    
    public void setMatricula(String v){
        this.matricula = v;
    }
    
    public void setXDest(double xD){
        this.locDest.setX(xD);
    }
    
    /**public void setYDest(double yD){
        this.locDest.setY(yD);
    }*/
    
    public void setData(GregorianCalendar d){
        this.data = d;
    }
    
    public void setEstado(int e){
        this.estado = e;
    }
    
    public void setNif(int n){
        this.nif = n;
    }
    
    public void setCombustivel(String c){
        this.combustivel = c;
    }
    
    public void setPreferencia(String p){
        this.preferencia = p;
    }
    
    public Pedido clone(){
        return new Pedido(this);
    }
    
    public String toString(){
        StringBuilder s = new StringBuilder();  
        
        s.append("Localização Cliente: "+loc.getX()+","+loc.getY()+"\n");
        s.append("Localização Destino: "+locDest.getX()+","+locDest.getY()+"\n");
        s.append("Data: "+data.toString()+"\n");
        s.append("Matricula: "+matricula+"\n");
        
        switch(estado){
            
            case 0:
            s.append("Estado: Não Visto\n");
            break;
            
            case 1:
            s.append("Estado: Visto\n");
            break;
            
            case 2:
            s.append("Estado: Aceite\n");
            break;
            
            case 3:
            s.append("Estado: Recusado\n");
            break;
        }
        
        return s.toString();
    }
    
}
