package lotto.controller

import lotto.models.*
import lotto.views.InputView
import lotto.views.OutputView

class LottoGameController() {
    private val inputView: InputView = InputView()
    private val outputView: OutputView = OutputView()

    fun start() {
        val publisher = Publisher()
        val winningRecord = WinningRecord()
        val profitRate = ProfitRate()

        val purchase = inputPurchaseAmount()

        val purchasedLottos = publisher.publishLottos(purchase)
        outputView.printPurchasedLottos(purchasedLottos)
        outputView.endOutput()

        val winningLotto = inputWinningLottoNumbers()
        val bonus = inputBonusNumber(winningLotto)

        winningRecord.updateWinningResults(purchasedLottos, winningLotto, bonus)
        outputView.printWinningStatics(winningRecord)

        profitRate.calculate(purchase, winningRecord)
        outputView.printProfitRate(profitRate)
    }

    private fun inputPurchaseAmount(): Purchase {
        var purchase: Purchase? = null

        while (purchase == null) {
            try {
                val amount = inputView.inputPurchaseAmount()
                purchase = Purchase(amount)
                inputView.endInput()
            } catch (e: IllegalArgumentException) {
                outputView.printErrorMessage(e.message)
            }
        }

        return purchase
    }

    private fun inputWinningLottoNumbers(): Lotto {
        var lotto: Lotto? = null

        while (lotto == null) {
            try {
                val numbers = inputView.inputWinningNumbers()
                lotto = Lotto(numbers)
                inputView.endInput()
            } catch (e: IllegalArgumentException) {
                outputView.printErrorMessage(e.message)
            }
        }

        return lotto
    }

    private fun inputBonusNumber(winningLotto: Lotto): Bonus {
        var bonus: Bonus? = null

        while (bonus == null) {
            try {
                val number = inputView.inputBonusNumber()
                bonus = Bonus(number)
                bonus.checkDistinctWithWinningLotto(winningLotto.getNumbers())
                inputView.endInput()
            } catch (e: IllegalArgumentException) {
                outputView.printErrorMessage(e.message)
            }
        }

        return bonus
    }
}