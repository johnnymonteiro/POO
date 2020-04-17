
import java.io.Serializable;

public class Viagem implements Serializable
{
    // variáveis de instância - substitua o exemplo abaixo pelo seu próprio
    //coordenadas inicio
    private Coordenadas inicio;
    //coordenadas destino
    private Coordenadas fim;
    //dados do cliente
    private int nif;
    //dados do carro
    private String matricula;
    //preco a pagar
    private double preco;
    
    public Viagem()
    {
        this.inicio = new Coordenadas();
        this.fim = new Coordenadas();
        this.nif = 0;
        this.matricula = "Sem Matricula";
        this.preco = 0;
    }
    
    public Viagem(int xI, int yI, int xF, int yF, int n, String m, double p)
    {
        this.inicio = new Coordenadas(xI,yI);
        this.fim = new Coordenadas(xF,yF);
        this.nif = n;
        this.matricula = m;
        this.preco = p;
    }
    
    public Viagem(Viagem v)
    {
        this.inicio = v.inicio.clone();
        this.fim = v.fim.clone();
        this.nif = v.getNif();
        this.matricula = v.getMatricula();
        this.preco = v.getPreco();
    }
    
    public Coordenadas getLoc(){return this.inicio;}
    public Coordenadas getLocDest(){return this.fim;}
    public double getXInicio(){
        return this.inicio.getX();
    }
    
    public double getYInicio(){
        return this.inicio.getY();
    }
    
    public double getXFim(){
        return this.fim.getX();
    }
    
    public double getYFim(){
        return this.fim.getY();
    }
    
    public int getNif(){
        return this.nif;
    }
    
    public String getMatricula(){
        return this.matricula;
    }
    
    public double getPreco(){
        return this.preco;
    }
    
    public void setXInicio(double x){
        this.inicio.setX(x);
    }
    
    /**public void setYInicio(double y){
        this.inicio.setY(y);
    }*/
    
    public void setXFim(double x){
        this.fim.setX(x);
    }
    
    /**public void setYFim(double y){
        this.fim.setY(y);
    }*/
    
    public void setNif(int n){
        this.nif = n;
    }
    
    public void setMatricula(String m){
        this.matricula = m;
    }
    
    public void setPreco(double p){
        this.preco = p;
    }
    public void setLoc(Coordenadas coor){this.inicio = coor;}
    public void setLocDest(Coordenadas coor){this.fim = coor;}
    
    public Viagem clone(){
        return new Viagem(this);
    }
    
    public String toString(){
          StringBuilder s = new StringBuilder(); 
          
          s.append("Localização Inicial: "+this.getXInicio()+","+this.getYInicio()+"\n");
          s.append("Localização Final: "+this.getXFim()+","+this.getYFim()+"\n");
          s.append("NIF Cliente: "+this.nif+"\n");
          s.append("Matricula Veiculo: "+this.matricula+"\n");
          s.append("Preço Viagem: "+this.preco+"\n");
          
          return s.toString();
    }
}
