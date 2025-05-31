package ru.myapp.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        componentModel = MappingConstants.ComponentModel.SPRING, // Использование Spring DI
        unmappedTargetPolicy = ReportingPolicy.IGNORE, // Игнорировать несопоставленные поля цели
        unmappedSourcePolicy = ReportingPolicy.WARN, // Предупреждать о несопоставленных полях источника
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, // Игнорировать null-значения
        injectionStrategy = InjectionStrategy.CONSTRUCTOR // Стратегия инъекции зависимостей
)
public interface MapstructConfig {
}
