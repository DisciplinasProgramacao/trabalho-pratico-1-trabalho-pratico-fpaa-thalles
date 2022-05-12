package com.knapsack;

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
        System.out.println("Hello World!");
        Mochila mochila = new Mochila(15);
        ArrayList<ItemMochila> itens = ItemMochila.gerarItens(15, 5, true);

        App.resolverForcaBruta(mochila, itens);
    }

    public static Mochila max(Mochila mochilaA, Mochila mochilaB) {
        return mochilaA.getPreenchido() > mochilaB.getPreenchido() ? mochilaA : mochilaB;
    }

    public static void resolverForcaBruta(Mochila mochila, ArrayList<ItemMochila> itens) {
        System.out.println("Itens disponíveis: \n");
        for (ItemMochila it : itens) {
            System.out.println("Valor: " + it.getValor() + " Peso: " + it.getPeso() + "");
        }
        System.out.println("\n");

        Mochila solucaoFB = App.resolverForcaBruta(mochila, itens, 0);

        System.out.println("=============================================");
        for (ItemMochila it : solucaoFB.getItens()) {
            System.out.println("Valor: " + it.getValor() + " Peso: " + it.getPeso() + "");
        }
        System.out.println("=============================================");

        System.out.println("Capacidade: " + solucaoFB.getCapacidade());
        System.out.println("Preenchido: " + solucaoFB.getPreenchido());
    }

    public static Mochila resolverForcaBruta(Mochila mochila, ArrayList<ItemMochila> itens, int index) {
        Mochila novaMochila = mochila;

        if (index >= itens.size()) {
            return novaMochila;
        }

        if (itens.get(index).getPeso() > mochila.getEspacoDisponivel()) {
            return resolverForcaBruta(novaMochila, itens, index + 1);
        } else {
            novaMochila = new Mochila(mochila.getCapacidade());

            novaMochila.setItens(mochila.getItens());

            novaMochila.adicionarItem(itens.get(index));

            return App.max(resolverForcaBruta(novaMochila, itens, index + 1), resolverForcaBruta(mochila, itens, index + 1));
        }
    }
}
