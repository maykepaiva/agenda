package br.com.project.x.util;

import org.springframework.stereotype.Service;

@Service
public class CpfValidator {

    public boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int firstDigit = (sum % 11 < 2) ? 0 : (11 - (sum % 11));

        if (Character.getNumericValue(cpf.charAt(9)) != firstDigit) {
            return false;
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int secondDigit = (sum % 11 < 2) ? 0 : (11 - (sum % 11));

        return Character.getNumericValue(cpf.charAt(10)) == secondDigit;
    }
}
