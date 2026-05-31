package com.smartsoft.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class FacturaUtil {

    private static final DateTimeFormatter YEAR_FORMAT = DateTimeFormatter.ofPattern("yyyy");

    private FacturaUtil() {}

    /**
     * Formats the invoice number using the configured pattern.
     * Pattern: NNNNNNNN/YYYY -> e.g., 00000001/2024
     */
    public static String formatarNumeroFactura(int numero, String formato) {
        String ano = LocalDate.now().format(YEAR_FORMAT);
        if (formato != null && formato.contains("NNNNNNNN")) {
            return String.format("%08d", numero) + "/" + ano;
        }
        return String.format("%08d", numero) + "/" + ano;
    }

    /**
     * Calculates VAT amount from a rate string like "17%".
     */
    public static BigDecimal calcularIva(BigDecimal valor, String taxaIva) {
        if (taxaIva == null || taxaIva.equals("0%")) return BigDecimal.ZERO;
        double rate = Double.parseDouble(taxaIva.replace("%", "")) / 100.0;
        return valor.multiply(BigDecimal.valueOf(rate)).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Generates a SHA-256 hash for invoice integrity check.
     */
    public static String gerarHashIntegridade(String conteudo) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(conteudo.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo SHA-256 não disponível", e);
        }
    }
}
