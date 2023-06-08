import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AppTrabalho2 {
    private int[][] coordenadas;
    private ArrayList<Nodo> portos;

    public AppTrabalho2() {
        portos = new ArrayList<>();
    }

    public void aplicacao(String arquivoLido){
        portos.clear();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(arquivoLido));
            String line = bufferedReader.readLine();
            Scanner scanner = new Scanner(line).useDelimiter(" ");
            int x = Integer.parseInt(scanner.next());
            int y = Integer.parseInt(scanner.next());
            coordenadas = new int[x][y];
            int valor = 9;
            for(int i=0;i<x;i++){
                line = bufferedReader.readLine();
                scanner = new Scanner(line).useDelimiter("");
                for (int j=0;j<y;j++){
                    var aux=scanner.next();
                    if(!Objects.equals(aux, "*")){
                        if(!Objects.equals(aux, ".")){
                            coordenadas[i][j] = Integer.parseInt(aux);
                            portos.add(new Nodo(Integer.parseInt(aux),i,j));
                        }
                        else{
                            valor++;
                            coordenadas[i][j] = valor;
                        }
                    }
                }
            }

            portos.sort(Comparator.comparingInt(Nodo::getValor));
            Queue<Nodo> filaPrioridadePortos = new LinkedList<>(portos);

            double tempoEmSegundos = 0.0;
            long start = System.currentTimeMillis();

            DijkstraFenicios dijkstraFenicios = new DijkstraFenicios();
            Nodo porto1 = filaPrioridadePortos.remove();
            Nodo porto2 = filaPrioridadePortos.remove();
            filaPrioridadePortos.add(porto1);
            int totalCombustivelGasto = 0;
            while (true){
                int distancia = dijkstraFenicios.caminhamentoMinimoParaFenicios(coordenadas,porto1,porto2);
                while (distancia==0){
                    if(!filaPrioridadePortos.isEmpty()){
                        porto2 = filaPrioridadePortos.remove();
                        distancia = dijkstraFenicios.caminhamentoMinimoParaFenicios(coordenadas,porto1,porto2);
                    }
                    else break;
                }
                totalCombustivelGasto += distancia;
                if(filaPrioridadePortos.isEmpty()){
                    break;
                }
                porto1 = porto2;
                porto2 = filaPrioridadePortos.remove();
            }

            double elapsed = System.currentTimeMillis() - start;
            tempoEmSegundos = elapsed / 1000.0;
            System.out.println("Arquivo Lido: " + arquivoLido);

            System.out.println("Tempo de execução: " + tempoEmSegundos + " segundos");

            System.out.println("o total de combustivel gasto foi: " + totalCombustivelGasto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
