package com.jhola.product.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhola.product.dto.Categories;
import com.jhola.product.dto.ProductDTO;
import com.jhola.product.model.Product;
import com.jhola.product.repository.ProductRepository;

import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;
import technology.tabula.extractors.BasicExtractionAlgorithm;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProductRepository repository;

	@PostMapping("")
	public ResponseEntity<Long> createProduct(@RequestBody ProductDTO productDTO) {

		Product product = modelMapper.map(productDTO, Product.class);
		Product savedProduct = repository.save(product);
		return new ResponseEntity<>(savedProduct.getProductId(), HttpStatus.CREATED);

	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {

		Optional<Product> optionalProduct = repository.findById(productId);
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
			return new ResponseEntity<>(productDTO, HttpStatus.OK);
		}

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<List<ProductDTO>> getAllProducts() {

		Iterable<Product> iterableProduct = repository.findAll();
		ProductDTO productDTO;
		List<ProductDTO> listOfProductDTO = new ArrayList<>();

		for (Product product : iterableProduct) {
			productDTO = modelMapper.map(product, ProductDTO.class);
			listOfProductDTO.add(productDTO);
		}

		return new ResponseEntity<>(listOfProductDTO, HttpStatus.OK);
	}

	@GetMapping("/filter/{category}")
	public ResponseEntity<List<ProductDTO>> getProductByCategory(@PathVariable String category) {

		List<Product> listOfProducts = repository.findProductByCategory(Categories.valueOf(category));

		List<ProductDTO> listOfProductDTO = new ArrayList<>();

		for (Product product : listOfProducts) {
			ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
			listOfProductDTO.add(productDTO);
		}

		return new ResponseEntity<>(listOfProductDTO, HttpStatus.OK);

	}

	@GetMapping("/pdf")
	public ResponseEntity<String> parsePdf() throws InvalidPasswordException, IOException {

		File file = ResourceUtils.getFile("classpath:6111259747-3YR.pdf");
		try (PDDocument document = PDDocument.load(file)) {

			document.getClass();

			if (!document.isEncrypted()) {

				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				PDFTextStripper tStripper = new PDFTextStripper();

				String pdfFileInText = tStripper.getText(document);

				String lines[] = pdfFileInText.split("\\r?\\n");
				for (String line : lines) {
					System.out.println(line);
				}

			}
			approach4BasicExtractionAlgorithm(document);
			approach4SpreadsheetExtractionAlgorithm(document);

		}

		return new ResponseEntity<>("PDF Parsed", HttpStatus.OK);

	}

	private void approach4BasicExtractionAlgorithm(PDDocument document) {
		ObjectExtractor objectExtractor = new ObjectExtractor(document);
		BasicExtractionAlgorithm bea = new BasicExtractionAlgorithm();

		Page page1 = objectExtractor.extract(1);
		List<Table> listOfTables = bea.extract(page1);

		for (Table table : listOfTables) {
			List<List<RectangularTextContainer>> rows = table.getRows();
			// loop 2
			int rowCount = 0;
			for (List<RectangularTextContainer> list1 : rows) {
				if (rowCount == 10) {
					break;
				}

				for (RectangularTextContainer value : list1) {
					if (!value.isEmpty()) {
						String text = value.getText();
						text.trim();
						log.info(text);

						if (text.contains("Quote #:") && text.substring(8) != null) {
//							topDetail.setQuoteNumber(text.substring(8).trim());
						} else if (text.contains("Quote Issue Date:") && text.substring(17) != null) {
//							topDetail.setQuoteIssueDate(text.substring(17).trim());
						} else if (text.contains("Quote Expiration:") && text.substring(16) != null) {
//							topDetail.setQuoteExpiration(text.substring(16).trim());
						} else if (text.contains("Prepared By:") && text.substring(12) != null) {
//							topDetail.setPreparedBy(text.substring(12).trim());
						} else if (text.contains("Email:") && text.substring(6) != null) {
//							topDetail.setEmail(text.substring(6).trim());
						} else if (text.contains("Billing Frequency:") && text.substring(17) != null) {
//							topDetail.setBilingFrequency(text.substring(17).trim());
						} else if (text.contains("Payment Term:") && text.substring(12) != null) {
//							topDetail.setPaymentTerm(text.substring(12).trim());
						} else if (text.contains("Quote Total:") && text.substring(11) != null) {
//							topDetail.setQuoteTotal(removeStartSymbol(text.substring(11).trim()));
						}

					}

				}
			}

		}
	}

	private void approach4SpreadsheetExtractionAlgorithm(PDDocument document) {
		ObjectExtractor objectExtractor = new ObjectExtractor(document);

		SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm(); // Tabula algo.

		// PageIterator extract = objectExtractor.extract();
		Page page = objectExtractor.extract(1);
		if (page != null) {
			List<Table> listOfTables = sea.extract(page);
			log.info("Page Number: {}", page.getPageNumber());

			int tableNo = 0;
			for (Table table2 : listOfTables) {
				log.info("table #: {}", ++tableNo);
				log.info("Table Found at Coordinates: ( {} , {} )", table2.getX(), table2.getY());
				log.info("Row Count: {}", table2.getRowCount());

				if (table2.getRowCount() == 0) {
					continue;
				}

				List<List<RectangularTextContainer>> rows = table2.getRows();
				// loop 2
				int rowCount = 0;
				
				for(List<RectangularTextContainer> list: rows) {
					list.forEach((lis) -> {
						log.info(lis.getText());
					});
				}
			}
		}

	}

}
