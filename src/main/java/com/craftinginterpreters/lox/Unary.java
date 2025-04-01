package com.craftinginterpreters.lox;

public class Unary extends Expr {
    final Token operator;
    final Expr right;

    Unary(Token operator, Expr right) {
        this.operator = operator;
        this.right = right;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitUnaryExpr(this);
    }
}
