package com.makarios.mkcredito.dto;

public class ResumoPontoDTO {

    private String totalHorasTrabalhadas;
    private String totalHorasExtras;
    private long totalMinutosTrabalhados;
    private long totalMinutosExtras;

    public ResumoPontoDTO(long totalMinutosTrabalhados, long totalMinutosExtras) {
        this.totalMinutosTrabalhados = totalMinutosTrabalhados;
        this.totalMinutosExtras = totalMinutosExtras;
        this.totalHorasTrabalhadas = formatarMinutos(totalMinutosTrabalhados);
        this.totalHorasExtras = formatarMinutos(totalMinutosExtras);
    }

    private String formatarMinutos(long minutos) {
        long horas = minutos / 60;
        long min = minutos % 60;
        return String.format("%02d:%02d", horas, Math.abs(min));
    }

    public String getTotalHorasTrabalhadas() {
        return totalHorasTrabalhadas;
    }

    public void setTotalHorasTrabalhadas(String totalHorasTrabalhadas) {
        this.totalHorasTrabalhadas = totalHorasTrabalhadas;
    }

    public String getTotalHorasExtras() {
        return totalHorasExtras;
    }

    public void setTotalHorasExtras(String totalHorasExtras) {
        this.totalHorasExtras = totalHorasExtras;
    }

    public long getTotalMinutosTrabalhados() {
        return totalMinutosTrabalhados;
    }

    public void setTotalMinutosTrabalhados(long totalMinutosTrabalhados) {
        this.totalMinutosTrabalhados = totalMinutosTrabalhados;
    }

    public long getTotalMinutosExtras() {
        return totalMinutosExtras;
    }

    public void setTotalMinutosExtras(long totalMinutosExtras) {
        this.totalMinutosExtras = totalMinutosExtras;
    }
}
