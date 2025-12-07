package com.planit_square.holiday_service.application;

import com.planit_square.holiday_service.application.inbound.HolidayCommandUseCase;
import com.planit_square.holiday_service.application.outbound.CountryRepository;
import com.planit_square.holiday_service.application.outbound.HolidayKeeper;
import com.planit_square.holiday_service.application.outbound.HolidayRepository;
import com.planit_square.holiday_service.domain.aggregate.Country;
import com.planit_square.holiday_service.domain.aggregate.Holiday;
import com.planit_square.holiday_service.domain.aggregate.command.RefreshHolidayCommand;
import com.planit_square.holiday_service.domain.aggregate.command.RegisterHolidayCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.querydsl.core.util.CollectionUtils.partition;

@Slf4j
@RequiredArgsConstructor
@Service
public class HolidayCommandService implements HolidayCommandUseCase {

    private final CountryRepository countryRepository;
    private final HolidayRepository holidayRepository;
    private final HolidayBatchHelper holidayBatchHelper;
    private final HolidayKeeper holidayKeeper;

    private static final int BATCH_SIZE = 1000;

    @Override
    public void initialize(List<RegisterHolidayCommand> commands) {

        log.info("### 전체 공휴일 수: {} 건", commands.size());

        Set<String> codes = toCountryCodes(commands);
        List<Country> countries = countryRepository.findAllByCodeIn(codes);

        Map<String, Country> countryMap = countries.stream()
                .collect(Collectors.toMap(Country::getCode, Function.identity()));

        List<List<RegisterHolidayCommand>> batches = partition(commands, BATCH_SIZE);
        log.info("### 공휴일 배치 수: {} 건", batches.size());

        int successCount = 0;
        int failCount = 0;
        int batchNumber = 0;

        for (List<RegisterHolidayCommand> batch : batches) {
            batchNumber++;
            try {
                holidayBatchHelper.saveBatch(batch, countryMap);
                successCount += batch.size();
                log.info("### 배치 [{}/{}] 저장 완료: {} 건", batchNumber, batches.size(), batch.size());
            } catch (Exception e) {
                failCount += batch.size();
                log.error("### 배치 [{}/{}] 저장 실패: {}", batchNumber, batches.size(), e.getMessage());
            }
        }

        log.info("### 공휴일 데이터 저장 완료 (성공: {} 건, 실패: {} 건)", successCount, failCount);
    }

    @Transactional
    @Override
    public void refresh(RefreshHolidayCommand command) {

        Country country = countryRepository.findByCode(command.code())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 국가입니다."));

        List<Holiday> holidays = holidayRepository.findByYearAndCode(command.year(), command.code());
        Map<String , Holiday> holidayMap = toHolidayMap(holidays);

        List<RegisterHolidayCommand> holidayCommands = holidayKeeper.findHolidays(command.year(), command.code());

        for (RegisterHolidayCommand holidayCommand : holidayCommands) {

            Holiday holiday = holidayMap.get(getKey(holidayCommand.code(), holidayCommand.date(), holidayCommand.localName()));

            if (Objects.nonNull(holiday)) {
                if (holiday.isDifferent(holidayCommand)) {
                    holiday.update(holidayCommand);
                }
            } else {
                Holiday newHoliday = Holiday.register(country, holidayCommand);
                holidayRepository.save(newHoliday);
            }
        }
    }

    @Transactional
    @Override
    public void delete(int year, String code) {
        List<Holiday> holidays = holidayRepository.findByYearAndCode(year, code);
        holidayRepository.deleteAll(holidays);
    }

    private Set<String> toCountryCodes(List<RegisterHolidayCommand> commands) {
        return commands.stream()
                .map(RegisterHolidayCommand::code)
                .collect(Collectors.toSet());
    }

    private Map<String, Holiday> toHolidayMap(List<Holiday> holidays) {
        return holidays.stream()
                .collect(Collectors.toMap(holiday -> getKey(holiday.getCountry().getCode(), holiday.getDate(), holiday.getName().getLocalName()), Function.identity()));
    }

    private static String getKey(String code, LocalDate date, String localName) {
        return code + date + localName;
    }
}
