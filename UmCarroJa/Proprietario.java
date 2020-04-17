
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class Proprietario extends Utilizador
{

    private ArrayList<Viagem> listaAlugueres;
    private ArrayList<Veiculo> veiculos;//veiculos disponiveis
    private ArrayList<Veiculo> nveiculos;//veiculos não disponiveis
    private ArrayList<Integer> classificacoes;
    //private int classificacao;
    private ArrayList<Pedido> listaPedidos;
    Input input;

    /**
     * COnstrutor para objetos da classe Proprietario
     */
    public Proprietario()
    {
        // inicializa variáveis de instância
        super();
        this.listaAlugueres = new ArrayList<>();
        this.veiculos = new ArrayList<>();
        this.nveiculos =  new ArrayList<>();
        this.classificacoes = new ArrayList<>();
        //this.classificacao = 0;
        this.listaPedidos = new ArrayList<>();
    }
    public Proprietario(String nome,int nif,String email,String password,String morada){
    
    super(nif,nome,email,password,morada);
    this.listaAlugueres = new ArrayList<>();
    this.veiculos = new ArrayList<>();
    this.nveiculos =  new ArrayList<>();
    this.classificacoes = new ArrayList();
    //this.classificacao = 0;
    this.listaPedidos= new ArrayList<>();
    }
    
    public Proprietario(int nif,String nome, String email, String password,String morada,/**, GregorianCalendar data,*/ArrayList<Viagem> listaAlugueres,ArrayList<Veiculo> veiculos,/**int classificacao*/ArrayList<Integer> classificacoes, ArrayList<Pedido> pedidos)
    {
        // inicializa variáveis de instância
        super(nif,nome,email,password,morada/**,data*/);
        this.listaAlugueres = new ArrayList<Viagem>(listaAlugueres);
        this.veiculos = new ArrayList<Veiculo>(veiculos);
        this.nveiculos =  new ArrayList<>();
        this.classificacoes = new ArrayList<Integer>(classificacoes);
        //this.classificacao = 0;
        this.listaPedidos = new ArrayList<Pedido>(pedidos);
    }
    
    public Proprietario(Proprietario p){
        // inicializa variáveis de instância
        this.setNif(p.getNif());
        this.setNome(p.getNome());
        this.setEmail(p.getEmail());
        //this.setPassword(p.getPassword());
        this.setMorada(p.getMorada());
        //this.setData(p.getData());
        this.listaAlugueres = p.getListaAlugueres();
        this.veiculos = p.getVeiculos();
        this.classificacoes = p.getClassificacoes();
        //this.classificacao = p.getClassificacao();
        this.listaPedidos = p.getPedidos();
    }

    /**
     * Get
     */

    
    public ArrayList<Viagem> getListaAlugueres(){return this.listaAlugueres;}
    public ArrayList<Veiculo> getVeiculos(){return this.veiculos;}
    public ArrayList<Integer> getClassificacoes(){return this.classificacoes;}
    //public int getClassificacao(){return this.classificacao;}
    public ArrayList<Pedido> getPedidos(){return this.listaPedidos;}
    /**
     * Set
     */

    public void setListaAlugueres(ArrayList<Viagem> listaAlugueres){this.listaAlugueres = new ArrayList<Viagem> (listaAlugueres);}
    public void setVeiculos(ArrayList<Veiculo> veiculos){this.veiculos = new ArrayList<Veiculo>(veiculos);}
    public void setClassificacoes(ArrayList<Integer> classificacoes){this.classificacoes = new ArrayList<Integer>(classificacoes);}
    //public void setClassificacao(int x){this.classificacao = x;}
    public void setPedidos(ArrayList<Pedido> pedidos){this.listaPedidos = new ArrayList<Pedido>(pedidos);}
    
    
    public String toString(){
            StringBuilder s = new StringBuilder();         
      
            s.append("NIF: "+getNif()+"\n");
            s.append("Nome: "+getNome()+"\n");
            s.append("Email: "+getEmail()+"\n"); 
            s.append("Morada"+getMorada()+"\n");
            //s.append("Data: "+getData()+"\n");
            s.append("Veiculos: \n");
            for( int i = 0 ; i < veiculos.size(); i++){
                s.append((i+1)+" - "+veiculos.get(i).toString()+"\n");
            }
            s.append("Classificacao: "+returnMedClassificacoes()+"\n");
            return s.toString();
    }
        
    public String toListaAlugueres(){
        StringBuilder s = new StringBuilder();
        
        s.append("Histórico de Alugueres: \n");
        for( int i = 0 ; i < listaAlugueres.size(); i++){
                s.append((i+1)+" - "+listaAlugueres.get(i).toString()+"\n");
            }
        return s.toString();
    }
    
    public String listarVeiculos(){
        StringBuilder s = new StringBuilder();
        
        s.append("Lista de Veiculos:\n");
        for( int i = 0 ; i < veiculos.size(); i++){
            s.append((i+1)+" - "+listaAlugueres.get(i).toString()+"\n");
        }
        return s.toString();
    }
    
    public Proprietario clone(){return new Proprietario(this);}
    
    public void addClassificacao(int val){ //adiciona uma classificação entre 0 a 100 a um proprietario
            classificacoes.add(val);    
    }
    
    public float returnMedClassificacoes(){
        if(classificacoes.size() != 0){
            int out=0;
            for(int i=0;i<classificacoes.size();i++){
                out+=classificacoes.get(i);
            }
            return out/classificacoes.size();
        }
        return 0;
    }
    

    
    public void addVeiculo(Veiculo v){
        veiculos.add(v);
    }
    public void addVeiculoN(Veiculo v){
        nveiculos.add(v);
    }
    public void addPedido(double xInicial, double yInicial, double xDist, double yDist, String matricula,int nif){
    Coordenadas loc = new Coordenadas(xInicial,yInicial);
    String ma; 
    Coordenadas locDest = new Coordenadas(xDist,yDist);
    GregorianCalendar data = new GregorianCalendar();
    int estado = 0; // 0-Não Visto. 1-Visto. 2-Aceite. 3-Recusado.
    String combustivel = "";
    String preferencia = "MaisPerto";
    Pedido p = new Pedido();
    p.setLoc(xInicial,yInicial);
    p.setLocDest(xDist,yDist);
    p.setMatricula(matricula);
    p.setNif(nif);
    p.setCombustivel(combustivel);
    p.setPreferencia(preferencia);
    p.setEstado(estado);
    listaPedidos.add(p);
    }
    
    public void removerVeiculo(String matricula){
        int n = 0;        
        for( int i = 0 ; i < veiculos.size(); i++){
            if(matricula.equals(veiculos.get(i).getMatricula())){
                n = i;
                i = veiculos.size();
            }
        }
        veiculos.remove(n);
    }
    
    public void removerVeiculoN(String matricula){
        int n = 0;        
        for( int i = 0 ; i < nveiculos.size(); i++){
            if(matricula.equals(nveiculos.get(i).getMatricula())){
                n = i;
                i = nveiculos.size();
            }
        }
        nveiculos.remove(n);
    }
    /**Troca um veiculo da lista de disponiveis para a lista dos não disponiveis*/
    public void trocarListaN(String matricula){
        for( int i = 0;i < veiculos.size();i++){
            if(matricula.equals(veiculos.get(i).getMatricula())){
                Veiculo v = veiculos.get(i);
                removerVeiculo(matricula);
                veiculos.trimToSize();
                addVeiculoN(v);
            }
        }
    }
    /**Troca um veiculo da lista de não disponiveis para a lista de disponiveis*/
    public void trocarLista(String matricula){
        for( int i = 0;i < nveiculos.size();i++){
            if(matricula.equals(nveiculos.get(i).getMatricula())){
                Veiculo v = nveiculos.get(i);
                removerVeiculoN(matricula);
                nveiculos.trimToSize();
                addVeiculo(v);
            }
        }
    }
    
    public void removerPedido(Pedido p){
        listaPedidos.remove(p);
    }
    
    public void abastecer(String matricula,int g){
        for( int i = 0;i < veiculos.size();i++){
            if(matricula.equals(veiculos.get(i).getMatricula())){
                int x=0;
                Veiculo v = veiculos.get(i);
                x=v.getAutonomia();
                v.setAutonomia(x+g);
            }
        }
        for( int i = 0;i < nveiculos.size();i++){
            if(matricula.equals(nveiculos.get(i).getMatricula())){
                int x=0;
                Veiculo v = nveiculos.get(i);
                x=v.getAutonomia();
                v.setAutonomia(x+g);
            }
        }
    
    }
    
    public void setPreco(String matricula,double preco){
        for( int i = 0;i < veiculos.size();i++){
            if(matricula.equals(veiculos.get(i).getMatricula())){
                Veiculo v = veiculos.get(i);
                v.setPrecoBase(preco);
            }
        }
        for( int i = 0;i < nveiculos.size();i++){
            if(matricula.equals(nveiculos.get(i).getMatricula())){
                Veiculo v = nveiculos.get(i);
                v.setPrecoBase(preco);
            }
        }
    
    }
    
    public ArrayList<Pedido> getPendingPedidos(){
        ArrayList<Pedido> out = new ArrayList<Pedido>();
        for(int i = 0; i < listaPedidos.size(); i++){
            if(listaPedidos.get(i).getEstado() != 2||listaPedidos.get(i).getEstado() != 3){
                out.add(listaPedidos.get(i));
            }
        }
        return out;
    }
    
    public String listarAlugueresDatas(GregorianCalendar inicio,GregorianCalendar fim){
        StringBuilder s = new StringBuilder();
        
        s.append("Histórico de Alugueres entre as Datas pedidas: \n");
        for( int i = 0 ; i < listaAlugueres.size(); i++){
                if(inicio.compareTo(fim) >= 0){s.append((i+1)+" - "+listaAlugueres.get(i).toString()+"\n");}
            }
        return s.toString();
    }

}