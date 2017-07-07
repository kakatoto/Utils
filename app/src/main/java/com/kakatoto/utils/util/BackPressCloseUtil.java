package com.kakatoto.utils.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

/**
 *
 *
 * 백버튼 2번 연속으로 클릭하면 앱 종료를 한다.
 *
 *    0. 원하는 Activity 에 아래의 순서대로 적용한다.
 *    1. private BackPressCloseUtil backClose; 멤버변수 선언
 *    2. onCreate() 메소드에서 backClose = new BackPressCloseUtil(this); 호출
 *
 *    3. onBackPressed() 메소드에 backClose.onBackPressed(); 추가
 *      public void onBackPressed() {
 *          backClose.onBackPressed();
 *      }
 */
public class BackPressCloseUtil {

    private long backKeyPressedTime = 0;
    private Toast toast;
    @SuppressWarnings("unused")
	private AlertDialog alert;

    private Activity mActivity;

    public BackPressCloseUtil(Activity activity) {
        this.mActivity = activity;
    }

    public void onBackPressed() {

        if (isAfter2Seconds()) {
             // 현재시간을 다시 초기화
            backKeyPressedTime = System.currentTimeMillis();

            toast = Toast.makeText(mActivity,"\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            
            return;
        }

        if (isBefore2Seconds()) {
            if(Build.VERSION.SDK_INT >= 16) {
            	Log.d("BackPressCloseUtil", "[activity] : " + mActivity);
                ActivityCompat.finishAffinity(mActivity);
                
                // LYS 테스트(종료 TASK, 생명주기 테스트)
//            	alert = new AlertDialog.Builder(mActivity)
//				.setIconAttribute(android.R.attr.alertDialogIcon)
//	            .setTitle(R.string.alert_title)
//	            .setMessage("정말 종료하시겠습니까?")
//	            .setPositiveButton(R.string.confirm_button, new DialogInterface.OnClickListener() {
//	            	public void onClick(DialogInterface dialog, int whichButton) {
//	            		dialog.dismiss();
//	                    programExit();
//	            		kill();
//            		}
//	            })
//	            .setNegativeButton(R.string.cancle_button, new DialogInterface.OnClickListener() {
//	            	public void onClick(DialogInterface dialog, int whichButton) {
//	                 	dialog.dismiss();
//	            	}
//    			})
//	            .show();
                
            }else{
            	Log.d("BackPressCloseUtil", "[sdk] : " + Build.VERSION.SDK_INT);
                programExit();
            }
            toast.cancel();
        }
    }
    
	private Boolean isAfter2Seconds() {
        // 3초 지났을 경우
        return System.currentTimeMillis() > backKeyPressedTime + 3000;
    }

    private Boolean isBefore2Seconds() {
        // 3초가 지나지 않았을 경우
        return System.currentTimeMillis() <= backKeyPressedTime + 3000;
    }

    private void programExit() {
        mActivity.moveTaskToBack(true);
        mActivity.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}