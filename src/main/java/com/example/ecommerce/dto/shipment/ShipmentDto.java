package com.example.ecommerce.dto.shipment;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(example = "{\n" + "  \"shipment\": [\n" + "    {\n" + "      \"payment\": \"string\"\n" + "    }\n" + "  ]\n" + "}")
public record ShipmentDto(List<PaymentDto> dto) {
}
