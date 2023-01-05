package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.information.SaleReportInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleSummaryDTO> getSummary(String minDate, String maxDate, Pageable pageable) {
		LocalDate min = convertMinDate(minDate);
		LocalDate max = convertMaxDate(maxDate);

		Page<SaleSummaryDTO> summary = repository.getSummary(min, max, pageable);
		return summary;
	}

	public List<SaleReportDTO> getReport(String minDate, String maxDate, String name) {
		LocalDate min = convertMinDate(minDate);
		LocalDate max = convertMaxDate(maxDate);

		List<SaleReportInformation> list = repository.getReport(min, max, name);
		List<SaleReportDTO> report = list.stream().map(x -> new SaleReportDTO(x)).collect(Collectors.toList());
		return report;
	}
	private LocalDate convertMinDate(String minDate) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate min = null;

		if (minDate.isEmpty()) {
			min = today.minusYears(1L);
		} else {
			min = LocalDate.parse(minDate);
		}
		return min;
	}

	private LocalDate convertMaxDate(String maxDate) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate max = null;

		if (maxDate.isEmpty()) {
			max = today;
		} else {
			max = LocalDate.parse(maxDate);
		}
		return max;
	}

}
