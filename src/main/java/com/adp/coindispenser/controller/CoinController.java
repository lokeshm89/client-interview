package com.adp.coindispenser.controller;

/**
 * @author Lokesh Venktesan
 */

import com.adp.coindispenser.exception.UnSupportedApiVersion;
import com.adp.coindispenser.factories.CoinServiceFactory;
import com.adp.coindispenser.model.CoinChaneResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.adp.coindispenser.config.ApplicationConstants.versionHeader;

@RestController
@Tag(name = "Coins API", description = "Contains operational APIs.")
@RequiredArgsConstructor
public class CoinController {

    private final CoinServiceFactory coinServiceFactory;

    @GetMapping(value = "/calculate-change")
    @Operation(description = "Returns coins for any given valid bill.")
    public ResponseEntity<List<CoinChaneResponse>> getChange(
            //@Parameter(in = ParameterIn.HEADER, schema = @Schema(allowableValues = {"v1"}), required = false, description = "Denotes the version of the API")
            @RequestHeader(value = versionHeader, defaultValue = "v1") String version,
            @Parameter(in = ParameterIn.QUERY, schema = @Schema(type = "integer", allowableValues = {"1", "3", "2", "5", "10", "20", "50", "100"}), required = true,
                    description = "Denotes dollar bill for which change is required."
            )
            @RequestParam(name = "bill") Integer bill,
            @Parameter(in = ParameterIn.QUERY, schema = @Schema(type = "integer"), required = false,
                    description = "Denotes maximum number of coins that user wish to accept for this conversion."
            )
            @RequestParam(name = "maxCoins", required = false) Integer maxCoins

    ) throws UnSupportedApiVersion {
        if (maxCoins == null)
            maxCoins = Integer.MAX_VALUE;
        if (coinServiceFactory.isSupportedVersion(version))
            return ResponseEntity.ok(coinServiceFactory.getCoinServiceForVersion(version).issueChange(bill, maxCoins));
        else throw new UnSupportedApiVersion(version);
    }

}
