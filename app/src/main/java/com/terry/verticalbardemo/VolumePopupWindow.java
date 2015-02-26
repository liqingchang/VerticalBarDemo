package com.terry.verticalbardemo;

import android.app.ActionBar;
import android.content.Context;
import android.media.AudioManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import com.terry.verticalbardemo.widget.VerticalSeekBar;


/**
 * 声音调整poup window
 * Created by terry on 15-2-11.
 */
public class VolumePopupWindow extends PopupWindow {

    private View view;
    private VerticalSeekBar seekBar;
    private AudioManager audioManager;

    /**
     * 因为本身系统的声音数值的最高值相对较小
     * 为了提高seekbar拖动对声音改变的精度
     * 所以对seekbar的最高值 当前值都乘以一个倍率
     * 对seekbar改变的progress除以一个倍率
     */
    private static final int MULTIPLYING_POWER = 2;

    public VolumePopupWindow(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.dialog_volume , null);
        seekBar = (VerticalSeekBar) view.findViewById(R.id.volumebar);
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        seekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * MULTIPLYING_POWER);
        seekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * MULTIPLYING_POWER);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress / MULTIPLYING_POWER , 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        setOutsideTouchable(true);
        setContentView(view);
        setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        setFocusable(false);
    }

    /**
     * 音量上升
     */
    public void seekUp() {
        if(seekBar.getProgress() < seekBar.getMax()) {
            seekBar.setProgress(seekBar.getProgress() + 1);
            seekBar.onSizeChanged();
        }
    }

    /**
     * 音量下降
     */
    public void seekDown(){
        if(seekBar.getProgress() > 0) {
            seekBar.setProgress(seekBar.getProgress() - 1);
            seekBar.onSizeChanged();
        }
    }

}
