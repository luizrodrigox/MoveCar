package com.movecar.controller;

import com.movecar.model.Car;
import com.movecar.model.Rental;
import com.movecar.service.CarService;
import com.movecar.service.RentalService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.*;
import com.lowagie.text.Rectangle;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;
import java.util.List;

@RestController
public class RelatorioController {

    private final CarService carService;
    private final RentalService rentalService;

    public RelatorioController(CarService carService, RentalService rentalService) {
        this.carService = carService;
        this.rentalService = rentalService;
    }

    @GetMapping("/api/relatorio/pdf")
    public void gerarRelatorioPDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf");

        Document document = new Document(PageSize.A4, 36, 36, 36, 36);
        
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font titulo = new Font(Font.HELVETICA, 24, Font.BOLD, Color.BLACK);
            Paragraph p = new Paragraph("Relatório MoveCar\n\n", titulo);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);

            Font secao = new Font(Font.HELVETICA, 16, Font.BOLD, Color.BLACK);
            Paragraph carrosTitulo = new Paragraph("Carros:\n\n", secao);
            document.add(carrosTitulo);

            List<Car> carros = carService.listarTodos();
            PdfPTable tabelaCarros = new PdfPTable(3);
            tabelaCarros.setWidthPercentage(100);
            tabelaCarros.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            for (Car c : carros) {
                Paragraph carroInfo = new Paragraph(
                    "ID: " + c.getId() + "\n\n" +
                    "Marca: " + c.getMarca() + "\n\n" +
                    "Modelo: " + c.getModelo() + "\n\n" +
                    "Ano: " + c.getAno() + "\n\n" +
                    "Diaria: R$ " + String.format("%.2f", c.getDiaria()) + "\n\n" +
                    "Disponível: " + (c.isDisponivel() ? "Sim" : "Não") + "\n\n\n"
                );
                tabelaCarros.addCell(carroInfo);
            }

            int sobra = 3 - (carros.size() % 3);
            if (sobra < 3) {
                for (int i = 0; i < sobra; i++) tabelaCarros.addCell("");
            }

            document.add(tabelaCarros);
            document.add(Chunk.NEWLINE);

            Paragraph alugueisTitulo = new Paragraph("Aluguéis:\n\n", secao);
            document.add(alugueisTitulo);

            List<Rental> alugueis = rentalService.listarTodos();
            PdfPTable tabelaAlugueis = new PdfPTable(3);
            tabelaAlugueis.setWidthPercentage(100);
            tabelaAlugueis.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            for (Rental r : alugueis) {
                Paragraph aluguelInfo = new Paragraph(
                    "ID: " + r.getId() + "\n\n" +
                    "Cliente: " + r.getNomeCliente() + "\n\n" +
                    "Carro: " + (r.getCarro() != null ? r.getCarro().getMarca() + " " + r.getCarro().getModelo() : "-") + "\n\n" +
                    "Início: " + r.getDataInicio() + "\n\n" +
                    "Fim: " + r.getDataFim() + "\n\n" +
                    "Valor: R$ " + String.format("%.2f", r.getValorTotal()) + "\n\n\n"
                );
                tabelaAlugueis.addCell(aluguelInfo);
            }

            sobra = 3 - (alugueis.size() % 3);
            if (sobra < 3) {
                for (int i = 0; i < sobra; i++) tabelaAlugueis.addCell("");
            }

            document.add(tabelaAlugueis);
            document.close();

        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        }
    }
}