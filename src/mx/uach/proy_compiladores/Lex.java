package mx.uach.proy_compiladores;

import java.util.ArrayList;

/**
 *@author Manuel Gonzalez velazco
 * @author Yazmin Srahi Ramirez Fuentes
 */
public class Lex{
    /**
     * Transforma una cadena de texto en tokens.
     *
     * @param input Cadena de texto.
     * @return Lista de tokens.
     */
    public static ArrayList<Token> getTokens(String input){
       
        input = input.replaceAll("\\s", "");
       
        ArrayList<Token> tokens = new ArrayList<>();
       
        for (int i = 0; i < input.length(); i++)
        {
            // Si el caracter es diferente a cualquiera de estos
            if( input.charAt(i) != Token.tipo_char.EOF
             && input.charAt(i) != Token.tipo_char.FIN_SENT
             && input.charAt(i) != Token.tipo_char.CONCATENACION
             && input.charAt(i) != Token.tipo_char.ALTERNACION
             && input.charAt(i) != Token.tipo_char.LLA_DER
             && input.charAt(i) != Token.tipo_char.LLA_IZQ
             && input.charAt(i) != Token.tipo_char.COR_DER
             && input.charAt(i) != Token.tipo_char.COR_IZQ
             && input.charAt(i) != Token.tipo_char.PAR_DER
             && input.charAt(i) != Token.tipo_char.PAR_IZQ)
            {
                
                if (input.charAt(i) == ':'
                 && (i + 3) <= input.length()
                 && input.charAt(i + 1) == ':'
                 && input.charAt(i + 2) == '=')
                {
                    tokens.add(new Token(Token.tipo_char.DEFINICION, "::="));
                    i += 2;
                }else if
                /** como la terminal '+' y '-' no estan dentro de los operandos
                 * se ponen aqui entre '' para que sea valido */
                 (input.charAt(i) == '\''
                      && input.indexOf('\'', i + 1) != -1)
                {
                    int ni = input.indexOf('\'', i + 1);
                    tokens.add
                    (   new Token
                        (   Token.tipo_char.TERMINAL,
                            input.substring
                            ( i,
                                ni + 1)
                        )
                    );
                    i = ni;
                }
                /** Las variables tienen que ir entre '<>'*/ 
                     else if (input.charAt(i)=='<'&& input.indexOf('>', i + 1) != -1
                      && (i + 1) < input.length()&& 
                        Character.isLetter(input.charAt(i + 1)) == true)
                {
                    
                    int ni = input.indexOf('>', i + 1);

                    //* simbolos y variables*/
                    String sim = input.substring(i, ni + 1);
                    // Valida que sean letras y numeros.
                    for (int j = 1; j < sim.length() - 1; j++)
                    {
                        // Si no son ni numeros ni letras
                        if (Character.isDigit(sim.charAt(j)) != true
                         && Character.isLetter(sim.charAt(j)) != true)
                        {
                            //lanzamos un error
                            throw new Error
                            (String.format("Error lexico en columna: %d", i));
                        }
                    }
                    /** se agrega el siguiente token*/
                    tokens.add
                    (new Token(Token.tipo_char.VARIABLE,sim));
                    i = ni;
                }else{
                 
                  /**Si no es ninguno de esos, entonces es un error léxico                   
                    */
                
                    throw new Error
                    (String.format("Error lexico en columna: %d", i));
                }
            }else{
                tokens.add
                (new Token(input.charAt(i), String.valueOf(input.charAt(i))));
            }
        } 
        return tokens;
    }
}
