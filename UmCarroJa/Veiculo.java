
/**
 * Escreva a descrição da classe Veiculo aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
import java.util.ArrayList;
import java.io.Serializable;

public abstract class Veiculo implements Serializable
{
    // variáveis de instância
    
    private String tipo; //tipo de veiculo (eletrico, combustivel ou hibrido)
    private String marca;
    private String matricula;
    private int nif;     // nif dono do carro
    private int velocidade; //velocidade média por km
    private double precoBase; //preco base por km
    private double consumo; //consumo medio por km
    private int autonomia; // quantidade de quilómetros que ainda pode percorrer (maximo 900km para todos)
    private Coordenadas localizacao; //localização do veiculo no plano 2D
    private ArrayList<Viagem> viagens; //historico de viagens efetuadas no veiculo
    private ArrayList<Integer> classificacoes; //classificação média
    
    
    private static int RESERVA = 50; // se autonomia de combustivel/bateria for menor do que 50 km -> não viaja 
    
    /**
     * Construtor vazio
     */
    public Veiculo()
    {
        this.tipo = null;
        this.marca = null;
        this.matricula = null;
        this.nif = 0;
        this.velocidade = 0;
        this.precoBase = 0;
        this.consumo = 0;
        this.autonomia = 0;
        this.localizacao = new Coordenadas(0,0);
        this.viagens = new ArrayList<>();
        this.classificacoes = new ArrayList<>();
    }
    
    /**
     * Construtor a partir de parametros definidos
     */
    
    //todos veiculos inseridos no sistema pela primeira vez, não têm classificação ainda
    public Veiculo(String tipo, String marca, String matricula,
                   int nif, int velocidade, double precoBase, double consumo, int autonomia, double x, double y)
    {
        this.tipo = tipo;
        this.marca = marca;
        this.matricula = matricula;
        this.nif = nif;
        this.velocidade = velocidade;
        this.precoBase = precoBase;
        this.consumo = consumo;
        this.autonomia = autonomia;
        this.localizacao = new Coordenadas(x,y);      
        this.viagens = new ArrayList<>();
        this.classificacoes = new ArrayList<>();
    }
    
    public Veiculo(Veiculo v){
        this.tipo = v.getTipo();
        this.marca = v.getMarca();
        this.matricula = v.getMatricula();
        this.nif = v.getNif();
        this.velocidade = v.getVelocidade();
        this.precoBase = v.getPrecoBase();
        this.consumo = v.getConsumo();
        this.autonomia = v.getAutonomia();
        this.localizacao = v.getLocalizacao();
        this.viagens = v.getViagens();
        this.classificacoes = v.getClassificacoes(); 
    }
      
    //GETTERS
    public String getTipo(){return this.tipo;}
    public String getMarca(){return this.marca;}
    public String getMatricula(){ return this.matricula;}
    public int getNif(){return this.nif;}
    public int getVelocidade(){return this.velocidade;}
    public double getPrecoBase(){return this.precoBase;}
    public double getConsumo(){return this.consumo;}
    public int getAutonomia(){return this.autonomia;}
    public Coordenadas getLocalizacao(){return this.localizacao;}
    public ArrayList<Viagem> getViagens(){ //retorna uma cópia do historial de viagens
        ArrayList<Viagem> aux = new ArrayList<>();
          for(Viagem v: this.viagens)
                  aux.add(v);
        return aux;
    }
    
    public ArrayList<Integer> getClassificacoes(){return this.classificacoes;}
    
    
    //SETTERS
    public void setTipo(String tipo){this.tipo = tipo;}
    public void setMarca(String marca){this.marca = marca;}
    public void setMatricula(String matricula){this.matricula = matricula;}
    public void setNif(int nif){this.nif = nif;}
    public void setVelocidade(int velocidade){this.velocidade = velocidade;}
    public void setPrecoBase(double precoBase){this.precoBase = precoBase;}
    public void setConsumo(double consumo){this.consumo = consumo;}
    public void setAutonomia(int autonomia){this.autonomia = autonomia;}
    public void setLocalizacao(Coordenadas loc){this.localizacao = loc;} //altera a localização do veiculo  
    public void setViagens(ArrayList<Viagem> novoHist){ //Altera o arraylist do historial
        ArrayList<Viagem> list = new ArrayList<>();
        if(novoHist != null){
            for(Viagem v : novoHist)
                list.add(v.clone());
        }
        this.viagens = list;
    }
    
    public void setClassificacoes(ArrayList<Integer> classificacoes){this.classificacoes = new ArrayList<Integer>(classificacoes);}
    
    
    public abstract Veiculo clone(); // faz a copia de um veiculo
    
    
    public String toString(){//Imprime a informação de um veículo
       StringBuilder sb = new StringBuilder();
       
       sb.append("Tipo: ");
       sb.append(tipo);
       sb.append("\n");
       sb.append("Marca: ");
       sb.append(marca);
       sb.append("\n");
       sb.append("Matricula: ");
       sb.append(matricula);
       sb.append("\n");
       sb.append("Dono do carro: ");
       sb.append(nif);
       sb.append("\n");
       sb.append("Velocidade média por km: ");
       sb.append(velocidade);
       sb.append("\n");
       sb.append("Preço base por km: ");
       sb.append(precoBase);
       sb.append("\n");
       sb.append("Consumo médio por km: ");
       sb.append(consumo);
       sb.append("\n");
       sb.append("Autonomia em km: ");
       sb.append(autonomia);
       sb.append("\n");
       sb.append("Classificação média (entre 0 e 100): ");
       sb.append(MedClassificacoes());
       sb.append("\n");
       sb.append("Localização: ");
       sb.append("(");
       sb.append(this.localizacao.getX());
       sb.append(", ");
       sb.append(this.localizacao.getY());
       sb.append(")");
       sb.append("\n");
       
       return sb.toString();
    }
    
       
    public boolean equals(Veiculo v){ //compara 2 veiculos (só precisa-se da matricula)
        return this.matricula.equals(v.getMatricula());
    }
    
    
    public boolean viaja(){ // so viaja se autonomia >= 50 km
        return this.autonomia >= RESERVA;
    }
    
    public void addClassificacao(int val){ //adiciona uma classificação entre 0 a 100 a um veiculo
            classificacoes.add(val);    
    }
    
    public double MedClassificacoes(){ //media das classificaçoes
        if(classificacoes.size() != 0){
            int out=0;
            for(int i=0;i<classificacoes.size();i++){
                out+=classificacoes.get(i);
            }
            return out/classificacoes.size();
        }
        return 0;
    }
    
    public String imprimeHistorial(){ //imprime historial do veiculo no ecrã
        StringBuilder s = new StringBuilder();
        
        s.append("Histórico de viagens:\n");
        for( int i = 0 ; i < viagens.size(); i++){
            s.append((i+1)+" - "+viagens.get(i).toString()+"\n");
        }
        return s.toString();
    }
    

    
  
   
}


