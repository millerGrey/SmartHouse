package grey.smarthouse.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import grey.smarthouse.services.NetService;
import grey.smarthouse.R;
import java.util.ArrayList;
import java.util.List;




/**
 * Created by GREY on 26.05.2018.
 */

public class MainFragment extends refreshFragment {
    public static final String ARG_PAGE = "ARG PAGE";
    List<String> mTemp;

    TextView mTempText1;
    TextView mTempText2;
    TextView mTempText3;
    ProgressBar mProgress;

    private boolean getRequest = false;

    private int mPage;
    private int cnt=0;
    public static MainFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startProcess(5000);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mTempText1 = (TextView) view.findViewById(R.id.sensorTempText1);
        mTempText2 = (TextView) view.findViewById(R.id.sensorTempText2);
        mTempText3 = (TextView) view.findViewById(R.id.sensorTempText3);
        mProgress = (ProgressBar) view.findViewById(R.id.progress);
        init();
        return view;
    }

    public void init()
    {
        mTemp = new ArrayList<String>();

        for(int i=0;i<3;i++){
            mTemp.add(i,"0");
        }
        mTempText1.setText("0 °С");
        mTempText2.setText("0 °С");
        mTempText3.setText("0 °С");
        mTempText1.setVisibility(View.INVISIBLE);
        mTempText2.setVisibility(View.INVISIBLE);
        mTempText3.setVisibility(View.INVISIBLE);

    }
    @Override
    public void handleTickEvent(){
        Log.d("RX", "handletick");
        mTemp = NetService.getTemp();
        if(!getRequest)
        {
//            getRequest = false;
            mTempText1.setVisibility(View.VISIBLE);
            mTempText2.setVisibility(View.VISIBLE);
            mTempText3.setVisibility(View.VISIBLE);
            try {

                mTempText1.setText(mTemp.get(0) + " °С");
                mTempText2.setText(mTemp.get(1) + " °С");
                mTempText3.setText(mTemp.get(2) + " °С");

            }catch(Exception e){
                e.printStackTrace();
            }
            mProgress.setVisibility(View.INVISIBLE);
        }
        else{
            mTempText1.setVisibility(View.INVISIBLE);
            mTempText2.setVisibility(View.INVISIBLE);
            mTempText3.setVisibility(View.INVISIBLE);
            if(cnt>2)
            {
//                Toast.makeText(getContext(),"Проверьте соединение",Toast.LENGTH_SHORT).show();
                cnt=0;
            }

        }
        cnt++;
    }
}
