package com.gestao.livros.gestaolivros.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static LocalDate localDateAjustes(String date) {

        if(date== null || date.isEmpty()){
            return null;
        }

        if (date.length() == 4) {
            // Formato "yyyy" (ex: "2016")
            return LocalDate.parse(date + "-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")); //Colocando o mês e o dia padrao como 01 - 01
        }
        if (date.length() == 7) {
            // Formato "yyyy-MM" (ex: "2016-10")
            return LocalDate.parse(date + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")); //Colocando o dia como 01
        }

        return LocalDate.parse(date);
    }
}
