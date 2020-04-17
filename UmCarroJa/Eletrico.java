
/**
 * Escreva a descrição da classe Eletrico aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
import java.io.Serializable;

public class Eletrico extends Veiculo implements Serializable
{
    private int bateria = 800; //capacidade total da bateria em km
    
    
    public Eletrico(){ //constroi um Eletrico com condiçoes minimas
        super();
        this.bateria = 0;  
    }
    
    
    public Eletrico(String tipo, String marca, String matricula,  int nif, 
                    int velocidade, double precoBase, 
                    double consumo, int autonomia, double coordX, double coordY){
       super(tipo, marca, matricula,nif,velocidade, precoBase,consumo,autonomia,coordX, coordY);
    }
    
    
    public Eletrico(Eletrico e){ //constroi um Eletrico com condiçoes minimas
        this.setTipo(e.getTipo());
        this.setMarca(e.getMarca());
        this.setMatricula(e.getMatricula());
        this.setNif(e.getNif());
        this.setVelocidade(e.getVelocidade());
        this.setPrecoBase(e.getPrecoBase());
        this.setConsumo(e.getConsumo());
        this.setAutonomia(e.getAutonomia());
        this.setLocalizacao(e.getLocalizacao());
        //this.setClassificacao(e.getClassificacao());

        this.bateria = e.getBateria();   
    }
    
    
    //GETTERS
    public int getBateria(){return this.bateria;}
    
    
    //SETTERS
    public void setBateria(int cap){this.bateria = cap;}

    
    public Eletrico clone(){
        return new Eletrico(this);
    }
    
    public String toString(){  // representação textual de um hibrido
       StringBuilder sb = new StringBuilder();

       sb.append(super.toString());
       sb.append("Capacidade da bateria: ");
       sb.append(bateria);
       sb.append("\n");
       
       return sb.toString();
    }
    

    
    public void abastecer(){ //considerando sempre carregar a bateria a 100%
        setAutonomia(bateria);
    }

  
}
