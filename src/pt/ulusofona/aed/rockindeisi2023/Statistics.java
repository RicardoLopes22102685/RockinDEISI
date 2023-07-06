package pt.ulusofona.aed.rockindeisi2023;

public class Statistics {
    String nomeFicheiro;
    int linhasOk, linhasNok = 0;
    int primeiraLinhaNok = -1;

    public Statistics(String nomeFicheiro, int linhasOk, int linhasNok, int primeiraLinhaNok) {
        this.nomeFicheiro = nomeFicheiro;
        this.linhasOk = linhasOk;
        this.linhasNok = linhasNok;
        this.primeiraLinhaNok = primeiraLinhaNok;
    }


    @Override
    public String toString() {
        return nomeFicheiro + " | " + linhasOk + " | "+ linhasNok + " | " + primeiraLinhaNok;
    }
}
