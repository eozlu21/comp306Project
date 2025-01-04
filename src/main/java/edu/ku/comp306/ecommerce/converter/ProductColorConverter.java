package edu.ku.comp306.ecommerce.converter;

import edu.ku.comp306.ecommerce.enums.ProductColor;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProductColorConverter implements AttributeConverter<ProductColor, String> {

    @Override
    public String convertToDatabaseColumn(ProductColor attribute) {
        return attribute == null ? null : attribute.name().replace("_", " ");
    }

    @Override
    public ProductColor convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            // Normalize database value and map to enum
            return ProductColor.valueOf(dbData.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value for ProductColor: " + dbData, e);
        }
    }
}