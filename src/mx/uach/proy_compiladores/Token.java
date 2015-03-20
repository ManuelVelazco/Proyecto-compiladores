package mx.uach.proy_compiladores;

/**
 * Tipos de tokens
 * 
 * @author Jose Manuel Gonzalesz Velazco
 * @author Yazmin Sarahi Ramirez Fuentes
 */

public class Token{
    public class tipo_char{
        public static final int EOF = '.';
        public static final int FIN_SENT = ';';
        public static final int CONCATENACION = '&';
        public static final int ALTERNACION = '|';
        public static final int LLA_DER = '}';
        public static final int LLA_IZQ = '{';
        public static final int COR_DER = ']';
        public static final int COR_IZQ = '[';
        public static final int PAR_DER = ')';
        public static final int PAR_IZQ = '(';
        public static final int DEFINICION = 700;
        public static final int TERMINAL = 800;
        public static final int VARIABLE = 900;
    }
    private int token;
    private String lexema;

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

     // Constructor para generar los token´s
    public Token(int token, String lexema) {
        this.token = token;
        this.lexema = lexema;
    }
    
    
    @Override
    public String toString(){
        return
            String.format("Tipo: %s y Dato: %s",this.token, this.lexema);
    }
}
