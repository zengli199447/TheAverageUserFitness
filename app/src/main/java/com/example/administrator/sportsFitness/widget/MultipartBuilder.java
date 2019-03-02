package com.example.administrator.sportsFitness.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import com.example.administrator.sportsFitness.R;
import com.example.administrator.sportsFitness.global.DataClass;
import com.example.administrator.sportsFitness.global.MyApplication;
import com.example.administrator.sportsFitness.model.bean.UpLoadFileNetBean;
import com.example.administrator.sportsFitness.model.bean.UpLoadVideoFileNetBean;
import com.example.administrator.sportsFitness.model.event.EventCode;
import com.example.administrator.sportsFitness.ui.dialog.ShowDialog;
import com.example.administrator.sportsFitness.utils.LogUtil;
import com.example.administrator.sportsFitness.utils.SystemUtil;
import com.example.administrator.sportsFitness.utils.ToastUtil;
import com.google.gson.Gson;
import com.yanzhenjie.album.AlbumFile;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者：真理 Created by Administrator on 2018/11/20.
 * 邮箱：229017464@qq.com
 * remark:
 */
public class MultipartBuilder {

    String TAG = getClass().getSimpleName();

    Context context;
    private HashMap<String, String> textParams;
    private HashMap<String, File> fileparams;
    int flags;
    private int size = 0;
    private List<String> stringList = new ArrayList<>();
    public static ArrayList<AlbumFile> AlbumFileList;
    String fileType = "image";
    String Heart = DataClass.IMAGE_SAVE_SET;

    public MultipartBuilder(Context context, int flags) {
        this.context = context;
        this.flags = flags;
    }

    /**
     * @param context
     * @param flags   0.单图、1.多图
     */
    public MultipartBuilder(Context context, int flags, ArrayList<AlbumFile> AlbumFileList) {
        this.context = context;
        this.flags = flags;
        this.AlbumFileList = AlbumFileList;
    }

    public MultipartBuilder(Context context, int flags, String fileType, String Heart) {
        this.context = context;
        this.flags = flags;
        this.fileType = fileType;
        this.Heart = Heart;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            size = size + 1;
            if (size < AlbumFileList.size()) {
                commitFile(AlbumFileList.get(size).getPath());
            }
        }
    };

    public void arrangementUpLoad() {
        size = 0;
        stringList.clear();
        commitFile(AlbumFileList.get(size).getPath());
    }

    public void commitFile(final String file) {

        MyApplication.executorService.submit(new Runnable() {
            @Override
            public void run() {
                if (file.contains("upload/")) {
                    stringList.add(file);
                    if (stringList.size() == AlbumFileList.size()) {
                        if (upLoadFileListener != null)
                            upLoadFileListener.onUpLoadFileListener(LineFormatNetData(), stringList);
                    } else {
                        handler.sendEmptyMessage(0);
                    }
                } else {
                    try {
                        URL url = new URL(DataClass.File_URL);
                        textParams = new HashMap<>();
                        fileparams = new HashMap<>();

                        File oldFileUrl = new File(file);
                        File fileUrl = null;
                        if ("image".equals(fileType) && SystemUtil.getFileSize(oldFileUrl) > 2 * 1024 * 1024) {
                            fileUrl = CompressHelperBuilder.CompressHelperFile(context, oldFileUrl, oldFileUrl.getName());
                        } else {
                            fileUrl = oldFileUrl;
                        }

                        if (!SystemUtil.JudgeNetFilePathType(file))
                            if (file.contains(".gif")) {
                                fileType = "file";
                                Heart = DataClass.FILE_SAVE_SET;
                            } else {
                                fileType = "image";
                                Heart = DataClass.IMAGE_SAVE_SET;
                            }

                        fileparams.put(fileType, fileUrl);

                        LinkedHashMap linkedHashMap = new LinkedHashMap();
                        linkedHashMap.put("action", Heart);
                        String toJson = new Gson().toJson(linkedHashMap);
                        textParams.put("version", "v1");
                        textParams.put("vars", toJson);

                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                        httpURLConnection.setConnectTimeout(5000);

                        httpURLConnection.setDoOutput(true);

                        httpURLConnection.setRequestMethod("POST");

                        httpURLConnection.setUseCaches(false);

                        httpURLConnection.setRequestProperty("ser-Agent", "Fiddler");

                        httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + DataClass.BOUNDARY);

                        OutputStream outputStream = httpURLConnection.getOutputStream();

                        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                        writeStringParams(textParams, dataOutputStream);

                        writeFileParams(fileparams, dataOutputStream);

                        paramsEnd(dataOutputStream);

                        outputStream.close();

                        int responseCode = httpURLConnection.getResponseCode();
                        LogUtil.e(TAG, "");
                        if (responseCode == 200) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String result = new String(readBytes(inputStream));
                            switch (flags) {
                                case 0:
                                    if (upLoadFileListener != null)
                                        upLoadFileListener.onUpLoadFileListener(GsonFormatNetData(result), null);
                                    break;
                                case 1:
                                    stringList.add(GsonFormatNetData(result));
                                    if (stringList.size() == AlbumFileList.size()) {
                                        if (upLoadFileListener != null)
                                            upLoadFileListener.onUpLoadFileListener(LineFormatNetData(), stringList);
                                    } else {
                                        handler.sendEmptyMessage(0);
                                    }
                                    break;
                            }
                        } else {
                            LogUtil.e(TAG, "Exception - responseCode : " + responseCode);
                            if (upLoadFileListener != null)
                                upLoadFileListener.onUpLoadFileErrorListener();
                        }
                    } catch (final Exception e) {
                        e.printStackTrace();
                        if (upLoadFileListener != null)
                            upLoadFileListener.onUpLoadFileErrorListener();
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ShowDialog.getInstance().showHelpfulHintsDialog(context, context.getString(R.string.up_laod_prompt), EventCode.ONSTART);
                                LogUtil.e(TAG, "Exception : " + e.toString());
                            }
                        });
                    }
                }
            }
        });

    }

    private String GsonFormatNetData(String result) {
        String url;
        if ("image".equals(fileType)) {
            UpLoadFileNetBean upLoadFileNetBean = new Gson().fromJson(result, UpLoadFileNetBean.class);
            if (upLoadFileNetBean.getStatus() == 1) {
                url = upLoadFileNetBean.getSrc();
            } else {
                url = null;
            }
        } else {
            UpLoadVideoFileNetBean upLoadFileNetBean = new Gson().fromJson(result, UpLoadVideoFileNetBean.class);
            if (upLoadFileNetBean.getStatus() == 1) {
                url = upLoadFileNetBean.getSrc();
            } else {
                url = null;
            }
        }
        return url;
    }

    private String LineFormatNetData() {
        String fileList = "";
        String theComma = "";
        for (int i = 0; i < stringList.size(); i++) {
            if (i > 0) {
                theComma = ",";
            }
            fileList = new StringBuffer().append(fileList).append(theComma).append(stringList.get(i)).toString();
        }
        return fileList;
    }


    public BitmapFactory.Options getBitmapOptions(String srcPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, options);
        return options;
    }

    public void writeStringParams(Map<String, String> textParams, DataOutputStream dataOutputStream) throws Exception {
        Set<String> keySet = textParams.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
            String name = it.next();
            String value = textParams.get(name);
            dataOutputStream.writeBytes("--" + DataClass.BOUNDARY + "\r\n");
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"\r\n");
            dataOutputStream.writeBytes("\r\n");
            value = value + "\r\n";
            dataOutputStream.write(value.getBytes());
        }
    }

    public void writeFileParams(Map<String, File> fileparams, DataOutputStream dataOutputStream) throws Exception {
        Set<String> keySet = fileparams.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
            String name = it.next();
            File value = fileparams.get(name);
            dataOutputStream.writeBytes("--" + DataClass.BOUNDARY + "\r\n");
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\""
                    + URLEncoder.encode(value.getName(), "UTF-8") + "\"\r\n");
            dataOutputStream.writeBytes("Content-Type:application/octet-stream \r\n");
            dataOutputStream.writeBytes("\r\n");
            dataOutputStream.write(getBytes(value));
            dataOutputStream.writeBytes("\r\n");
        }
    }

    private byte[] getBytes(File file) throws Exception {
        FileInputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, n);
        }
        inputStream.close();
        return outputStream.toByteArray();
    }

    public void paramsEnd(DataOutputStream ds) throws Exception {
        ds.writeBytes("--" + DataClass.BOUNDARY + "--" + "\r\n");
        ds.writeBytes("\r\n");
    }

    public byte[] readBytes(InputStream is) {
        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface UpLoadFileListener {
        void onUpLoadFileListener(String url, List<String> list);

        void onUpLoadFileErrorListener();
    }

    private UpLoadFileListener upLoadFileListener;

    public void setUpLoadFileListener(UpLoadFileListener upLoadFileListener) {
        this.upLoadFileListener = upLoadFileListener;
    }

}
