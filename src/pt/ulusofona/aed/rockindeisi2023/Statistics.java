package pt.ulusofona.aed.rockindeisi2023;

public class Statistics {
    String nome_ficheiro;
    int linhas_ok,linhas_nok = 0;
    int primeira_linha_nok = -1;

    public Statistics(String nome_ficheiro, int linhas_ok, int linhas_nok, int primeira_linha_nok) {
        this.nome_ficheiro = nome_ficheiro;
        this.linhas_ok = linhas_ok;
        this.linhas_nok = linhas_nok;
        this.primeira_linha_nok = primeira_linha_nok;
    }


    @Override
    public String toString() {
        return nome_ficheiro+ " | " + linhas_ok + " | "+ linhas_nok + " | " + primeira_linha_nok;
    }
}
