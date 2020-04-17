
/**
 * Escreva a descrição da classe Combustivel aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
import java.io.Serializable;
 
public class Combustivel extends Veiculo implements Serializable
{
    // variáveis de instância

    private int deposito = 900;  // capacidade total do deposito em km
    
    public Combustivel(){
        super();
        this.deposito = 0;
    }
    
    public Combustivel(String tipo, String marca, String matricula, int nif, 
                        int velocidade, double precoBase,double consumo, int autonomia, 
                        double coordX, double coordY){
        super(tipo, marca, matricula,nif,velocidade, precoBase,consumo,autonomia,coordX, coordY);
    }
    
    public Combustivel(Combustivel c){
        this.setTipo(c.getTipo());
        this.setMarca(c.getMarca());
        this.setMatricula(c.getMatricula());
        this.setNif(c.getNif());
        this.setVelocidade(c.getVelocidade());
        this.setPrecoBase(c.getPrecoBase());
        this.setConsumo(c.getConsumo());
        this.setAutonomia(c.getAutonomia());
        this.setLocalizacao(c.getLocalizacao());
        //this.setClassificacao(c.getClassificacao());
        //this.setNumeroClassificacoes(c.getNumeroCl);
        
        this.deposito = c.getDeposito();
    }
    
    //GETTERS
    public int getDeposito(){return this.deposito;}
    
    
    //SETTERS
    public void setDeposito(int dep){this.deposito = dep;}
    
    
    
    public Combustivel clone(){ // faz uma copia de um veiculo a combustivel
        return new Combustivel(this);
    }
    
    
    public String toString(){
       StringBuilder sb = new StringBuilder();

       sb.append(super.toString());
       sb.append("Capacidade do Depósito: ");
       sb.append(deposito);
       sb.append("\n");
       
       return sb.toString();
    }
     
    /*public void abastecer(int litros){ 
        if(litros <= (deposito - getAutonomia())){
            setAutonomia(litros+getAutonomia());
        }
        
        else{
            System.out.println("Impossível abastecer (litros em excesso)");
        }
    }*/
    
    public void abastecer(){ //considerando sempre encher o depósito a 100%
        setAutonomia(deposito);
    }
}
