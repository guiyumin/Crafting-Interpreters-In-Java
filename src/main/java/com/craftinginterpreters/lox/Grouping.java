package com.craftinginterpreters.lox;

public class Grouping extends Expr {
    final Expr expression;

    Grouping(Expr expression) {
        this.expression = expression;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
        return visitor.visitGroupingExpr(this);
    }
}
