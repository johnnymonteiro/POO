
/**
 * Escreva a descrição da classe Cliente aqui.
 */
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class Cliente extends Utilizador
{
    // variáveis de instância - substitua o exemplo abaixo pelo seu próprio
    private Coordenadas localizacao;
    private ArrayList<Viagem> listaViagens;

    /**
     * COnstrutor para objetos da classe Cliente
     */
    public Cliente()
    {
        // inicializa variáveis de instância
        super();
        this.localizacao = new Coordenadas();
        this.listaViagens = new ArrayList<>();
    }
    
    public Cliente(int cod,String nome, String email, String password,String morada/**, GregorianCalendar data*/,double x,double y/**,ArrayList<Viagem> listaViagens*/)
    {
        // inicializa variáveis de instância
        super(cod,nome,email,password,morada/**,data*/);
        this.localizacao = new Coordenadas(x,y);
        this.listaViagens = new ArrayList<Viagem>();
    }
    
    public Cliente(int cod,String nome, String email, String password,String morada)
    {
        // inicializa variáveis de instância
        super(cod,nome,email,password,morada/**,data*/);
        this.localizacao = new Coordenadas();
        this.listaViagens = new ArrayList<Viagem>();
    }
    
    public Cliente(Cliente c)
    {
        // inicializa variáveis de instância
        this.setNif(c.getNif());
        this.setNome(c.getNome());
        this.setEmail(c.getEmail());
        //this.setPassword(c.getPassword());
        this.setMorada(c.getMorada());
        //this.setData(c.getData());
        this.localizacao = c.getLocalizacao();
        this.listaViagens = c.getListaViagens();
    }

    /**
     * Get
     */
    public Coordenadas getLocalizacao(){return this.localizacao;}
    public double getLocX(){return this.localizacao.getX();}
    public double getLocY(){return this.localizacao.getY();}
    public ArrayList<Viagem> getListaViagens(){return this.listaViagens;}
    public String getNome(){return getNome();}
    /**
     * Set
     */
    public void setLocalizacao(Coordenadas x){this.localizacao = x;}
    public void setListaViagens(ArrayList<Viagem> listaViagens){this.listaViagens = new ArrayList<Viagem> (listaViagens);}
    
    public void addViagem(Viagem v){
        listaViagens.add(v);
    }
    public String toString(){
            StringBuilder s = new StringBuilder();         
      
            s.append("Código: "+getNif()+"\n");
            s.append("Nome: "+getNome()+"\n");
            s.append("Email: "+getEmail()+"\n"); 
            s.append("Morada"+getMorada()+"\n");
            //s.append("Data: "+getData()+"\n");
            s.append("Latitude: "+localizacao.getX()+"\n");
            s.append("Longitude: "+localizacao.getY()+"\n");
            return s.toString();
        }
        
    public String toListaAlugueres(){
        StringBuilder s = new StringBuilder();
        
        s.append("Histórico de Viagens: \n");
        for( int i = 0 ; i < listaViagens.size(); i++){
                s.append((i+1)+" - "+listaViagens.get(i).toString()+"\n");
            }
        return s.toString();
    }
    
    public ArrayList<String> listaCarros(){
        StringBuilder s = new StringBuilder();
        ArrayList<String> aux = new ArrayList<String>();
        for(int i = 0;i<listaViagens.size();i++){
            Viagem vi = listaViagens.get(i);
            String matricula = vi.getMatricula();
            aux.add(matricula);
        }
        return aux;
    }
    
    public Cliente clone(){return new Cliente(this);}
    
}
