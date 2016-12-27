package net.gringrid.siso.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.gringrid.siso.R;
import net.gringrid.siso.RootActivity;
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
public class SitterListReceiveAdapter extends BaseAdapter implements View.OnClickListener {


    private static final String TAG = "jiho";

    private LayoutInflater mInflater = null;

    private int mImageWidth;
    Context mContext;
    List<SitterListItem> mList;
    int mPhotoWidth;
    FrameLayout.LayoutParams mLp;
    private String mUserEmail;
    private Activity mActivity;

    public SitterListReceiveAdapter(Context mContext, Activity activity, String userEmail, List<SitterListItem> list) {
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
            convertView = mInflater.inflate(R.layout.adapter_sitter_list_receive_row, null);
            holder = new ViewHolder();
            holder.id_sslc = (SisoSitterListCommon) convertView.findViewById(R.id.id_sslc);
            holder.id_ll_received = (LinearLayout)convertView.findViewById(R.id.id_ll_received);
            holder.id_ll_accepted = (LinearLayout)convertView.findViewById(R.id.id_ll_accepted);
            holder.id_ll_rejected = (LinearLayout) convertView.findViewById(R.id.id_ll_rejected);
            holder.id_tv_reject = (TextView) convertView.findViewById(R.id.id_tv_reject);
            holder.id_tv_accept = (TextView) convertView.findViewById(R.id.id_tv_accept);
            holder.id_tv_delete = (TextView) convertView.findViewById(R.id.id_tv_delete);

            holder.id_tv_reject.setOnClickListener(this);
            holder.id_tv_accept.setOnClickListener(this);
            holder.id_tv_delete.setOnClickListener(this);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        item = mList.get(position);
        holder.id_sslc.setData(item);

        holder.id_ll_received.setVisibility(View.GONE);
        holder.id_ll_accepted.setVisibility(View.GONE);
        holder.id_ll_rejected.setVisibility(View.GONE);

        // 연락처요청상태에 따라 하단 View control
        if(!TextUtils.isEmpty(item.contact_status)){
            if(item.contact_status.equals(Contact.CONTACT_STATUS_RECEIVE)){
                holder.id_ll_received.setVisibility(View.VISIBLE);
            }else if(item.contact_status.equals(Contact.CONTACT_STATUS_ACCEPT)){
                holder.id_ll_accepted.setVisibility(View.VISIBLE);
            }else if(item.contact_status.equals(Contact.CONTACT_STATUS_REJECT)){
                holder.id_ll_rejected.setVisibility(View.VISIBLE);
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

        // 모드에 따라 accept, reject, delete
        Call<Contact> call = null;
        if(action.equals(Contact.CONTACT_ACTION_ACCEPT)){
            call = api.accept(contact);
        }else if(action.equals(Contact.CONTACT_ACTION_REJECT)){
            call = api.reject(contact);
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

    @Override
    public void onClick(View v) {
        TextView tv = (TextView) v;
        if (tv.getText().equals(mActivity.getString(R.string.btn_accept))) {
            doExecute(tv, Contact.CONTACT_ACTION_ACCEPT);
        } else if (tv.getText().equals(mActivity.getString(R.string.btn_reject))) {
            doExecute(tv, Contact.CONTACT_ACTION_REJECT);
        } else if (tv.getText().equals(mActivity.getString(R.string.btn_delete))) {
            doExecute(tv, Contact.CONTACT_ACTION_DELETE);
        }
    }

    class ViewHolder{
        SisoSitterListCommon id_sslc;
        LinearLayout id_ll_received;
        LinearLayout id_ll_accepted;
        LinearLayout id_ll_rejected;
        TextView id_tv_reject;
        TextView id_tv_accept;
        TextView id_tv_delete;
    }
}
