package utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

import com.example.boxuegu.R;

/**
 * Created by 狒狒 on 2020/7/7.
 */
public class CountDownTimerUtils extends CountDownTimer {
    private Button sendcode;
    private String tickText;
    private String finishText;


    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     * @param sendcode
     */
    public CountDownTimerUtils(long millisInFuture, long countDownInterval,
                               CountDownButton sendcode, String tickText, String finishText) {
        super(millisInFuture, countDownInterval);
        this.sendcode = sendcode;
        this.tickText=tickText;
        this.finishText=finishText;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        sendcode.setText(millisUntilFinished/1000+tickText);
        sendcode.setEnabled(false);


    }

    @Override
    public void onFinish() {
        sendcode.setEnabled(true);
        sendcode.setText(finishText);
    }
}
