/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.personal.utiles;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RyuujiMD
 */
public class FechaUtil {

    private static final Logger LOG = Logger.getLogger(FechaUtil.class.getName());

    public static int compararFechaHora(Date fecha1, Date hora1, Date fecha2, Date hora2) {
        if (fecha1.compareTo(fecha2) < 0) {
            return -1;
        } else if (fecha1.compareTo(fecha2) == 0) {
            return hora1.compareTo(hora2);
        } else {
            return 1;
        }
    }

    public static int[] milisToTime(long milis) {
        int[] hora = new int[3];

        hora[0] = milisAHoras(milis);

        milis = milis - horasAMilis(hora[0]);

        hora[1] = milisAMinutos(milis);

        milis = milis - minutosAMilis(hora[1]);

        hora[2] = milisASegundos(milis);

        return hora;
    }

    public static int ultimoDiaMes(int mes, int anio) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(anio, mes - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int horasAMilis(int horas) {
        return horas * 60 * 60 * 1000;
    }

    public static int minutosAMilis(int minutos) {
        return minutos * 60 * 1000;
    }

    public static int milisAHoras(long milis) {
        return (int) milis / (60 * 60 * 1000);
    }

    public static int milisAMinutos(long milis) {
        return (int) milis / (60 * 1000);
    }

    public static int milisASegundos(long milis) {
        return (int) milis / 1000;
    }

    public static Date soloHora(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(1970, 0, 1);
        return cal.getTime();
    }

    public static Date soloFecha(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
