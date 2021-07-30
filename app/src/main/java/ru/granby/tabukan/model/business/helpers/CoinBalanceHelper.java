package ru.granby.tabukan.model.business.helpers;

import io.reactivex.rxjava3.core.SingleSource;
import ru.granby.tabukan.exception.IncorrectCardIndexException;
import ru.granby.tabukan.exception.NotEnoughBalanceException;

public class CoinBalanceHelper {
    public static int withdraw(int balance, int amountToWithdraw) {
        balance -= amountToWithdraw;

        if (balance < 0) {
            throw new NotEnoughBalanceException("balance after withdraw can't be less than 0");
        }

        return balance;
    }


    public static int deposit(int coinBalance, int levelReward) {
        return coinBalance + levelReward;
    }
}
