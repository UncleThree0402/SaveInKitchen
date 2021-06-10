package fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.unclethree.saveinkitchen.R;
import formatters.DateFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaryHistoryFragment extends Fragment implements View.OnClickListener, MaterialPickerOnPositiveButtonClickListener {
    private static final String TAG = "EatFoodFragment";

    //Ui
    private TextView mDateTextView;
    private ImageView mCalendarIcon;
    private MaterialDatePicker mMaterialDatePicker;

    //Var
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_page_fragment, container, false);

        mDateTextView = view.findViewById(R.id.eat_page_title_date);
        mCalendarIcon = view.findViewById(R.id.eat_page_title_calendar_icon);

        mCalendarIcon.setOnClickListener(this);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT A DATE");
        mMaterialDatePicker = builder.build();
        mMaterialDatePicker.addOnPositiveButtonClickListener(this);

        setFrameOut(dayTime(System.currentTimeMillis()));

        Log.d(TAG, "onCreateView: " + dayTime(System.currentTimeMillis()));

        return view;
    }

    private void setFrameOut(long date){
        Bundle bundle = new Bundle();
        bundle.putLong("dateTime",date);
        DiaryHistoryContentFragment diaryHistoryContentFragment = new DiaryHistoryContentFragment();
        diaryHistoryContentFragment.setArguments(bundle);
        fragmentManager = getParentFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.eat_page_frame_layout, diaryHistoryContentFragment);
        fragmentTransaction.commit();
    }

    private long dayTime(long time) {

        long setTime;
        Date currentDate;
        long nextOneDaySecond = 86400;

        setTime = time;
        currentDate = new Date(setTime);
        long currentSecond = time;

        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
        String minuteString = minuteFormat.format(currentDate);
        long minuteLong = Long.parseLong(minuteString);

        SimpleDateFormat secondFormat = new SimpleDateFormat("ss");
        String secondString = secondFormat.format(currentDate);
        long secondLong = Long.parseLong(secondString);

        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        String hourString = hourFormat.format(currentDate);
        long hourLong = Long.parseLong(hourString);

        nextOneDaySecond += ((currentSecond / 1000) - (minuteLong * 60) - (hourLong * 3600) - secondLong);
        return nextOneDaySecond * 1000;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eat_page_title_calendar_icon:
                mMaterialDatePicker.show(getChildFragmentManager(), "Date Picker");
                break;
        }
    }

    @Override
    public void onPositiveButtonClick(Object selection) {
        try {
            if(dayTime(DateFormatter.objectToLong(selection)) == dayTime(System.currentTimeMillis())){
                mDateTextView.setText("Today");
            }else {
                mDateTextView.setText(DateFormatter.objectToString(selection));
            }
            setFrameOut(dayTime(DateFormatter.objectToLong(selection)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
