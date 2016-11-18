package net.gringrid.siso;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * 연락처 요청
 * 요청 수락
 * 요청 거절
 * 요청 취소
 * 요청 삭제
 */
public class PopupContact extends Popup implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
//        setContentView(R.layout.popup_contact);
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.popup_contact, null);
        setPopupContent(view);
        setButton(BTN_ORDER_FIRST, BTN_STYLE_WHITE, R.string.btn_cancel, this);
        setButton(BTN_ORDER_SECOND, BTN_STYLE_GREEN, R.string.btn_confirm, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_tv_btn_first:
                break;
            case R.id.id_tv_btn_second:
                break;
        }
    }
}
