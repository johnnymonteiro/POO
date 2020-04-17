
/****
 * Classe com o método main para utilização da aplicação
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */


import java.io.*;
import java.util.Scanner;
import java.util.Calendar;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class GeoCachingPOO implements Serializable
{
    static Input input;   
    static Rede rede;
    static Utilizador user;     //user é a variável global utilizada para guardar o utilizador atual
    static String nomeFicheiro;
        
    public static void main(){
        input = new Input();
        rede = new Rede();
        user = new Utilizador();
        nomeFicheiro = "default";
        
        menuCarregarFicheiro();
    }
    
    /********************************************************************************************************************************************************************
     **************************************************************************** MENU INICIAL ************************************************************************** 
     ********************************************************************************************************************************************************************
     ********************************************************************************************************************************************************************/
    
    /**
     *  Menu inicial para carregar ficheiro existente ou criar um novo
     */
    public static void menuCarregarFicheiro(){
        int op;
        boolean ligado = true;
        while(ligado){            
            title();
            System.out.println("1 - Carregar ficheiro existente\n2 - Criar novo (apenas admin)\n0 - Sair do programa");
            op = input.lerInt();
            switch(op){
                case 0 : {
                            ligado = false; break;
                         }
                case 1 : { 
                            if(loadFile()) { menuLogin(); } 
                            else { title(); } 
                            break; 
                         }
                case 2 : {  if(criaNovo()) { menuPrincipal(); }
                            else{ System.out.println("Credenciais inválidas! Prima enter para continuar.....\n"); input.lerString(); }
                            break; 
                         }
                default: { 
                            System.out.println("\n *** Opção inválida! *** \n\n prima ENTER para voltar a tentar....");
                            input.lerString(); 
                            title(); 
                            break;
                         }
            }
        }
        System.exit(0);
    }
    
    public static boolean criaNovo(){
        System.out.println("Insira email de admin: ");
        String email = input.lerString();
        System.out.println("Insira password de admin: ");
        String password = input.lerString();
        
        try{
            rede.validaLoginAdmin(email,password);
            user = rede.getAdmin();
            saveFile();
            return true;
        } catch (Excepcoes e) {
            System.out.println(e);
            user = new Utilizador();
            return false;
        }
        
    }
    /********************************************************************************************************************************************************************
     *************************************************************** MENU PARA FAZER LOGIN ****************************************************************************** 
     ********************************************************************************************************************************************************************
     ********************************************************************************************************************************************************************/
    
    
        /***
     * MENU DE LOGIN NA APLICAÇÃO
     * Este menu aparece depois de carregar um ficheiro já existente.
     */
    
    public static void menuLogin(){
        
        int op = -1;     
        
        while(op!=0){ 
            title();
            System.out.println("1 - Login\n2 - Criar conta\n0 - Voltar atrás");
            op = input.lerInt();
            title();
            switch(op){
               case 0 : { break;} 
               case 1 : {  
                           if(login()){ //se o login for válido vai para o menu principal. Dados de utilizador ficam carregados na variável user
                            menuPrincipal();
                           }
                           else{
                               System.out.println("Credenciais inválidas!\nPrima ENTER para continuar..."); input.lerString();
                           }
                           break;
                        }
               case 2 : {
                           criaConta(); //cria conta e vai para o menu principal. Dados de utilizador ficam carregado na variável user
                           menuPrincipal();
                           break;
                        }
            }
        }
        menuCarregarFicheiro();               
    }
    
    
    /********************************************************************************************************************************************************************
     ******************************************************************************************************************************************************************** 
     **************************************************************   VALIDAÇÕES DE LOGIN    ****************************************************************************
     ********************************************************************************************************************************************************************/
    
     
       
    /**
     * FUNÇÃO DE LEITURA DO LOGIN DE UM USER
     * Esta função pede ao utilizador para inserir as suas credenciais de login, email e password, e caso estejam corretas retorna true, caso contrário retorna false.
     */
    public static boolean login(){
        String email,pass;
        System.out.println("Insira email: ");
        email = input.lerString();
        System.out.println("Insira password: ");
        pass = input.lerString();
        try {
            rede.validaLogin(email,pass);
            user = rede.getUtilizador(email);
            return true;
        } catch(Excepcoes e){
            System.out.println(e);
            return false;
        }
    }
    
    
    /********************************************************************************************************************************************************************
     *************************************************************** MENU PRINCIPAL ******************************************************************************** 
     ********************************************************************************************************************************************************************
     ********************************************************************************************************************************************************************/
    
    
     /**
      * Menu principal da aplicação
      */    
    public static void menuPrincipal(){
        boolean sair=false;
        int op;
        while(!sair){
            title();
            System.out.println("  ***           MENU PRINCIPAL              ***  \n");
            System.out.println("\n1 - Lista de utilizadores\n2 - Amigos\n3 - Caches & Atividades\n4 - Consultar dados pessoais\n"+
                               "5 - Editar dados pessoais\n6 - Estatísticas Mensais GeoCachingPOO\n7 - Estatísticas Anuais GeoCachingPOO\n"+
                               "8 - Gravar \n9 - Logout");
            op = input.lerInt();
            switch(op){
                case 1 : { System.out.println(rede.getListaUsers()+"\nPrima ENTER para continuar..."); input.lerString(); break;}
                case 2 : { menuAmigos(); break;}
                case 3 : { menuCaches(); break; }
                case 4 : { System.out.println(user.toString());
                           System.out.println("\nPrima ENTER para continuar....\n"); input.lerString();
                           break;
                         }
                case 5 : { menuEditarDados(); break; }
                case 6 : { System.out.println("Qual o ano a consultar?"); int ano = input.lerInt(); statsMensaisApp(ano); break; }
                case 7 : { statsAnuaisApp(); break; }
                case 8 : { saveFile(); break;  }
                case 9 : {   
                            int grava=0;
                            while(grava!=1 && grava!=2){
                                System.out.println("Pretende gravar antes de voltar atrás?\n1 - Sim\n2 - Não");
                                grava = input.lerInt();
                                if(grava==1) { saveFile(); }                                
                            }
                            
                            user = new Utilizador();
                            sair=true; 
                            break; 
                         }
                default: { System.out.println("Opção inválida!\nPrima ENTER para continuar..."); input.lerString(); break; }
            }
        }
        menuLogin(); 
    }
    
    public static void statsMensaisApp(int ano){
        ArrayList<ArrayList<String>> cs = new ArrayList<>();
        ArrayList<ArrayList<String>> uu = new ArrayList<>();
        uu = rede.registosMensais(ano);
        cs = rede.cachesMensais(ano);
        
        int op=-1;
        
        while(op!=0){
            
            title();
            System.out.println("\n** Estatísticas do ano "+ano+" **\n");
            for(int i = 0 ; i < 12 ; i++){
                System.out.println("Mês "+(i+1)+" -> Utilizadores registados: "+uu.get(i).size()+" | Caches criadas: "+cs.get(i).size());
            }
            System.out.println("\n\n");
        
            System.out.println("1 - Consultar emails registados num dado mês\n2 - Consultar códigos de caches criadas num dado mês\n0 - Sair");
            op = input.lerInt();
            switch(op)
            {
                case 0 : { break; }
                case 1 : { System.out.println("Qual o mês a consultar?"); int mes = input.lerInt(); mes--;
                           if(mes<0 || mes>11) { System.out.println("Mês inválido"); }
                           else { if(uu.get(mes).size()==0) { System.out.println("Não há registos a apresentar nesse mês\n"); }
                                  else {
                                        System.out.println("____________________________\n");
                                        for(String s : uu.get(mes)) { System.out.println(s); }
                                        System.out.println("____________________________");                          
                                  }
                           }
                           System.out.println("Prima ENTER para continuar..."); input.lerString();
                           break;
                          }
                case 2 : { System.out.println("Qual o mês a consultar?"); int mes = input.lerInt(); mes--;
                           if(mes<0 || mes>11) { System.out.println("Mês inválido"); break; }
                           else { if(cs.get(mes).size()==0) { System.out.println("Não há criações de caches a apresentar nesse mês\n"); }
                                  else {
                                        System.out.println("____________________________\n");   
                                        for(String s : cs.get(mes)) { System.out.println(s); } 
                                        System.out.println("____________________________");   
                                  }
                           }
                           System.out.println("Prima ENTER para continuar..."); input.lerString();
                           break;
                         }
                default: { System.out.println("Opção inválida! Prima ENTER para continuar..."); input.lerString(); break; }
            }
        }
        
    }
    
    
    public static void statsAnuaisApp(){
        HashMap<Integer,ArrayList<String>> cc = new HashMap<>();
        HashMap<Integer,ArrayList<String>> uu = new HashMap<>();
        cc = rede.cachesAnuais();
        uu = rede.registosAnuais();
        
        int op=-1;
        
        while(op!=0){
            
            title();
            System.out.println("*** Registo de utilizadores ***\n");
            
            for(int ano : uu.keySet()){
                System.out.println("Ano "+ano+" -> Utilizadores registados: "+uu.get(ano).size());
            }
            
            System.out.println("\n___________________________________\n\n*** Criação de caches ***\n");
            
            for(int ano : cc.keySet()){
                System.out.println("Ano "+ano+" -> Caches criadas: "+uu.get(ano).size());
            }
            System.out.println("\n\n");
        
            System.out.println("1 - Consultar emails registados num dado ano\n2 - Consultar códigos de caches criadas num dado ano\n3 - Consultar Estaísticas mensais de um ano\n0 - Sair");
            op = input.lerInt();
            switch(op)
            {
                case 0 : { break; }
                case 1 : { System.out.println("Qual o ano a consultar?"); int ano = input.lerInt();
                           if(uu.get(ano)==null) { System.out.println("Não há registos a apresentar nesse ano!");}
                           else { System.out.println("____________________________\n");
                                  for(String s : uu.get(ano)) { System.out.println(s); }
                                  System.out.println("____________________________"); 
                                  }
                           System.out.println("Prima ENTER para continuar..."); input.lerString();
                           break;
                         }
                case 2 : { System.out.println("Qual o ano a consultar?"); int ano = input.lerInt();
                           if(cc.get(ano)==null) { System.out.println("Não há caches criadas nesse ano!"); }
                           else { System.out.println("____________________________\n");   
                                  for(String s : cc.get(ano)) { System.out.println(s); } 
                                  System.out.println("____________________________");  
                                }
                           System.out.println("Prima ENTER para continuar..."); input.lerString(); 
                            break;
                         }
                case 3 : { System.out.println("Qual o ano a consultar?"); int ano = input.lerInt();
                           if(uu.get(ano)==null) { System.out.println("Não há registos a apresentar nesse ano!"); 
                                                   System.out.println("Prima ENTER para continuar..."); input.lerString(); }
                           else{ statsMensaisApp(ano); }
                           break;
                         }
                default: { System.out.println("Opção inválida! Prima ENTER para continuar..."); input.lerString(); break; }
            }
        }
    }
    
    
    /********************************************************************************************************************************************************************
     ******************************************************* MENU E FUNÇÕES RESPECTIVAS A AMIZADES ********************************************************************** 
     ********************************************************************************************************************************************************************
     ********************************************************************************************************************************************************************/
    
    
     /**
      * Menu principal da aplicação
      */ 
    public static void menuAmigos(){
        int op=-1;
        while(op!=0){
            title();
            System.out.println("  ***            AMIZADES           ***  \n");
            System.out.println("1 - Lista de amigos\n2 - Lista de pedidos\n3 - Aceitar pedido\n4 - Remover pedido \n5 - Enviar pedido\n6 - Remover amigo\n"+
                               "7 - Consultar dados pessoais de uma amigo\n0 - Sair\n");
            op = input.lerInt();
            switch(op){                
                case 0 : { break; }
                case 1 : { TreeSet<String> am = new TreeSet<>(user.getAmigos());
                           if (am.size()==0) { 
                               System.out.println("Ainda não há amigos adicionados à lista.\n"); 
                            }
                           else {
                               Utilizador ut;
                               System.out.println("\n_____________________________________\n"); 
                               for(String email : am){
                                   try{
                                       ut = rede.getUser(email);
                                       System.out.println(email+" - "+ut.getNome());
                                    }
                                   catch(Excepcoes e){
                                       try {
                                                rede.removeAmigo(email, user.getEmail()); //remover email da lista de amigos porque não existe na lista de utilizadores
                                       }
                                       catch(Excepcoes ex){
                                        }                                     
                                       
                                    }
                                }
                               System.out.println("_____________________________________\n"); 
                           }
                           System.out.println("Prima ENTER para continuar...\n"); input.lerString();
                           break;
                         }
                case 2 : { TreeSet<String> ped = new TreeSet<>(user.getPedidos());
                           if (ped.size()==0) { 
                               System.out.println("Não existem pedidos pendentes\n"); 
                            }
                           else {
                               System.out.println("\n_____________________________________\n"); 
                               for(String email : ped){
                                       System.out.println(email+"\n");
                                }
                               System.out.println("_____________________________________\n"); 
                           }
                           System.out.println("Prima ENTER para continuar...\n"); input.lerString();
                           break;
                         }
                case 3 : { aceitaPedidoAmizade(); break;}
                case 4 : { removerPedidoAmizade(); break; }
                case 5 : { enviarPedidoAmizade(); break;}
                case 6 : { removeAmigo(); break; }
                case 7 : { consultaDados(); break; }
                default: { System.out.println("Opção inválida!\nPrima ENTER para continuar..."); input.lerString(); break; }
            }
        }
    }
    
        public static void removeAmigo(){
        String email;
        System.out.println("Insira o email do amigo a remover");
        email = input.lerString();
        String m = user.getEmail();
        
        try {
            rede.removeAmigo(email,m);
            System.out.println("Amigo removido com sucesso!\n"); 
        } 
        catch(Excepcoes e){
             System.out.println(e+"\n");
        }
        
        System.out.println("Prima ENTER para continuar..."); input.lerString();
    }
    
     public static void removerPedidoAmizade(){
        String email;
        System.out.println("Insira o email do pedido a remover");
        email = input.lerString();
        String m = user.getEmail();
        
        try{
            rede.removePedido(email,m);
            System.out.println("Pedido removido com sucesso!\n");
        }
        catch(Excepcoes e){
            System.out.println(e+"\n");
        }
        
        System.out.println("Prima ENTER para continuar..."); input.lerString();
    }
    
    public static void aceitaPedidoAmizade(){
        String email;
        System.out.println("Insira o email do pedido a aceitar");
        email = input.lerString();
        String m = user.getEmail();
        try{
            rede.aceitaPedido(email,m);
            System.out.println("Amigo adicionado\n");
        }
        catch(Excepcoes e){
            System.out.println(e+"\n");
        }
        
        System.out.println("Prima ENTER para continuar..."); input.lerString();
    }
    
    public static void enviarPedidoAmizade(){
        String email;
        System.out.println("Insira o mail do utilizador para enviar pedido de amizade");
        email = input.lerString();
        String m = user.getEmail();
        int res = -1;
        try{
            res = rede.enviarPedido(email,m);
        }
        catch(Excepcoes e){
            System.out.println(e+"\n");
        }
        
        switch(res){
            case 0 : {  System.out.println("Pedido Enviado! Aguarda aprovação do utilizador"); break; }        
            case 1 : {  System.out.println("Email não existe. Consulte na lista de utilizadores os email registados!"); break; }
            case 2 : {  System.out.println("Este utilizador já lhe enviou um pedido! Deseja aceitar amizade?\n1 - Sim\n2 - Não (remove email da lista de pedidos)\n0 - Decidir depois");
                        int op = input.lerInt();
                        while(op<0 && op > 2){
                            switch(op){
                                case 1 : {  try { rede.aceitaPedido(email,m); } catch(Excepcoes e) { System.out.println(e+"\n"); }
                                            break; 
                                         }
                                case 2 : {  try { rede.removePedido(email,m); } catch(Excepcoes e) { System.out.println(e+"\n"); }
                                            break;
                                         }
                                case 0 : { break; }
                                default: { System.out.println("Opção inválida! Prima ENTER para voltar a tentar..."); input.lerString(); break;  }
                            }
                        }
                        break;
                     }
            case 3 : {  System.out.println("Este utilizador já está na sua lista de amigos!"); break; }
        }
        
        
        System.out.println("Prima ENTER para continuar..."); input.lerString();
    }
    
    public static void consultaDados(){
        String email; int op=-1;  Utilizador amigo;
        System.out.println("Insira email do utilizador a consultar\n");
        email = input.lerString();
        
        if(!rede.existeAmigo(email,user.getEmail()) && !user.getEmail().equals("admin@geocachingpoo.pt")) 
            {  System.out.println("Este utilizador não está na sua lista de amigos!\nPrima ENTER para continuar..."); 
               input.lerString();
               return;
            }
                                                     
        try{
            amigo = rede.getUser(email).clone();
            System.out.println(amigo.toString()); System.out.println("Prima ENTER para continuar.....\n"); input.lerString();
        }
        catch(Excepcoes e){
            System.out.println(e);
            return;
        }
        
    }
    
    
    /********************************************************************************************************************************************************************
     **************************************************** CRIAÇÃO DE CONTA E EDIÇÃO DE DADOS PESSOAIS ******************************************************************* 
     ********************************************************************************************************************************************************************
     ********************************************************************************************************************************************************************/
    
     
     /**
     * FUNÇÃO PARA CRIAR NOVA CONTA DE UTILIZADOR
     */
    public static void criaConta(){
        String email = "", password, nome, genero="", morada="";
        int dia=0, mes=0, ano=0, gen=0, flagAno=0, param=0;
        boolean validado = false;
        while(validado == false){
            try{                
                System.out.println("Insira email: ");
                email = input.lerString();
                validado = rede.validaMail(email);
            } catch (Excepcoes e){
                System.out.println(e); 
            }
        }
        
        System.out.println("Insira password: "); password = input.lerString();
        System.out.println("Insira nome: "); nome = input.lerString();
        System.out.println("Insira morada: "); morada = input.lerString();
        
        while(gen!=1 && gen !=2){
            System.out.println("Género: 1 - Masculino | 2 - Feminino");
            gen = input.lerInt();
            if(gen==1) { genero = "Masculino"; }
            else if(gen==2) { genero = "Feminino"; }
            else { System.out.println("Opção inválida"); }
        }
        
         while(flagAno!=1){
            System.out.println("Data de nascimento:\nANO: ");
            ano = input.lerInt();
            System.out.println("MES: ");
            mes = input.lerInt(); mes--; //meses vão de 0 a 11
            System.out.println("Dia: ");
            dia = input.lerInt();
            if(validaData(ano,mes,dia,false)) { flagAno = 1; }      //false porque a data terá de ser uma data anterior à de hoje (ou a de hoje)
            else { System.out.println("Data inválida"); }
        }
        GregorianCalendar dn = new GregorianCalendar(ano,mes,dia);
        GregorianCalendar dr = new GregorianCalendar();//data de registo --> HOJE
        
        
        user = new Utilizador(email,password,nome,genero,morada, dn ,dr);
        try {
            rede.insereNovo(email,user);
        }
        catch(Excepcoes e){
            System.out.println(e+"\n");
        }
    }
     
    /**
     *  Menu/função para editar dados pessoais
     */
    public static void menuEditarDados(){
        int op=-1;
        
        while(op!=0){
            title();
            
            System.out.println("  ***        EDIÇÃO DADOS PESSOAIS            ***  \n");
            System.out.println("1 - Editar email \n2 - Editar nome\n3 - Editar password\n4 - Editar género\n5 - Editar morada\n6 - Editar data de Nascimento\n0 - Sair");
            op = input.lerInt();
            switch(op){
                case 0: { break; }
                case 1: { System.out.println("Insira novo email: "); String s = input.lerString(); user.setEmail(s); break; }
                case 2: { System.out.println("Insira novo nome: "); String s = input.lerString(); user.setNome(s); break; }
                case 3: { System.out.println("Insira password antiga: "); String s = input.lerString(); 
                          if(s.equals(user.getPassword())) { System.out.println("Insira password nova: "); s = input.lerString(); user.setPassword(s); }
                          else { System.out.println("Password errada!");}
                          break;
                        }
                case 4: { int gen = 0;
                          while(gen!=1 && gen !=2){
                              System.out.println("Género: 1 - Masculino | 2 - Feminino");
                              gen = input.lerInt();
                              if(gen==1) { user.setGenero("Masculino"); }
                              else if(gen==2) {user.setGenero("Feminino"); }
                                    else { System.out.println("Opção inválida!");input.lerString(); }
                          }                  
                          break;
                        }
                case 5: { System.out.println("Insira nova morada: "); String s = input.lerString(); user.setMorada(s); }
                case 6: {  System.out.println("Insira nova data\nAno: "); int ano = input.lerInt();
                           System.out.println("Mes: "); int mes = input.lerInt(); mes--;
                           System.out.println("Dia: "); int dia = input.lerInt();
                           GregorianCalendar dn = new GregorianCalendar(ano,mes,dia);
                           if(validaData(ano,mes,dia,false)) { user.setDataNasc(dn); }  //false porque a data terá de ser uma data anterior à de hoje (ou a de hoje)
                           else {  System.out.println("Data inválida!"); input.lerString(); }
                           break;
                        }                
                default : { System.out.println("Opção inválida!"); break;}
            }
            System.out.println("Prima ENTER para continuar....."); input.lerString();
        }
    }
    
    
    /********************************************************************************************************************************************************************
     ******************************************************* MENU E FUNÇÕES RESPECTIVAS A CACHES ************************************************************************ 
     ********************************************************************************************************************************************************************
     ********************************************************************************************************************************************************************/
    
    
    /**
     * Menu com todas as opções sobre caches: registos, consultas, remoções, criações etc.
     */
    public static void menuCaches(){
        int op = -1;        
        while(op!=0){
            title();
            System.out.println("  ***          CACHES & ATIVIDADES           ***  \n");
            System.out.println("1  - Criar cache\n2  - Remover Cache\n3  - Consultar caches\n4  - Registar nova atividade\n5  - Últimas 10 atividades\n"+
                               "6  - Consultar todas as atividades\n7  - Remover atividade\n8  - Estatísticas Mensais\n9  - Estatísticas Anuais\n"+
                               "10 - Report Abuse\n11 - Consultar Reports(Admin)\n0  - Sair\n");
            op = input.lerInt();
            switch(op){
                case 0 : { break; }
                case 1 : { menuCriarCache(); break; }
                case 2 : { removerCache(); break; }
                case 3 : { consultaCaches(); break; }
                case 4 : { if(rede.cachesIsEmpty()) { System.out.println("Não existem caches criadas para adicionar à atividade\nPrima ENTER para continuar...."); input.lerString(); } 
                           else { registaAtividade(); } break; }
                case 5 : { infoQuem(5); break; }
                case 6 : { infoQuem(6); break; }
                case 7 : { removeAtividade(); break; }    
                case 8 : { infoQuem(8); break; }
                case 9 : { infoQuem(9); break; }
                case 10: { fazerReport(); break; }
                case 11: { menuReports(); break;}
                default: { System.out.println("Opção inválida!\n");}                 
            }
        }
        System.out.println("Prima ENTER para continuar...."); input.lerString();
    }
    
    /**
     * Menu para escolher o tipo de cache a criar
     */
    public static void menuCriarCache(){
        int op = -1;
        
        while(op!=0){
            title();
            System.out.println("Qual o tipo de cache a criar?\n1 - Micro Cache\n2 - Mistery Cache\n3 - Multi Cache\n4 - Virtual Cache\n5 - Event Cache\n0 - Sair");
            op = input.lerInt();            
            switch(op){
                case 0 : { break; }
                case 1 : 
                case 2 :
                case 3 :
                case 4 : 
                case 5 : { dadosCache(op); op = 0; break;}
                default : System.out.println("Opção inválida! Prima ENTER para continuar...."); input.lerString();
            }
            
        }
    }
    
    /**
     * Função que recebe os dados comuns a todas as caches. Recebe como parâmetro a cache escolhida pelo utilizador para depois reencaminhar a função de criação da mesma
     * Foi criada esta função para permitir reutilização de código uma vez que todas as caches têm alguns atributos obrigatórios que são comuns.
     */
    public static void dadosCache(int op){
        String cod ="zzz"; GregorianCalendar data = new GregorianCalendar();
        int flagAno = 0, ano, mes, dia;
        Coordenadas cor;
        boolean codigoVal = true;
        
        while(codigoVal==true){
            System.out.println("Código da cache: ");
            cod = input.lerString();
            codigoVal = rede.cacheExiste(cod);
            if(codigoVal == true){
                System.out.println("Código já registado. Por favor escolha outro código: ");
            }
        }
        
        System.out.println("Insira decrição da cache:" );
        String descricao = input.lerString();
        
        System.out.println("Qual a data em que foi criada esta cache?");        
        
        boolean val = false;
        
        while(val==false){
            System.out.println("Insira ano: ");
            ano = input.lerInt();
            System.out.println("Insira mes: ");
            mes = input.lerInt(); mes--;
            System.out.println("Insira dia: ");
            dia = input.lerInt();
            val = validaData(ano, mes, dia,false);
            if(val==false){
                System.out.println("Data inválida!");
            }
            else{
                data = new GregorianCalendar(ano,mes,dia);
            }
        }
        
        if(op!=3){      //se for a opção 3 então estamos perante uma multiCache o que significa que não é revelada a localização a não ser pela visita
                        //das varias localizações inseridas pelo criador.
            cor = getCoordenadas(); 
        }
        else{   
            Coord lat = new Coord(); // ficam com os valores 0, 0 ,0 'Z'
            Coord lon = new Coord();
            cor = new Coordenadas();
            cor.setLat(lat); cor.setLon(lon);
        }
        
        switch(op){
            case 1 : { try { rede.criaMicroCache(cod, data, descricao, cor, user.getNome()); } catch(Excepcoes e) {System.out.println(e+"\n");} break; }
            case 2 : { dadosMisteryCache(cod, data, descricao, cor, user.getNome()); break; }
            case 3 : { dadosMultiCache(cod, data, descricao, cor, user.getNome()); break; }
            case 4 : { try { rede.criaVirtualCache(cod, data, descricao, cor, user.getNome()); } catch(Excepcoes e) {System.out.println(e+"\n");} break; }
            case 5 : { dadosEventCache(cod, data, descricao, cor, user.getNome()); break; }
            default: break;
        }
        
    }
      
    public static void dadosMisteryCache(String cod, GregorianCalendar data, String descricao, Coordenadas cor, String criador) {
        System.out.println("Qual o objecto que se encontrará na cache?: ");
        String obj = input.lerString();
        System.out.println("Qual o tipo de charada? (por ex: \"Equação para desvendar grau da latitude\" ) ");
        String tipo = input.lerString();        
        System.out.println("Charada a resolver: ");
        String charada = input.lerString();        
        
        try { 
            rede.criaMisteryCache(cod, data, descricao, cor, criador, obj, tipo, charada);
        }
        catch(Excepcoes e){
            System.out.println(e+"\n");
        }
    }
    
     public static void dadosMultiCache(String cod, GregorianCalendar data, String descricao, Coordenadas cor, String criador){
        System.out.println("Qual o objecto que se encontrará na cache?: ");
        String obj = input.lerString();        
        System.out.println("Quantas coordenadas a visitar?: ");
        int n = input.lerInt();
        
        ArrayList<Coordenadas> lista = new ArrayList<>();
        
        for( int i = 0 ; i < n ; i++){
            Coordenadas c = getCoordenadas();
            lista.add(c);
        }
        
        try {
            rede.criaMultiCache(cod, data, descricao, cor, criador, obj, lista);
        }
        catch(Excepcoes e){
            System.out.println(e+"\n");
        }
    }
    
    public static void dadosEventCache(String cod, GregorianCalendar data, String descricao, Coordenadas cor, String criador){
        GregorianCalendar dataEncontro = null;
        boolean val = false;
        int ano, mes, dia;
        
        System.out.println("Qual a data para a realização do encontro?");        
                
        while(val==false){
            System.out.println("Insira ano: ");
            ano = input.lerInt();
            System.out.println("Insira mes: ");
            mes = input.lerInt(); mes--;
            System.out.println("Insira dia: ");
            dia = input.lerInt();
            val = validaData(ano, mes, dia,true);   //true significa que pode ser uma data futura, ou seja, maior que a de hoje
            if(val==false){
                System.out.println("Data inválida!");
            }
            else{
                dataEncontro = new GregorianCalendar(ano,mes,dia);
            }
        }
        
        
        try {
            rede.criaEventCache(cod, data, descricao, cor, criador, dataEncontro);
            System.out.println("O seu email foi automaticamente adicionada à lista de participantes\n");
            System.out.println("Prima ENTER para continuar..."); input.lerString();
        }
        catch(Excepcoes e){
            System.out.println(e+"\n");
        }
    }
    
    /**
     * Função para leitura de coordenadas (latitude e longitude)
     */
    public static Coordenadas getCoordenadas(){
        Coordenadas cor = new Coordenadas();        
        Coord lat, lon;
        int flagC = 0;
        
        while(flagC!=1){
            System.out.println("Coordenadas:\nLatitude ");
            System.out.print("grau: ");
            int g1 = input.lerInt(); 
            System.out.print("minuto: ");
            int m1 = input.lerInt();
            System.out.print("segundo: ");
            int s1 = input.lerInt();
            System.out.print("direção: ");
            char d1 = Character.toUpperCase(input.lerChar());
            if(cor.validaLat(g1,m1,s1,d1)){ lat = new Coord(g1,m1,s1,d1);
                                            cor.setLat(lat); 
                                            flagC = 1; }
            else { System.out.println("Coordenada Inválida! Prima ENTER para voltar a tentar....\n"); input.lerString(); }
        }
        flagC = 0;         
        while(flagC!=1){
            System.out.println("Coordenadas:\nLongitude -> grau min seg direcao: ");
            System.out.print("grau: ");
            int g2 = input.lerInt(); 
            System.out.print("minuto: ");
            int m2 = input.lerInt();
            System.out.print("segundo: ");
            int s2 = input.lerInt();
            System.out.print("direção: ");
            char d2 = Character.toUpperCase(input.lerChar());
            if(cor.validaLon(g2,m2,s2,d2)){ lon = new Coord(g2,m2,s2,d2);
                                            cor.setLon(lon); 
                                            flagC = 1; }
            else { System.out.println("Coordenada Inválida! Prima ENTER para voltar a tentar....\n"); input.lerString(); }
        }        
        return cor;
    }
    
    public static void registaAtividade(){
        String cod = "", desc = "", obr = "", obc = "", nome = "default";
        int op = 0, opCache = 0, ano, mes, dia;
        Meteo met = null;
        GregorianCalendar data = null;
        Cache ca = null;
        
        System.out.println("Insria nome/código para esta atividade:");
        nome = input.lerString();        
        
        
        boolean val = false;
        while(!val){
            
            System.out.println("\nQual o tipo de cache?\n1 - MicroCache\n2 - MisteryCache\n3 - MultiCache\n4 - Virtual Cache\n5 - EventCache");
            opCache = input.lerInt();
            while(opCache<1 || opCache>5){
                System.out.println("Opção inválida\n");             
                opCache = input.lerInt();
            }
            
            System.out.println("Insira o código da cache:");
            cod = input.lerString(); 
            if(!rede.cacheExiste(cod)){
                System.out.println("Código inválido");
            }            
            else { switch(opCache)
                   {
                       case 1 : { if(rede.isMicroCache(cod)) { val = true; } else { System.out.println("Este código não pertence a uma MicroCache"); } break; }
                       case 2 : { if(rede.isMisteryCache(cod)) { val = true; } else { System.out.println("Este código não pertence a uma MisteryCache"); } break; }
                       case 3 : { if(rede.isMultiCache(cod)) { val = true; } else { System.out.println("Este código não pertence a uma MultiCache"); } break; }
                       case 4 : { if(rede.isVirtualCache(cod)) { val = true; } else { System.out.println("Este código não pertence a uma VirtualCache"); } break; }
                       case 5 : { if(rede.isEventCache(cod)) { val = true; } else { System.out.println("Este código não pertence a uma EventCache"); } break; }                        
                   }         
            }
        }        

        ca = rede.getCache(cod);
        
        if(opCache==2 || opCache==3){
            System.out.println("Retirou o objeto desta cache?\n1 - Sim\n2 - Não");
            int x = input.lerInt();
            while(x!= 1 && x != 2){
                System.out.println("Opção inválida!\n");
                x = input.lerInt();
            }
        
            if(x==1){
                switch(opCache){
                    case 2 : {
                              MisteryCache mc = (MisteryCache) ca;
                              obr = mc.getObjeto();
                              System.out.println("Qual o objeto que colocou na cache? (se não colocou nenhum pf digite \"NONE\"");
                              obc = input.lerString();
                              mc.setObjeto(obc);
                              break;
                             }
                    case 3 : {
                             MultiCache mc = (MultiCache) ca;
                              obr = mc.getObjeto();
                              System.out.println("Qual o objeto que colocou na cache? (se não colocou nenhum pf digite \"NONE\"");
                              obc = input.lerString();
                              mc.setObjeto(obc);
                              break;
                            }
                default: { break; }
               }
            }
        }
        
        if(opCache==5) {
            EventCache ev = (EventCache) ca;
            ev.adicionaParticipante(user.getEmail());
        }
        System.out.println("Qual a meteorologia no decorrer da atividade?\n"
                           +" 1 - "+Meteo.CHFR.toString()+"\n"
                           +" 2 - "+Meteo.CH.toString()+"\n"
                           +" 3 - "+Meteo.CHFT.toString()+"\n"
                           +" 4 - "+Meteo.SOL.toString()+"\n"
                           +" 5 - "+Meteo.NEV.toString()+"\n"
                           +" 6 - "+Meteo.TEMP.toString()+"\n"
                           +" 7 - "+Meteo.NUB.toString()+"\n");
        
        op = input.lerInt();
        
        while(op<1 || op>7){
            System.out.println("Opção inválida\n");
            op = input.lerInt();
        }
        
        switch(op){
            case 1 : { met = Meteo.CHFR; break; } 
            case 2 : { met = Meteo.CH; break; } 
            case 3 : { met = Meteo.CHFT; break; } 
            case 4 : { met = Meteo.SOL; break; } 
            case 5 : { met = Meteo.NEV; break; } 
            case 6 : { met = Meteo.TEMP; break; }
            case 7 : { met = Meteo.NUB; break; } 
        }
        
        System.out.println("Insira descrição desta atividade");
        desc = input.lerString();
        
        boolean valData = false;
        
        while(valData==false){
            System.out.println("Insira ano: ");
            ano = input.lerInt();
            System.out.println("Insira mes: ");
            mes = input.lerInt(); mes--;
            System.out.println("Insira dia: ");
            dia = input.lerInt();
            valData = validaData(ano, mes, dia,false);  //false porque a data terá de ser uma data anterior à de hoje (ou a de hoje)
            if(valData==false){
                System.out.println("Data inválida!");
            }
            else{
                data = new GregorianCalendar(ano,mes,dia);
            }
        }
        
        try{
            rede.insereAtividade(user.getEmail(), nome,cod,desc,met,obr,obc,data);
        }
        catch(Exception e){
            System.out.println(e+"\n");
        }
        
    }
       
    public static void consultaCaches(){
        HashMap<String,Cache> cc = new HashMap<>(rede.getCaches());                           
        System.out.println("_______________________________________________________\n"); 
        for(String cod : cc.keySet()){
            System.out.println(cc.get(cod).toString());
            System.out.println("    ------------------------------------\n");
        }                           
        System.out.println("_______________________________________________________\n");        
        System.out.println("Prima ENTER para continuar..."); input.lerString();
    }
    
    
    public static void infoQuem(int op){
        int quem = -1;
        while(quem!=0 ){
            title();
            System.out.println("    *** Consulta de atividades ***  \n");
            System.out.println("1 - Pessoais\n2 - De um amigo\n0 - Voltar atrás");
            quem = input.lerInt();
            switch(quem){
                case 0: { break; }
                case 1:  { 
                          switch (op){
                              case 5 : { ultimasDez(user.getEmail()); break; }
                              case 6 : { consultaAtividades(user.getEmail()); break; }
                              case 8 : { statsMensais(user.getEmail(),0); break; }
                              case 9 : { statsAnuais(user.getEmail()); break; }
                              default : { System.out.println("Erro!"); break; }
                          }
                          break;
                        }
                case 2: {  atividadesAmigo(op); break; }
                default:{  System.out.println("Opção inválida"); input.lerString(); }
                }
        }
    }
    
    
    public static void atividadesAmigo(int op){
        String email;
        Utilizador ut;
        
        System.out.println("Insira email do amigo a consultar: ");
        email = input.lerString();
        
        try{
            ut = rede.getUser(email);
            if( rede.existeAmigo(email, user.getEmail() ) || user.getEmail().equals("admin@geocachinpoo.pt")  ){
                switch(op){
                    case 5: { ultimasDez(email); break; } 
                    case 6: { consultaAtividades(email); break; }
                    case 8: { statsMensais(email,0); break; }
                    case 9: { statsAnuais(email); break; }
                    default:{ System.out.println("Erro!"); break; }
                }
            }
            else {
                System.out.println("Este utilizador não é seu amigo. Não pode efetuar esta consultar!");
            }
            
        }
        catch(Excepcoes e){
           System.out.println(e); 
        }
        
        System.out.println("Prima ENTER para continuar..."); input.lerString();
    }
    
    public static void ultimasDez(String email){
        int op = -1;
        
        while(op!=0){
            title();
            ArrayList<Atividade> ats = new ArrayList<>(rede.getDezAtividades(email));        
            System.out.println("        *** 10 ATIVIDADES MAIS RECENTES ***            \n");
            System.out.println("_______________________________________________________\n");
            for(Atividade at : ats){
                System.out.println("Data: "+ at.dataString() + " | Atividade: "+at.getNome()+"\n");
            }                               
            System.out.println("_______________________________________________________\n");
        
            System.out.println("1 - Ver uma destas atividades em detalhe\n0 - Sair");
            op = input.lerInt();
            
            if(op == 1){
                int i = 0;
                System.out.println("Insira código: ");
                String cod = input.lerString();
                Atividade x;
                for(i = 0; i < ats.size(); i++){
                    x = ats.get(i);
                    if(x.getNome().equals(cod)){
                        System.out.println("--------------------------------------\n");
                        System.out.println(x.toString());
                        System.out.println("--------------------------------------\n");
                    }
                    else if (i == ats.size()-1){
                        System.out.println("Código não existe nas últimas 10 atividades");
                    }
                }   
                System.out.println("Prima ENTER para continuar..."); input.lerString();
            }
            else if(op!=0 && op !=1) { System.out.println("Opção inválida");      
                                       System.out.println("Prima ENTER para continuar..."); input.lerString(); }       
        }
        
    }
    
    
    public static void fazerReport(){
        String cod, motivo;
        System.out.println("Insira código da cache: ");
        cod = input.lerString();
        
        if( rede.cacheExiste(cod)){
            System.out.println("Qual o motivo do report? ");
            motivo = input.lerString();
            rede.insereReport(user.getEmail(), motivo, cod);            
            System.out.println("Report inserido!\n");
        }
        else {
            System.out.println("Código não existe. Por favor consulte lista de caches existentes\n");
        }
        
        
    }
    
    public static void menuReports(){        
        int op = -1;
        String email, pass;
        Utilizador u;
        
        /*
         * Se o user atual for o admin dá automaticamente acesso ao menu dos reports, caso contrário pede as credenciais de admin.
         * Isto permite a um utilizador (que seja admin) poder fazer alterações acessíveis ao admin sem a necessidade de fazer 
         * logout da sua conta e voltar a fazer login com a conta de admin.
         */
        if(user.getEmail().equals("admin@geocachingpoo.pt")) { email = user.getEmail(); }
        else {
            System.out.println("São necessárias as credenciais de Admin!\nInsira email:\n");
            email = input.lerString();
            System.out.println("Insira password: \n");
            pass = input.lerString();
                
            try{
                u = rede.getUser(email);            
            } catch(Excepcoes e){
                System.out.println(e);
                return;
            }
        
            if(u.validaLogin(pass)==false){
                System.out.println("Credenciais inválidas!");
                return;
            }
        }
        
         ArrayList<Report> rep = new ArrayList<>(rede.getReports());
        
        while(op!=0){
            title();            
            System.out.println("  ***          REPORTS            ***  \n");
            System.out.println("1 - Consultar reports\n2 - Remover report\n3 - Remover uma cache reportada\n 0- Sair");
            op = input.lerInt();            
            switch(op){
                case 0 : { break; }
                case 1 : { System.out.println("_________________________________________");
                           for(int i = 0; i<rep.size() ; i++){
                               Report r = rep.get(i);
                               System.out.println("Nº "+(i+1)+"\n"+r.toString());
                            }
                            System.out.println("_________________________________________");
                            break;
                         }
                case 2 : { System.out.println("Qual a cache sobre a qual eliminar os reports?");
                           String n = input.lerString();
                           for(Report r : rep){
                               if(r.getCodCache().equals(n)){
                                   rep.remove(r);
                               }
                            }
                           System.out.println("Todos os reports existentes sobre a cache "+n+" foram eliminados");
                           break;
                         }
                case 3 : { System.out.println("Insira o código da cache a eliminar\n");
                           String cod = input.lerString(); int flag = 0;   
                           if(!rede.cacheExiste(cod)) { System.out.println("Cache não existe!\n"); break; }
                           for(Report r : rep){
                               if(r.getCodCache().equals(cod)){
                                   rep.remove(r);
                                   flag = 1;    //remover pelo menos report desta cache. 
                               }
                           }                           
                           if(flag==0) { System.out.println("Não existem reports para essa cache"); }
                           else{ try { 
                              rede.removeCache(email,cod);  //usamos o método da classe rede em vez do método removerCache criada em baixo porque esse mesmo método
                                                            //usa o email do user atual e neste caso podemos aceder com as credenciais de admin e estar 'logados' com outra conta
                            } catch(Excepcoes e) { System.out.println(e); break; } } //em princípio não deve dar excepção nesta linha
                           
                           System.out.println("Cache removida com sucesso. Todos os reports sobre esta cache foram removidos");
                           
                           break;
                         }
                default : System.out.println("Opção inválida! Prima ENTER para continuar...."); input.lerString();
            }
            System.out.println("Coordenada Inválida! Prima ENTER para voltar a tentar....\n"); input.lerString();
        }
        
        rede.setReports(rep); //para guardar a lista de reports anterior pela nova lista atualizada
    }
    
    public static void removerCache(){
        String cod;
        System.out.println("Insira código da cache a remover: ");
        cod = input.lerString();
        try{
            rede.removeCache(user.getEmail(), cod);
        } catch(Excepcoes e) {
            System.out.println(e);
            return;
        }
        
        System.out.println("Cache removida com sucesso!");        
        System.out.println("Prima ENTER para continuar..."); input.lerString();
    }  
    
    public static void consultaAtividades(String email){
        int op = -1;
        ArrayList<Atividade> ats = new ArrayList<>(rede.getAtividades(email));
        while(op!=0){
            title();
            System.out.println("           *** TODAS AS ATIVIDADES ***                 \n");
            System.out.println("_______________________________________________________\n");
            for(Atividade at : ats){
                System.out.println("Data: "+ at.dataString() + " | Atividade: "+at.getNome()+"\n");
            }                               
            System.out.println("_______________________________________________________\n");
        
        
            System.out.println("1 - Ver uma destas caches em detalhe\n0 - Sair");
            op = input.lerInt();
            if(op == 1){
                int i = 0;
                System.out.println("Insira código: ");
                String cod = input.lerString();
                Atividade x;
                for(i = 0; i < ats.size(); i++){
                    x = ats.get(i);
                    if(x.getNome().equals(cod)){                        
                        System.out.println("--------------------------------------\n");
                        System.out.println(x.toString());                        
                        System.out.println("--------------------------------------\n");
                        System.out.println("Prima ENTER para continuar..."); input.lerString();
                    }
                    else if (i == ats.size()-1){
                        System.out.println("Código não existe nas atividades");
                    }
                }   
            }
            else if(op!=0 && op !=1) { System.out.println("Opção inválida"); }
        }                
    }
    
    public static void removeAtividade(){
        String cod;
        System.out.println("Insira o nome da atividade a remover: \n");
        cod = input.lerString();
        
        try{
            rede.removeAtividade(user.getEmail(),cod);
        } catch(Excepcoes e) {
            System.out.println(e);
            System.out.println("Prima ENTER para continuar...."); input.lerString();
            return;
        }
        
        System.out.println("Atividade removida com sucesso!");
        System.out.println("Prima ENTER para continuar...."); input.lerString();
    }
    
    public static void statsMensais(String email, int yy){
        ArrayList<ArrayList<Atividade>> stats = null; 
        int op = -1;
        int ano;
        
        if (yy==0) {
            System.out.println("Qual o ano para consultar estatísticas mensais deste utilizador?\n");
            ano = input.lerInt();
        }
        else { ano= yy; }
        
        try{
           stats = rede.getStatsMensais(email,ano);
        } catch(Excepcoes e){
            System.out.println(e);
            System.out.println("Prima ENTER para continuar..."); input.lerString();
            return;
        }
        
        System.out.println("________________________________________\n");
        for(int i = 0 ; i < stats.size() ; i++){
            System.out.println("Mês "+(i+1)+": "+stats.get(i).size()+" atividades\n");
        }
        System.out.println("________________________________________\n");
          
        while(op!= 0 && op != 1){
            System.out.println("1 - Consultar mês\n0 - Sair");
            op = input.lerInt();
            if(op==1){
                System.out.println("\nQual o mês a consultar (1..12)? (Prima 0 para sair)");
                int mes = input.lerInt(); mes--;
                if(mes < 0 && 11 > mes) { System.out.println("Mês inválido!\n"); }
                else if( stats.get(mes).size() == 0) { System.out.println("Não existem atividades neste mês\n"); }
                     else {
                         consultaStatsMes(stats.get(mes));
                        }
            }
            else if (op != 0 ) { System.out.println("Opção inválida\n"); }
            
            System.out.println("Prima ENTER para continuar..."); input.lerString();
        }
        
    }
    
    public static void consultaStatsMes(ArrayList<Atividade> listames){       
        int op = -1;
        while(op!=0){            
            title(); 
            System.out.println(" *** Atividades do mês escolhido *** \n");       
        
            for(Atividade at : listames){
                System.out.print("- Data: "+at.dataString()+" | Código/Nome da atividade: "+at.getNome()+"\n");
            }
            System.out.println("\n\n");
        
            System.out.println("1 - Consultar detalhes da atividade\n0 - Sair\n");
            op = input.lerInt();
            if(op==1){
                System.out.println("Insira código da atividade: "); String cod = input.lerString();
                System.out.println("\n *** Detalhes de uma atividade ***\n");
                for(Atividade at : listames){
                    if(at.getNome().equals(cod)){
                        System.out.println("----------------------------------\n");
                        System.out.println(at.toString());
                        System.out.println("----------------------------------\n");
                    }
                }                
                System.out.println("Prima ENTER para continuar...."); input.lerString();
            }
            else if (op != 0) { System.out.println("Opção inválida\n"); 
                                System.out.println("Prima ENTER para continuar...."); input.lerString(); }
        }
                
    }
    
    public static void statsAnuais(String email){
        HashMap<Integer,ArrayList<Atividade>> stats = null; 
        int op = -1;              
        
        try{
           stats = rede.getStatsAnuais(email);
        } catch(Excepcoes e){
            System.out.println(e);
            System.out.println("Prima ENTER para continuar...."); input.lerString();
            return;
        }
        
        System.out.println("________________________________________\n");
        for(int ano : stats.keySet()){
            System.out.println("Ano "+ ano +": "+stats.get(ano).size()+" atividades\n");
        }
        System.out.println("________________________________________\n");
          
        while(op!= 0 && op != 1){
            System.out.println("1 - Consultar ano\n0 - Sair");
            op = input.lerInt();
            if(op==1){
                System.out.println("\nQual o ano a consultar? (Prima 0 para sair)");
                int ano = input.lerInt();
                if(!stats.containsKey(ano)) { System.out.println("Ano inválido! Não existem atividades neste ano\n"); }
                     else {
                         statsMensais(email,ano);
                        }
            }
            else if (op != 0 ) { System.out.println("Opção inválida\n"); }
            
            System.out.println("Prima ENTER para continuar..."); input.lerString();
        }
        
    }
    
    /********************************************************************************************************************************************************************
     ************************************************* FUNÇÕES DE CARREGAMENTO DE FICHEIROS, GRAVAÇÃO, OUTRAS... ******************************************************** 
     ********************************************************************************************************************************************************************
     ********************************************************************************************************************************************************************/
    
     
    /**
     * FUNÇÃO AUXILIAR QUE VALIDA UMA DATA INSERIDA
     */
    private static boolean validaData(int ano, int mes, int dia, boolean datafutura){   //datafutura se for false significa que terá de ser uma data não superior à de hoje
        boolean res=true;
        
        if(ano<1900) { res = false; }
        
        if (datafutura == false){   //Se puder ser uma data futura (por exemplo para um evento ou uma cache evento)
            if(ano>Calendar.getInstance().get(Calendar.YEAR)){ res = false; }        
            if(ano==Calendar.getInstance().get(Calendar.YEAR) && mes>Calendar.getInstance().get(Calendar.MONTH)) { res = false; }
            if(ano==Calendar.getInstance().get(Calendar.YEAR) 
               && mes==Calendar.getInstance().get(Calendar.MONTH)
               && dia>Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) { res = false; }
        }
        
        if(mes<0 || mes>11) { res = false; }
        
        switch(mes){
            case 0  :
            case 2  :
            case 4  :
            case 6  :
            case 7  :
            case 9 :
            case 11 : { if (dia<0 || dia>31) { res = false; }  break; }
            case 3  :
            case 5  :
            case 8  :
            case 10 : { if (dia<0 || dia>30) { res = false; }  break; }
            case 1  : { if (dia<0 || dia>(28+(ano%400))) { res = false; } break; }
            default : return false;
        }
        
        return res;
    }
    
    
    /**
     * FUNÇÃO PARA GRAVAR FICHEIRO
     */
    public static void saveFile(){
        Scanner rd = new Scanner(System.in);
        String filename="default";
        int op=-1;
        ObjectOutputStream oOut;
        
        while(op<0 || op>2){
            System.out.println("Pretende gravar com o mesmo nome e fazer overwrite? (Nome atual: "+nomeFicheiro+")\n1 - Sim\n2 - Não\n0 - Cancelar");
            op = input.lerInt();
            switch(op){
                case 0 : { return; }
                case 1 : { filename = nomeFicheiro; break; }
                case 2 : {  System.out.println("Insira o nome do ficheiro a gravar");
                            filename = input.lerString();
                            nomeFicheiro = filename;
                            break;
                         }
                default : { System.out.println("Opção inválida! Prima ENTER para continuar...."); input.lerString(); title(); break; }
            
            }
        }
        
        try{
            oOut = new ObjectOutputStream(new FileOutputStream(filename));
            oOut.writeObject(rede);
            oOut.flush();
            oOut.close();
        }catch(IOException e){
            System.out.println("Não foi possível gravar o ficheiro!\nTente outra vez mais tarde.\n Prima enter para continuar...");
            rd.close();
            return;
        }
        System.out.println("Gravado com sucesso! Prima enter para continuar...");
        rd.nextLine();
        title();
        rd.close();
        
    }
    
    
    /**
     * FUNÇÃO PARA CARREGAR UM FICHEIRO
     */
    public static boolean loadFile(){
        ObjectInputStream oIn;
        Scanner rd = new Scanner(System.in);
        
        System.out.print("Nome do ficheiro: ");
        nomeFicheiro = rd.nextLine();
        
        try{
            oIn = new ObjectInputStream(new FileInputStream(nomeFicheiro));
            rede = (Rede) oIn.readObject();
            oIn.close();
        }catch(FileNotFoundException fl){
             System.out.println("\n*** Ficheiro nao encontrado ***\n\n Prima ENTER para voltar a tentar...\n");
             input.lerString();
             return false;
        }catch(IOException e1 ){
            e1.getMessage();
        }
        catch(ClassNotFoundException e2){
            e2.getMessage();
        }
       System.out.println("Carregado com sucesso! Prima enter para continuar...");
        rd.nextLine();
        title();
        rd.close();
        return true;
    }    
    
    /**
     * CABEÇALHO DA APLICAÇÃO
     */
    public static void title(){        
        System.out.print('\f');
        System.out.println(" _____   _____   _____   _____   _____   _____   _   _   ____   _   _   _____                 ");
        System.out.println("| ____| | ____| |  _  | | ____| |  _  | | ____| | | | | |_  _| | \\ | | | ____|                ");
        System.out.println("||  __  ||____  | | | | ||      | |_| | ||      | |_| |   ||   |  \\| | ||  __   ___  ___  ___ ");
        System.out.println("|| |_ | | ____| | | | | ||      |  _  | ||      |  _  |   ||   |     || | |_ | | _ ||   ||   |");
        System.out.println("||___|| ||____  | |_| | ||____  | | | | ||____  | | | |  _||_  | |\\  | ||___|| |  _|| | || | |");
        System.out.println("|_____| |_____| |_____| |_____| |_| |_| |_____| |_| |_| |____| |_| \\_| |_____| |_|  |___||___|\n\n");
        
    }
    
}
