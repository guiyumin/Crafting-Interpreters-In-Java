package com.craftinginterpreters.lox;

public class Unary extends Expr {
    final Token operator;
    final Expr right;

    Unary(Token operator, Expr right) {
        this.operator = operator;
        this.right = right;
    }
}
