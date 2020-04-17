
/**
 * Escreva a descrição da classe Conjunto aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
import java.util.TreeMap;
import java.io.Serializable;
import java.util.ArrayList;
import java.lang.Math;

public class Conjunto implements Serializable
{
    private TreeMap<Integer,Proprietario> proprietarios;
    private TreeMap<Integer,Cliente> clientes;
    private TreeMap<String,Veiculo> veiculos;
    private TreeMap<Integer,Pedido> alugueres;
    private Cliente c;
    private Proprietario p;
    private Utilizador u;
    private Input input;
    /**
     * COnstrutor para objetos da classe Conjunto
     */
    public Conjunto()
    {
        // inicializa variáveis de instância
        this.proprietarios = new TreeMap<Integer,Proprietario>();
        this.clientes = new TreeMap<Integer,Cliente>();
        //this.utilizadores = new TreeMap<Integer,Utilizador>();
        this.veiculos = new TreeMap<String,Veiculo>();
        this.alugueres = new TreeMap<Integer,Pedido>();
        this.p = new Proprietario("Andreia Alves",1,"a54759@alunos.uminho.pt","password","rua do telhado");
        this.c = new Cliente();
        this.proprietarios.put(p.getNif(),p);
        this.clientes.put(c.getNif(),c);
    }

    public Proprietario getProprietario()        { return this.p; }
    
    public TreeMap<Integer,Proprietario> getProprietarios() { return this.proprietarios; }
    public TreeMap<Integer,Cliente> getClientes() { return this.clientes; }
    //public TreeMap<Integer,Utilizador> getUtilizadores(){return this.utilizadores;}
    public TreeMap<String,Veiculo> getVeiculos() { return this.veiculos; }    
    public TreeMap<Integer,Pedido> getAlugueres(){ return this.alugueres; }
    public Proprietario getAdmin(){return this.p;}
    public Proprietario getProprietario(int nif) throws Excepcoes {
        Proprietario p = proprietarios.get(nif);
        if(p==null) { throw new Excepcoes("Utilizador não existe!\n"); }
            else { return p; }
    }
    
    public Cliente getCliente(int nif) throws Excepcoes {
        Cliente c = clientes.get(nif);
        if(p==null) { throw new Excepcoes("Utilizador não existe!\n"); }
            else { return c; }
    }
    

    
    public String getListaProprietarios(){
        StringBuilder s = new StringBuilder();
        Proprietario p;
        s.append("*** Lista de Proprietarios ***\n\n");
        s.append("        NOME        |       EMAIL        \n"); 
        s.append("-----------------------------------------\n");
        for(int nif : proprietarios.keySet()){
            p = proprietarios.get(nif);
            s.append(p.getNome());
            s.append(" - ");
            s.append(p.getEmail());
            s.append("\n");
        }
        s.append("------------------------------------------\n");
        return s.toString();
    }
    
    public String getListaClientes(){
        StringBuilder s = new StringBuilder();
        Cliente c;
        s.append("*** Lista de Clientes ***\n\n");
        s.append("        NOME        |       EMAIL        \n"); 
        s.append("-----------------------------------------\n");
        for(int nif : clientes.keySet()){
            c = clientes.get(nif);

            s.append(c.getNome());
            s.append(" - ");
            s.append(c.getEmail());
            s.append("\n");
        }
        s.append("------------------------------------------\n");
        return s.toString();
    }
    
    /**Pesquisa proximidade*/
    public String getVeiculosProximos(){
        StringBuilder s = new StringBuilder();
        double xInicial = 0;
        double xDestino = 0;
        double yInicial = 0;
        double yDestino = 0;
        Veiculo v;
        TreeMap<Double,Veiculo> listaDist = new TreeMap<Double,Veiculo>();
        System.out.println("Porfavor insira a sua longitude (x)");
        xInicial = input.lerDouble();
        System.out.println("Por favor insira a sua longitude (y)");
        yInicial = input.lerDouble();
        listaDist = listaProximos(xInicial,yInicial);
        s.append("***            Lista de Veiculos            ***\n\n");
        s.append("        Distancia       |       Matricula        \n"); 
        s.append("-------------------------------------------------\n");    
        for(double dist : listaDist.keySet()){
            v = listaDist.get(dist);
            s.append(dist);
            s.append(" - ");
            s.append(v.getMatricula());
            s.append("\n");
        }
        s.append("--------------------------------------------------\n");
        return s.toString();
    }
    
    public boolean novoPedido(String matricula,int n,int xD,int yD){
        boolean t = false;
        double xIn,yIn;
        Proprietario p;
        Veiculo v = veiculos.get(matricula);
        int nif = v.getNif();
        p = proprietarios.get(nif);
        xIn = v.getLocalizacao().getX();
        yIn = v.getLocalizacao().getY();
        
        p.addPedido(xIn,yIn,xD,yD,matricula,nif);
        return t;
    
    }
    
    public TreeMap<Double,Veiculo> listaProximos(double xInicial,double yInicial){
        TreeMap<Double,Veiculo> aux = new TreeMap<Double,Veiculo>();
        Veiculo v;
        double x,y,dist;
        String ma;
        for(String matricula : veiculos.keySet()){
            v = veiculos.get(matricula);
            x = v.getLocalizacao().getX();
            y = v.getLocalizacao().getY();
            dist = distancia(xInicial,yInicial,x,y);
            aux.put(dist,v);
        }
        return aux;
    }
    
    public static double distancia(double xInicial,double yInicial, double xDestino, double yDestino){
        double dist = 0;
        dist = Math.sqrt((xDestino-xInicial)*2+(yDestino-yInicial)*2);
        return dist;
    
    }
    
    /**pesquisa preço*/
    
    public String getVeiculosBaratos(){
        StringBuilder s = new StringBuilder();
        Veiculo v;
        double xDest,yDest;
        TreeMap<Double,Veiculo> listaPreco = new TreeMap<Double,Veiculo>();
        System.out.println("Por favor insira a longitude (x) do destino");
        xDest = input.lerDouble();
        System.out.println("Por favor insira a longitude (y) do destino");
        yDest = input.lerDouble();
        listaPreco = listaBaratos(xDest,yDest);
        s.append("***            Lista de Veiculos            ***\n\n");
        s.append("          Preço       |         Matricula        \n"); 
        s.append("-------------------------------------------------\n");    
        for(double preco : listaPreco.keySet()){
            v = listaPreco.get(preco);
            s.append(preco);
            s.append(" - ");
            s.append(v.getMatricula());
            s.append("\n");
        }
        s.append("--------------------------------------------------\n");
        return s.toString();
    }
    
    public TreeMap<Double,Veiculo> listaBaratos(double xDest,double yDest){
        TreeMap<Double,Veiculo> aux = new TreeMap<Double,Veiculo>();
        Veiculo v;
        double xcarro,ycarro,preco,km;
        String ma;
        for(String matricula : veiculos.keySet()){
            v = veiculos.get(matricula);
            xcarro = v.getLocalizacao().getX();
            ycarro = v.getLocalizacao().getY();
            km = v.getPrecoBase();
            preco = menorPreco(xDest,yDest,xcarro,ycarro,km);
            aux.put(preco,v);
        }
        return aux;
    }
    
    public double menorPreco(double xDest,double yDest,double xcarro,double ycarro,double km){  
        double preco,dist;
        dist = distancia(xcarro,ycarro,xDest,yDest);
        preco = dist*km;
        return preco;
    }
    /**Pesquisa por preço e distancia*/
    public String getVeiculosPrecoDist(){
        StringBuilder s = new StringBuilder();
        Veiculo v;
        double xDest,yDest,distancia;
        TreeMap<Double,Veiculo> listaPrecoDist = new TreeMap<Double,Veiculo>();
        System.out.println("Por favor insira a longitude (x) do destino");
        xDest = input.lerDouble();
        System.out.println("Por favor insira a longitude (y) do destino");
        yDest = input.lerDouble();
        System.out.println("Por favor insira a distancia que esta disposto a andar em m");
        distancia = input.lerDouble();
        listaPrecoDist = listaBaratosPerto(xDest,yDest,distancia);
        s.append("***    Lista de Veiculos dentro de ");s.append(distancia);s.append("m        ***\n\n");
        s.append("          Preço       |         Matricula        \n"); 
        s.append("-------------------------------------------------\n");    
        for(double dist : listaPrecoDist.keySet()){
            v = listaPrecoDist.get(dist);
            s.append(dist);
            s.append(" - ");
            s.append(v.getMatricula());
            s.append("\n");
        }
        s.append("--------------------------------------------------\n");
        return s.toString();
    }
    

    public TreeMap<Double,Veiculo> listaBaratosPerto(double xDest,double yDest,double distancia){
        TreeMap<Double,Veiculo> aux = new TreeMap<Double,Veiculo>();
        Veiculo v;
        double xcarro,ycarro,preco,km,dist;
        String ma;
        for(String matricula : veiculos.keySet()){
            v = veiculos.get(matricula);
            xcarro = v.getLocalizacao().getX();
            ycarro = v.getLocalizacao().getY();
            dist = distancia(xcarro,ycarro,xDest,yDest);
            km = v.getPrecoBase();
            preco = menorPreco(xDest,yDest,xcarro,ycarro,km);
            if(dist < distancia){aux.put(preco,v);}
            
        }
        return aux;
    }
    /**Procura por autonomia*/
    public String getVeiculosAutonomia(){
        StringBuilder s = new StringBuilder();
        Veiculo v;
        double xDest,yDest;
        TreeMap<Double,Veiculo> listaAutonomia = new TreeMap<Double,Veiculo>();
        System.out.println("Por favor insira a longitude (x) do destino");
        xDest = input.lerDouble();
        System.out.println("Por favor insira a longitude (y) do destino");
        yDest = input.lerDouble();
        listaAutonomia = listaAutonoma();
        s.append("***            Lista de Veiculos            ***\n\n");
        s.append("          Autonomia       |         Matricula        \n"); 
        s.append("-------------------------------------------------\n");    
        for(double autonomia : listaAutonomia.keySet()){
            v = listaAutonomia.get(autonomia);
            s.append(autonomia);
            s.append(" - ");
            s.append(v.getMatricula());
            s.append("\n");
        }
        s.append("--------------------------------------------------\n");
        return s.toString();
    }
    
    public TreeMap<Double,Veiculo> listaAutonoma(){
        TreeMap<Double,Veiculo> aux = new TreeMap<Double,Veiculo>();
        Veiculo v;
        double autonomia;
        for(String matricula : veiculos.keySet()){
            v = veiculos.get(matricula);
            autonomia = v.getAutonomia();;
            aux.put(autonomia,v);
        }
        return aux;
    }
    /**Aceitar ou Rejeitar Pedidos*/
    public void aceitarRejeitar(Proprietario prop){
        Pedido pedido = new Pedido();
        p = prop;
        ArrayList<Pedido> pending = p.getPendingPedidos();
        int i;        
        for( i = 0; i < pending.size(); i++){
                pedido = pending.get(i);
                pedido.toString();
                System.out.println("Porfavor escolha uma opção\n1-Aceitar\n2-Rejeitar\n3-Ignorar\n0-MenuProprietario\n");
                int op = input.lerInt();
                switch(op){
                case 1: pedido.setEstado(2);criaViagem(pedido);break;
                case 2: pedido.setEstado(3);p.removerPedido(pedido);break;
                case 3: pedido.setEstado(1);break;
                default:System.out.println("Opção invalida\n Introduza outra opção");op = input.lerInt();
                }
        }
    }
    
    public void criaViagem(Pedido pedido){
        Viagem viagem =new Viagem();
        int ncliente = pedido.getNif();
        for( int nif : clientes.keySet()){
            Cliente c = clientes.get(nif);
            if(ncliente == nif){viagem = mudaPedidoViagem(pedido);
            c.addViagem(viagem);
            }
        }
    
    }
    
    public Viagem mudaPedidoViagem(Pedido pedido){
        Viagem viagem = new Viagem();
        String mat;
        Coordenadas inicio;
        viagem.setLoc(pedido.getLoc());
        viagem.setLocDest(pedido.getLocDest());
        viagem.setNif(pedido.getNif());
        viagem.setMatricula(pedido.getMatricula());
        mat = pedido.getMatricula();
        Veiculo vei = veiculos.get(mat);
        double pb = vei.getPrecoBase();
        viagem.setPreco(calcPreco(pedido.getLoc(),pedido.getLoc(),pb));
        
        return viagem;
    }
    
    public double calcPreco(Coordenadas inicio, Coordenadas fim,double pb){
        double xI,yI,xD,yD;
        String matricula;
        xI = inicio.getX();
        yI = inicio.getY();
        xD = fim.getX();
        yD = fim.getY();
        return menorPreco(xI,yI,xD,yD,pb);
    }
    /**Lista de carros do historico*/
    
     public String toListaCarros(ArrayList<Veiculo> vei){
        StringBuilder s = new StringBuilder();
        s.append("Histórico de Carros: \n");
        for( int i = 0 ; i < vei.size(); i++){
                s.append((i+1)+" - "+vei.get(i).toString()+"\n");
            }
        return s.toString();
    }
    
    public ArrayList<Veiculo> matParaVei(ArrayList<String> matriculas){
        ArrayList<Veiculo> vei = new ArrayList<Veiculo>();
        for(int i = 0; i < matriculas.size();i++){
            String m = matriculas.get(i);
            for (String matricula: veiculos.keySet()){
                Veiculo vi = veiculos.get(matricula);
                if(matricula == m) {vei.add(vi);}
            }
        }
        return vei;
    }
    /**Validar*/
    public boolean validaLoginUtilizador(String email, String pass) throws Excepcoes {
        if(!this.u.getEmail().equals(email)) { throw new Excepcoes("Email incorreto\n"); }
        if(!u.validaLogin(pass)) { throw new Excepcoes("Passe incorreta!\n"); }
        return true;
    }
    
    public boolean validaLoginProp(int nif, String pass) throws Excepcoes {
        Proprietario p = proprietarios.get(nif);
        if(p==null) { throw new Excepcoes("Utilizador não existe\n"); }
        if(!p.validaLogin(pass)) { throw new Excepcoes("Pass incorreta!\n"); }
        return true;
    }
    
    public boolean validaLoginCliente(int nif, String pass) throws Excepcoes {
        Cliente c = clientes.get(nif);
        if(c==null) { throw new Excepcoes("Utilizador não existe\n"); }
        if(!c.validaLogin(pass)) { throw new Excepcoes("Pass incorreta!\n"); }
        return true;
    }
    
    public boolean validarMatricula(String matricula) throws Excepcoes {
        Veiculo v = veiculos.get(matricula);
        if(v==null) { throw new Excepcoes("Veiculo não existe\n"); }
        return true;
    }
    
    
    
    public void insereNovoProprietario(Integer i, Proprietario p) throws Excepcoes{
        if(p == null){
            throw new Excepcoes("Erro ao criar novo utilizador! Tente outra vez.");
        }
        else{
            proprietarios.put(i,p);
        }
    }
    
    public void insereNovoCliente(Integer i, Cliente c) throws Excepcoes{
        if(c == null){
            throw new Excepcoes("Erro ao criar novo utilizador! Tente outra vez.");
        }
        else{
            clientes.put(i,c);
        }
    }
    
    public boolean validaNif(int nif) throws Excepcoes{
        if(nif == 0) {throw new Excepcoes ("Nif não pode ser zero!\n"); }
        if(proprietarios.containsKey(nif)){ throw new Excepcoes ("Email já registado!\n"); }
        if(clientes.containsKey(nif)){ throw new Excepcoes ("Email já registado!\n"); }
        return true;
    }
    
    public boolean emailExiste(String mail){
        boolean t;
        if((proprietarios.containsKey(mail))||clientes.containsKey(mail))return true;
        else return false;
    }
    
    public Proprietario getPropritario(int nif) throws Excepcoes{
        Proprietario p = proprietarios.get(nif);
        if (p==null){
            throw new Excepcoes("Utilizador não existe");
        }
        else{
            return p;
        }
    }    
    
}

