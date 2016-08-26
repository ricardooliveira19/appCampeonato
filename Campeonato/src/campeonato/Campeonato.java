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
    static String[][] formado; // TIME X JOGADOR (0-TIME, 1-JOGADOR, 2-VALIDAÇÃO)
    static String[][] mataMata; // TIME X TIME (0-TIME, 1-JOGADOR, 2-TIME, 3-JOGADOR)
    static String[][] classificacao; // CLASSIFICAÇÃO POR PONTOS (0-TIME, 1-JOGADOR, 2-PONTOS, 3-JOGOS, 4-VITÓRIAS, 5-EMPATES, 6-DERROTAS, 7-GOLS PRÓ, 8-GOLS CONTRA, 9-SALDO DE GOLS)
    static String paraTexto; // PARA VALIDAÇÃO

    public static void main(String[] args) {
        timesEJogadores(); // RECEBE TIMES E JOGADORES
        sorteioTimes(); // DEFINE TIME DE CADA JOGADOR
        porPontosCorrido(formado[0], 4, formado[1], 1); // ATUALIZA PONTUAÇÃO

        imprimePontuacao();
    }

    static void porPontosCorrido(String time1[], int gols1, String time2[], int gols2) {
        // ATRIBUINDO INFORMAÇÕES SOMENTE PARA OS VENCEDORES||TIME1
        // FUNÇÃO ATUALIZA AS INFORMAÇÕES DE PARTIDA, JOGOS, PONTOS
        if (gols1 > gols2) { // VITÓRIA DO TIME 1
            classificacao[0][2] = Integer.toString(Integer.parseInt(classificacao[0][2]) + 3); // PONTOS
            classificacao[0][4] = Integer.toString(Integer.parseInt(classificacao[0][4]) + 1); // VITÓRIAS
        } else if (gols1 == gols2) { // EMPATE
            classificacao[0][2] = Integer.toString(Integer.parseInt(classificacao[0][2]) + 1); // PONTOS
            classificacao[0][5] = Integer.toString(Integer.parseInt(classificacao[0][5]) + 1); // EMPATES
        } else { // VITÓRIA DO TIME 2
            // DERROTA DO TIME 1
            classificacao[0][6] = Integer.toString(Integer.parseInt(classificacao[0][6]) + 1); // DERROTAS
        }
        classificacao[0][0] = time1[0]; // TIME
        classificacao[0][1] = time2[1]; // JOGADOR
        classificacao[0][3] = Integer.toString(Integer.parseInt(classificacao[0][3]) + 1); // JOGOS
        classificacao[0][7] = Integer.toString(Integer.parseInt(classificacao[0][7]) + gols1); // GOLS PRÓS
        classificacao[0][8] = Integer.toString(Integer.parseInt(classificacao[0][8]) + gols2); // GOLS CONTRAS
        classificacao[0][9] = Integer.toString(Integer.parseInt(classificacao[0][7]) - Integer.parseInt(classificacao[0][8])); // SALDO DE GOLS
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

    static void setComplementar() {
        for (int i = 0; i < n; i++) {
            formado[i][2] = "";
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

    static void auxiliar() { // DEFINE TAMNHO DOS VETORES
        System.out.println("Quantidade de times? ");
        n = leitor.nextInt();

        jogadores = new String[n];
        classificacao = new String[n][10];
        times = new String[n];
        formado = new String[n][3];
        mataMata = new String[n / 2][4];

    }

    static void sorteioTimes() { // ACIONA O SORTEIO DE TIME X JOGADOR
        for (int i = 0; i < formado.length; i++) {
            formado[i][0] = times[i];
            formado[i][1] = jogadores[aleatorio()];
            formado[i][2] = paraTexto;

        }
        setComplementar();
        imprimeTimesXJogadores();
    }

    static int aleatorio() { // SORTEI JOGADOR PRA CADA TIME SEM REPETIÇÃO
        int verifica = random.nextInt(n); // NUMERO ALEATÓRIO
        paraTexto = String.valueOf(verifica); //CONVERTE NUMERO PARA STRING

        for (int i = 0; i < n; i++) {
            if (formado[i][2].equals(paraTexto)) {
                return aleatorio();
            }
        }
        return verifica;
    }

    static void confrontMataMata() { // DEFINE ADVERSARIOS
        for (int i = 0; i < n; i++) {
            paraTexto = String.valueOf(random.nextInt(n)); //CONVERTE NUMERO PARA STRING
            for (int j = 0; j < (n / 2); j++) {
                if (mataMata[j][0] == null && !paraTexto.equals(formado[i][2])) {
                    mataMata[j][0] = formado[i][0];
                    mataMata[j][1] = formado[i][1];
                    break;
                } else if (mataMata[j][2] == null && !formado[i][2].equals(paraTexto)) {
                    mataMata[j][2] = formado[i][0];
                    mataMata[j][3] = formado[i][1];
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
        for (String[] formado1 : formado) {
            System.out.println(formado1[0] + " = " + formado1[1]);
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
