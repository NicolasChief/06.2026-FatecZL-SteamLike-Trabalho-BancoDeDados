private List<JogoAdquirido> jogosA = new ArrayList<>();

    public Biblioteca() {
    }

    public Biblioteca(List<JogoAdquirido> jogosA) {
        this.jogosA = jogosA;
    }

    public List<JogoAdquirido> getJogosA() {
        return jogosA;
    }

    public void setJogosA(List<JogoAdquirido> jogosA) {
        this.jogosA = jogosA;
    }

    public void adicionarJogo(JogoAdquirido jogoAdquirido) {
        this.jogosA.add(jogoAdquirido);
    }

    public void exibirBiblioteca() {
        if (jogosA.isEmpty()) {
            System.out.println("Biblioteca vazia.");
            return;
        }

        for (JogoAdquirido jogoAdquirido : jogosA) {
            String nomeJogo = jogoAdquirido.getJogo() != null ? jogoAdquirido.getJogo().getNome() : "Indisponível";
            System.out.println("Jogo: " + nomeJogo + " | Data de aquisição: " + jogoAdquirido.getDataAquisicao());
        }
    }

}
