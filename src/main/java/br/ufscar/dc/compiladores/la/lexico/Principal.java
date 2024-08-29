package br.ufscar.dc.compiladores.la.lexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

public class Principal
{
    public static void main(String[] args)
    {
        File dir = new File(args[0]);
        File[] directoryListing = dir.listFiles();

        if(directoryListing != null)
        {
            for(File entrada : directoryListing)
            {

                try(FileWriter writer = new FileWriter(
                    System.getProperty("user.home")
                    + "/Projects/Java/Compiladores/la-lexico/saida/" + entrada.getName(), false))
                {
                    tokenizeAndWrite(entrada, writer);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }

                File saida = new File(
                    System.getProperty("user.home")
                    + "/Projects/Java/Compiladores/la-lexico/saida/" + entrada.getName());

                File resposta = new File(
                    System.getProperty("user.home")
                    + "/Projects/Java/Compiladores/la-lexico/testes/saida/" + entrada.getName());

                try
                {
                    long resultado = compareFiles(saida, resposta);

                    if(resultado == -1)
                    {
                        System.out.println(entrada.getName() + ": Sucesso");
                    }
                    else
                    {
                        System.out.println(entrada.getName() + ": Falhou na linha " + resultado);
                    }                 
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            System.out.println("Not a valid directory");
        }
    }

    static void tokenizeAndWrite(File file, FileWriter writer) throws IOException
    {
        CharStream cs = CharStreams.fromFileName(file.getPath());
        Lalex lexer = new Lalex(cs);
        Token t = null;
        while ((t = lexer.nextToken()).getType() != Token.EOF)
        {
            String tokenName = Lalex.VOCABULARY.getDisplayName(t.getType());
            boolean shouldBreak = false;

            switch(tokenName)
            {
                case "ERRO_CADEIA":
                    writer.write("Linha " + t.getLine() + ": cadeia literal nao fechada\n");
                    shouldBreak = true;
                    break;

                case "ERRO_COMENTARIO":
                    writer.write("Linha " + t.getLine() + ": comentario nao fechado\n");
                    shouldBreak = true;
                    break;

                case "NAO_RECONHECIDO":
                    writer.write("Linha " + t.getLine() + ": " + t.getText() + " - simbolo nao identificado\n");
                    shouldBreak = true;
                    break;

                case "PALAVRA_CHAVE":
                    writer.write("<'" + t.getText() + "','" + t.getText() + "'>\n");
                    break;

                default:
                    writer.write("<'" + t.getText() + "'," + tokenName + ">\n");


            }

            if(shouldBreak)
            {
                break;
            }
        }
    }

    static long compareFiles(File file1, File file2) throws IOException
    {
        try(BufferedReader bf1 = new BufferedReader(new FileReader(file1));
            BufferedReader bf2 = new BufferedReader(new FileReader(file2)))
        {   
            long lineNumber = 1;
            String line1 = "", line2 = "";
            while((line1 = bf1.readLine()) != null)
            {
                line2 = bf2.readLine();
                if(line2 == null || !line1.equals(line2))
                {
                    return lineNumber;
                }
                lineNumber++;
            }
            if (bf2.readLine() == null)
            {
                return -1;
            }
            else
            {
                return lineNumber;
            }
        }
    }

}
