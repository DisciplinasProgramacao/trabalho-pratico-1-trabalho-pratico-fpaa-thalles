package com.knapsack;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        Mochila mochila = new Mochila(350);
        int quantidadeEquivalentes = 0;

        Instant inst1 = Instant.now();
        for (int i = 0; i < 500; i++) {
            mochila.resetItens();
            ArrayList<ItemMochila> itens = ItemMochila.gerarItens(350, 1015, false);

            System.out.println("Itens disponíveis: \n");
            for (ItemMochila it : itens) {
                System.out.println("Valor: " + it.getValor() + " Peso: " + it.getPeso() + "");
            }
            System.out.println("\n");

            Mochila solucaoGuloso = App.resolverGuloso(mochila, itens);
            Mochila solucaoFB = App.resolverForcaBruta(mochila, itens);
            
            if (solucaoGuloso.possuiMesmosItens(solucaoFB)) {
                quantidadeEquivalentes++;
            }
        }
        Instant inst2 = Instant.now();          
        System.out.println("Elapsed Time: "+ Duration.between(inst1, inst2).toString());

        System.out.println("Resultados equivalentes: " + quantidadeEquivalentes);
    }

    public static Mochila max(Mochila mochilaA, Mochila mochilaB) {
        return mochilaA.getPreenchido() > mochilaB.getPreenchido() ? mochilaA : mochilaB;
    }

    public static Mochila resolverForcaBruta(Mochila mochila, ArrayList<ItemMochila> itens) {
        System.out.println("Forca bruta: \n");

        Mochila solucaoFB = App.resolverForcaBrutaMelhorCaso(mochila, itens);

        System.out.println("=============================================");
        for (ItemMochila it : solucaoFB.getItens()) {
            System.out.println("Valor: " + it.getValor() + " Peso: " + it.getPeso() + "");
        }
        System.out.println("=============================================");

        System.out.println("Capacidade: " + solucaoFB.getCapacidade());
        System.out.println("Preenchido: " + solucaoFB.getPreenchido());

        return solucaoFB;
    }

    public static Mochila resolverForcaBrutaMelhorCaso(Mochila mochila, ArrayList<ItemMochila> itens) {
        Mochila melhorCombinacao = new Mochila(mochila.getCapacidade());
        Mochila aux = new Mochila(mochila.getCapacidade());

        for (ItemMochila it : itens) {
            aux.resetItens();
            aux.adicionarItem(it);

            Mochila melhor = App.max(melhorCombinacao, aux);

            if (melhor.equals(aux)) {
                melhorCombinacao.setItens(melhor.getItens());
            }

            for (ItemMochila item : itens) { 
                if (!item.equals(it)) {
                    aux.adicionarItem(item);
                }
            
                melhor = App.max(melhorCombinacao, aux);

                if (melhor.equals(aux)) {
                    melhorCombinacao.setItens(melhor.getItens());
                }
            }
        }

        return melhorCombinacao;
    }

    public static Mochila resolverGuloso(Mochila mochila, ArrayList<ItemMochila> itens) {
        System.out.println("Metodo guloso: \n");

        itens.sort((a, b) -> {
            double mediaA = (a.getValor() / (double) a.getPeso());
            double mediaB = (b.getValor() / (double) b.getPeso());
    
            return mediaA >= mediaB ? -1 : 1;
        });

        Mochila solucaoFB = App.resolverGuloso(mochila, itens, 0);

        System.out.println("=============================================");
        for (ItemMochila it : solucaoFB.getItens()) {
            System.out.println("Valor: " + it.getValor() + " Peso: " + it.getPeso() + "");
        }
        System.out.println("=============================================");

        System.out.println("Capacidade: " + solucaoFB.getCapacidade());
        System.out.println("Preenchido: " + solucaoFB.getPreenchido());

        return solucaoFB;
    }

    public static Mochila resolverGuloso(Mochila mochila, ArrayList<ItemMochila> itens, int index) {
        Mochila novaMochila = mochila;

        if (index >= itens.size()) {
            return novaMochila;
        }

        if (itens.get(index).getPeso() > mochila.getEspacoDisponivel()) {
            return resolverGuloso(novaMochila, itens, index + 1);
        } else {
            novaMochila = new Mochila(mochila.getCapacidade());

            novaMochila.setItens(mochila.getItens());

            novaMochila.adicionarItem(itens.get(index));

            return App.max(resolverGuloso(novaMochila, itens, index + 1), resolverGuloso(mochila, itens, index + 1));
        }
    }
}
