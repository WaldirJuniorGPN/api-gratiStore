package com.gratiStore.api_gratiStore.domain.service.planilha.impl;

import com.gratiStore.api_gratiStore.controller.dto.request.atendente.AtendenteRequestPlanilha;
import com.gratiStore.api_gratiStore.domain.service.atendente.AtendenteService;
import com.gratiStore.api_gratiStore.domain.service.planilha.PlanilhaService;
import com.gratiStore.api_gratiStore.domain.utils.SemanaUtils;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanilhaServiceImpl implements PlanilhaService {

    private final AtendenteService atendenteService;
    private final List<AtendenteRequestPlanilha> requests = new ArrayList<>();

    @Override
    public void lerPlanilha(MultipartFile file, Long lojaId, SemanaUtils semana) throws IOException {
        ler(file, lojaId);
        requests.forEach(request -> atendenteService.uploadSemana(request, semana));
        requests.clear();
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK && !cell.toString().isBlank()) {
                return false;
            }
        }
        return true;
    }

    private void ler(MultipartFile file, Long lojaId) throws IOException {
        @Cleanup var fileInputStream = file.getInputStream();
        var workbook = new XSSFWorkbook(fileInputStream);

        var sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();
        rowIterator.next();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if (isRowEmpty(row)) {
                break;
            }

            var nome = row.getCell(0).getStringCellValue();

            var quantidadeAtendimentos = 0;
            var cellAtendimentos = row.getCell(1);
            if (cellAtendimentos.getCellType() == CellType.STRING) {
                quantidadeAtendimentos = Integer.parseInt(cellAtendimentos.getStringCellValue());
            } else if (cellAtendimentos.getCellType() == CellType.NUMERIC) {
                quantidadeAtendimentos = (int) cellAtendimentos.getNumericCellValue();
            }

            var vendasString = row.getCell(2).toString().replace(",", ".");
            var vendas = new BigDecimal(vendasString);

            var request = new AtendenteRequestPlanilha(nome, quantidadeAtendimentos, vendas, lojaId);
            requests.add(request);
        }
    }
}
