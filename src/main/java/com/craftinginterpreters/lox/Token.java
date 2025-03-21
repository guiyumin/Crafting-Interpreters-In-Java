package com.craftinginterpreters.lox;

class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    @Override
    public String toString() {
        return type + " " + lexeme + " " + literal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Token token = (Token) o;
        return line == token.line &&
                type == token.type &&
                (lexeme == null ? token.lexeme == null : lexeme.equals(token.lexeme)) &&
                (literal == null ? token.literal == null : literal.equals(token.literal));
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (lexeme != null ? lexeme.hashCode() : 0);
        result = 31 * result + (literal != null ? literal.hashCode() : 0);
        result = 31 * result + line;
        return result;
    }
}