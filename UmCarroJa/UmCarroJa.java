    
    import java.util.ArrayList;
    import java.util.GregorianCalendar;
    import java.util.Calendar;
    import java.io.Serializable;
    import java.util.TreeMap;
    import java.util.Scanner;
    import java.io.*;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.io.PrintWriter;
    import java.util.Iterator;
    import java.util.Map;
    
    
    public class UmCarroJa implements Serializable
    {
        
        static Input input;
        static Conjunto conj;
        static Cliente cliente;
        static Proprietario prop;
        static Veiculo v;
        static Combustivel c;
        static Hibrido h;
        static Eletrico e;
        static String nomeFicheiro;
        static Pedido p;
        
        public static void main(){
            input = new Input();
            conj = new Conjunto();
            prop = new Proprietario();
            cliente = new Cliente();
            nomeFicheiro = "umCarroJa.bak";
            
            menuCarregarFicheiro();
        }
           
        public static void menuCarregarFicheiro(){
            int op;
            boolean ligado = true;
            while(ligado){            
                System.out.println("1 - Carregar ficheiro prof");
                System.out.println("2 - Carregar ficheiro guardado");
                System.out.println("3 - Criar novo (apenas admin)");
                System.out.println("0 - Sair do programa");
                
                op = input.lerInt();
                switch(op){
                    case 0 : {
                                ligado = false; break;
                             }
                    case 1 : { 
                                if(loadFile2()) { menuInicial(); } 
                                break; 
                             }
                    case 2 : {
                                if(loadFile()) { menuInicial(); }
                                break;
                             }
                    case 3 : {  if(criaNovo()) { menuInicial(); }
                                else{ System.out.println("Credenciais inválidas! Prima enter para continuar.....\n"); input.lerString();    }
                                break; 
                             }
                    default: { 
                                System.out.println("\n *** Opção inválida! *** \n\n prima ENTER para voltar a tentar....");
                                input.lerString();  
                                break;
                             }
                }
            }
            System.exit(0);
        }
        
        public static boolean criaNovo(){
            System.out.println("Insira nif: ");
            int nif = input.lerInt();
            System.out.println("Insira password de admin: ");
            String password = input.lerString();
            
            try{
                conj.validaLoginProp(nif,password);
                prop = conj.getAdmin();
                saveFile();
                return true;
            } catch (Excepcoes e) {
                System.out.println(e);
                prop = new Proprietario();
                return false;
            }
        }
        
        
         /**
         * FUNÇÃO PARA GRAVAR FICHEIRO
         */
        
        public static void saveFile(){
            Scanner rd = new Scanner(System.in);
            String filename = nomeFicheiro;
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
                    default : { System.out.println("Opção inválida! Prima ENTER para continuar...."); input.lerString(); break; }
                
                }
            }
            
            try{
                oOut = new ObjectOutputStream(new FileOutputStream(filename));
                oOut.writeObject(conj);///Mudar
                oOut.flush();
                oOut.close();
            }catch(IOException e){
                System.out.println("Não foi possível gravar o ficheiro!\nTente outra vez mais tarde.\n Prima enter para continuar...");
                rd.close();
                return;
            }
            System.out.println("Gravado com sucesso! Prima enter para continuar...");
            rd.nextLine();
            rd.close();
            
        }
        
        
         /**
         * FUNÇÃO PARA CARREGAR UM FICHEIRO
         */
        
        public static boolean loadFile2(){
            Scanner rd = new Scanner(System.in);
            
            System.out.print("Nome do ficheiro: ");
            String file = rd.nextLine();
            
            try{
                BufferedReader in = new BufferedReader(new FileReader(new File(file)));
                
                for (String x = in.readLine(); x != null; x = in.readLine())
                {
                    if(x.indexOf(':') >= 0){
                        tratarLinha(x);
                    }
                }
            }
            catch(FileNotFoundException fl){
                 System.out.println("\n*** Ficheiro nao encontrado ***\n\n Prima ENTER para voltar a tentar...\n");
                 input.lerString();
                 return false;
            }catch(IOException e1 ){
                e1.getMessage();
            }
            System.out.println("Carregado com sucesso! Prima enter para continuar...");
            rd.nextLine();
            rd.close();
            return true;
        }
        
        public static boolean loadFile(){
            ObjectInputStream oIn;
            Scanner rd = new Scanner(System.in);
            
            System.out.print("Nome do ficheiro: ");
            nomeFicheiro = rd.nextLine();
            
            try{
                oIn = new ObjectInputStream(new FileInputStream(nomeFicheiro));
                conj =  (Conjunto) oIn.readObject();
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
            rd.close();
            return true;
        }  
          
   
        
        
        static void tratarLinha(String linha){
            String[] parts = linha.split(":");
            String part1 = parts[0];
            String part2 = parts[1];
            
            switch (part1){
                case "NovoProp":{linhaProprietario(part2);break;}
                case "NovoCliente":{linhaCliente(part2);break;}
                case "NovoCarro":{linhaCarro(part2);break;}
                case "Aluguer":{break;}
                case "Classificar":{;break;}
            }
            
        }
        static void linhaProprietario(String s){
    
            String[] parts = s.split(",",4);
            String nome = parts[0];
            int nif = Integer.valueOf(parts[1]);
            String email = parts[2];
            String morada = parts[3];
            
            prop = new Proprietario(nome,nif,email,"password",morada);
            
            conj.getProprietarios().put(nif,prop);
            
        }
        
        static void linhaCliente(String s){
            String[] parts = s.split(",",6); 
            String nome = parts[0]; 
            int nif = Integer.valueOf(parts[1]); 
            String email = parts[2]; 
            String morada = parts[3]; 
            double x = Double.parseDouble(parts[4]); 
            double y = Double.parseDouble(parts[5]);
            cliente = new Cliente(nif,nome,email,"password",morada,x,y);
            
            conj.getClientes().put(nif,cliente);
            
        }
        
        static void linhaCarro(String s){
            
            String[] parts = s.split(",",10);
            String marca = parts[1];
            String matricula = parts[2];
            int nif = Integer.valueOf(parts[3]);
            int velocidade = Integer.valueOf(parts[4]);
            double preço = Double.parseDouble(parts[5]);
            double consumo = Double.parseDouble(parts[6]);
            int autonomia = Integer.parseInt(parts[7]);
            double x = Double.parseDouble(parts[8]);
            double y = Double.parseDouble(parts[9]);
            if(parts[0].contains("Gasolina")||parts[0].contains("Gasolio")){c = new Combustivel("Combustivel",marca,matricula,nif,velocidade,preço,consumo,autonomia,x,y); conj.getVeiculos().put(matricula,c);}
            else if(parts[0].contains("Hibrido")) {h = new Hibrido("Hibrido",marca,matricula,nif,velocidade,preço,consumo,autonomia,x,y); conj.getVeiculos().put(matricula,h);}
            else if(parts[0].contains("Electrico")){e = new Eletrico("Electrico",marca,matricula,nif,velocidade,preço,consumo,autonomia,x,y); conj.getVeiculos().put(matricula,e);}
            else System.out.println("tipo indisponivel");
            
        }
        
        static void linhaAluguer(String s){
            String[] parts = s.split(",",5);
            int nifcliente = Integer.parseInt(parts[0]);
            double destX = Double.parseDouble(parts[1]);
            double destY = Double.parseDouble(parts[2]);
            String tipoCom = parts[3];
            String preferencia = parts[4];
            
            Pedido ped = new Pedido(conj.getClientes().get(nifcliente).getLocX(),conj.getClientes().get(nifcliente).getLocY(),"Sem Matricula",destX,destY,new GregorianCalendar(),3,nifcliente,tipoCom,preferencia);
            
            conj.getAlugueres().put(nifcliente,ped); 
            
        }
        static void linhaClassificar(String s){
            String[] parts = s.split(",",2);
            String matricula;
            String nif;
            boolean t;
            int nota = Integer.parseInt(parts[1]);
            t = isInt(parts[0]);
            if(t){nif=parts[0]; conj.getProprietarios().get(nif).addClassificacao(nota);}
            else{matricula = parts[0]; conj.getVeiculos().get(matricula).addClassificacao(nota);}
                   
        }
        /**Adicionar classificacoes aos proprietarios e veiculos*/
       /** static void addClassificacaoProp(int nif,int nota){
            if(nif==proprietarios.getkey())proprietarios.getClassificacoes().addClassificacao(nota);
        }*/
        
        static boolean isInt(String s){
        try {
                Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
        //Procura o veiculo Mais perto
        static ArrayList<Veiculo> pesqMaisPerto(int nifCliente,Coordenadas coor,String tipoCom){
            ArrayList<Veiculo> vei =new ArrayList<Veiculo>();
            
            return vei;
        }
        //Procura o veiculo mais barato
        static ArrayList<Veiculo> pesqMaisBarato(int nifCliente,Coordenadas coor,String tipoCom){
            ArrayList<Veiculo> vei = new ArrayList<Veiculo>();
            return vei;
        }
        
        
        public static void menuInicial(){
        int op;
            System.out.println("1-proprietario");
            System.out.println("2-cliente");
            System.out.println("3-Sair");
            
            op= input.lerInt();
            switch(op){
                case 1:{menuLogin();break;}
                case 2:{menuLogin2();break;}
                case 3:{saveFile();System.exit(0);}
                default:{System.out.println("Opção inválida\n");menuInicial();}
            }
            
        }
        
        //Menu login
        //Pede os dados do utilizador e verifica se tem acesso
        public static void menuLogin(){
            
            int op = -1;     
            
            while(op!=0){ 
                System.out.println("1 - Login");
                System.out.println("2 - Criar conta");
                System.out.println("0 - Voltar atrás");
                op = input.lerInt();
                switch(op){
                   case 0 : { break;} 
                   case 1 : {  
                               if(login()){ //se o login for válido vai para o menu principal. Dados de utilizador ficam carregados na variável user
                                menuProprietario();
                               }
                               else{
                                   System.out.println("Credenciais inválidas!\nPrima ENTER para continuar..."); input.lerString();
                               }
                               break;
                            }
                   case 2 : {
                               criaContaProp(); //cria conta e vai para o menu principal. Dados de utilizador ficam carregado na variável user
                               break;
                            }
                }
            }
            menuInicial();               
        }
        
        public static void menuLogin2(){
            
            int op = -1;     
            
            while(op!=0){ 
                System.out.println("1 - Login");
                System.out.println("2 - Criar conta");
                System.out.println("0 - Voltar atrás");
                op = input.lerInt();
                switch(op){
                   case 0 : { break;} 
                   case 1 : {  
                               if(login2()){ //se o login for válido vai para o menu principal. Dados de utilizador ficam carregados na variável user
                                menuCliente();
                               }
                               else{
                                   System.out.println("Credenciais inválidas!\nPrima ENTER para continuar..."); input.lerString();
                               }
                               break;
                            }
                   case 2 : {
                               criaContaCliente(); //cria conta e vai para o menu principal. Dados de utilizador ficam carregado na variável user
                               break;
                            }
                }
            }
            menuInicial();               
        }
        public static boolean login(){
            int nif;
            String email,pass;
            System.out.println("Insira nif: ");
            nif = input.lerInt();
            System.out.println("Insira password: ");
            pass = input.lerString();
            try {
                conj.validaLoginProp(nif,pass);
                prop = conj.getProprietario(nif);
                return true;
            } catch(Excepcoes e){
                System.out.println(e);
                return false;
            }
        }
        
        public static boolean login2(){
            int nif;
            String email,pass;
            System.out.println("Insira nif: ");
            nif = input.lerInt();
            System.out.println("Insira password: ");
            pass = input.lerString();
            try {
                conj.validaLoginCliente(nif,pass);
                cliente = conj.getCliente(nif);
                return true;
            } catch(Excepcoes e){
                System.out.println(e);
                return false;
            }
        }
        //Menu criar conta
        //Criar utilizador
        public static void criaContaProp(){
            String email = "", password, nome, morada="";
            int nif=0;
            boolean validado = false;
            while(validado == false){
                try{                
                    System.out.println("Insira nif: ");
                    nif = input.lerInt();
                    validado = conj.validaNif(nif);
                } catch (Excepcoes e){
                    System.out.println(e); 
                }
            }
            System.out.println("Insira email: "); email = input.lerString();
            System.out.println("Insira password: "); password = input.lerString();
            System.out.println("Insira nome: "); nome = input.lerString();
            System.out.println("Insira morada: "); morada = input.lerString();
           
            
            prop = new Proprietario(nome,nif,email,password,morada);
            try {
                conj.insereNovoProprietario(nif,prop);
            }
            catch(Excepcoes e){
                System.out.println(e);
            }
        } 
        public static void criaContaCliente(){
            String email = "", password, nome, morada="";
            int nif=0;
            boolean validado = false;
            while(validado == false){
                try{                
                    System.out.println("Insira nif: ");
                    nif = input.lerInt();
                    validado = conj.validaNif(nif);
                } catch (Excepcoes e){
                    System.out.println(e); 
                }
            }
            System.out.println("Insira email: "); email = input.lerString();
            System.out.println("Insira password: "); password = input.lerString();
            System.out.println("Insira nome: "); nome = input.lerString();
            System.out.println("Insira morada: "); morada = input.lerString();
           
            
            cliente = new Cliente(nif,nome,email,password,morada);
            try {
                conj.insereNovoCliente(nif,cliente);
            }
            catch(Excepcoes e){
                System.out.println(e);
            }
        }
        //Menu utilizador
        //Dá acesso a todas as funções do utilizador
        public static void menuCliente(){
            int op;
            System.out.println("1-Pesquisa para aluguer");
            System.out.println("2-Histórico");
            System.out.println("3-Comentar e classificar viagem");
            System.out.println("4-Alterar Dados pessoais");
            System.out.println("5-Sair");
            
            op = input.lerInt();
            switch(op){
                case 1: menuPesquisa();break;
                case 2: menuHistorico();break;
                case 3: comentario();break;
                case 4: menuAlterar();break;
                case 5: menuInicial();break;
                default: System.out.println("Opção Inválida! Tente outra vez.");menuCliente();
            
            }
        }
        
        
        //Menu proprietario
        //Dá acesso a todas as funções do proprietário
        
        public static void menuProprietario(){
        
            int op,g;
            String mat;
            System.out.println("Escolha uma opção");
            System.out.println("1-Registrar um novo carro");
            //sinalizar um veiculo disponivel para alugar
            System.out.println("2-Sinalizar um carro como disponivel");  
            //abastecer veiculo
            System.out.println("3-Abastecer veiculo");
            //alterar o preço por km
            System.out.println("4-Alterar o preço por km");
            //aceitar/rejeitar o aluguer de um determinado veiculo
            System.out.println("5-Aceitar/Rejeitar o aluguer de um veiculo");
            //registar quanto custa a viagem
            System.out.println("6-Calcular custo de viagem");
            System.out.println("7-Sair");
            op = input.lerInt();
            
            switch(op){
                case 1: novoCarro();break;
                case 2: System.out.println("Escreva a matricula do carro  disponibilizar");mat=input.lerString();prop.trocarListaN(mat);menuProprietario();break;
                case 3: abastecerVeiculo();break;
                case 4: alterarPreco();break;
                case 5: conj.aceitarRejeitar(prop);menuProprietario();break;
                case 6: calculaPreco();menuProprietario();break;
                case 7: menuInicial();break;
                default: System.out.println("Invalido");menuPesquisa();
            
        }
    }   
        public static void novoCarro(){
            System.out.println("Escolha o tipo de veiculo que pretende criar");
            System.out.println("1-Hibrido");
            System.out.println("2-Electrico");
            System.out.println("3-Combustivel");
            System.out.println("4-Menu Proprietario");
            System.out.println("%-Sair");
            int op =input.lerInt();
            switch(op){
                case 1: criarHibrido();break;
                case 2: criarEletrico();break;
                case 3: criarCombustivel();break;
                case 4: menuProprietario();break;
                case 5: saveFile();System.exit(0);
                default:System.out.println("Opção invalida");novoCarro();
            }
        }
        
        public static void criarHibrido(){
            System.out.println("Por favor insira a marca do veiculo");
            String marca = input.lerString();
            System.out.println("Por favor insira a matricula do veiculo");
            String matricula = input.lerString();
            int nif = prop.getNif();
            System.out.println("Por favor insira a velocidade media recomendada do veiculo");
            int velocidade = input.lerInt();
            System.out.println("Por favor introduza o precoBase do veiculo");
            double precoBase = input.lerDouble();
            System.out.println("Por favor introduza o consumo do veiculo");
            double consumo = input.lerDouble();
            System.out.println("Por favor introduza a autonomia do veiculo");
            int autonomia = input.lerInt();
            System.out.println("Por favor introduza a longitude do veiculo(x)");
            double coordX = input.lerDouble();
            System.out.println("Por favor introduza a latitude do veiculo(y)");
            double coordY = input.lerDouble();
            Hibrido hi = new Hibrido("Hibrido", marca, matricula,nif, velocidade, precoBase,consumo,autonomia,coordX,coordY);
            conj.getVeiculos().put(matricula,hi);
            conj.getProprietarios().get(nif).getVeiculos().add(hi);
            menuProprietario();
        }
        
        public static void criarEletrico(){
            System.out.println("Por favor insira a marca do veiculo");
            String marca = input.lerString();
            System.out.println("Por favor insira a matricula do veiculo");
            String matricula = input.lerString();
            int nif = prop.getNif();
            System.out.println("Por favor insira a velocidade media recomendada do veiculo");
            int velocidade = input.lerInt();
            System.out.println("Por favor introduza o precoBase do veiculo");
            double precoBase = input.lerDouble();
            System.out.println("Por favor introduza o consumo do veiculo");
            double consumo = input.lerDouble();
            System.out.println("Por favor introduza a autonomia do veiculo");
            int autonomia = input.lerInt();
            System.out.println("Por favor introduza a longitude do veiculo(x)");
            double coordX = input.lerDouble();
            System.out.println("Por favor introduza a latitude do veiculo(y)");
            double coordY = input.lerDouble();
         Eletrico el = new Eletrico("Eletrico",marca, matricula,nif, velocidade,precoBase,consumo,autonomia,coordX, coordY);
            conj.getVeiculos().put(matricula,el);
            conj.getProprietarios().get(nif).getVeiculos().add(el);
            menuProprietario();
        }
        
        public static void criarCombustivel(){
            System.out.println("Por favor insira a marca do veiculo");
            String marca = input.lerString();
            System.out.println("Por favor insira a matricula do veiculo");
            String matricula = input.lerString();
            int nif = prop.getNif();
            System.out.println("Por favor insira a velocidade media recomendada do veiculo");
            int velocidade = input.lerInt();
            System.out.println("Por favor introduza o precoBase do veiculo");
            double precoBase = input.lerDouble();
            System.out.println("Por favor introduza o consumo do veiculo");
            double consumo = input.lerDouble();
            System.out.println("Por favor introduza a autonomia do veiculo");
            int autonomia = input.lerInt();
            System.out.println("Por favor introduza a longitude do veiculo(x)");
            double coordX = input.lerDouble();
            System.out.println("Por favor introduza a latitude do veiculo(y)");
            double coordY = input.lerDouble();
         Combustivel com = new Combustivel("Combustivel",marca, matricula,nif, velocidade,precoBase,consumo,autonomia,coordX, coordY);
            conj.getVeiculos().put(matricula,com);
            conj.getProprietarios().get(nif).getVeiculos().add(com);
            menuProprietario();
        }
        
        public static void calculaPreco(){
            System.out.println("Porfavor introduza a longirude do inicio do percurso");
            double x = input.lerDouble();
            System.out.println("Porfavor introduza a latitude do inicio do percurso");
            double y = input.lerDouble();
            System.out.println("Porfavor introduza a longirude do fim do percurso");
            double xD = input.lerDouble();
            System.out.println("Porfavor introduza a latitude do fim do percurso");
            double yD = input.lerDouble();
            System.out.println("Porfavor introduza o precoBase do veiculo");
            double pb = input.lerDouble();
            double calc = conj.menorPreco(x,y,xD,yD,pb);
            System.out.println("O resultado é: "+calc+"\n");
        }
        
        public static void abastecerVeiculo(){
            int g;
            String mat;
            System.out.println("Introduza a matricula do carro que quer abastecer");
            mat = input.lerString();
            System.out.println("Quantas unidades pretende abastecer(int)");
            g = input.lerInt();
            prop.abastecer(mat,g);
            menuProprietario();
        }
        
        public static void alterarPreco(){
            System.out.println("Introduza a matricula do carro cujo preço quer alterar");
            String mat = input.lerString();
            System.out.println("Introduza o novo preco");double preco = input.lerDouble();
            prop.setPreco(mat,preco);
            menuProprietario();
        }
        
        
        public static void menuPesquisa(){
            int op;
            System.out.println("Que tipo de pesquisa pretende?");
            //solicitar o aluguer de um carro mais proximo das coordenadas;
            System.out.println("1-Proximidade");  
            //solicitar o aluguer do carro mais barato
            System.out.println("2-Preço");
            //solicitar o aluguer do carro mais barato dentro de uma distancia que esta disposto a andar
            System.out.println("3-Preço e distância");
            //solicitar o aluguer de um carro especifico
            System.out.println("4-Autonomia");
            //solicitar o aluguer de um carro com uma autonomia desejada
            System.out.println("5-Pedido por carro");
            System.out.println("6-Voltar ao Menu Cliente");
            op = input.lerInt();
            
            switch(op){
                case 1: System.out.println(conj.getVeiculosProximos());fazerPedido();break;
                case 2: System.out.println(conj.getVeiculosBaratos());fazerPedido();break;
                case 3: System.out.println(conj.getVeiculosPrecoDist());fazerPedido();break;
                case 4: System.out.println(conj.getVeiculosAutonomia());fazerPedido();break;
                case 5: fazerPedido();break;
                case 6: menuCliente();break;
                default: System.out.println("Invalido");menuPesquisa();
            
            }
            
        }
    public static void fazerPedido(){
        try{ 
            int nif = cliente.getNif();
            String matricula = "";
            System.out.println("Por favor insira matricula");
            matricula = input.lerString();
            if(conj.validarMatricula(matricula)){
                    System.out.println("Por favor insira a longitude do destino pretendido (x)");
                        int xD = input.lerInt();
                            System.out.println("Por favor insira a latitude do destino pretendido (y)");
                                int yD = input.lerInt();
                                conj.novoPedido(matricula,nif,xD,yD);
            }
        
        }
        catch(Exception e){
            System.out.println("Matricula não existe");
        }
        menuCliente();   
    }
    
    public static void comentario(){
        String mat;
        int nif;
        int val;
        System.out.println("1-Classificar um veiculo");
        System.out.println("2-Classificar um proprietário");
        System.out.println("3-Menu Cliente");
        int op = input.lerInt();
        switch(op){
            case 1: {
                System.out.println("Insira a matricula do veiculo a classificar.");
                mat = input.lerString();
                System.out.println("Pontue o Veiculo de 0 a 100.");
                val = input.lerInt();
                conj.getVeiculos().get(mat).addClassificacao(val);
                break;
            }
            case 2: {
                System.out.println("Insira o nif do proprietario a classificar.");
                nif = input.lerInt();
                System.out.println("Pontue o proprietario de 0 a 100.");
                val = input.lerInt();
                conj.getProprietarios().get(nif).addClassificacao(val);
                break;
            }
            case 3: {menuCliente();break;}
            default:{System.out.println("Invalido. Tente novamente");comentario();}
        }
    }
    
    public static void menuAlterar(){
        String x;
        int op = -1;
        System.out.println("1-nome");
        System.out.println("2-email");
        System.out.println("3-password");
        System.out.println("4-morada");
        System.out.println("5-Voltar a traz");
        op = input.lerInt();
        
        switch(op){
            case 1: {System.out.println("Insira novo nome"); x=input.lerString();prop.setNome(x);break;}
            case 2: {System.out.println("Insira novo email"); x = input.lerString();prop.setEmail(x);break;}
            case 3: {System.out.println("Insira nova password");x = input.lerString();prop.setPassword(x);break;}
            case 4: {System.out.println("Insira nova morada");x=input.lerString();prop.setMorada(x);break;}
            case 5: {menuProprietario();break;}
            default: {System.out.println("Opção inválida");menuAlterar();}
        }
        menuProprietario();
    }
    
    public static void menuAlterar2(){
        String x;
        int op;
        System.out.println("1-nome");
        System.out.println("2-email");
        System.out.println("3-password");
        System.out.println("4-morada");
        System.out.println("5-Voltar a traz");
        op = input.lerInt();
        
        switch(op){
            case 1: {System.out.println("Insira novo nome"); x=input.lerString();cliente.setNome(x);break;}
            case 2: {System.out.println("Insira novo email"); x = input.lerString();cliente.setEmail(x);break;}
            case 3: {System.out.println("Insira nova password");x = input.lerString();cliente.setPassword(x);break;}
            case 4: {System.out.println("Insira nova morada");x=input.lerString();cliente.setMorada(x);break;}
            case 5: {menuCliente();break;}
            default: {System.out.println("Opção inválida");menuAlterar2();}
        }
        menuCliente();
    }
    
    public static void menuHistorico(){
        //Lista de viagens feita pelo Utilizador
        System.out.println("1-Lista de viagens feitas pelo cliente");
        //Lista de carros alugados
        System.out.println("2-Lista de carros que já alugou");
        System.out.println("3-Menu Cliente");
        System.out.println("4-Sair");
        int op = input.lerInt();
        switch(op){
            case 1:{cliente.toListaAlugueres();menuHistorico();break;}
            case 2:{conj.toListaCarros(conj.matParaVei(cliente.listaCarros()));break;}
            case 3:{menuCliente(); break;}
            default: System.out.println("Opção inválida");
        }
        menuCliente();
    }
    
}
