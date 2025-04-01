package com.craftinginterpreters.lox;

public abstract class Expr {
    // Abstract method to accept a visitor.
    // The type parameter R is the return type of the visit method.
    public abstract <R> R accept(ExprVisitor<R> visitor);
}
