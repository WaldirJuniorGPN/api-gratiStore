package com.gratiStore.api_gratiStore.domain.service.planilha.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequestPlanilha;
import com.gratiStore.api_gratiStore.domain.service.atendente.AtendenteService;
import com.gratiStore.api_gratiStore.domain.service.planilha.PlanilhaService;
import com.gratiStore.api_gratiStore.domain.utils.SemanaUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.core.io.doubleparser.JavaBigDecimalParser.parseBigDecimal;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL;

@Service
@RequiredArgsConstructor
public class LeitorDePlanilhaGenericoImpl implements PlanilhaService {

    private static final int COLUNA_NOME = 1;
    private static final int COLUNA_ATENDIMENTOS = 3;
    private static final int COLUNA_VENDAS = 9;

    private final AtendenteService atendenteService;

    @Override
    public void lerPlanilha(MultipartFile file, Long lojaId, SemanaUtils semana) throws IOException {

        var vendas = lerVendas(file, lojaId);
        vendas.forEach(v -> atendenteService.uploadSemana(v, semana));
    }

    private List<AtendenteRequestPlanilha> lerVendas(MultipartFile file, Long lojaId) throws IOException {
        try (var in = file.getInputStream(); var workbook = WorkbookFactory.create(in)) {

            var sheet = workbook.getSheetAt(0);
            var inicioDosDados = 2;

            return lerLinhas(sheet, inicioDosDados, lojaId);
        }
    }

    private List<AtendenteRequestPlanilha> lerLinhas(Sheet sheet, int primeiraLinhaDados, Long lojaId) {
        var resultado = new ArrayList<AtendenteRequestPlanilha>();
        var formatter = new DataFormatter();
        var evaluator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();

        var ultimaLinha = sheet.getLastRowNum();
        for (int rowNum = primeiraLinhaDados; rowNum <= ultimaLinha; rowNum++) {
            var row = sheet.getRow(rowNum);

            if (isLinhaVazia(row)) continue;

            var dto = mapearLinha(row, lojaId, formatter, evaluator);
            if (dto == null) continue;

            resultado.add(dto);
        }
        return resultado;
    }

    private AtendenteRequestPlanilha mapearLinha(Row row, Long lojaId, DataFormatter fmt, FormulaEvaluator eval) {
        var nome = getTexto(row, COLUNA_NOME, fmt, eval);
        if (nome == null || nome.isBlank()) return null;

        var atendimentos = getInteiro(row, fmt, eval);
        if (atendimentos < 0) return null;

        var vendas = getBigDecimalPtBr(row, fmt, eval);
        if (vendas == null) return null;

        return new AtendenteRequestPlanilha(nome.trim(), atendimentos, vendas, lojaId);
    }

    private BigDecimal getBigDecimalPtBr(Row row, DataFormatter fmt, FormulaEvaluator eval) {
        var txt = getTexto(row, COLUNA_VENDAS, fmt, eval);
        if (txt == null) return null;

        txt = txt.trim();

        var soNumerosVirgulaPonto = txt.replaceAll("[^0-9,.]", "");

        if (soNumerosVirgulaPonto.contains(",") && soNumerosVirgulaPonto.contains(".")) {
            var semMilhar = soNumerosVirgulaPonto.replaceAll("\\.", "");
            var pontoDecimal = semMilhar.replace(",", ".");
            return parseBigDecimal(pontoDecimal);
        }

        if (soNumerosVirgulaPonto.contains(",") && !soNumerosVirgulaPonto.contains(".")) {
            var pontoDecimal = soNumerosVirgulaPonto.replace(",", ".");
            return parseBigDecimal(pontoDecimal);
        }

        return parseBigDecimal(soNumerosVirgulaPonto);
    }

    private String getTexto(Row row, int col, DataFormatter fmt, FormulaEvaluator eval) {
        var cell = row.getCell(col, RETURN_BLANK_AS_NULL);
        if (cell == null) return null;
        return fmt.formatCellValue(cell, eval);
    }

    private int getInteiro(Row row, DataFormatter fmt, FormulaEvaluator eval) {
        var txt = getTexto(row, COLUNA_ATENDIMENTOS, fmt, eval);
        if (txt == null) return 0;

        txt = txt.trim().replaceAll("[^0-9\\-]", "");
        if (txt.isBlank()) return 0;

        try {
            return Integer.parseInt(txt);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private boolean isLinhaVazia(Row row) {
        if (row == null) return true;
        var primeiro = row.getFirstCellNum();
        var ultimo = row.getLastCellNum();
        if (primeiro < 0 || ultimo < 0) return true;

        for (int i = primeiro; i < ultimo; i++) {
            var cell = row.getCell(i, RETURN_BLANK_AS_NULL);
            if (cell == null) continue;
            if (cell.getCellType() != BLANK) return false;
        }
        return true;
    }
}
