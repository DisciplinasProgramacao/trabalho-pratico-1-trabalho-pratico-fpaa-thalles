package com.knapsack;

import java.util.ArrayList;
import java.util.Random;

public class ItemMochila {
  private int peso;
  private int valor;

  ItemMochila(int peso, int valor) {
    this.setPeso(peso);
    this.setValor(valor);
  }

  public static ArrayList<ItemMochila> gerarItens(int pesoMochila, int quantidade, boolean ordenado) {
    Random rand = new Random();
    int valor = (pesoMochila * 3) / quantidade;
    ArrayList<ItemMochila> itens = new ArrayList<ItemMochila>();

    for (int i = 0; i < quantidade; i++) {
      ItemMochila item = new ItemMochila(rand.nextInt(valor) + 1, rand.nextInt(valor) + 1);

      itens.add(item);      
    }

    if (ordenado) {
      itens.sort((a, b) -> {
        double mediaA = (a.getValor() / (double) a.getPeso());
        double mediaB = (b.getValor() / (double) b.getPeso());

        return mediaA >= mediaB ? -1 : 1;
      });
    }

    return itens;
  }

  public int getPeso() {
    return peso;
  }

  public void setPeso(int peso) {
    this.peso = peso;
  }

  public int getValor() {
    return valor;
  }

  public void setValor(int valor) {
    this.valor = valor;
  }  
}
