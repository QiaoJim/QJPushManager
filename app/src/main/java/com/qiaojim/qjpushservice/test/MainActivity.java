package com.qiaojim.qjpushservice.test;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.api.HuaweiApiClient;
import com.qiaojim.qjpushservice.QJPushReceiver;
import com.qiaojim.qjpushservice.baidu.BaiduPushReceiver;
import com.qiaojim.qjpushservice.entity.QJMessage;
import com.qiaojim.qjpushservice.huawei.HuaweiListenerImpl;
import com.qiaojim.qjpushservice.entity.QJConstant;
import com.qiaojim.qjpushservice.QJPushListener;
import com.qiaojim.qjpushservice.QJPushManager;
import com.qiaojim.qjpushservice.R;
import com.qiaojim.qjpushservice.huawei.HuaweiPushReceiver;
import com.qiaojim.qjpushservice.mi.MiPushReceiver;
import com.qiaojim.qjpushservice.utils.QJBroadcastUtil;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements QJPushListener.QJHuaweiListener, QJPushListener {

    private Button start;
    private TextView imei;
    private TextView rom;
    private HuaweiListenerImpl impl;

    private QJPushReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                impl = new HuaweiListenerImpl();
                // 设置 QJPushListener.QJHuaweiListener 实现
                impl.setQJListener(MainActivity.this);

                /*
                 *  context - 建议传入getApplicationContext()
                 *  flag - true为自动设置手机 IMEI 号为用户标签
                 */
                QJPushManager.prepare(getApplicationContext(), true);
                /*
                 *  appId - 申请的小米push的 app id
                 *  appKey - 申请的小米push的 app key
                 */
                QJPushManager.prepareMi("2882303761517585298", "5531758546298");
                /*
                 *  appKey - 申请的百度push的 api key
                 */
                QJPushManager.prepareBaidu("dtHgWqOiGR1EqoWHF7rlSCwy");
                /*
                 *  listener - HuaweiListenerImpl 实例
                 */
                QJPushManager.prepareHuawei(impl);

                // 开启推送
                QJPushManager.startPushService();

                imei.setText("IMEI: " + QJPushManager.getIMEI());
                rom.setText("Type: " + QJPushManager.getRomType());

                Log.e("MainActivity", "======== imei ==========\n" + QJPushManager.getIMEI());

            }
        });
        imei = (TextView) findViewById(R.id.imei);
        rom = (TextView) findViewById(R.id.rom);

    }


    @Override
    protected void onResume() {
        super.onResume();

        receiver = new QJPushReceiver();
        /*
         * 分别传入 Context, QJPushReceiver的实例, QJPushListener的实现类
         */
        QJBroadcastUtil.regQJReceiver(this, receiver, this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 实际一般不使用stopPushService
        // 因为一般只要用户打开一次app，就要能一直接受推送消息
        // QJPushManager.stopPushService();

        QJBroadcastUtil.unregQJReceiver(this, receiver);
    }


    @Override
    public void onUpdateFailed(@NonNull ConnectionResult connectionResult) {
        Log.w("QJHuaweiListener", "onUpdateFailed, " + connectionResult.getErrorCode() + "");
    }

    @Override
    public void onConnected() {
        Log.i("QJHuaweiListener", "onConnected, IsConnected: " + QJPushManager.getHuaweiClient().isConnected());
        // 已连接，获取华为push唯一标识token
        if (QJPushManager.getHuaweiClient().isConnected()) {
            QJPushManager.getHuaweiToken();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("QJHuaweiListener", "onConnectionSuspended, cause: " + i + ", IsConnected: " + QJPushManager.getHuaweiClient().isConnected());

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w("QJHuaweiListener", "onConnectionFailed, " + connectionResult.getErrorCode() + "");

        // 如果连接错误，判断是否可处理
        final int errorCode = connectionResult.getErrorCode();
        final HuaweiApiAvailability availability = HuaweiApiAvailability.getInstance();
        if (availability.isUserResolvableError(errorCode)) {
            // 用户端解决错误
            availability.resolveError(this, errorCode, QJConstant.REQUEST_RESOLVE_HUAWEI_ERROR, impl);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == QJConstant.REQUEST_RESOLVE_HUAWEI_ERROR) {
            final int errorCode = HuaweiApiAvailability.getInstance().isHuaweiMobileServicesAvailable(this);
            // 错误处理成功
            if (errorCode == ConnectionResult.SUCCESS) {
                // 获取华为client，并尝试连接
                HuaweiApiClient client = QJPushManager.getHuaweiClient();
                if (!client.isConnecting() && !client.isConnected()) {
                    client.connect();
                }
            } else {
                // TODO: 处理errorCode
            }
        }
    }

    @Override
    public void onMsgArrived(QJMessage msg) {
        String msgType = msg.getMsgType();
        if (msgType.equals(QJConstant.TYPE_PASS_THROUGH)) {
            Map<String, String> extra = (Map<String, String>) msg.getExtra();
        }
    }

    @Override
    public void onNotificationClicked(QJMessage msg) {
    }

    @Override
    public void onError() {
    }
}
