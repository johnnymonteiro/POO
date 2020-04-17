
/**
 * Escreva a descrição da classe Utilizador aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Calendar;

public abstract class Utilizador implements Serializable
{
    // variáveis de instância - substitua o exemplo abaixo pelo seu próprio
    private int nif;//cod = nif
    private String nome;
    private String email;
    private String password;
    private String morada;
    private GregorianCalendar data;
    /**
     * COnstrutor para objetos da classe Utilizador
     */
    public Utilizador()
    {
        // inicializa variáveis de instância
        this.nif = 0;
        this.nome = "";
        this.email = "";
        this.password = "";
        this.morada = "";
        //this.data = new GregorianCalendar();
    }
    
    public Utilizador(int nif,String nome, String email, String password,String morada/**, GregorianCalendar data*/)
    {
        // inicializa variáveis de instância
        this.nif = nif;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.morada = morada;
        //this.data = new GregorianCalendar();
    }
    
    public Utilizador(Utilizador u)
    {
        // inicializa variáveis de instância
        this.nif = u.getNif();
        this.nome = u.getNome();
        this.email = u.getEmail();
        this.password = u.getPassword();
        this.morada = u.getMorada();
        //this.data = u.getData();
    }

    /**
     * Get
     */
    public int getNif(){return this.nif;}
    public String getNome(){return this.nome;}
    public String getEmail(){return this.email;}
    public String getPassword(){return this.password;}
    public String getMorada(){return this.morada;}
    //public GregorianCalendar getData(){return this.data;}
    
    /**
     * Set
     */
    public void setNif(int n){this.nif = n;}
    public void setNome(String n){this.nome = n;}
    public void setEmail(String e){this.email = e;}
    public void setPassword(String pass){this.password = pass;}
    public void setMorada(String m){this.morada = m;}
   // public void setData(GregorianCalendar d){this.data = d;}
   /**
     * Este método verifica se a String recebida é igual à password deste utilizador
     */
    public boolean validaLogin(String p){
        return (this.password.equals(p));        
    }
   
    public abstract Utilizador clone();
    
    public abstract String toString();

    
    
    
    
    
    
    
    
    
    
    
    
}