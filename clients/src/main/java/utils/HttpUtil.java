package utils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 模拟发送Http请求
 * @author linxz
 */
public class HttpUtil {

    /**
     * 发送post请求
     *
     * @param sendUrl        要发送请求到的http地址
     * @param params         要附带的请求的参数
     * @param headers        要设置的请求头
     * @param getDataCharset 最后获取的返回的内容的字符集，默认为utf-8
     * @return
     */
    public  String sendPost(String sendUrl, String params, Map<String, String> headers, String getDataCharset) {
        StringBuffer result = new StringBuffer();
        PrintWriter out = null;
        OutputStream os = null;
        BufferedReader br = null;
        InputStreamReader isr = null;
        InputStream in = null;
        try {
            URL url = new URL(sendUrl);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            Set<String> headerskey = headers.keySet();
            for (String headerkey : headerskey) {
                conn.setRequestProperty(headerkey, headers.get(headerkey));
            }
            os = conn.getOutputStream();
            out = new PrintWriter(os);
            out.print(params);
            out.flush();
            in = conn.getInputStream();
            if (getDataCharset == null || "".equals(getDataCharset.trim())) {
                getDataCharset = "utf-8";
            }
            isr = new InputStreamReader(in, getDataCharset);
            br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {//释放资源
            if (out != null) {
                out.close();
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

    /**
     * 发送get请求
     *
     * @param sendUrl        要发送请求到的http地址
     * @param headers        要设置的请求头
     * @param getDataCharset 最后获取的返回的内容的字符集，默认为utf-8
     * @return
     */
    public  String sendGet(String sendUrl, Map<String, String> headers, String getDataCharset) {
        StringBuffer result = new StringBuffer();
        BufferedReader br = null;
        InputStreamReader isr = null;
        InputStream in = null;
        try {
            URL url = new URL(sendUrl);
            URLConnection conn = url.openConnection();
            Set<String> headerskey = headers.keySet();
            for (String headerkey : headerskey) {
                conn.setRequestProperty(headerkey, headers.get(headerkey));
            }
            in = conn.getInputStream();
            if (getDataCharset == null || "".equals(getDataCharset.trim())) {
                getDataCharset = "utf-8";
            }
            isr = new InputStreamReader(in, getDataCharset);
            br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }

}