package com.craftinginterpreters.lox;

public class ParseError extends RuntimeException {
    // You can leave it empty, or add constructors if needed
    public ParseError() {
        super();
    }

    public ParseError(String message) {
        super(message);
    }
}
