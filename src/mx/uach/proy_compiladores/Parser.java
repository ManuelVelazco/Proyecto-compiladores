package mx.uach.proy_compiladores;

import java.util.ArrayList;

/**
 * Valida la gramatica del lenguaje
 * 
 * <ul>
 * <li> Prog --> ConjProds</li>
 * <li> ConjProds --> ConjProds | prod</li>
 * <li> Prod --> variable DEF Expr;</li>
 * <li> Expr --> Expr ALT Term | Term</li>
 * <li> Term --> Term & Factor | Factor</li>
 * <li> Factor --> {Expr} | [Expr] | Primario</li>
 * <li> Primario --> (Expr) | variable | Terminal</li>
 * </ul>
 * 
 * @author Jose Manuel Gonzalez Velazco
 * @author Yazmin Sarahi Ramirez Fuentes
 *  */

public class Parser
{
    private ArrayList<Token> tokens;
    private int next = 0;
    private Token tokenActual;
    private String output = "";

    /**
     *Resultados del analizador*/
    public String salida(){
        return output;
    }

    /**
     * Inicia el analisis.
     * @param tokens para verificar.
     */
    
    public void parse(ArrayList<Token> tokens){
        this.tokens = tokens;
        tokenActual = tokens.get(next++);
        prog();
    }

    /** Revisa que el prog contenga la gramatica del lenguaje ya especificada*/
    private void prog(){
        conj();
        if (tokenActual.getToken()!= Token.tipo_char.EOF){
            throw new Error(String.format
                ("Error de sintaxis. Se esperaba: %s",
                    tokenActual.getLexema(), (char) Token.tipo_char.EOF));
        }
    }

      /** Revisa que ConjProds cntenga la gramatica del lenguaje ya especificada*/   
    private void conj(){
        prod();
        while (tokenActual.getToken()== Token.tipo_char.FIN_SENT){
            output = String.format("%s\n", output);
            tokenActual = tokens.get(next++);
            if (tokenActual.getToken()== Token.tipo_char.EOF){
                break;
            }
            prod();
        }
    }
    /** Revisa que prod contenga la gramatica del lenguaje ya especificada*/
    private void prod(){
        if (tokenActual.getToken()== Token.tipo_char.VARIABLE){
            output = String.format("%s%s", output, tokenActual.getLexema());
        }else{
            throw new Error
            ("Error de sintaxis: "
                    + "Toda produccion debe de iniciar con una variable.");
        }
        tokenActual = tokens.get(next++);
        if (tokenActual.getToken()== Token.tipo_char.DEFINICION){
            tokenActual = tokens.get(next++);
            expr();
            output = String.format("%s%s", output, "::=");
        }else{
            throw new Error("Error de sintaxis: Se esperaba: ::=");
        }
    }
       /** Revisa que la expresion contenga la gramatica del lenguaje ya especificada*/

    private void expr(){
        term();
        while (tokenActual.getToken()== Token.tipo_char.ALTERNACION){
            tokenActual = tokens.get(next++);
            expr();
            output = String.format(
                    "%s%s", output, (char) Token.tipo_char.ALTERNACION);
        }
    }
    
    private void term(){
        factor();
        tokenActual = tokens.get(next++);
        while (tokenActual.getToken()== Token.tipo_char.CONCATENACION){
        output = String.format("%s%s", output, (char) 
                Token.tipo_char.CONCATENACION);
        tokenActual = tokens.get(next++);
        term();
        }
    }
    private void factor(){
        if (tokenActual.getToken()== Token.tipo_char.LLA_IZQ){
            tokenActual = tokens.get(next++);
            expr();
            if (tokenActual.getToken()== Token.tipo_char.LLA_DER){
                output = String.format("%s%s", output, tokenActual.getLexema());
            }else{
                throw new Error
                (String.format
                  ("Error de sintaxis. Se tiene: %s ~~~ Se esperaba: %s",
                   tokenActual.getLexema(), (char) Token.tipo_char.LLA_DER));
            }
        }
        else if (tokenActual.getToken()== Token.tipo_char.COR_IZQ)
        {tokenActual = tokens.get(next++);
            expr();
            if (tokenActual.getToken()== Token.tipo_char.COR_DER){
                output = String.format("%s%s", output, tokenActual.getLexema());
            }else{
                throw new Error
                (String.format
                  ("Error de sintaxis. Se tiene: %s ~~~ Se esperaba: %s",
                   tokenActual.getLexema(), (char) Token.tipo_char.COR_DER));
            }
        }else{
            primario();
        }
    }
    private void primario(){
        if (tokenActual.getToken()== Token.tipo_char.PAR_IZQ){
            tokenActual = tokens.get(next++);
            expr();
            if (tokenActual.getToken()== Token.tipo_char.PAR_DER){
                output = String.format("%s%s", output, tokenActual.getLexema());
            }else{throw new Error(String.format("Error de sintaxis. Se tiene: "
                        + "%s ~~~ Se esperaba: %s",tokenActual.getLexema(), 
                        (char) Token.tipo_char.PAR_DER));
            }
        }
        else if (tokenActual.getToken()== Token.tipo_char.VARIABLE
              || tokenActual.getToken()== Token.tipo_char.TERMINAL)
        {output = String.format("%s%s", output, tokenActual.getLexema());
        }else{throw new Error(String.format(
                "Error de sintaxis. Se tiene: %s ~~~ " + "Se esperaba: '(' || "
                        + "'VAR' || 'TERML'.",tokenActual.getLexema()));
        }
    }
}