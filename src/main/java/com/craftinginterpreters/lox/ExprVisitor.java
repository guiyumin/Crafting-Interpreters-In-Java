package com.craftinginterpreters.lox;

// Import the concrete expression types that this visitor can visit.

// Defines the interface for visitors for the Expr hierarchy.
// Using ExprVisitor name to be slightly more specific than just "Visitor".
public interface ExprVisitor<R> {
    R visitBinaryExpr(Binary expr);

    R visitGroupingExpr(Grouping expr);

    R visitLiteralExpr(Literal expr);

    R visitUnaryExpr(Unary expr);
    // Add more visit methods here corresponding to new Expr subclasses
}