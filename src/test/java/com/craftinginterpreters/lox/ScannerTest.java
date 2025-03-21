package com.craftinginterpreters.lox;

import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
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
}