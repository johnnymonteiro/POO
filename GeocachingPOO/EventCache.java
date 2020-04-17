/**
 * Write a description of class EventCahce here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.TreeSet;

public class EventCache extends Cache implements Serializable
{
    private TreeSet<String> nomes; //lista dos participantes
    private GregorianCalendar dataEvento;  //dia do encontro
    
    /*
     * CONSTRUTORES
     */
    
    //Construtor vazio
    public EventCache(){
        super();
        this.nomes = new TreeSet<>(); 
        this.dataEvento = new GregorianCalendar();
    }
    
    //Construtor parametrizado
    public EventCache(String cod, GregorianCalendar datacriacao, String descricao, Coordenadas cor, String criador, GregorianCalendar data){
        super(cod, datacriacao, descricao, cor, criador);
        this.nomes = new TreeSet<>(); nomes.add(criador); //adiciona o criador à lista de participantes
        this.dataEvento = data;
    }
    
    //Construtor de copia
    public EventCache(EventCache ev){
        this.setCodigo(ev.getCodigo());
        this.setData(ev.getData());
        this.setDescricao(ev.getDescricao());
        this.setCoordenadas(ev.getCoordenadas());
        this.setCriador(ev.getCriador());
        this.nomes = ev.getNomes();
        this.dataEvento = ev.getDataEvento();
    }
    
    /*
     * GETTERS
     */        
    public TreeSet<String> getNomes()     { return this.nomes;}
    public GregorianCalendar getDataEvento()      { return this.dataEvento;}
    
    /*
     * SETTERS
     */
    public void setNomes(TreeSet<String> nom)     { this.nomes = nom;}
    public void setDataEvento(GregorianCalendar d)        { this.dataEvento = d;}
    
    public void adicionaParticipante(String email){
        nomes.add(email);
    }

    public void removeParticipante(String email){
        nomes.remove(email);
    }
    
    public String toString(){
        StringBuilder s = new StringBuilder();         
        GregorianCalendar d = this.getData();
            
        s.append("Código: "+getCodigo()+"\n");
        s.append("Tipo de Cache: EventCache\n");        
        s.append("Criador: "+getCriador()+"\n");
        s.append("Data criação: "+d.get(Calendar.YEAR)+"/"+(d.get(Calendar.MONTH)+1)+"/"+d.get(Calendar.DAY_OF_MONTH)+"\n");     
        s.append("Descrição: "+getDescricao()+"\n");    
        s.append("Coordenadas: "+getCoordenadas().toString()+"\n");
        s.append("Data do Encontro: "+dataEvento.get(Calendar.YEAR)+"/"+(dataEvento.get(Calendar.MONTH)+1)+"/"+dataEvento.get(Calendar.DAY_OF_MONTH)+"\n"); 
        s.append("Participantes: ");
        for(String em : nomes){
            s.append(em+" - ");
        }
            
        return s.toString();
    }
        
    
    public EventCache clone(){
        return new EventCache(this);
    }
}
