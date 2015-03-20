package mx.uach.proy_compiladores;
import java.io.*;

/**Revisa que el grupo de tokens sea valido en la gramatica del lenguaje
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
 */

public class Analizador{
    /**
     * /** Manda llamar el archivo y la gramatica del lenguaje
     * @param args the command line arguments
     */
    public static void main(String[] args){
        Parser parser = new Parser();
        parser.parse(Lex.getTokens(getInputFrom("Archivo.txt")));
        System.out.println(parser.salida());
    }

    /**
     * Obtiene la cadena de caracteres de un archivo.
     * Manda llamar el archivo y esto hace que lo pueda leer
     *
     * @param file Nombre del archivo.
     * @return Cadena de caracteres.
     */
    private static String getInputFrom(String file){
        String texto = "";
        FileReader Archivo = null;
        String line = "";
        try
        {
            Archivo = new FileReader(file);
            BufferedReader buff = new BufferedReader(Archivo);
            while ((line = buff.readLine()) != null){
                texto = String.format("%s%s", texto, line);
            }
        }
        catch (FileNotFoundException ex){
            throw new RuntimeException("Archivo no encontrado!");
        }catch (IOException ex){
            throw new RuntimeException("Error de entrada/saladia!");
        }
        finally
        {
            if (Archivo != null){
                try{
                    Archivo.close();
                }catch (IOException ex){
                    throw new RuntimeException("Error al cerrar el archivo!");
                }
            }
        }
        return texto;
    }
}
