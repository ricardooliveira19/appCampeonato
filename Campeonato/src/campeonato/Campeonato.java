package campeonato;

import java.util.Random;
import java.util.Scanner;

/**
 * @author ricardo.oliveira
 */
public class Campeonato {

    static Random random = new Random();
    static Scanner leitor = new Scanner(System.in);

    static int n; // QUANTIDADE DE TIMES
    static String[] times; // TIMES (0 - NOME DO TIME)
    static String[] jogadores; // JOGADORES (0 - NOME DO JOGADOR)
    static String[][] validacao; // TIME X JOGADOR (0-TIME, 1-JOGADOR, 2-VALIDAÇÃO)
    static String[][] mataMata; // TIME X TIME (0-TIME, 1-JOGADOR, 2-TIME, 3-JOGADOR)
    static String[][] classificacao; // CLASSIFICAÇÃO POR PONTOS (0-TIME, 1-JOGADOR, 2-PONTOS, 3-JOGOS, 4-VITÓRIAS, 5-EMPATES, 6-DERROTAS, 7-GOLS PRÓ, 8-GOLS CONTRA, 9-SALDO DE GOLS)
    static String paraTexto; // PARA VALIDAÇÃO

    public static void main(String[] args) {
        timesEJogadores(); // RECEBE TIMES E JOGADORES
        sorteioTimes(); // DEFINE TIME DE CADA JOGADOR
        porPontosCorrido(0, 4, 1, 4); //PASSAR POSICAO DOS TIMES E GOLS

        imprimePontuacao();
    }

    static void porPontosCorrido(int time1, int gols1, int time2, int gols2) { // FUNÇÃO ATUALIZA AS INFORMAÇÕES DE PARTIDA, JOGOS, PONTOS

        if (gols1 > gols2) { // VITÓRIA DO TIME 1
            classificacao[time1][8] = Integer.toString(Integer.parseInt(classificacao[time1][8]) + gols2); // GOLS CONTRAS
            vitoria(time1, gols1);
            classificacao[time2][8] = Integer.toString(Integer.parseInt(classificacao[time2][8]) + gols1); // GOLS CONTRAS
            derrota(time2, gols2);
        } else if (gols1 == gols2) { // EMPATE
            empate(time1, gols1);
            empate(time2, gols2);
        } else { // VITÓRIA DO TIME 2
            classificacao[time1][8] = Integer.toString(Integer.parseInt(classificacao[time1][8]) + gols2); // GOLS CONTRAS
            derrota(time1, gols1);
            classificacao[time2][8] = Integer.toString(Integer.parseInt(classificacao[time2][8]) + gols1); // GOLS CONTRAS
            vitoria(time2, gols2);
        }
    }

    static void vitoria(int posicao, int gols) { // PONTUAÇÃO DE VENCEDOR
        classificacao[posicao][2] = Integer.toString(Integer.parseInt(classificacao[posicao][2]) + 3); // PONTOS
        classificacao[posicao][3] = Integer.toString(Integer.parseInt(classificacao[posicao][3]) + 1); // JOGOS
        classificacao[posicao][4] = Integer.toString(Integer.parseInt(classificacao[posicao][4]) + 1); // VITÓRIAS        
        classificacao[posicao][7] = Integer.toString(Integer.parseInt(classificacao[posicao][7]) + gols); // GOLS PRÓS
        classificacao[posicao][9] = Integer.toString(Integer.parseInt(classificacao[posicao][7]) - Integer.parseInt(classificacao[posicao][8])); // SALDO DE GOLS
    }

    static void empate(int posicao, int gols) { // PONTUAÇÃO DE DERROTA
        classificacao[posicao][2] = Integer.toString(Integer.parseInt(classificacao[posicao][2]) + 1); // PONTOS
        classificacao[posicao][3] = Integer.toString(Integer.parseInt(classificacao[posicao][3]) + 1); // JOGOS
        classificacao[posicao][5] = Integer.toString(Integer.parseInt(classificacao[posicao][5]) + 1); // EMPATES
        classificacao[posicao][7] = Integer.toString(Integer.parseInt(classificacao[posicao][7]) + gols); // GOLS PRÓS
        classificacao[posicao][8] = Integer.toString(Integer.parseInt(classificacao[posicao][8]) + gols); // GOLS CONTRAS
    }

    static void derrota(int posicao, int gols) { // PONTUAÇÃO DE EMPATE
        classificacao[posicao][3] = Integer.toString(Integer.parseInt(classificacao[posicao][3]) + 1); // JOGOS
        classificacao[posicao][6] = Integer.toString(Integer.parseInt(classificacao[posicao][6]) + 1); // DERROTAS
        classificacao[posicao][7] = Integer.toString(Integer.parseInt(classificacao[posicao][7]) + gols); // GOLS PRÓ
        classificacao[posicao][9] = Integer.toString(Integer.parseInt(classificacao[posicao][7]) - Integer.parseInt(classificacao[posicao][8])); // SALDO DE GOLS
    }

    static void timesEJogadores() { // RECEBE TIMES E JOGADORES
        auxiliar();

        System.out.println("\n" + n + " times, liste-os:\n");
        leitor.nextLine();
        for (int i = 0; i < times.length; i++) {
            System.out.println("Nome do time " + (i + 1) + ":");
            times[i] = leitor.nextLine();
            setComplementar();
        }

        System.out.println("\n" + n + " jogadores, liste-os:\n");
        for (int i = 0; i < jogadores.length; i++) {
            System.out.println("Nome do jogador " + (i + 1) + ":");
            jogadores[i] = leitor.nextLine();
        }
        System.out.println("");
        //imprime(times);
        //imprime(jogadores);
    }

    static void setComplementar() { // SET MATRIZES E VETORES
        for (int i = 0; i < n; i++) {
            validacao[i][0] = "";
            classificacao[i][2] = "0";
            classificacao[i][3] = "0";
            classificacao[i][4] = "0";
            classificacao[i][5] = "0";
            classificacao[i][6] = "0";
            classificacao[i][7] = "0";
            classificacao[i][8] = "0";
            classificacao[i][9] = "0";
        }
    }

    static void auxiliar() { // DEFINE TAMNHO DOS VETORES/MATRIZES
        System.out.println("Quantidade de times? ");
        n = leitor.nextInt();

        jogadores = new String[n];
        classificacao = new String[n][10];
        times = new String[n];
        validacao = new String[n][1];
        mataMata = new String[n / 2][4];
    }

    static void sorteioTimes() { // DEFINE TIME DE CADA JOGADOR
        for (int i = 0; i < validacao.length; i++) {
            classificacao[i][0] = times[i];
            classificacao[i][1] = jogadores[aleatorio()];
            validacao[i][0] = paraTexto;
        }
        setComplementar();
        imprimeTimesXJogadores();
    }

    static int aleatorio() { // SORTEIA JOGADOR PRA CADA TIME SEM REPETIÇÃO
        int verifica = random.nextInt(n); // NUMERO ALEATÓRIO
        paraTexto = String.valueOf(verifica); //CONVERTE NUMERO PARA STRING

        for (int i = 0; i < n; i++) {
            if (validacao[i][0].equals(paraTexto)) {
                return aleatorio();
            }
        }
        return verifica;
    }

    static void confrontMataMata() { // DEFINE ADVERSARIOS
        for (int i = 0; i < n; i++) {
            paraTexto = String.valueOf(random.nextInt(n)); //CONVERTE NUMERO PARA STRING
            for (int j = 0; j < (n / 2); j++) {
                if (mataMata[j][0] == null && !paraTexto.equals(validacao[i][0])) {
                    mataMata[j][0] = classificacao[i][0];
                    mataMata[j][1] = classificacao[i][1];
                    break;
                } else if (mataMata[j][2] == null && !validacao[i][0].equals(paraTexto)) {
                    mataMata[j][2] = classificacao[i][0];
                    mataMata[j][3] = classificacao[i][1];
                    break;
                }
            }
        }

        System.out.println("");
        for (int i = 0; i < (n / 2); i++) {
            System.out.println(mataMata[i][0] + " (" + mataMata[i][1] + ") X "
                    + mataMata[i][2] + " (" + mataMata[i][3] + ")");
        }
    }

    static void imprimeTimesXJogadores() { // IMPRESSÃO DO TIME DE CADA JOGADOR
        for (String[] classificacao1 : classificacao) {
            System.out.println(classificacao1[0] + " = " + classificacao1[1]);
        }
    }

    static void imprime(String[] vetor) { // IMPRESSÃO DE VETOR
        System.out.println("");
        for (int i = 0; i < vetor.length; i++) {
            System.out.println((i + 1) + " - " + vetor[i]);
        }
        System.out.println("\n");
    }

    static void imprimePontuacao() { //IMPRESSÃO DA MATRIZ DE PONTUAÇÃO
        System.out.print("Time | Jogador | Pontos | Jogos | Vitórias | Empates "
                + "| Derrotas | Gols Pró | Gols Contra | Saldo de Gols\n");
        for (String[] classificacao1 : classificacao) {
            System.out.print("| ");
            for (int j = 0; j < classificacao[0].length; j++) {
                System.out.print(classificacao1[j] + " | ");
            }
            System.out.print("\n ");
        }
        System.out.println("");
    }

}
