package com.adp.coindispenser.controller;

import com.adp.coindispenser.model.CoinCountUpdateRequest;
import com.adp.coindispenser.services.CoinInventoryService;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.adp.coindispenser.config.ApplicationConstants.versionHeader;


@RestController
@RequestMapping(value = "/coins")
@RequiredArgsConstructor
@Tag(name = "Admin API", description = "Contains admin APIs.")
public class AdminController {

    private final CoinInventoryService inventoryService;

    @PutMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(description = "Allows admin to update existing inventory of coins.")
    public ResponseEntity<String> updateCoins(
            @Parameter(in = ParameterIn.HEADER, schema = @Schema(allowableValues = {"v1"}), required = true)
            @RequestHeader(versionHeader) String version,
            @RequestBody List<CoinCountUpdateRequest> coinCountUpdateRequests
    ) {
        return inventoryService.updateCoinCounts(coinCountUpdateRequests) ?
                ResponseEntity.ok("Operation completed") : ResponseEntity.internalServerError().body("Unable to process the request");
    }
}
