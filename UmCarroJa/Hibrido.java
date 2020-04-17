
/**
 * Escreva a descrição da classe Hibrido aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
import java.io.Serializable;

public class Hibrido extends Veiculo implements Serializable
{
    private int bateria = 800; //capacidade total da bateria (em km)
    private int deposito = 900;  // capacidade total do deposito de gasolina (em km)

    /**
     * COnstrutor para objetos da classe Hibrido
     */
    public Hibrido(){ //constroi um Hibrido com condiçoes minimas
        super();
        this.bateria = 0;  //em km
        this.deposito = 0; // em km
    }
    
    public Hibrido(String tipo, String marca, String matricula, 
                    int nif, int velocidade, double precoBase, 
                    double consumo, int autonomia, double coordX, double coordY){
        super(tipo, marca, matricula,nif,velocidade, precoBase,consumo,autonomia,coordX, coordY);
    }
    
    public Hibrido(Hibrido h){
        this.setTipo(h.getTipo());
        this.setMarca(h.getMarca());
        this.setMatricula(h.getMatricula());
        this.setNif(h.getNif());
        this.setVelocidade(h.getVelocidade());
        this.setPrecoBase(h.getPrecoBase());
        this.setConsumo(h.getConsumo());
        this.setAutonomia(h.getAutonomia());
        this.setLocalizacao(h.getLocalizacao());
        //this.setClassificacao(h.getClassificacao());
        this.bateria = h.getBateria(); 
        this.deposito = h.getDeposito();
    }
    
    
    //GETTERS
    public int getBateria(){return this.bateria;}
    public int getDeposito(){return this.deposito;}
    
    //SETTERS
    public void setBateria(int bat){this.bateria = bat;}
    public void setDeposito(int dep){this.deposito = dep;}

    public Hibrido clone(){ //cópia de um hibrido
        return new Hibrido(this);
    }
    
    public String toString(){  // representação textual de um hibrido
       StringBuilder sb = new StringBuilder();
       
       sb.append(super.toString());
       sb.append("Capacidade da bateria: ");
       sb.append(bateria);
       sb.append("\n");
       sb.append("Capacidade do Depósito: ");
       sb.append(deposito);
       sb.append("\n");
       
       return sb.toString();
    }
    
    public void abastecerCarregar(){ //considerando sempre a 100%, calculada com a media da bateria+deposito
        setAutonomia((deposito+bateria)/2);
    }
}
