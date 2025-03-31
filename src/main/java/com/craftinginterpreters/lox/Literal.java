package com.craftinginterpreters.lox;

public class Literal extends Expr {
    final Object value;

    Literal(Object value) {
        this.value = value;
    }

}
