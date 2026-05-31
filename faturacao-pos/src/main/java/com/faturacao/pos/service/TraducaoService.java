package com.faturacao.pos.service;

import javax.enterprise.context.ApplicationScoped;
import java.util.Locale;
import java.util.ResourceBundle;

@ApplicationScoped
public class TraducaoService {

    public String traduzir(String chave, String idioma) {
        Locale locale;
        switch (idioma == null ? "pt" : idioma) {
            case "en": locale = Locale.ENGLISH; break;
            case "fr": locale = Locale.FRENCH; break;
            case "zh": locale = Locale.CHINESE; break;
            default: locale = new Locale("pt");
        }
        return ResourceBundle.getBundle("i18n.messages", locale).getString(chave);
    }
}
