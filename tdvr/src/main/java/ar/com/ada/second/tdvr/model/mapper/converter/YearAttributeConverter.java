package ar.com.ada.second.tdvr.model.mapper.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Year;

@Converter(autoApply = true)
public class YearAttributeConverter implements AttributeConverter<Year, Short> {
    @Override
    public Short convertToDatabaseColumn(Year year) {
        return year != null ? (short) year.getValue() : null;
    }

    @Override
    public Year convertToEntityAttribute(Short aShort) {
        return aShort != null ? Year.of(aShort) : null;
    }
}
