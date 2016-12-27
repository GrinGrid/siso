package net.gringrid.siso.adapter;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.gringrid.siso.R;
import net.gringrid.siso.models.Contact;
import net.gringrid.siso.models.SitterListItem;
import net.gringrid.siso.network.restapi.APIError;
import net.gringrid.siso.network.restapi.ContactAPI;
import net.gringrid.siso.network.restapi.ErrorUtils;
import net.gringrid.siso.network.restapi.FavoriteAPI;
import net.gringrid.siso.network.restapi.ServiceGenerator;
import net.gringrid.siso.util.SisoUtil;
import net.gringrid.siso.views.SisoSitterListCommon;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by choijiho on 16. 10. 4..
 */
public class SitterListSendAdapter extends BaseAdapter{


    private static final String TAG = "jiho";

    private LayoutInflater mInflater = null;

    private int mImageWidth;
    Context mContext;
    List<SitterListItem> mList;
    int mPhotoWidth;
    FrameLayout.LayoutParams mLp;
    private String mUserEmail;
    private Activity mActivity;

    public SitterListSendAdapter(Context mContext, Activity activity, String userEmail, List<SitterListItem> list) {
        this.mContext = mContext;
        this.mList = list;
        mInflater = LayoutInflater.from(mContext);
        mUserEmail = userEmail;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        SitterListItem item;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.adapter_sitter_list_send_row, null);
            holder = new ViewHolder();
            holder.id_sslc = (SisoSitterListCommon) convertView.findViewById(R.id.id_sslc);
            holder.id_ll_accepted = (LinearLayout)convertView.findViewById(R.id.id_ll_accepted);
            holder.id_ll_other = (LinearLayout)convertView.findViewById(R.id.id_ll_other);
            holder.id_tv_other = (TextView)convertView.findViewById(R.id.id_tv_other);
            holder.id_tv_other_btn = (TextView)convertView.findViewById(R.id.id_tv_other_btn);
            holder.id_tv_other_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv = (TextView)v;
                    if(tv.getText().equals(mActivity.getString(R.string.btn_cancel))){
                        doExecute(tv, Contact.CONTACT_ACTION_CANCEL);
                    }else if(tv.getText().equals(mActivity.getString(R.string.btn_delete))){
                        doExecute(tv, Contact.CONTACT_ACTION_DELETE);
                    }
                }
            });

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        item = mList.get(position);
        holder.id_sslc.setData(item);

        holder.id_ll_accepted.setVisibility(View.GONE);
        holder.id_ll_other.setVisibility(View.GONE);
        // 연락처요청상태에 따라 하단 View control
        if(!TextUtils.isEmpty(item.contact_status)){
            if(item.contact_status.equals(Contact.CONTACT_STATUS_REQUEST)){
                holder.id_ll_other.setVisibility(View.VISIBLE);
                holder.id_tv_other.setText(R.string.sitter_list_accepting);
                holder.id_tv_other_btn.setText(R.string.btn_cancel);
                holder.id_tv_other_btn.setTag(item.email);
            }else if(item.contact_status.equals(Contact.CONTACT_STATUS_REJECT)){
                holder.id_tv_other.setText(R.string.sitter_list_reject);
                holder.id_tv_other_btn.setText(R.string.btn_delete);
                holder.id_tv_other_btn.setTag(item.email);
            }else if(item.contact_status.equals(Contact.CONTACT_STATUS_ACCEPT)){
                holder.id_ll_accepted.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }

    private void doExecute(TextView tv, String action) {
        Log.d(TAG, "doExecute: sitter email : "+(String)tv.getTag());

        ContactAPI api = ServiceGenerator.getInstance(mActivity).createService(ContactAPI.class);
        Contact contact = new Contact();
        contact.req_email = mUserEmail;
        contact.rcv_email = (String)tv.getTag();

        Call<Contact> call = null;
        if(action.equals(Contact.CONTACT_ACTION_CANCEL)){
            call = api.cancel(contact);
        }else if(action.equals(Contact.CONTACT_ACTION_DELETE)){
            call = api.delete(contact);
        }

        if(call == null) return;

        call.enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: contact : "+response.body().toString());
                    SisoUtil.showMsg(mContext, "관심시터로 등록 되었습니다");
                }else{
                    APIError error = ErrorUtils.parseError(response);
                    String msgCode = error.msgCode();
                    String msgText = error.msgText();
                    Toast.makeText(mContext, msgText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contact> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    class ViewHolder{
        SisoSitterListCommon id_sslc;
        LinearLayout id_ll_accepted;
        LinearLayout id_ll_other;
        TextView id_tv_other;
        TextView id_tv_other_btn;
    }
}
