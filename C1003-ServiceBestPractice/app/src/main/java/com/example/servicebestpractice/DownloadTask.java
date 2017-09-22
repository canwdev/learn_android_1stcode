package com.example.servicebestpractice;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Dictionary;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// 三个泛型参数，1.传入一个字符串参数；2.整型的进度数据显示单位；3.表示执行结果的整型数据。
public class DownloadTask extends AsyncTask<String, Integer, Integer> {
    public static final String TAG = "DownloadTask!!";

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;
    private DownloadListener listener;

    public DownloadTask(DownloadListener listener) {
        this.listener = listener;
    }

    // 后台执行的具体下载逻辑
    @Override
    protected Integer doInBackground(String... strings) {
        // 文件输入流
        InputStream in = null;
        // 要保存的文件
        RandomAccessFile savedFile = null;
        // 用于检查文件是否存在的文件对象
        File file = null;
        try {
            long downloadLength = 0;
            // 获取下载地址、文件名、存放目录
            String downloadUrl = strings[0];
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            String directory = Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory+fileName);
            if (file.exists()) {
                // 如果文件已存在，则获取其长度
                downloadLength = file.length();
            }
            // 获取要下载的文件的长度
            long contentLength = getContentLength(downloadUrl);
            Log.d(TAG, "doInBackground: contentLength="+contentLength);
            if (contentLength == 0) {
                return TYPE_FAILED;
            } else if (contentLength == downloadLength) {
                return TYPE_SUCCESS;
            }
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    // 断点下载，指定从哪个字节下载
                    .addHeader("RANGE", "bytes=" + downloadLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                in = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                // 跳过已下载的字节
                savedFile.seek(downloadLength);
                // 字节
                byte[] b = new byte[1024];
                // 已下载总量
                int total = 0;
                // 下载读取的长度
                int len;
                while ((len = in.read(b)) != -1) {
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        total += len;
                        savedFile.write(b, 0, len);
                        // 计算下载的百分比
                        int progress = (int) ((total + downloadLength) * 100 / contentLength);
                        Log.d(TAG, "doInBackground: len="+len);
                        Log.d(TAG, "doInBackground: total="+total);
                        Log.d(TAG, "doInBackground: progress="+progress+"%");
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (savedFile != null) {
                    savedFile.close();
                }
                if (isCanceled && file != null) {
                    file.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    // 在界面上更新下载进度
    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress) {
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    // 通知最终的下载结果
    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer) {
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default:
                break;
        }
    }

    public void pauseDownload() {
        isPaused = true;
    }

    public void cancelDownload() {
        isCanceled = true;
    }


    // 获取要下载的文件的长度
    private long getContentLength(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }
}
