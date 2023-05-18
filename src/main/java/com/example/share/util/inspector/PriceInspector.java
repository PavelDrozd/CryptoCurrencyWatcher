package com.example.share.util.inspector;

import com.example.data.dto.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@Log4j2
public class PriceInspector {

    private final Map<UserDto, Double> cryptoCurrencyPricePool;

    PriceInspector() {
        cryptoCurrencyPricePool = new HashMap<>();
    }

    public void inspectPrices(List<UserDto> users) {
        for (UserDto user : users) {
            Double oldPrice = user.getPriceAtRegister();
            Double actualPrice = user.getCryptocurrency().getPriceUSD();
            if (comparePreviousAndActualPrice(user, actualPrice)) continue;
            double difference = (oldPrice - actualPrice) / actualPrice;
            double percentDifference = -difference * 100;
            if (difference < -0.01 || difference > 0.01) {
                writeWarnLog(user, oldPrice, actualPrice, percentDifference);
            }
        }
    }

    private static void writeWarnLog(UserDto user, Double oldPrice, Double actualPrice, double percentDifference) {
        String warnMessage = String.format(
                "Price for %s has changed by %.2f%% for user %s since %s: %.2f -> %.2f",
                user.getCryptocurrency().getSymbol(), percentDifference, user.getUsername(), user.getRegisterDate(),
                oldPrice, actualPrice);
        log.warn(warnMessage);
    }

    private boolean comparePreviousAndActualPrice(UserDto user, Double actualPrice) {
        if (cryptoCurrencyPricePool.containsKey(user)) {
            Double previousPrice = cryptoCurrencyPricePool.get(user);
            return Objects.equals(previousPrice, actualPrice);
        }
        cryptoCurrencyPricePool.put(user, actualPrice);
        return false;
    }
}
