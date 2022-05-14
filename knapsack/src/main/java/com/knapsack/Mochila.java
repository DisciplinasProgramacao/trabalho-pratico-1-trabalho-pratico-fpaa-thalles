package com.knapsack;

import java.util.ArrayList;

public class Mochila {
  private ArrayList<ItemMochila> itens;
  private int preenchido = 0;
  private int capacidade;
  
  Mochila(int capacidade) {
    this.setCapacidade(capacidade);
    this.itens = new ArrayList<ItemMochila>();
  }

  public boolean possuiMesmosItens(Mochila comparacao) {
    for (ItemMochila it : this.itens) {
      if (!comparacao.getItens().contains(it)) {
        return false;
      }
    }

    return true;
  }

  public boolean adicionarItem(ItemMochila item) {
    if (item.getPeso() > this.getEspacoDisponivel()) {
      return false;
    } else {
      itens.add(item);
      this.setPreenchido(this.preenchido + item.getPeso());

      return true;
    }
  }

  public int getEspacoDisponivel() {
    return this.capacidade - this.preenchido;
  }

  public int getCapacidade() {
    return capacidade;
  }

  public void setCapacidade(int capacidade) {
    this.capacidade = capacidade;
  }

  public int getPreenchido() {
    return preenchido;
  }

  public void setPreenchido(int preenchido) {
    this.preenchido = preenchido;
  }

  public ArrayList<ItemMochila> getItens() {
    return itens;
  }

  public void resetItens() {
    this.itens = new ArrayList<ItemMochila>();
    this.setPreenchido(0);
  }

  public void setItens(ArrayList<ItemMochila> itens) {
    this.itens = itens;

    int preenchido = this.itens.stream().map(i -> i.getPeso()).reduce(0, (subtotal, element) -> subtotal + element);
    this.setPreenchido(preenchido);
  }

  
}
