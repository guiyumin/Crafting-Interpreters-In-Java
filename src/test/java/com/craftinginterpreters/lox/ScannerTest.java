package com.craftinginterpreters.lox;

import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ScannerTest {
    private Scanner scanner;

    @Before
    public void setUp() {
        // Initialize a new Scanner before each test
        scanner = new Scanner("");
    }

    @Test
    public void testSingleCharacterToken() {
        scanner = new Scanner("(");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(2, tokens.size()); // Should have LEFT_PAREN and EOF
        assertEquals(new Token(TokenType.LEFT_PAREN, "(", null, 1), tokens.get(0));
        assertEquals(new Token(TokenType.EOF, "", null, 1), tokens.get(1));
    }

    @Test
    public void testMultiCharacterToken() {
        scanner = new Scanner("==");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(2, tokens.size()); // EQUAL_EQUAL, EOF
        assertEquals(new Token(TokenType.EQUAL_EQUAL, "==", null, 1), tokens.get(0));
        assertEquals(new Token(TokenType.EOF, "", null, 1), tokens.get(1));

        // Test single "=" for comparison
        scanner = new Scanner("=");
        tokens = scanner.scanTokens();
        assertEquals(2, tokens.size()); // EQUAL, EOF
        assertEquals(new Token(TokenType.EQUAL, "=", null, 1), tokens.get(0));
        assertEquals(new Token(TokenType.EOF, "", null, 1), tokens.get(1));
    }

    @Test
    public void testKeywords() {
        scanner = new Scanner("if");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(2, tokens.size()); // IF, EOF
        assertEquals(new Token(TokenType.IF, "if", null, 1), tokens.get(0));
        assertEquals(new Token(TokenType.EOF, "", null, 1), tokens.get(1));
    }

    @Test
    public void testKeywords2() {
        scanner = new Scanner("if else");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(3, tokens.size()); // IF, ELSE, EOF
        assertEquals(new Token(TokenType.IF, "if", null, 1), tokens.get(0));
        assertEquals(new Token(TokenType.ELSE, "else", null, 1), tokens.get(1));
        assertEquals(new Token(TokenType.EOF, "", null, 1), tokens.get(2));
    }

    @Test
    public void testLiterals() {
        // Test a number
        scanner = new Scanner("123.45");
        List<Token> tokens = scanner.scanTokens();
        assertEquals(2, tokens.size()); // NUMBER, EOF
        assertEquals(new Token(TokenType.NUMBER, "123.45", 123.45, 1), tokens.get(0));
        assertEquals(new Token(TokenType.EOF, "", null, 1), tokens.get(1));

        // Test a string
        scanner = new Scanner("\"hello\"");
        tokens = scanner.scanTokens();
        assertEquals(2, tokens.size()); // STRING, EOF
        assertEquals(new Token(TokenType.STRING, "\"hello\"", "hello", 1), tokens.get(0));
        assertEquals(new Token(TokenType.EOF, "", null, 1), tokens.get(1));
    }

    @Test
    public void testIdentifiers() {
        scanner = new Scanner("variable iffy");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(3, tokens.size()); // IDENTIFIER, IDENTIFIER, EOF
        assertEquals(new Token(TokenType.IDENTIFIER, "variable", null, 1), tokens.get(0));
        assertEquals(new Token(TokenType.IDENTIFIER, "iffy", null, 1), tokens.get(1)); // Not "if"
        assertEquals(new Token(TokenType.EOF, "", null, 1), tokens.get(2));
    }

    @Test
    public void testWhitespaceAndComments() {
        scanner = new Scanner("  (  // comment\n)");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(3, tokens.size()); // LEFT_PAREN, RIGHT_PAREN, EOF
        assertEquals(new Token(TokenType.LEFT_PAREN, "(", null, 1), tokens.get(0));
        assertEquals(new Token(TokenType.RIGHT_PAREN, ")", null, 2), tokens.get(1));
        assertEquals(new Token(TokenType.EOF, "", null, 2), tokens.get(2));
    }

    @Test
    public void testInvalidCharacter() {
        scanner = new Scanner("#(");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(2, tokens.size()); // LEFT_PAREN, EOF (skips #)
        assertEquals(new Token(TokenType.LEFT_PAREN, "(", null, 1), tokens.get(0));
        assertEquals(new Token(TokenType.EOF, "", null, 1), tokens.get(1));
    }

    // @Test
    // public void testIncrementOperator() {
    // scanner = new Scanner("++");
    // List<Token> tokens = scanner.scanTokens();
    // assertEquals(2, tokens.size());
    // assertEquals(new Token(TokenType.PLUS_PLUS, "++", null, 1), tokens.get(0));
    // assertEquals(new Token(TokenType.EOF, "", null, 1), tokens.get(1));
    // }

    @Test
    public void testMultipleTokens() {
        scanner = new Scanner("var x = 10;");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(6, tokens.size()); // VAR, IDENTIFIER, EQUAL, NUMBER, SEMICOLON, EOF
        assertEquals(new Token(TokenType.VAR, "var", null, 1), tokens.get(0));
        assertEquals(new Token(TokenType.IDENTIFIER, "x", null, 1), tokens.get(1));
        assertEquals(new Token(TokenType.EQUAL, "=", null, 1), tokens.get(2));
        assertEquals(new Token(TokenType.NUMBER, "10", 10.0, 1), tokens.get(3));
        assertEquals(new Token(TokenType.SEMICOLON, ";", null, 1), tokens.get(4));
        assertEquals(new Token(TokenType.EOF, "", null, 1), tokens.get(5));
    }

    @Ignore
    @Test
    public void testAllSingleCharOperators() {
        scanner = new Scanner("(){},.-+;/*!");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(12, tokens.size()); // All single char tokens + EOF
        assertEquals(new Token(TokenType.LEFT_PAREN, "(", null, 1), tokens.get(0));
        assertEquals(new Token(TokenType.RIGHT_PAREN, ")", null, 1), tokens.get(1));
        assertEquals(new Token(TokenType.LEFT_BRACE, "{", null, 1), tokens.get(2));
        assertEquals(new Token(TokenType.RIGHT_BRACE, "}", null, 1), tokens.get(3));
        assertEquals(new Token(TokenType.COMMA, ",", null, 1), tokens.get(4));
        assertEquals(new Token(TokenType.DOT, ".", null, 1), tokens.get(5));
        assertEquals(new Token(TokenType.MINUS, "-", null, 1), tokens.get(6));
        assertEquals(new Token(TokenType.PLUS, "+", null, 1), tokens.get(7));
        assertEquals(new Token(TokenType.SEMICOLON, ";", null, 1), tokens.get(8));
        assertEquals(new Token(TokenType.SLASH, "/", null, 1), tokens.get(9));
        assertEquals(new Token(TokenType.STAR, "*", null, 1), tokens.get(10));
        assertEquals(new Token(TokenType.BANG, "!", null, 1), tokens.get(11));
    }

    @Ignore
    @Test
    public void testAllTwoCharOperators() {
        scanner = new Scanner("!= == <= >= <>");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(6, tokens.size()); // All two-char tokens + EOF
        assertEquals(new Token(TokenType.BANG_EQUAL, "!=", null, 1), tokens.get(0));
        assertEquals(new Token(TokenType.EQUAL_EQUAL, "==", null, 1), tokens.get(1));
        assertEquals(new Token(TokenType.LESS_EQUAL, "<=", null, 1), tokens.get(2));
        assertEquals(new Token(TokenType.GREATER_EQUAL, ">=", null, 1), tokens.get(3));
        assertEquals(new Token(TokenType.LESS, "<", null, 1), tokens.get(4));
        assertEquals(new Token(TokenType.GREATER, ">", null, 1), tokens.get(5));
    }

    @Ignore
    @Test
    public void testAllKeywords() {
        scanner = new Scanner("and class else false for fun if nil or print return super this true var while");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(16, tokens.size()); // 15 keywords + EOF
        assertEquals(new Token(TokenType.AND, "and", null, 1), tokens.get(0));
        assertEquals(new Token(TokenType.CLASS, "class", null, 1), tokens.get(1));
        assertEquals(new Token(TokenType.ELSE, "else", null, 1), tokens.get(2));
        assertEquals(new Token(TokenType.FALSE, "false", null, 1), tokens.get(3));
        assertEquals(new Token(TokenType.FOR, "for", null, 1), tokens.get(4));
        assertEquals(new Token(TokenType.FUN, "fun", null, 1), tokens.get(5));
        assertEquals(new Token(TokenType.IF, "if", null, 1), tokens.get(6));
        assertEquals(new Token(TokenType.NIL, "nil", null, 1), tokens.get(7));
        assertEquals(new Token(TokenType.OR, "or", null, 1), tokens.get(8));
        assertEquals(new Token(TokenType.PRINT, "print", null, 1), tokens.get(9));
        assertEquals(new Token(TokenType.RETURN, "return", null, 1), tokens.get(10));
        assertEquals(new Token(TokenType.SUPER, "super", null, 1), tokens.get(11));
        assertEquals(new Token(TokenType.THIS, "this", null, 1), tokens.get(12));
        assertEquals(new Token(TokenType.TRUE, "true", null, 1), tokens.get(13));
        assertEquals(new Token(TokenType.VAR, "var", null, 1), tokens.get(14));
        assertEquals(new Token(TokenType.WHILE, "while", null, 1), tokens.get(15));
    }

    @Test
    public void testMultiLineInput() {
        scanner = new Scanner("var x = 10;\nvar y = 20;");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(11, tokens.size());
        assertEquals(new Token(TokenType.VAR, "var", null, 1), tokens.get(0));
        assertEquals(new Token(TokenType.IDENTIFIER, "x", null, 1), tokens.get(1));
        assertEquals(new Token(TokenType.EQUAL, "=", null, 1), tokens.get(2));
        assertEquals(new Token(TokenType.NUMBER, "10", 10.0, 1), tokens.get(3));
        assertEquals(new Token(TokenType.SEMICOLON, ";", null, 1), tokens.get(4));
        assertEquals(new Token(TokenType.VAR, "var", null, 2), tokens.get(5));
        assertEquals(new Token(TokenType.IDENTIFIER, "y", null, 2), tokens.get(6));
        assertEquals(new Token(TokenType.EQUAL, "=", null, 2), tokens.get(7));
        assertEquals(new Token(TokenType.NUMBER, "20", 20.0, 2), tokens.get(8));
        assertEquals(new Token(TokenType.SEMICOLON, ";", null, 2), tokens.get(9));
        assertEquals(new Token(TokenType.EOF, "", null, 2), tokens.get(10));
    }

    @Ignore
    @Test
    public void testBlockComment() {
        scanner = new Scanner("/* This is a\nmulti-line\ncomment */ var x = 10;");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(6, tokens.size()); // Should ignore the comment
        assertEquals(new Token(TokenType.VAR, "var", null, 3), tokens.get(0));
        assertEquals(new Token(TokenType.IDENTIFIER, "x", null, 3), tokens.get(1));
        assertEquals(new Token(TokenType.EQUAL, "=", null, 3), tokens.get(2));
        assertEquals(new Token(TokenType.NUMBER, "10", 10.0, 3), tokens.get(3));
        assertEquals(new Token(TokenType.SEMICOLON, ";", null, 3), tokens.get(4));
    }

    @Test
    public void testUnterminatedString() {
        scanner = new Scanner("\"This string has no closing quote");
        List<Token> tokens = scanner.scanTokens();

        // The scanner should handle the error but still produce an EOF token
        assertEquals(1, tokens.size());
        assertEquals(new Token(TokenType.EOF, "", null, 1), tokens.get(0));
    }

    @Ignore
    @Test
    public void testComplexExpression() {
        scanner = new Scanner("if (x < 10 and y > 5) { print \"Hello\"; }");
        List<Token> tokens = scanner.scanTokens();

        assertEquals(17, tokens.size());
        assertEquals(new Token(TokenType.IF, "if", null, 1), tokens.get(0));
        assertEquals(new Token(TokenType.LEFT_PAREN, "(", null, 1), tokens.get(1));
        assertEquals(new Token(TokenType.IDENTIFIER, "x", null, 1), tokens.get(2));
        assertEquals(new Token(TokenType.LESS, "<", null, 1), tokens.get(3));
        assertEquals(new Token(TokenType.NUMBER, "10", 10.0, 1), tokens.get(4));
        assertEquals(new Token(TokenType.AND, "and", null, 1), tokens.get(5));
        assertEquals(new Token(TokenType.IDENTIFIER, "y", null, 1), tokens.get(6));
        assertEquals(new Token(TokenType.GREATER, ">", null, 1), tokens.get(7));
        assertEquals(new Token(TokenType.NUMBER, "5", 5.0, 1), tokens.get(8));
        assertEquals(new Token(TokenType.RIGHT_PAREN, ")", null, 1), tokens.get(9));
        assertEquals(new Token(TokenType.LEFT_BRACE, "{", null, 1), tokens.get(10));
        assertEquals(new Token(TokenType.PRINT, "print", null, 1), tokens.get(11));
        assertEquals(new Token(TokenType.STRING, "\"Hello\"", "Hello", 1), tokens.get(12));
        assertEquals(new Token(TokenType.SEMICOLON, ";", null, 1), tokens.get(13));
        assertEquals(new Token(TokenType.RIGHT_BRACE, "}", null, 1), tokens.get(14));
    }
}