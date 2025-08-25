package bibar.com.agenda_cultural_servidor.utils;

public class CpfChecker
{
    public static String limpaCpfCnpj(String str)
    {
        return str.replaceAll("\\D", "");
    }



    public static boolean isCpfValid(String cpf, boolean sujo)
    {
        // limpeza da string
        if(sujo)
            cpf = limpaCpfCnpj(cpf);

        // formato
        if (cpf.length() != 11 || !cpf.matches("[\\d]+"))
            return false;
        
        // string -> array
        int[] digitos = new int[11];
        
        for(int ii = 0; ii != cpf.length(); ii++)
            digitos[ii] = (cpf.charAt(ii) - '0');		

        // checksum
        int ca, cb, sum;

        // digito verif 1
        sum = 0;
        for(int ii = 0, ww = 10; ii != 9; ii++, ww--)
            sum += digitos[ii] * ww;

        ca = 11 - (sum % 11);
        ca = (ca > 9) ? 0 : ca;

        // digito verif 2
        sum = 0;
        for(int ii = 0, ww = 11; ii != 10; ii++, ww--)
            sum += digitos[ii] * ww;

        cb = 11 - (sum % 11);
        cb = (cb > 9) ? 0 : cb;
        
        // check
        if(ca != digitos[9] || cb != digitos[10])
            return false;

        return true;
    }


    public static boolean isCnpjValid(String cnpj, boolean sujo)
    {
        // limpeza da string
        if(sujo)
            cnpj = limpaCpfCnpj(cnpj);

        // formato
        if (cnpj.length() != 11 || !cnpj.matches("[\\d]+"))
            return false;
        
        // string -> array
        int[] digitos = new int[14];
        
        for(int ii = 0; ii != cnpj.length(); ii++)
            digitos[ii] = cnpj.charAt(ii) - '0';

        // checksum
        int ca, cb, sum;
        
        // digito verif 1
        sum = 0;
        int[] pesos = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

        for(int ii = 0; ii != 12; ii++)
            sum += digitos[ii] * pesos[ii + 1]; // <- IMPORTANTE usar pesos com offset 1 para primero digito 

        ca = 11 - (sum % 11);
        ca = (ca > 9) ? 0 : ca;

        // digito verif 2
        sum = 0;
        
        for(int ii = 0; ii != 13; ii++)
            sum += digitos[ii] * pesos[ii + 0];

        cb = 11 - (sum % 11);
        cb = (cb > 9) ? 0 : cb;
        
        // check
        if(ca != digitos[12] || cb != digitos[13])
            return false;

        return true;
    }        

    public static boolean isCpfCnpjValid(String str, boolean sujo) { return isCpfValid(str, sujo) || isCnpjValid(str, sujo); }
    
    // overloads com parametros default
    public static boolean isCpfCnpjValid(String str) { return isCpfCnpjValid(str, false); }
    public static boolean isCpfValid(String cpf) { return isCpfValid(cpf, false); }
    public static boolean isCnpjValid(String cnpj) { return isCnpjValid(cnpj, false); }
}
